package assignment2.test;

import assignment2.*;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link Recursive} implementation class.
 * 
 * We will use a more comprehensive test suite to test your code, so you should
 * add your own tests to this test suite to help you to debug your
 * implementation.
 */
public class RecursiveTest {

    /**
     * A basic test of the Recursive.optimalCostRecursive method.
     */
    @Test
    public void basicCostTest() {
        // initialise the arrays used for testing
        int[] fullRebootCapacity = { 0, 10, 4, 5, 1 };
        int[] partialRebootCapacity = { 2, 8, 3, 2, 0 };
        int[] data = { 15, 5, 12, 17, 7, 10 };

        // compare expected to actual results
        int expectedOptimalCost = 32;
        int actualOptimalCost = Recursive.optimalCostRecursive(
                fullRebootCapacity, partialRebootCapacity, data);
        Assert.assertEquals(expectedOptimalCost, actualOptimalCost);
    }

    /**
     * A basic test of the Recursive.optimalCostRecursive method: checks that
     * the method works correctly when k=0.
     */
    @Test
    public void basicCostTestBoundaryCondition() {
        // initialise the arrays used for testing
        int[] fullRebootCapacity = { 0, 10, 4, 1 };
        int[] partialRebootCapacity = { 2, 8, 3, 2, 0 };
        int[] data = {};

        // compare expected to actual results
        int expectedOptimalCost = 0;
        int actualOptimalCost = Recursive.optimalCostRecursive(
                fullRebootCapacity, partialRebootCapacity, data);
        Assert.assertEquals(expectedOptimalCost, actualOptimalCost);
    }

}
