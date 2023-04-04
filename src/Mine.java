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
     * Method serving for dispatching last lorry by simulation admin.
     */
    public void dispatchLastLorry(){
        this.dock.dispatchLastLorry();
    }

    /**
     * Method representing loading current lorry with one minefield.
     */
    public synchronized void loadLorry(){
        this.dock.loadLorry();

        if (this.dock.isLorryFull()) this.dock.dispatchLorry();
    }

    /**
     * Method representing report of total dispatched material.
     */
    public void reportTotalDispatched(){
        this.dock.reportTotalDispatched();
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
     * Getter that returns indicator of whether all lorries are done with their work.
     * @return indicator of whether all lorries are done with their work.
     */
    public boolean areLorriesDone(){
        return this.dock.areLorriesDone();
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
