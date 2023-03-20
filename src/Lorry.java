
public class Lorry {

/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Maximum capacity of lorry.
     */
    private final int lorryCapacity;

    /**
     * Current capacity of lorry.
     */
    private int currentCapacity;

    /**
     * Time it takes lorry to get from work block to ferry
     * and vice versa.
     */
    private final int lorryTime;

    /**
     * Indicator if lorry is fully loaded.
     */
    private boolean isFull;

   /**
    * Ferry to which lorry brings loaded material.
    */
    private final Ferry assignedFerry;

    /*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of Lorry
     * with specified capacity, time it takes to get to ferry (and vice versa) and target ferry.
     * @param lorryCapacity Maximum capacity of lorry.
     * @param lorryTime Time it takes lorry to get to ferry (and vice versa).
     * @param assignedFerry Target ferry.
     */
    public Lorry(int lorryCapacity, int lorryTime, Ferry assignedFerry) {
        this.lorryCapacity = lorryCapacity;
        this.currentCapacity = lorryCapacity;
        this.lorryTime = lorryTime;
        this.assignedFerry = assignedFerry;
        this.isFull = false;
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    public void goToFerry(){
        //wait for lorryTime
        //ferry.getTo
    }

    private int generateTime(){
        return 0;
    }

    public void load(){
        this.currentCapacity--;

        if (this.currentCapacity == 0) this.isFull = true;
    }

/*______________________________________________________GETTERS_______________________________________________________*/

/*______________________________________________________SETTERS_______________________________________________________*/

}
