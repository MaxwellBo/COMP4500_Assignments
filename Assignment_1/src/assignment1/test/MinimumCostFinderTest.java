package assignment1.test;

import assignment1.*;
import java.util.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Basic tests for the {@link MinimumCostFinder} implementation class.
 * 
 * We will use a much more comprehensive test suite to test your code, so you
 * should add your own tests to this test suite to help you to debug your
 * implementation.
 */
public class MinimumCostFinderTest {

    @Test
    public void example1Test() {
        /* Initialise parameters to the test. */

        // number of locations
        int n = 6;
        // create n locations so that location.get(i) has identifier i
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            locations.add(new Location(i));
        }
        Location source = locations.get(0);
        int ts = 2;
        Location destination = locations.get(5);
        int td = 20;
        List<Delivery> deliveries = new ArrayList<>();
        deliveries.add(getDelivery(locations, 0, 1, 3, 4, 10));
        deliveries.add(getDelivery(locations, 0, 1, 5, 7, 2));
        deliveries.add(getDelivery(locations, 0, 1, 10, 11, 1));
        deliveries.add(getDelivery(locations, 0, 3, 8, 9, 0));
        deliveries.add(getDelivery(locations, 1, 2, 5, 6, 2));
        deliveries.add(getDelivery(locations, 1, 2, 7, 9, 3));
        deliveries.add(getDelivery(locations, 1, 4, 8, 10, 1));
        deliveries.add(getDelivery(locations, 1, 5, 12, 13, 9));
        deliveries.add(getDelivery(locations, 2, 5, 4, 6, 1));
        deliveries.add(getDelivery(locations, 2, 5, 11, 15, 3));
        deliveries.add(getDelivery(locations, 4, 0, 11, 12, 2));
        deliveries.add(getDelivery(locations, 4, 5, 9, 10, 1));

        /* Run method on inputs and test result. */

        // the expected minimum cost
        int expectedCost = 8;
        // the actual cost returned
        int actualCost = MinimumCostFinder.findMinimumCost(
                new HashSet<Location>(locations), source, ts, destination, td,
                deliveries);

        // compare the actual and expected outputs
        Assert.assertEquals(expectedCost, actualCost);
    }

    @Test
    public void example1Reversed() {
        /* Initialise parameters to the test. */

        // number of locations
        int n = 6;
        // create n locations so that location.get(i) has identifier i
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            locations.add(new Location(i));
        }
        Location source = locations.get(0);
        int ts = 2;
        Location destination = locations.get(5);
        int td = 20;
        List<Delivery> deliveries = new ArrayList<>();
        deliveries.add(getDelivery(locations, 0, 1, 3, 4, 10));
        deliveries.add(getDelivery(locations, 0, 1, 5, 7, 2));
        deliveries.add(getDelivery(locations, 0, 1, 10, 11, 1));
        deliveries.add(getDelivery(locations, 0, 3, 8, 9, 0));
        deliveries.add(getDelivery(locations, 1, 2, 5, 6, 2));
        deliveries.add(getDelivery(locations, 1, 2, 7, 9, 3));
        deliveries.add(getDelivery(locations, 1, 4, 8, 10, 1));
        deliveries.add(getDelivery(locations, 1, 5, 12, 13, 9));
        deliveries.add(getDelivery(locations, 2, 5, 4, 6, 1));
        deliveries.add(getDelivery(locations, 2, 5, 11, 15, 3));
        deliveries.add(getDelivery(locations, 4, 0, 11, 12, 2));
        deliveries.add(getDelivery(locations, 4, 5, 9, 10, 1));
        Collections.reverse(deliveries);

        /* Run method on inputs and test result. */

        // the expected minimum cost
        int expectedCost = 8;
        // the actual cost returned
        int actualCost = MinimumCostFinder.findMinimumCost(
                new HashSet<Location>(locations), source, ts, destination, td,
                deliveries);

        // compare the actual and expected outputs
        Assert.assertEquals(expectedCost, actualCost);
    }

    @Test
    public void basicTestReversed() {
        // number of locations in the postal network
        int n = 6;
        // create n locations so that location.get(i) has identifier i
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            locations.add(new Location(i));
        }
        // the postal location P_s and time t_s
        Location source = locations.get(0);
        int ts = 2;
        // the postal location P_d and time t_d
        Location destination = locations.get(4);
        int td = 20;
        // the delay d
        int d = 3;

        // log of deliveries that departed after ts and arrived before td
        List<Delivery> log = new ArrayList<>();
        log.add(getDelivery(locations, 0, 1, 3, 4, 10));
        log.add(getDelivery(locations, 5, 4, 4, 5, 2 ));
        log.add(getDelivery(locations, 1, 2, 5, 7, 1));
        log.add(getDelivery(locations, 0, 1, 10, 12, 0));
        log.add(getDelivery(locations, 0, 3, 10, 16, 6));
        log.add(getDelivery(locations, 2, 1, 10, 11, 3));
        log.add(getDelivery(locations, 0, 3, 12, 14, 1));
        log.add(getDelivery(locations, 1, 4, 13, 16, 9));
        log.add(getDelivery(locations, 3, 4, 16, 19, 1));
        Collections.reverse(log);

        // the expected minimum cost
        int expectedCost = 2;

        // the locations actually returned by LocationFinder.findLocations
        int actualCost = MinimumCostFinder.findMinimumCost(
                new HashSet<>(locations), source, ts, destination, td, log);

        // compare the actual and expected outputs
        Assert.assertEquals(expectedCost, actualCost);
    }

    @Test
    public void basicTest() {
        // number of locations in the postal network
        int n = 6;
        // create n locations so that location.get(i) has identifier i
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            locations.add(new Location(i));
        }
        // the postal location P_s and time t_s
        Location source = locations.get(0);
        int ts = 2;
        // the postal location P_d and time t_d
        Location destination = locations.get(4);
        int td = 20;
        // the delay d
        int d = 3;

        // log of deliveries that departed after ts and arrived before td
        List<Delivery> log = new ArrayList<>();
        log.add(getDelivery(locations, 0, 1, 3, 4, 10));
        log.add(getDelivery(locations, 5, 4, 4, 5, 2 ));
        log.add(getDelivery(locations, 1, 2, 5, 7, 1));
        log.add(getDelivery(locations, 0, 1, 10, 12, 0));
        log.add(getDelivery(locations, 0, 3, 10, 16, 6));
        log.add(getDelivery(locations, 2, 1, 10, 11, 3));
        log.add(getDelivery(locations, 0, 3, 12, 14, 1));
        log.add(getDelivery(locations, 1, 4, 13, 16, 9));
        log.add(getDelivery(locations, 3, 4, 16, 19, 1));

        // the expected minimum cost
        int expectedCost = 2;

        // the locations actually returned by LocationFinder.findLocations
        int actualCost = MinimumCostFinder.findMinimumCost(
                new HashSet<>(locations), source, ts, destination, td, log);

        // compare the actual and expected outputs
        Assert.assertEquals(expectedCost, actualCost);
    }

    @Test
    public void example2Test() {
        /* Initialise parameters to the test. */

        // number of locations
        int n = 6;
        // create n locations so that location.get(i) has identifier i
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            locations.add(new Location(i));
        }
        Location source = locations.get(0);
        int ts = 2;
        Location destination = locations.get(5);
        int td = 20;
        List<Delivery> deliveries = new ArrayList<>();
        deliveries.add(getDelivery(locations, 0, 1, 15, 16, 1));
        deliveries.add(getDelivery(locations, 0, 3, 8, 9, 0));
        deliveries.add(getDelivery(locations, 1, 2, 5, 6, 2));
        deliveries.add(getDelivery(locations, 1, 2, 7, 9, 3));
        deliveries.add(getDelivery(locations, 1, 4, 8, 10, 1));
        deliveries.add(getDelivery(locations, 1, 5, 12, 13, 9));
        deliveries.add(getDelivery(locations, 2, 5, 4, 6, 1));
        deliveries.add(getDelivery(locations, 2, 5, 11, 15, 3));
        deliveries.add(getDelivery(locations, 4, 0, 11, 12, 2));
        deliveries.add(getDelivery(locations, 4, 5, 9, 10, 1));

        /* Run method on inputs and test result. */

        // the expected minimum cost
        int expectedCost = -1;
        // the actual cost returned
        int actualCost = MinimumCostFinder.findMinimumCost(
                new HashSet<Location>(locations), source, ts, destination, td,
                deliveries);

        // compare the actual and expected outputs
        Assert.assertEquals(expectedCost, actualCost);
    }

    /*---Helper methods--------------------*/

    /**
     * Creates and returns a delivery from the ith location in locations to the
     * jth location in locations, departing at time departure and arriving at
     * time arrival, with the given cost.
     */
    private static Delivery getDelivery(List<Location> locations, int source,
            int destination, int departure, int arrival, int cost) {
        return new Delivery(locations.get(source), locations.get(destination),
                departure, arrival, cost);
    }

}
