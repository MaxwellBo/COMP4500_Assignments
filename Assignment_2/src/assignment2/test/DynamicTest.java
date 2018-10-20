package assignment2.test;

import assignment2.*;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link Dynamic} implementation class.
 * 
 * We will use a more comprehensive test suite to test your code, so you should
 * add your own tests to this test suite to help you to debug your
 * implementation.
 */
public class DynamicTest {

    /**
     * A basic test of the Dynamic.optimalCostDynamic method
     */
    @Test
    public void basicCostTest() {
        // initialise the arrays used for testing
        int[] fullRebootCapacity = { 0, 10, 4, 5, 1 };
        int[] partialRebootCapacity = { 2, 8, 3, 2, 0 };
        int[] data = { 15, 5, 12, 17, 7, 10 };

        // compare expected to actual results
        int expectedOptimalCost = 32;
        int actualOptimalCost = Dynamic.optimalCostDynamic(fullRebootCapacity,
                partialRebootCapacity, data);
        Assert.assertEquals(expectedOptimalCost, actualOptimalCost);
    }

    /**
     * A basic test of the Dynamic.optimalActivitiesDynamic method.
     */
    @Test
    public void basicActivitiesTest() {
        // initialise the arrays used for testing
        int[] fullRebootCapacity = { 0, 10, 4, 5, 1 };
        int[] partialRebootCapacity = { 2, 8, 3, 2, 0 };
        int[] data = { 15, 5, 12, 17, 7, 10 };

        // compare expected to actual results
        int expectedOptimalCost = 32;
        Activity[] actualActivities = Dynamic.optimalActivitiesDynamic(
                fullRebootCapacity, partialRebootCapacity, data);
        int actualOptimalCost = getCost(fullRebootCapacity,
                partialRebootCapacity, data, actualActivities);
        Assert.assertEquals(expectedOptimalCost, actualOptimalCost);
    }

    /*------------helper methods------------*/

    /**
     * Returns the cost to the company (for the given input parameters
     * fullRebootCapacity, partialRebootCapacity and data) given the maintenance
     * schedule described by activities.
     */
    private static int getCost(int[] fullRebootCapacity,
            int[] partialRebootCapacity, int[] data, Activity[] activities) {

        // there should be one activity for each day we operate the system
        Assert.assertEquals(activities.length, data.length);

        // the last activity performed and the number of days, i, since that
        // last activity was performed.
        Activity lastActivity = Activity.FULL_REBOOT;
        int i = 1;

        // determine the cost of the activities
        int cost = 0;
        for (int d = 0; d < data.length; d++) {
            if (activities[d] != null) {
                lastActivity = activities[d];
                i = 0;
            }
            if (lastActivity == Activity.FULL_REBOOT) {
                cost += Math.max(0,
                        data[d] - getCurrentCapacity(fullRebootCapacity, i));
            } else {
                cost += Math.max(0,
                        data[d] - getCurrentCapacity(partialRebootCapacity, i));
            }
            System.out.println(cost);
            i = i + 1;
        }
        return cost;
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

}
