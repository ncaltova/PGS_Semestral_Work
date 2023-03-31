import java.util.Random;

public class Worker extends Thread{

/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Work block assigned to worker by leader.
     */
    private WorkBlock assignedWorkBlock;

    /**
     * Mine where assigned workc block is located.
     */
    private Mine assignedMine;

    /**
     * Maximum time it takes worker to mine one field in work block.
     */
    private final int workerTime;

    /**
     * Indicator if worker is done with assigned job.
     */
    private boolean isDone;

    /**
     * Number of mined materials.
     */
    private int minedMaterial;

    /**
     * Current simulation time for this thread.
     */
    private int simTime;

    /**
     *
     */
    private Reporter reporter;



    /*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of worker with assigned job and lorry to load.
     * @param assignedWorkBlock Work block assigned to worker.
     * @param workerTime Maximum speed to mine one field in work block for worker.
     */
    public Worker(WorkBlock assignedWorkBlock, int workerTime, Mine assignedMine, int simTime, Reporter reporter) {

        this.assignedWorkBlock = assignedWorkBlock;
        this.workerTime = workerTime;
        this.assignedMine = assignedMine;
        this.minedMaterial = assignedWorkBlock.getFieldCount();
        this.simTime = simTime;
        this.reporter = reporter;

    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    @Override
    public void run() {
        this.isDone = false;
        this.mine();
        this.load();
        this.isDone = true;
    }

    /**
     * Method representing worker mining assigned work block.
     * Mining one block takes worker between zero (exlusive) and max mining time tWorker (inclusive).
     */
    public void mine() {
        int mineTime = 0;

        while (!assignedWorkBlock.isEmpty()){
            assignedWorkBlock.mineField();
            int currentTime = this.generateTime();
            this.sleep(currentTime);
            mineTime += currentTime;
        }

        reporter.report("Simulation time: "+this.simTime+"ms, Role: Worker, Number of thread: "+this.getId()+", Message:" +
                " Mining one work block done, final time: "+mineTime+"ms");

    }

    /**
     * Method representing worker loading lorry with mined material.
     * Loading one mined field takes one second.
     */
    public void load() {

        while (this.minedMaterial > 0){
           //If lorry did not dock at mine yet
           if (!assignedMine.lorryAvailable()) continue;

           //Load one piece of mined material
           this.minedMaterial--;
           this.sleep(10);

           //If current lorry is full, set it to ferry
           if (!assignedMine.loadLorry()) assignedMine.dispatchLorry();
        }

    }

    /**
     * Method generating time it takes worker to mine one field in work block.
     * @return Time it takes worker to mine one field in work block.
     */
    public int generateTime() {
        Random rd = new Random();
        return (rd.nextInt(this.workerTime) + 1)*1000;
    }

/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter, that returns indicator if worker is done with assigned job.
     * @return Indicator if worker is done with assigned job.
     */
    public boolean isDone() {
        return isDone;
    }

/*______________________________________________________SETTERS_______________________________________________________*/

    /**
     * Setter, that sets current work block worker is working on.
     * @param assignedWorkBlock Current work block worker is working on.
     */
    public void setAssignedWorkBlock(WorkBlock assignedWorkBlock) {
        this.assignedWorkBlock = assignedWorkBlock;
    }

    /**
     * Setter, that sets indicator of whether worker is done mining and loading lorry.
     * @param done Indicator of whether worker is done mining and loading lorry.
     */
    public void setDone(boolean done) {
        isDone = done;
    }
}
