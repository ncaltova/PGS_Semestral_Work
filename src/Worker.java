import java.util.Random;

/**
 * Instances of this class represent individual workers working in mine.
 * @author Nikol Caltova.
 * @version 1.0
 */
public class Worker implements Runnable{

/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Work block assigned to worker by leader.
     */
    private WorkBlock assignedWorkBlock;

    /**
     * Mine where assigned work block is located.
     */
    private final Mine assignedMine;

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
     * Instance of state reporter.
     */
    private final Reporter reporter;

    /**
     * Starting time of the whole simulation.
     */
    private final long startTime;

/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of worker with assigned job and lorry to load.
     * @param workerTime Maximum speed to mine one field in work block for worker.
     * @param reporter Instance of state reporter.
     * @param startTime Starting time of the whole simulation.
     * @param assignedMine Mine where assigned work block is located.
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
/*____________________________________________________METHOD_RUN______________________________________________________*/

    /**
     * Method representing the work of one worker after being assigned with work.
     */
    @Override
    public void run() {
        this.minedMaterial = 0;

        this.mine();
        this.load();

        this.isDone = true;
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method representing worker mining assigned work block.
     * Mining one block takes worker between zero (exlusive) and max mining time tWorker (inclusive).
     */
    public void mine() {
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
    public void load() {

        while (this.minedMaterial > 0){
            if (assignedMine.isLorryFull()) continue;

           //Load one piece of mined material
           this.minedMaterial--;
           SandMan.waitFor(10);

           //If current lorry is full, set it to ferry
           if (!assignedMine.loadLorry()) assignedMine.dispatchLorry();
        }

    }

    /**
     * Method serving for assigning work to this worker.
     * @param workBlock Work block to be assigned.
     */
    public void giveWork(WorkBlock workBlock){
        this.assignedWorkBlock = workBlock;
        this.isDone = false;
    }

/*__________________________________________________TIME_GENERATORS___________________________________________________*/

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

/*______________________________________________________REPORTS_______________________________________________________*/

    /**
     * Method serving to report the event of mining out one field out of assigned work block.
     * @param timeElapsed Time it took to mine out said field.
     */
    private void reportMinedField(long timeElapsed){
        this.reporter.report("Time: " + (System.currentTimeMillis() - this.startTime) + ", Role: Worker, ThreadID: " +
                Thread.currentThread().getId() + ", Message: One field form assigned block successfully mined," +
                " Time elapsed: "+ timeElapsed);
    }

    /**
     * Method serving to report the event of mining out assigned work block.
     * @param timeElapsed Time it took to mine out assigned work block.
     */
    private void reportMinedBlock(long timeElapsed){
        this.reporter.report("Time: " + (System.currentTimeMillis() - this.startTime) + ", Role: Worker, ThreadID: " +
                Thread.currentThread().getId() + ", Message: Assigned work block successfully mined," +
                " Time elapsed: "+ timeElapsed);
    }

}
