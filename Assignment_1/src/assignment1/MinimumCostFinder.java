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

        // O(D)
        for (Delivery delivery: deliveries) {
            HashSet<Delivery> sourceDeliveries = sourceToDeliveries.computeIfAbsent(delivery.source(), (k) -> new HashSet<>());
            HashSet<Delivery> destinationDeliveries = destinationToDeliveries.computeIfAbsent(delivery.destination(), (k) -> new HashSet<>());

            sourceDeliveries.add(delivery);
            destinationDeliveries.add(delivery);
            deliveryToVertex.put(delivery, new Vertex<>(delivery));
        }

        HashMap<Vertex<Delivery>, HashSet<Vertex<Delivery>>> adjacency = new HashMap<>();

        // Total O(D^2)
        // O(D)
        for (Delivery delivery: deliveries) {
            // Worst-case O(D)

            HashSet<Vertex<Delivery>> candidateNeighbours = sourceToDeliveries
                            .computeIfAbsent(delivery.destination(), (k) -> new HashSet<>())
                            .stream()
                            .filter(t -> delivery.arrival() <= t.departure())
                            .map(t -> deliveryToVertex.get(t))
                            .collect(Collectors.toCollection(HashSet::new));

            adjacency.put(deliveryToVertex.get(delivery), candidateNeighbours);
        }

        HashSet<Vertex<Delivery>> sources = sourceToDeliveries
                .get(source)
                .stream()
                .filter(s -> ts <= s.departure())
                .map(s -> deliveryToVertex.get(s))
                .collect(Collectors.toCollection(HashSet::new));


        djikstra(adjacency, sources);

        Optional<Vertex<Delivery>> minVertex = destinationToDeliveries
                .get(destination)
                .stream()
                .filter(s -> s.arrival() <= td)
                .map(s -> deliveryToVertex.get(s))
                .min(Comparator.comparingInt(u -> u.d));


        Vertex<Delivery> head = minVertex.get().pi;
        while (true) {
            if (head != null) {
                System.out.println(head.element);
                head = head.pi;
            } else {
                break;
            }
        }

        int cost = minVertex.get().d;

        return cost == Integer.MAX_VALUE ? -1 : cost;
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

    public static void djikstra(
            HashMap<Vertex<Delivery>, HashSet<Vertex<Delivery>>> G,
            HashSet<Vertex<Delivery>> sources
    ) {
        PriorityQueue<Priority<Vertex<Delivery>>> Q = new PriorityQueue<>();

        for (Vertex<Delivery> source: sources) {
            source.d = source.element.cost();
            Q.add(
                    new Priority<>(source.d, source)
            );
        }

        while (!Q.isEmpty()) {
            Vertex<Delivery> u = Q.poll().element;

            for (Vertex<Delivery> v: G.computeIfAbsent(u, (k) -> new HashSet<>())) {
                int candidateCost = u.d + v.element.cost();

                if (v.d > candidateCost) {
                    v.d = candidateCost;
                    Q.add(new Priority<>(v.d, v));
                    v.pi = u;
                }
            }
        }
    }
}
