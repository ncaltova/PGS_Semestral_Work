import java.util.List;

/**
 * Instances of this class represent one mining site of the simulation.
 * @author Nikol Caltova
 * @version 1.0
 */
public class Mine {

/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * All working blocks in mine found by Leader.
     */
    private List<WorkBlock> workBlocks;

    /**
     * Dock with lorry waiting to be loaded.
     */
    private final MineDock dock;

/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor that creates instance of mine with assigned dock for lorry, ferry, reporter of key events and
     * starting time of the whole simulation.
     * @param dock Dock with lorry waiting to be loaded.
     */
    public Mine(MineDock dock) {
        this.dock = dock;
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method representing dispatching current lorry to ferry by worker.
     */
    public synchronized void dispatchLorry(){
        this.dock.dispatchLorry();
    }

    /**
     * Method serving for dispatching last lorry by simulation admin.
     * @return True if last lorry has been partially loaded and dispatched else false.
     */
    public boolean dispatchLastLorry(){
        return this.dock.dispatchLastLorry();
    }

    /**
     * Method representing loading current lorry with one minefield.
     * @return False if current lorry is fully loaded and cant take any other cargo else true.
     */
    public synchronized boolean loadLorry(){
        if (this.dock.isLorryFull()) return false;

        this.dock.loadLorry();
        return true;
    }

/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter, that returns list of work blocks located in the mine.
     * @return List of work blocks located in the mine.
     */
    public List<WorkBlock> getWorkBlocks() {
        return workBlocks;
    }

    /**
     * Getter that returns boolean value representing if all mine block are mined out.
     * @return Boolean value representing if all mine block are mined out.
     */
    public boolean isEmpty(){

        for (WorkBlock workBlock: this.workBlocks) {
            if (!workBlock.isEmpty()){
                return false;
            }
        }

        return true;
    }

    /**
     * Getter that returns indicator of whether current lorry is done with its work.
     * @return indicator of whether current lorry is done with its work.
     */
    public boolean isLorryDone(){
        return this.dock.isLorryDone();
    }

    /**
     * Getter that returns indicator of whether current lorry is full or not.
     * @return Indicator of whether current lorry is full or not.
     */
    public boolean isLorryFull(){
        return this.dock.isLorryFull();
    }

/*______________________________________________________SETTERS_______________________________________________________*/

    /**
     * Setter, that sets list of found working blocks.
     * @param workBlocks List of found work blocks.
     */
    public void setWorkBlocks(List<WorkBlock> workBlocks) {
        this.workBlocks = workBlocks;
    }
}
