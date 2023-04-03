/**
 * Instances of this class represent data transfer object of workers information.
 * @author Nikol Caltova.
 * @version 1.0
 */

public class InfoWorkers {
/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Maximum time it takes worker to mine one field in work block.
     */
    private final int workerTime;

    /**
     * Number of available workers.
     */
    private final int availableWorkers;

/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor that creates instance of worker info carrier.
     * @param workerTime Maximum time it takes worker to mine one field in work block.
     * @param availableWorkers Number of available workers.
     */
    public InfoWorkers(int workerTime, int availableWorkers) {

        this.workerTime = workerTime;
        this.availableWorkers = availableWorkers;

    }

/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter that returns maximum time it takes worker to mine one field in work block.
     * @return Maximum time it takes worker to mine one field in work block.
     */
    public int getWorkerTime() {
        return workerTime;
    }

    /**
     * Getter that returns number of available workers.
     * @return Number of available workers.
     */
    public int getAvailableWorkers() {
        return availableWorkers;
    }
}
