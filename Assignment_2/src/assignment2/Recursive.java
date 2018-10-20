package assignment2;

import java.util.*;
import java.util.stream.Stream;

public class Recursive {

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

        int capacity = lastActivity.equals(Activity.FULL_REBOOT)
                 ? (i < fullRebootCapacity.length) // take the last element if i is OOB
                    ? fullRebootCapacity[i]
                    : fullRebootCapacity[fullRebootCapacity.length - 1]
                 : (i < partialRebootCapacity.length) // take the last element if i is OOB
                    ? partialRebootCapacity[i]
                    : partialRebootCapacity[partialRebootCapacity.length - 1];

        int cost = data[d] - capacity;

        int fullRebootCost = optimalCostRecursive(
                fullRebootCapacity,
                partialRebootCapacity,
                data,
                d + 1, // increment d
                Activity.FULL_REBOOT, // change the lastActivity...
                0 // ...and reset i
        );

        int partialRebootCost = optimalCostRecursive(
                fullRebootCapacity,
                partialRebootCapacity,
                data,
                d + 1, // increment d
                Activity.PARTIAL_REBOOT, // change the lastActivity...
                0 // ...and reset i
        );

        int noRebootCost = optimalCostRecursive(
                fullRebootCapacity,
                partialRebootCapacity,
                data,
                d + 1, // increment d
                lastActivity, // persist the lastActivity
                i + 1 // increment i
        );


        int minCost = Stream.of(partialRebootCost, fullRebootCost, noRebootCost)
                             .min(Integer::compare)
                             .get();

        return cost + minCost;
    }
}
