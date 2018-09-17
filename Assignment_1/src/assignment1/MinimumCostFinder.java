package assignment1;

import java.util.*;
import java.util.stream.Collectors;

public class MinimumCostFinder {

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

        HashMap<Location, HashSet<Delivery>> sourceToDeliveries = new HashMap<>();
        HashMap<Location, HashSet<Delivery>> destinationToDeliveries = new HashMap<>();
        HashMap<Delivery, Vertex<Delivery>> deliveryToVertex = new HashMap<>();

        // D iterations
        // O(1) loop body
        // O(P) HashSet construction cost, due to handshake lemma
        // Overall: O(D + P)
        // Worst-case: |P| = 2 * |D|, 
        //   if each delivery bridges two unique locations
        // Overall: O(D)
        for (Delivery delivery: deliveries) {
            // foreach P, we construct a HashSet, if the P lookup fails
            HashSet<Delivery> sourceDeliveries = sourceToDeliveries.computeIfAbsent(delivery.source(), (k) -> new HashSet<>());
            HashSet<Delivery> destinationDeliveries = destinationToDeliveries.computeIfAbsent(delivery.destination(), (k) -> new HashSet<>());

            sourceDeliveries.add(delivery); // O(1)
            destinationDeliveries.add(delivery); // O(1)
            deliveryToVertex.put(delivery, new Vertex<>(delivery)); // O(1)
        }

        HashMap<Vertex<Delivery>, HashSet<Vertex<Delivery>>> adjacency = new HashMap<>();

        // D iterations
        // Worst-case: O(D) loop body, 
        //   if successive locations always depart after predecessor arrival
        // Overall: O(D^2)
        for (Delivery delivery: deliveries) {
            // worst-case O(D)
            HashSet<Vertex<Delivery>> candidateNeighbours = sourceToDeliveries
                            // defensive programming: cost already covered above
                            .computeIfAbsent(delivery.destination(), (k) -> new HashSet<>()) // O(1)
                            .stream()
                            .filter(d -> delivery.arrival() <= d.departure()) // worst-case O(D)
                            .map(d -> deliveryToVertex.get(d)) // // worst-case O(D)
                            .collect(Collectors.toCollection(HashSet::new)); // // worst-case O(D)

            adjacency.put(deliveryToVertex.get(delivery), candidateNeighbours); // O(1)
        }

        // worst-case O(D)
        HashSet<Vertex<Delivery>> sources = sourceToDeliveries
                .get(source) // O(1)
                .stream()
                .map(s -> deliveryToVertex.get(s)) // worst-case O(D)
                .collect(Collectors.toCollection(HashSet::new)); // worst-case O(D)


        // djikstras is \Theta(V * lg V + E * lg V),
        // As E is worst-case O(V^2), substituting, we get
        // \Theta(V * lg V + V^2 * lg V)
        // As our Ds are vertexes in our use of Djikstras algorithm, we get
        // Overall: \Theta(D * lg D + D^2 * lg D), which is
        // Overall: \Theta(D^2 * lg D)
        dijkstra(adjacency, sources);

        // O(D)
        Optional<Vertex<Delivery>> lowestDDestinationVertex = destinationToDeliveries
                .get(destination) // O(1)
                .stream()
                .map(d -> deliveryToVertex.get(d)) // worst-case O(D)
                .min(Comparator.comparingInt(v -> v.d)); // worst-case O(D - 1)


        // O(1)
        if (!lowestDDestinationVertex.isPresent()) {
            return -1;
        } else {
            int cost = lowestDDestinationVertex.get().d;

//        Vertex<Delivery> head = lowestDDestinationVertex.get().pi;
//
//        while (true) {
//            if (head != null) {
//                System.out.println(head.element);
//                head = head.pi;
//            } else {
//                break;
//            }
//        }

            return cost == Integer.MAX_VALUE ? -1 : cost;
        }
    }

    public static void dijkstra(
            HashMap<Vertex<Delivery>, HashSet<Vertex<Delivery>>> G,
            HashSet<Vertex<Delivery>> sources
    ) {
        PriorityQueue<Priority<Vertex<Delivery>>> Q = new PriorityQueue<>();

        // O(D)
        for (Vertex<Delivery> source: sources) {
            source.d = source.element.cost();
            Q.add(new Priority<>(source.d, source));
        }

        // overall \Theta(V * lg V  + E * lg V)
        // |D| times
        while (!Q.isEmpty()) {
            Vertex<Delivery> u = Q.poll().element; // // O(lg V)

            // degree(u) times
            for (Vertex<Delivery> v: G.computeIfAbsent(u, (k) -> new HashSet<>())) {
                int candidateCost = u.d + v.element.cost();

                if (v.d > candidateCost) {
                    v.d = candidateCost;
                    Q.add(new Priority<>(v.d, v)); // O(lg V)
                    v.pi = u;
                }
            }
        }
    }

    static class Priority<T> implements Comparable<Priority<T>> {
        public int priority;
        public T element;

        public Priority(int priority, T element) {
            this.priority = priority;
            this.element = element;
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

    static class Vertex<T> {
        public T element;
        public int d;
        public Vertex<T> pi;

        public Vertex(T element) {
            this.element = element;
            this.d = Integer.MAX_VALUE;
            this.pi = null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Vertex<?> vertex = (Vertex<?>) o;

            return element != null ? element.equals(vertex.element) : vertex.element == null;
        }

        @Override
        public int hashCode() {
            return element != null ? element.hashCode() : 0;
        }

    }

}
