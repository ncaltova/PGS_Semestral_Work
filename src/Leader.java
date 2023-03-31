import java.io.FileNotFoundException;
import java.util.ArrayList;
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

    private InfoWorkers infoWorkers;

    private Reporter reporter;

    /*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of shift leader that is assigned a mine and name of the site map.
     * @param siteMapName Name of the site map.
     */
    public Leader(String siteMapName, Mine assignedMine, InfoWorkers infoWorkers, Reporter reporter) {
        this.siteMapName = siteMapName;
        this.assignedMine = assignedMine;
        this.mineWorkers = new ArrayList<>();
        this.infoWorkers = infoWorkers;
        this.reporter = reporter;
    }

    /*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method representing shift leader inspecting mining site for mining blocks.
     * @throws FileNotFoundException If site map cannot be found.
     */
    public void inspectMiningSite() throws FileNotFoundException {
        int mineBlocks = this.assignedMine.getWorkBlocks().size();
        int mineFields = 0;

        this.assignedMine.setWorkBlocks(Parser.parseIntoBlocks(siteMapName));

        for (WorkBlock workBlock: this.assignedMine.getWorkBlocks()) {
            mineFields += workBlock.getFieldCount();
        }

        reporter.report("Simulation time: 0ms, Role: Leader, Number of thread: undef, Message:" +
                " Inspection of mining site done, mine blocks: "+mineBlocks+", mine fields: "+mineFields);
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

        if (this.mineWorkers.size() == this.infoWorkers.getAvailableWorkers())  return null;

        Worker newWorker = new Worker(null, infoWorkers.getWorkerTime(), assignedMine);
        this.mineWorkers.add(newWorker);
        return newWorker;
    }

    public void organizeWorkers(){
        Worker worker;
        for (WorkBlock workBlock: this.assignedMine.getWorkBlocks()){
            if ((worker = this.findWorker()) == null) return;
            worker.setAssignedWorkBlock(workBlock);
        }
    }

    public void startMining(){
        for (Worker worker: this.mineWorkers) {
            worker.start();
        }
    }

/*______________________________________________________GETTERS_______________________________________________________*/

}
