import java.util.ArrayList;
import java.util.List;

public class Ferry {
/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Maximum capacity of ferry.
     */
    private int ferryCapacity;

    /**
     * Current free capacity of ferry.
     */
    private int currentCapacity;

    /**
     * List of lorries loaded on ferry.
     */
    private List<Lorry> loadedLorries;

    /**
     * Indicator of whether ferry is full or not.
     */
    private boolean isFull;

    /**
     * Indicator of whether ferry is waiting to be loaded or not.
     */
    private boolean isDocked;

/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor that creates instance of ferry waiting to be loaded in dock.
     * @param ferryCapacity Maximum capacity of this instance.
     */
    public Ferry(int ferryCapacity) {
        this.ferryCapacity = ferryCapacity;
        this.currentCapacity = ferryCapacity;
        this.isDocked = true;
        this.isFull = false;
        this.loadedLorries = new ArrayList<>();
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method representing one lorry getting on ferry.
     * @param lorry Lorry getting on ferry.
     */
    public void loadFerry(Lorry lorry){

        if (!this.isFull){
            this.currentCapacity--;
            this.loadedLorries.add(lorry);
        }

        if (this.currentCapacity == 0){
            this.isFull = true;
            this.isDocked = false;
            this.unloadFerry();
        }
    }

    /**
     * Method representing ferry going over to the other side
     * and unloading all lorries onboard.
     */
    private void unloadFerry(){

        for (Lorry lorry: this.loadedLorries) {
            lorry.getOffFerry();

        }
        this.loadedLorries.clear();

        this.isFull = false;
        this.currentCapacity = this.ferryCapacity;
        this.isDocked = true;
    }

/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter that returns indicator of whether ferry is full or not.
     * @return Indicator of whether ferry is waiting to be loaded or not.
     */
    public boolean isFull() {
        return isFull;
    }

    /**
     * Getter that returns indicator of whether ferry is waiting to be loaded or not.
     * @return Indicator of whether ferry is waiting to be loaded or not.
     */
    public boolean isDocked() {
        return isDocked;
    }

/*______________________________________________________SETTERS_______________________________________________________*/
}
