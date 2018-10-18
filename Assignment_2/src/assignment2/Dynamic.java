package assignment2;

import java.util.*;

public class Dynamic {

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
        return -1; // REMOVE THIS LINE AND WRITE THIS METHOD
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
