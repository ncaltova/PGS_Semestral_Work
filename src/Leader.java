import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represent shift leader that organizes miners and analyzes mine before mining starts.
 * @author Nikol Caltova
 * @version 1.0
 */
public class Leader {
/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Assigned mining site.
     */
    private final Mine assignedMine;

    /**
     * Mining site map for leader.
     */
    private final String siteMapName;

    /**
     * Workers answering to leader.
     */
    private final List<Worker> mineWorkers;

    /**
     * Information about mine workers.
     */
    private final InfoWorkers infoWorkers;

    /**
     * Reporter for reporting key events.
     */
    private final Reporter reporter;

    /**
     * Starting time of the whole simulation.
     */
    private final long startTime;

/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of shift leader that is assigned a mine and name of the site map.
     * @param siteMapName Name of the site map.
     * @param assignedMine Assigned mining site.
     * @param infoWorkers Information about mine workers.
     * @param reporter Reporter for reporting key events.
     * @param startTime Starting time of the whole simulation.
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
        int mineBlocks;
        int mineFields = 0;

        //Inspecting mining site
        this.assignedMine.setWorkBlocks(Parser.parseIntoBlocks(siteMapName));

        //Saving count of work blocks for report
        mineBlocks = this.assignedMine.getWorkBlocks().size();

        //Saving count of total mining fields
        for (WorkBlock workBlock : this.assignedMine.getWorkBlocks()) {
            mineFields += workBlock.getFieldCount();
        }

        //Reporting found information about mining site
        this.reportInspection(mineBlocks, mineFields);
    }

    /**
     * Method representing shift organizing workers for mining.
     * For each work block not already taken leader finds free worker and starts mining process.
     * If no free worker is found leader stops looking for assignees.
     */
    public void organizeWorkers() throws IllegalAccessError{
        Worker worker;

        for (WorkBlock workBlock: this.assignedMine.getWorkBlocks()){
            //If work block has already been assigned or mined out
            if (workBlock.isTaken() || workBlock.isEmpty()) continue;

            //If there is no free worker at the moment
            if ((worker = this.findWorker()) == null) return;

            //Tag work block as assigned
            workBlock.takeBlock();

            //Assign work block to free worker
            worker.giveWork(workBlock);

            //Start mining
            this.startMining(worker);
        }
    }

    /**
     * Method representing leader finding free worker in assigned workers,
     * if no free worker is found and maximum capacity of workers is not reached a new one is created.
     * @return Free worker.
     */
    private Worker findWorker(){

        //If already existing worker is free return him
        for (Worker worker: this.mineWorkers) {
            if (worker.isDone()) return worker;
        }

        //If no existing worker is free and worker capacity has been met return null
        if (this.mineWorkers.size() == this.infoWorkers.getAvailableWorkers())  return null;

        //Else return new worker
        Worker newWorker = new Worker(this.infoWorkers.getWorkerTime(), this.assignedMine,
                this.reporter, this.startTime);

        //Add new worker to list of already existing workers under this leader
        this.mineWorkers.add(newWorker);

        //And return new worker
        return newWorker;
    }

    /**
     * Method representing leader sending worker to assigned job.
     * @param worker Worker to be sent.
     */
    private void startMining(Worker worker){
        Thread occupiedWorker = new Thread(worker);
        occupiedWorker.start();
    }

    /**
     * Method serving as reporter of total mined material at the end of simulation.
     */
    public void reportWorkers(){
        int i = 0;

        for ( Worker worker : this.mineWorkers) {
            this.reportTotalMined(worker.getAllMinedMaterial(), i);
            i++;
        }

    }

/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter, returning boolean value indicating if all workers are done with assigned job.
     * @return True if all workers are done, otherwise false.
     */
    public boolean allWorkersDone(){

        for (Worker worker: this.mineWorkers) {
            if (!worker.isDone()) return false;
        }

        return true;
    }

/*______________________________________________________REPORTS_______________________________________________________*/

    /**
     * Method for reporting found information about mining site using instance of reporter.
     * @param mineBlocks Total work blocks count.
     * @param mineFields Total mining fields count.
     */
    private void reportInspection(int mineBlocks, int mineFields){

        String log = "Time: " + (System.currentTimeMillis() - this.startTime)  + ", Role: Leader, ThreadID: undef," +
                " Message: Inspection of mining site done, Mine blocks: " + mineBlocks +
                ", Mine fields: " + mineFields;

        this.reporter.reportToFile(log);
        this.reporter.reportToConsole(log);

    }

    /**
     * Method for reporting found information about mining site using instance of reporter.
     * @param mineBlocks Total work blocks count.
     * @param workerId ID of current worker.
     */
    private void reportTotalMined(int mineBlocks, int workerId){

        this.reporter.reportToConsole("Time: " + (System.currentTimeMillis() - this.startTime)  + ", Role: Leader, ThreadID: undef," +
                " Message: Total mined fields by worker: " + mineBlocks + ", WorkerID: "+ workerId);

    }

}
