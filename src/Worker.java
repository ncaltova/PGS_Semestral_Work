import java.util.Random;

public class Worker {

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

/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of worker with assigned job and lorry to load.
     * @param assignedWorkBlock Work block assigned to worker.
     * @param workerTime Maximum speed to mine one field in work block for worker.
     */
    public Worker(WorkBlock assignedWorkBlock, int workerTime, Mine assignedMine) {

        this.assignedWorkBlock = assignedWorkBlock;
        this.workerTime = workerTime;
        this.isDone = false;
        this.assignedMine = assignedMine;
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method representing worker mining assigned work block.
     * Mining one block takes worker between zero (exlusive) and max mining time tWorker (inclusive).
     */
    public void mine() {

        while (!assignedWorkBlock.isEmpty()){
            //wait for generateTime();
            assignedWorkBlock.mineField();
        }

    }

    /**
     * Method representing worker loading lorry with mined material.
     * Loading one mined field takes one second.
     */
    public void load() {
        //wait for assignedWorkBlock.getFieldCount;
        assignedMine.getCurrentLorry().load();
        this.isDone = true;
    }

    /**
     * Method generating time it takes worker to mine one field in work block.
     * @return Time it takes worker to mine one field in work block.
     */
    public int generateTime() {
        Random rd = new Random();

        return rd.nextInt(this.workerTime);
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
