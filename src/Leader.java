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
    private long startTime;

    /*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of shift leader that is assigned a mine and name of the site map.
     * @param siteMapName Name of the site map.
     */
    public Leader(String siteMapName, Mine assignedMine, InfoWorkers infoWorkers, Reporter reporter, long startTime) {

        this.siteMapName = siteMapName;
        this.assignedMine = assignedMine;
        this.mineWorkers = new ArrayList<>();
        this.infoWorkers = infoWorkers;
        this.reporter = reporter;
        this.startTime = startTime;

    }

    /*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method representing shift leader inspecting mining site for mining blocks.
     * @throws FileNotFoundException If site map cannot be found.
     */
    public void inspectMiningSite() throws FileNotFoundException {
        int mineBlocks = 0;
        int mineFields = 0;

        this.assignedMine.setWorkBlocks(Parser.parseIntoBlocks(siteMapName));
        mineBlocks = this.assignedMine.getWorkBlocks().size();

        for (WorkBlock workBlock : this.assignedMine.getWorkBlocks()) {
            mineFields += workBlock.getFieldCount();
        }

        this.reportInspection(mineBlocks, mineFields);
    }

    private void reportInspection(int mineBlocks, int mineFields){

        this.reporter.report("Time: " + (System.currentTimeMillis() - this.startTime)  + ", Role: Leader, ThreadID: " +
                Thread.currentThread().getId() + ", Message: Inspection of mining site done, Mine blocks: " + mineBlocks +
                ", Mine fields: " + mineFields);

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

        Worker newWorker = new Worker(this.infoWorkers.getWorkerTime(), this.assignedMine,
                this.reporter, this.startTime);

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
        Thread occupiedWorker;
        for (Worker worker: this.mineWorkers) {
            if (!worker.isDone()) continue;
            occupiedWorker = new Thread(worker);
            worker.run();
        }
    }

    public boolean allWorkersDone(){

        for (Worker worker: this.mineWorkers) {
            if (!worker.isDone()) return false;
        }

        return true;
    }

/*______________________________________________________GETTERS_______________________________________________________*/

}
