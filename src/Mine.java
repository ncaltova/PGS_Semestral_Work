import java.util.ArrayList;
import java.util.List;

public class Mine {

/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * All working blocks in mine found by Leader.
     */
    private List<WorkBlock> workBlocks;

    /**
     * Lorry currently waiting in mine to be loaded.
     */
    private Lorry currentLorry;

    /*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Contructor that creates instance of mine with assigned mine leader.
     */
    public Mine() {
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/



/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter, that returns list of work blocks located in the mine.
     * @return List of work blocks located in the mine.
     */
    public List<WorkBlock> getWorkBlocks() {
        return workBlocks;
    }

    /**
     * Getter, that returns current lorry waiting in the mine.
     * @return Current lorry waiting in the mine.
     */
    public Lorry getCurrentLorry() {
        return currentLorry;
    }

    /*______________________________________________________SETTERS_______________________________________________________*/

    /**
     * Setter, that sets current lorry waiting in the mine.
     * @param currentLorry Current lorry waiting in the mine.
     */
    public void setCurrentLorry(Lorry currentLorry) {
        this.currentLorry = currentLorry;
    }

    /**
     * Setter, that sets list of found working blocks.
     * @param workBlocks List of found work blocks.
     */
    public void setWorkBlocks(List<WorkBlock> workBlocks) {
        this.workBlocks = workBlocks;
    }
}
