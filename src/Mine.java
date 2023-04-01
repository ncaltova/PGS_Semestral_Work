import java.util.List;

public class Mine {

/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * All working blocks in mine found by Leader.
     */
    private List<WorkBlock> workBlocks;

    /**
     * Dock with lorry waiting to be loaded.
     */
    private MineDock dock;

    /*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Contructor that creates instance of mine with assigned mine leader.
     */
    public Mine(MineDock dock) {
        this.dock = dock;
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    public void dispatchLorry(){
        this.dock.dispatchLorry();
    }

    public boolean lorryAvailable(){

        return this.dock.getCurrentLorry() != null;
    }

    public boolean loadLorry(){
        if (this.dock.getCurrentLorry().isFull()) return false;

        this.dock.getCurrentLorry().load();
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

    public boolean isEmpty(){

        for (WorkBlock workBlock: this.workBlocks) {
            if (!workBlock.isEmpty()){
                return false;
            }
        }

        return true;
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
