package assignment1;

import java.util.*;

public class MinimumCostFinder {


    public String deriveSNI(String studentNumber) {
        // No successive digits are identitcal, so initial input number is the
        // same as SNI
        return "98" + studentNumber + "52";
    }

    /**
     * @require The set of locations, locations, is not null and each location
     *          in the set of locations is not null and has a unique identifier
     *          in the range [0, locations.size()-1].
     * 
     *          The set of locations contains the source and destination
     *          locations, and those two locations are not equal.
     * 
     *          The earliest time that the package can depart the source
     *          location, ts, is non-negative (i.e. 0 <= ts). The latest time
     *          that the package can arrive at the destination location, td, is
     *          no earlier than ts (i.e. ts <= td).
     *
     *          The list of deliveries, deliveries, is not null, and for each
     *          delivery x in the deliveries, the source and destination of x
     *          are in the set of locations, and ts <= x.departure() <
     *          x.arrival() <= td.
     * 
     * @ensure Returns the minimum cost of any route from source to destination
     *         that departs the source location no earlier than time ts, and
     *         arrives at the destination location no later than time td, if at
     *         least one such route exists. If no such route exists the
     *         algorithm should return the distinguished value -1.
     * 
     *         (See the assignment handout for details.)
     */
    public static int findMinimumCost(Set<Location> locations, Location source,
            int ts, Location destination, int td, List<Delivery> deliveries) {

        for (Delivery delivery: deliveries) {

        }


        return -2; // REMOVE THIS LINE AND IMPLEMENT THIS METHOD
    }


    class Priority<T> implements Comparable<Priority<T>> {
        public int priority;
        public T element;

        public Priority(int priority) {
            this.priority = priority;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Priority<?> priority1 = (Priority<?>) o;
            return priority == priority1.priority &&
                    Objects.equals(element, priority1.element);
        }

        @Override
        public int hashCode() {
            return Objects.hash(priority, element);
        }

        @Override
        public int compareTo(Priority<T> that) {
            return Integer.compare(priority, that.priority);
        }
    }

    class Vertex<T> {
        public T t;
        public int d;
        public Vertex<T> pi;

        public Vertex(T t) {
            this.t = t;
            this.d = Integer.MAX_VALUE;
            this.pi = null;
        }
    }


    public void djikstra(Location source, Location destination) {
        PriorityQueue<Priority<Delivery>> frontier = new PriorityQueue<>();
//        frontier.put(start, 0)
//        came_from = {}
//        cost_so_far = {}
//        came_from[start] = None
//        cost_so_far[start] = 0
//
//        while not frontier.empty():
//        current = frontier.get()
//
//        if current == goal:
//        break
//
//        for next in graph.neighbors(current):
//        new_cost = cost_so_far[current] + graph.cost(current, next)
//        if next not in cost_so_far or new_cost < cost_so_far[next]:
//        cost_so_far[next] = new_cost
//        priority = new_cost
//        frontier.put(next, priority)
//        came_from[next] = current
    }
}
