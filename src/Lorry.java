import java.util.Random;

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
     * Assigned mining site to be loaded at.
     */
    private Mine assignedMine;

    /**
     * Assigned dock with waiting ferry.
     */
    private Ferry assignedFerry;

    /*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of Lorry
     * with specified capacity, time it takes to get to ferry (and vice versa) and target ferry.
     * @param lorryCapacity Maximum capacity of lorry.
     * @param lorryTime Time it takes lorry to get to ferry (and vice versa).
     */
    public Lorry(int lorryCapacity, int lorryTime, Mine assignedMine, Ferry assignedFerry) {
        this.lorryCapacity = lorryCapacity;
        this.currentCapacity = lorryCapacity;
        this.lorryTime = lorryTime;
        this.isFull = false;
        this.assignedMine = assignedMine;
        this.assignedFerry = assignedFerry;
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method representing lorry going to dock with waiting ferry.
     */
    public void goToFerry(){
        //wait for lorryTime
        assignedMine.setCurrentLorry(null);
        //ferry.getTo
    }

    /**
     * Method generating time it takes
     * lorry to get to dock with waiting ferry.
     * @return Time it takes lorry to get to dock with waiting ferry.
     */
    private int generateTime(){
        Random rd = new Random();

        return rd.nextInt(this.lorryTime - 1) + 1;
    }

    /**
     * Methos representing loading lorry with one piece of mined material.
     */
    public void load(){
        this.currentCapacity--;
        if (this.currentCapacity == 0) this.isFull = true;
    }

    /**
     * Method representing lorry waiting in assigned mine to be loaded.
     */
    public void dockAtMine(){
        assignedMine.setCurrentLorry(this);
    }

/*______________________________________________________GETTERS_______________________________________________________*/

/*______________________________________________________SETTERS_______________________________________________________*/

}
