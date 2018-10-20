package assignment2;

import java.util.*;

public class Dynamic {
    static class Entry {
        int cost;
        Activity activity;

        public Entry(int cost, Activity activity) {
            this.cost = cost;
            this.activity = activity;
        }

        public int getCost() {
            return cost;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "cost=" + cost +
                    ", activity=" + activity +
                    '}';
        }
    }

    static class State {
        int d;
        Activity lastActivity;
        int i;

        public State(int d, Activity lastActivity, int i) {
            this.d = d;
            this.lastActivity = lastActivity;
            this.i = i;
        }

        public int cost(int[] fullRebootCapacity,
                        int[] partialRebootCapacity, int[] data) {
            int capacity = lastActivity.equals(Activity.FULL_REBOOT)
                    ? getCurrentCapacity(fullRebootCapacity, i)
                    : getCurrentCapacity(partialRebootCapacity, i);

            int demand = data[d];
            int cost = Math.max(0, demand - capacity);

            return cost;
        }

        public State applyAction(Activity activity) {
            if (activity == null) {
                return new State(
                        d,
                        lastActivity, // persist the lastActivity
                        i
                );
            } else if (activity.equals(Activity.FULL_REBOOT)) {
                return new State(
                        d,
                        Activity.FULL_REBOOT, // change the lastActivity...
                        0 // ...and reset i
                );
            } else {
                return new State(
                        d,
                        Activity.PARTIAL_REBOOT, // change the lastActivity...
                        0 // ... and reset i
                );
            }
        }

        public State incrementDay() {
            return new State(
                    d + 1,
                    lastActivity,
                    i + 1
            );
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            State state = (State) o;

            if (d != state.d) return false;
            if (i != state.i) return false;
            return lastActivity == state.lastActivity;
        }

        @Override
        public int hashCode() {
            int result = d;
            result = 31 * result + lastActivity.hashCode();
            result = 31 * result + i;
            return result;
        }

        @Override
        public String toString() {
            return "State{" +
                    "d=" + d +
                    ", lastActivity=" + lastActivity +
                    ", i=" + i +
                    '}';
        }
    }

    /**
     * Returns the capacity of the system i days after the last maintenance
     * activity, given that the the last maintenance activity has the given
     * capacity array.
     */
    private static int getCurrentCapacity(int[] capacity, int i) {
        if (i < capacity.length) {
            return capacity[i];
        } else {
            return capacity[capacity.length - 1];
        }
    }

    public static HashMap<State, Entry> buildTable(int[] fullRebootCapacity,
            int[] partialRebootCapacity, int[] data) {

        // values are optimum costs
        HashMap<State, Entry> table = new HashMap<>();

        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(Activity.FULL_REBOOT);
        activities.add(Activity.PARTIAL_REBOOT);

        int k = data.length;
        int lastDay = k - 1;

        for (int d = lastDay; d >= 0; d--) {
            // our last reboot can always be the day before we started (hence the i < d + 1)
            for (int i = 0; i <= d + 1; i++) {
                for (Activity lastActivity: activities) {
                    State state = new State(d, lastActivity, i);

                    ArrayList<Activity> actions = new ArrayList<>();
                    actions.add(Activity.FULL_REBOOT);
                    actions.add(Activity.PARTIAL_REBOOT);
                    actions.add(null);

                    ArrayList<Entry> candidates = new ArrayList<>();

                    for (Activity action: actions) {
                        int instantaneousCost = state
                                .applyAction(action)
                                .cost(fullRebootCapacity, partialRebootCapacity, data);

                        Entry defaultValue = new Entry(0,  null); // if (d == k) { return 0 }
                        int successorCost = table
                                .getOrDefault(
                                        state
                                                .applyAction(action)
                                                .incrementDay(),
                                        defaultValue
                                ).cost;

                        candidates.add(new Entry(instantaneousCost + successorCost, action));
                    }

                    Entry minEntry = candidates
                            .stream()
                            .min(Comparator.comparing(Entry::getCost))
                            .get();

                    table.put(state, minEntry);
                }
            }
        }

        return table;
    }

    /**
     * Returns the least cost that can be incurred by your company over the k =
     * data.length days (i.e. day 0 to day k-1) that you operate the HPCS
     * system, given that a full reboot took place the day before you were put
     * in charge of the system (i.e. 1 day before day 0), and given parameters
     * fullRebootCapacity, partialRebootCapacity and data.
     *
     * (See handout for details.)
     *
     * This method must be implemented using an efficient bottom-up dynamic
     * programming solution to the problem (not memoised).
     *
     * @require The arrays fullRebootCapacity, partialRebootCapacity and data
     *          are not null, and do not contain null values. Each of the
     *          integer values in those arrays are greater than or equal to zero
     *          (i.e. they are non-negative). fullRebootCapacity.length > 0 and
     *          partialRebootCapacity.length > 0
     *
     * @ensure Returns the least cost that can be incurred by your company over
     *         the k = data.length days (i.e. day 0 to day k-1) that you operate
     *         the HPCS system, given that a full reboot took place the day
     *         before you were put in charge of the system (i.e. 1 day before
     *         day 0), and given parameters fullRebootCapacity,
     *         partialRebootCapacity and data.
     */
    public static int optimalCostDynamic(int[] fullRebootCapacity,
            int[] partialRebootCapacity, int[] data) {

        return buildTable(fullRebootCapacity,partialRebootCapacity, data).get(
                new State(0, Activity.FULL_REBOOT, 1)
        ).cost;
    }

    /**
     * Returns a schedule of the maintenance activities that should take place
     * on each of the k=data.length days that you operate the HPCS system, that
     * guarantees that the least possible cost will be incurred by your company
     * for the k days that you operate the HPCS system (given parameters
     * fullRebootCapacity, partialReboot capacity and data).
     * 
     * The schedule should be an array of activities of length k=data.length,
     * where for each array index i, for 0 <= i < k, the value of array at index
     * i should be either the maintenance activity scheduled for that day
     * (either Activity.FULL_REBOOT or Activity.PARTIAL_REBOOT), if there is
     * one, or null if there is no maintenance activity scheduled for that day.
     * 
     * For example, for a value of k=6, the return value
     * 
     * [null, null, FULL_REBOOT, null, FULL_REBOOT, null]
     * 
     * represents a schedule in which a full reboot is performed on day 2 (the
     * third day) and day 4 (the fifth day), and no maintenance activities are
     * scheduled for the other days.
     * 
     * You should assume that a full reboot took place the day before you were
     * put in charge of the system (i.e. 1 day before day 0).
     * 
     * (See handout for details.)
     * 
     * This method must be implemented using an efficient bottom-up dynamic
     * programming solution to the problem (not memoised).
     * 
     * @require The arrays fullRebootCapacity, partialRebootCapacity and data
     *          are not null, and do not contain null values. Each of the
     *          integer values in those arrays are greater than or equal to zero
     *          (i.e. they are non-negative). fullRebootCapacity.length > 0 and
     *          partialRebootCapacity.length > 0
     * 
     * @ensure Returns a schedule of the maintenance activities that that should
     *         take place on each of the k=data.length days that you operate the
     *         HPCS system, that guarantees that the least possible cost will be
     *         incurred by your company for the k days that you operate the HPCS
     *         system (given parameters fullRebootCapacity, partialReboot
     *         capacity and data).
     */
    public static Activity[] optimalActivitiesDynamic(int[] fullRebootCapacity,
            int[] partialRebootCapacity, int[] data) {

        HashMap<State, Entry> table = buildTable(fullRebootCapacity, partialRebootCapacity, data);
        Activity[] activities = new Activity[data.length];

        State state = new State(0, Activity.FULL_REBOOT, 1); // our initial state
        // we'll update this as we probe our table

        int k = data.length;

        for (int i = 0; i <= k; i++) {
            if (!table.containsKey(state)) {
                break;
            }

            Entry entry = table.get(state); // get our entry with our action
            activities[i] = entry.activity;
            state = state.applyAction(entry.activity).incrementDay(); // fetch the next state
        }

        return activities;
    }
}
