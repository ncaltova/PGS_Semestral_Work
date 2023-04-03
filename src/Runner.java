import java.io.IOException;
import java.util.concurrent.CyclicBarrier;

/**
 * Class serving as simulation administrator.
 * @author Nikol Caltova.
 * @version 1.0
 */
public class Runner {

/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Instance of command-line arguments carrier.
     */
    private static ParameterCarrier parameterCarrier;

    /**
     * Instance of key events reporter.
     */
    private static Reporter reporter;

    /**
     * Instance of active mine shift leader.
     */
    private static Leader shiftLeader;

    /**
     * Instance of currently active mine.
     */
    private static Mine activeMine;

    private static final int WAIT_TIME = 100;

/*___________________________________________________MAIN_METHOD______________________________________________________*/

    /**
     * Run method of main thread.
     * @param args Command-line arguments.
     */
    public static void main(String[] args){

        try {

            //Initialization od mining site
            simulationInit(args);

            //Leader inspecting mining site
            shiftLeader.inspectMiningSite();

        } catch (IOException e) {
            System.out.println("Either input file could not be found or output file could not be found or created, " +
                    "exiting program...");
            throw new RuntimeException(e);
        }

        //Simulation
        simulationLoop();

        reporter.closeReporter();
    }

/*___________________________________________________SIMULATION_LOOP__________________________________________________*/

    /**
     * Method serving as main simulation loop.
     */
    private static void simulationLoop(){

        //Initialization of mine workers
        while (!activeMine.isEmpty()) {
            shiftLeader.organizeWorkers();
        }

        //While all workers are not done wait
        while (!shiftLeader.allWorkersDone()){
            SandMan.waitFor(WAIT_TIME);
        }

        //Dispatching partially loaded lorry if it is needed.
        if (activeMine.dispatchLastLorry()){
            while (!activeMine.isLorryDone()){
                SandMan.waitFor(WAIT_TIME);
            }
        }
    }

/*___________________________________________________SIMULATION_INIT__________________________________________________*/

    /**
     * Initialization of simulation from command-line arguments.
     * @param args Command-line arguments.
     * @throws IOException If output file could not be found or created.
     */
    private static void simulationInit(String[] args) throws IOException {
        //Parsing user input
        parameterCarrier = parseInput(args);

        //Initialize reporter
        reporter = reporterInit();

        //Initialization of mining site

        //Starting simulation
        long startTime = System.currentTimeMillis();

        //Ferry init
        CyclicBarrier ferry = new CyclicBarrier(parameterCarrier.getCapFerry());

        //Mine init
        InfoLorry infoLorry = new InfoLorry(parameterCarrier.getCapLorry(), parameterCarrier.gettLorry(), ferry);
        MineDock dock = new MineDock(infoLorry, reporter, startTime);

        activeMine = new Mine(dock);

        //Shift leader init
        shiftLeader = new Leader(parameterCarrier.getInputFile(), activeMine, new InfoWorkers(parameterCarrier.gettWorker(),
                parameterCarrier.getcWorker()), reporter, startTime);
    }

    /**
     * Method serving to create instance of parameter carrier form command-line arguments.
     * @param args Command-line arguments.
     * @return Parameter carrier.
     */
    private static ParameterCarrier parseInput(String[] args){
        return new ParameterCarrier(args);
    }

    /**
     * Method serving as initialization of simulation reporter.
     * @return Reporter.
     * @throws IOException If output file could not be found or created.
     */
    private static Reporter reporterInit() throws IOException {
        return new Reporter(parameterCarrier.getOutputFile());
    }

}
