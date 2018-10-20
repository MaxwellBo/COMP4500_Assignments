package assignment2;

import java.util.*;
import java.util.stream.Stream;

public class Dynamic {

    static class State {
        int d;
        Activity lastActivity;
        int i;

        public State(int d, Activity lastActivity, int i) {
            this.d = d;
            this.lastActivity = lastActivity;
            this.i = i;
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

        // values are optimum costs
        HashMap<State, Integer> table = new HashMap<>();

        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(Activity.FULL_REBOOT);
        activities.add(Activity.PARTIAL_REBOOT);

        int k = data.length;
        int lastDay = k - 1;

        for (int d = lastDay; d >= 0; d--) {
            // our last reboot can always be the day before we started (hence the i < d + 1)
            for (int i = 0; i <= d + 1; i++) {
                for (Activity lastActivity: activities) {
                    int capacity = lastActivity.equals(Activity.FULL_REBOOT)
                            ? (i < fullRebootCapacity.length) // take the last element if i is OOB
                                ? fullRebootCapacity[i]
                                : fullRebootCapacity[fullRebootCapacity.length - 1]
                            : (i < partialRebootCapacity.length) // take the last element if i is OOB
                                ? partialRebootCapacity[i]
                                : partialRebootCapacity[partialRebootCapacity.length - 1];

                    int cost = data[d] - capacity;

                    int defaultValue = 0; // if (d == k) { return 0 }

                    int partialRebootValue = table.getOrDefault(new State(
                            d + 1, // increment d
                            Activity.PARTIAL_REBOOT, // change the lastActivity...
                            0 // ... and reset i
                    ), defaultValue);

                    int fullRebootValue = table.getOrDefault(new State(
                            d + 1, // increment d
                            Activity.FULL_REBOOT, // change the lastActivity...
                            0 // ...and reset i
                    ), defaultValue);

                    int noRebootValue = table.getOrDefault(new State(
                            d + 1, // increment d
                            lastActivity, // persist the lastActivity
                            i + 1 // increment i
                    ), defaultValue);

                    int minCost = Stream.of(partialRebootValue, fullRebootValue, noRebootValue)
                            .min(Integer::compare)
                            .get();

                    State key = new State(d, lastActivity, i);
                    table.put(key, cost + minCost);

                    // if we just assembled our root node of our tree, return the cost
                    if (key.equals(new State(0, Activity.FULL_REBOOT, 1))) {
                        return cost + minCost;
                    }
                }
            }
        }
        
        return 0;
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
        return null; // REMOVE THIS LINE AND WRITE THIS METHOD
    }

}
