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
     */
    public InfoWorkers(int workerTime, int availableWorkers) {

        this.workerTime = workerTime;
        this.availableWorkers = availableWorkers;

    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

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

    /*______________________________________________________SETTERS_______________________________________________________*/
}
