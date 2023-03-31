
public class ParameterCarrier {

/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Name of the input file.
     */
    private final String inputFile;

    /**
     * Name of the output file.
     */
    private final String outputFile;

    /**
     * Maximum number of simultaneously working workers.
     */
    private final int cWorker;

    /**
     * Time it takes worker to mine one field.
     */
    private final int tWorker;

    /**
     * Maximum number of fields a lorry can handle.
     */
    private final int capLorry;

    /**
     * Maximum time it takes lorry to get to ferry and from ferry to final destination.
     */
    private final int tLorry;

    /**
     * Maximum number of lorries a ferry can handle.
     */
    private final int capFerry;
/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor that creates instance of simulation parameters carrier.
     * @param inputFile Name of the input file.
     * @param outputFile Name of the output file.
     * @param cWorker Maximum number of simultaneously working workers.
     * @param tWorker Time it takes worker to mine one field.
     * @param capLorry Maximum number of fields a lorry can handle.
     * @param tLorry Maximum time it takes lorry to get to ferry and from ferry to final destination.
     * @param capFerry Maximum number of lorries a ferry can handle.
     */
    public ParameterCarrier(String inputFile, String outputFile, int cWorker, int tWorker, int capLorry, int tLorry, int capFerry) {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.cWorker = cWorker;
        this.tWorker = tWorker;
        this.capLorry = capLorry;
        this.tLorry = tLorry;
        this.capFerry = capFerry;
    }

/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter that returns name of the input file.
     * @return Name of the input file.
     */
    public String getInputFile() {
        return inputFile;
    }

    /**
     * Getter that returns name of the output file.
     * @return Name of the output file.
     */
    public String getOutputFile() {
        return outputFile;
    }

    /**
     * Getter that returns maximum number of simultaneously working workers
     * @return Maximum number of simultaneously working workers
     */
    public int getcWorker() {
        return cWorker;
    }

    /**
     * Getter that returns time it takes worker to mine one field.
     * @return Time it takes worker to mine one field.
     */
    public int gettWorker() {
        return tWorker;
    }

    /**
     * Getter that returns maximum number of fields a lorry can handle.
     * @return Maximum number of fields a lorry can handle.
     */
    public int getCapLorry() {
        return capLorry;
    }

    /**
     * Getter that returns maximum time it takes lorry to get to ferry and from ferry to final destination.
     * @return Maximum time it takes lorry to get to ferry and from ferry to final destination.
     */
    public int gettLorry() {
        return tLorry;
    }

    /**
     * Getter that returns maximum number of lorries a ferry can handle.
     * @return Maximum number of lorries a ferry can handle.
     */
    public int getCapFerry() {
        return capFerry;
    }
}
