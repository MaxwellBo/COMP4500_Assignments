package assignment1;

/**
 * An immutable representation of a delivery from one location to another.
 * 
 * A delivery has source and destination locations, as well as a cost, a
 * departure time of the delivery from the source location and an arrival time
 * of the delivery at the destination location. Both the departure and arrival
 * time are non-negative integers and the arrival time must be strictly greater
 * than the departure time. The cost is a non-negative integer.
 * 
 * Any two instances of the Delivery class are equal if they have equal source
 * locations, equal destination locations, equal departure times, equal arrival
 * times, and equal costs.
 */
public class Delivery {

    // the source location of the delivery
    private final Location source;
    // the destination location of the delivery
    private final Location destination;
    // the departure time of the delivery from the source location
    private final int departure;
    // the arrival time of the delivery at the destination location
    private final int arrival;
    // the cost of the delivery
    private final int cost;

    /*
     * class invariant:
     * 
     * source != null && destination != null && 0 <= departure < arrival && 0 <=
     * cost
     */

    /**
     * @require source!=null && destination!=null && 0 <= departure < arrival &&
     *          0 <= cost
     * @ensure creates a new delivery from the source location at the given
     *         departure time, to the destination location at the given arrival
     *         time, with the given cost
     */
    public Delivery(Location source, Location destination, int departure,
            int arrival, int cost) {
        if (source == null || destination == null) {
            throw new IllegalArgumentException(
                    "Source and destination must not be null.");
        }
        if (departure < 0 || arrival < 0 || arrival <= departure) {
            throw new IllegalArgumentException(
                    "Departure and arrival must be non-negative integers "
                            + "such that departure < arrival");
        }
        if (cost < 0) {
            throw new IllegalArgumentException("Cost must not be negative.");
        }
        this.source = source;
        this.destination = destination;
        this.departure = departure;
        this.arrival = arrival;
        this.cost = cost;
    }

    /**
     * @ensure Returns the source location of the delivery.
     */
    public Location source() {
        return source;
    }

    /**
     * @ensure Returns the destination location of the delivery.
     */
    public Location destination() {
        return destination;
    }

    /**
     * @ensure Returns the departure time of the delivery from the source
     *         location.
     */
    public int departure() {
        return departure;
    }

    /**
     * @ensure Returns the arrival time of the delivery at the destination
     *         location.
     */
    public int arrival() {
        return arrival;
    }

    /**
     * @ensure Returns the cost of the delivery.
     */
    public int cost() {
        return cost;
    }

    @Override
    public String toString() {
        return "(" + source.toString() + ", " + destination.toString() + ", "
                + departure + ", " + arrival + ", " + cost + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Delivery)) {
            return false;
        }
        Delivery other = (Delivery) object;
        return (source.equals(other.source)
                && destination.equals(other.destination)
                && departure == other.departure && arrival == other.arrival
                && cost == other.cost);
    }

    @Override
    public int hashCode() {
        int prime = 7;
        int result = source.hashCode();
        result = prime * result + destination.hashCode();
        result = prime * result + departure;
        result = prime * result + arrival;
        result = prime * result + cost;
        return result;
    }

}
