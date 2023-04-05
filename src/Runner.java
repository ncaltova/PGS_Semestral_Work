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

    /**
     * Starting time of simulation
     */
    private static long startTime;

    /**
     * Wait time for main thread.
     */
    private static final int WAIT_TIME = 10;


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

        //While all workers are not done and all lorries are not done wait
        while (!shiftLeader.allWorkersDone()){
            SandMan.waitFor(WAIT_TIME);
        }

        //Reporting total mined fields
        shiftLeader.reportWorkers();

        //Wait until all lorries are done working
        while (!activeMine.areLorriesDone()){
            SandMan.waitFor(WAIT_TIME);
        }

        //Reporting total dispatched material
        activeMine.countAllDispatched();
        activeMine.reportTotalDispatched();
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

        //Starting simulation
        startTime = System.currentTimeMillis();

        //Reporting loaded parameters
        reportParameters();

        //Initialization of mining site

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

/*___________________________________________________SIMULATION_INIT__________________________________________________*/

    /**
     * Method serving as reporter of loaded command-line parameters.
     */
    private static void reportParameters(){
        reporter.reportToConsole("Time: " + (System.currentTimeMillis() - startTime) + ", Role: Runner, "+
        "Message: Command-line parameters loaded\n" +
        "Input file: "+parameterCarrier.getInputFile()+"\n"+
        "Output file: "+parameterCarrier.getOutputFile()+"\n"+
        "Maximum worker count: "+parameterCarrier.getcWorker()+"\n"+
        "Maximum worker time: "+parameterCarrier.gettWorker()+"\n"+
        "Maximum lorry capacity: "+parameterCarrier.getCapLorry()+"\n"+
        "Maximum lorry time: "+parameterCarrier.gettLorry()+"\n"+
        "Maximum ferry capacity: "+parameterCarrier.getCapFerry()+"\n");
    }

}
