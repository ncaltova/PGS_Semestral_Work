import java.util.Random;

public class Worker implements Runnable{

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
     * Instance of state reporter
     */
    private Reporter reporter;
    private long startTime;

    /*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of worker with assigned job and lorry to load.
     * @param workerTime Maximum speed to mine one field in work block for worker.
     */
    public Worker(int workerTime, Mine assignedMine, Reporter reporter, long startTime) {

        this.assignedWorkBlock = null;
        this.workerTime = workerTime;
        this.assignedMine = assignedMine;
        this.minedMaterial = 0;
        this.reporter = reporter;
        this.startTime = startTime;
        this.isDone = true;

    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    @Override
    public void run() {
        try {
            this.minedMaterial = 0;

            this.mine();
            this.load();

            this.isDone = true;

        } catch (InterruptedException e) {
            System.out.println("Thread id#"+Thread.currentThread().getId()+" was interrupted during its work, exiting program ...");
            throw new RuntimeException(e);
        }
    }

    private void reportMinedField(long timeElapsed){
        this.reporter.report("Time: " + (System.currentTimeMillis() - this.startTime) + ", Role: Worker, ThreadID: " +
                Thread.currentThread().getId() + ", Message: One field form assigned block successfully mined," +
                " Time elapsed: "+ timeElapsed);
    }

    private void reportMinedBlock(long timeElapsed){
        this.reporter.report("Time: " + (System.currentTimeMillis() - this.startTime) + ", Role: Worker, ThreadID: " +
                Thread.currentThread().getId() + ", Message: Assigned work block successfully mined," +
                " Time elapsed: "+ timeElapsed);
    }

    /**
     * Method representing worker mining assigned work block.
     * Mining one block takes worker between zero (exlusive) and max mining time tWorker (inclusive).
     */
    public void mine() throws InterruptedException {
        long waitTime = this.generateTime();
        long start = System.currentTimeMillis();

        while (!assignedWorkBlock.isEmpty()){

            if (!assignedWorkBlock.isEmpty()) {

                assignedWorkBlock.mineField();
                SandMan.waitFor(waitTime);

                this.reportMinedField(waitTime);

                this.minedMaterial++;
            }

        }

        this.reportMinedBlock(System.currentTimeMillis() - start);
    }

    /**
     * Method representing worker loading lorry with mined material.
     * Loading one mined field takes one second.
     */
    public void load() throws InterruptedException {

        while (this.minedMaterial > 0){
           //If lorry did not dock at mine yet
           if (!assignedMine.lorryAvailable()) continue;

           //Load one piece of mined material
           this.minedMaterial--;
            SandMan.waitFor(10);

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
