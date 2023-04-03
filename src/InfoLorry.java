import java.util.concurrent.CyclicBarrier;

public class InfoLorry {

/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * The maximum capacity of lorry.
     */
    private final int lorryCapacity;

    /**
     * The maximum time it takes lorry to get to ferry and from ferry to final destination.
     */
    private final int lorryTime;

    /**
     * Assigned ferry for getting to final destination.
     */
    private final CyclicBarrier assignedFerry;

/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor that creates instance of worker info carrier.
     * @param lorryCapacity The maximum capacity of lorry.
     * @param lorryTime The maximum time it takes lorry to get to ferry and from ferry to final destination.
     */
    public InfoLorry(int lorryCapacity, int lorryTime, CyclicBarrier assignedFerry) {
        this.lorryCapacity = lorryCapacity;
        this.lorryTime = lorryTime;
        this.assignedFerry = assignedFerry;
    }

/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter that returns the maximum capacity of lorry.
     * @return The maximum capacity of lorry.
     */
    public int getLorryCapacity() {
        return lorryCapacity;
    }

    /**
     * Getter that returns the maximum time it takes lorry to get to ferry and from ferry to final destination.
     * @return The maximum time it takes lorry to get to ferry and from ferry to final destination.
     */
    public int getLorryTime() {
        return lorryTime;
    }

    /**
     * Getter that returns assigned ferry for getting to final destination.
     * @return Assigned ferry for getting to final destination.
     */
    public CyclicBarrier getAssignedFerry() {
        return assignedFerry;
    }
}
