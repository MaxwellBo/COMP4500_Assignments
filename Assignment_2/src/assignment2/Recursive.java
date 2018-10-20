package assignment2;

import java.util.*;

public class Recursive {

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

    /**
     * Returns the least cost that can be incurred by your company over the k =
     * data.length days (i.e. day 0 to day k-1) that you operate the HPCS
     * system, given that a full reboot took place the day before you were put
     * in charge of the system (i.e. 1 day before day 0), and given parameters
     * fullRebootCapacity, partialRebootCapacity and data.
     * 
     * (See handout for details.)
     * 
     * This method must be implemented using a recursive programming solution to
     * the problem. It is expected to have a worst-case running time that is
     * exponential in k.
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
    public static int optimalCostRecursive(int[] fullRebootCapacity,
            int[] partialRebootCapacity, int[] data) {
        // IMPLEMENT THIS METHOD BY IMPLEMENTING THE PRIVATE METHOD IN THIS
        // CLASS THAT HAS THE SAME NAME
        return optimalCostRecursive(fullRebootCapacity, partialRebootCapacity,
                data, 0, Activity.FULL_REBOOT, 1);
    }

    /**
     * Given parameters fullRebootCapacity, partialRebootCapacity and data, this
     * method returns the least cost that can be incurred by your company from
     * day "d" to day "k-1" (inclusive) of the days that you operate the system
     * (where k = data.length), given that the last maintenance activity before
     * day "d" is given by parameter "lastActivity", and that it occurred "i"
     * days before day "d".
     * 
     * (See handout for details.)
     * 
     * This method must be implemented using a recursive programming solution to
     * the problem. It is expected to have a worst-case running time that is
     * exponential in k.
     * 
     * @require The arrays fullRebootCapacity, partialRebootCapacity and data
     *          are not null, and do not contain null values. Each of the
     *          integer values in those arrays are greater than or equal to zero
     *          (i.e. they are non-negative). fullRebootCapacity.length > 0 and
     *          partialRebootCapacity.length > 0
     * 
     *          Additionally, 0 <= d <= data.length, and 0 < i.
     * 
     * @ensure Given parameters fullRebootCapacity, partialRebootCapacity and
     *         data, this method returns the least cost that can be incurred by
     *         your company from day "d" to day "k-1" (inclusive) of the days
     *         that you operate the system (where k = data.length), given that
     *         the last maintenance activity before day "d" is given by
     *         parameter "lastActivity", and that it occurred "i" days before
     *         day "d".
     */
    private static int optimalCostRecursive(int[] fullRebootCapacity,
            int[] partialRebootCapacity, int[] data, int d,
            Activity lastActivity, int i) {

        int k = data.length;

        if (d == k) {
            return 0;
        }

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

            State successor = state
                            .applyAction(action)
                            .incrementDay();

            int successorCost = optimalCostRecursive(
                    fullRebootCapacity,
                    partialRebootCapacity,
                    data,
                    successor.d,
                    successor.lastActivity,
                    successor.i
            );

            candidates.add(new Entry(instantaneousCost + successorCost, action));
        }

        return candidates
                .stream()
                .min(Comparator.comparing(Entry::getCost))
                .get()
                .cost;
    }
}
