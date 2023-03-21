import java.io.FileNotFoundException;
import java.util.List;

/**
 * Class representing shift leader that organizes miners and analyzes mine before mining starts.
 * @author Nikol Caltova
 */
public class Leader {
/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Assigned mining site.
     */
    private Mine assignedMine;

    /**
     * Mining site map for leader.
     */
    private String siteMapName;

    /**
     * Workers answering to leader.
     */
    private List<Worker> mineWorkers;
/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of shift leader that is assigned a mine and name of the site map.
     * @param siteMapName Name of the site map.
     */
    public Leader(String siteMapName, List<Worker> mineWorkers, Mine assignedMine) {
        this.siteMapName = siteMapName;
        this.assignedMine = assignedMine;
        this.mineWorkers = mineWorkers;
    }

    /*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method representing shift leader inspecting mining site for mining blocks.
     * @throws FileNotFoundException If site map cannot be found.
     */
    public void inspectMiningSite() throws FileNotFoundException {
        this.assignedMine.setWorkBlocks(Parser.parseIntoBlocks(siteMapName));
    }

    /**
     * Method representing leader assigning work to assigned workers.
     * @param designatedBlock Work block for leader to assign.
     */
    public void assignWork(WorkBlock designatedBlock){
        Worker designatedWorker = findWorker();

        if (designatedWorker != null) {
            designatedWorker.setDone(false);
            designatedWorker.setAssignedWorkBlock(designatedBlock);
        }

    }

    /**
     * Method representing leader finding free worker in assigned workers.
     * @return Free worker in assigned workers.
     */
    private Worker findWorker(){

        for (Worker worker: this.mineWorkers) {
            if (worker.isDone()) return worker;
        }
        return null;
    }

/*______________________________________________________GETTERS_______________________________________________________*/

}
