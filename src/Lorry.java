import java.util.Random;

public class Lorry extends Thread{

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
     * Assigned dock with waiting ferry.
     */
    private Ferry assignedFerry;

    private boolean isDispatched;

    /*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of Lorry
     * with specified capacity, time it takes to get to ferry (and vice versa) and target ferry.
     * @param lorryCapacity Maximum capacity of lorry.
     * @param lorryTime Time it takes lorry to get to ferry (and vice versa).
     */
    public Lorry(int lorryCapacity, int lorryTime, Ferry assignedFerry) {
        this.lorryCapacity = lorryCapacity;
        this.currentCapacity = lorryCapacity;
        this.lorryTime = lorryTime;
        this.isFull = false;
        this.assignedFerry = assignedFerry;
        this.isDispatched = false;
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method representing lorry going to dock with waiting ferry.
     */
    public void goToFerry(){
        //wait for generateFerryTime()
        //assignedMine.setCurrentLorry(null);
        assignedFerry.loadFerry(this);
    }

    public void getOffFerry(){
        //wait for generateFinishTime()
    }

    /**
     * Method generating time it takes
     * lorry to get to its final destination.
     * @return Time it takes lorry to get to its final destination.
     */
    private int generateFinishTime(){
        Random rd = new Random();

        return rd.nextInt(this.lorryTime - 1) + 1;
    }

    /**
     * Method generating time it takes
     * lorry to get to dock with waiting ferry.
     * @return Time it takes lorry to get to dock with waiting ferry.
     */
    private int generateFerryTime(){
        Random rd = new Random();

        return rd.nextInt(this.lorryTime - 2) + 1;
    }

    /**
     * Methos representing loading lorry with mined material.
     */
    public void load(){
        this.currentCapacity--;
        if (this.currentCapacity == 0) this.isFull = true;
    }

    /**
     * Method representing lorry waiting in assigned mine to be loaded.
     */
    public void dockAtMine(){
        //assignedMine.setCurrentLorry(this);
    }

/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter, that returns indicator if lorry is fully loaded.
     * @return Indicator if lorry is fully loaded.
     */
    public boolean isFull() {
        return isFull;
    }

    public boolean isDispatched() {
        return isDispatched;
    }

    /*______________________________________________________SETTERS_______________________________________________________*/

}
