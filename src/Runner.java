import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.CyclicBarrier;

public class Runner {

    public static void main(String[] args){
        //Parsing user input
        ParameterCarrier parameterCarrier = parseInput(args);

        //Initialization of simulation reporter
        Reporter reporter = null;

        try {
            reporter = new Reporter(parameterCarrier.getOutputFile());
        } catch (IOException e) {
            System.out.println("Could not initialize reporter, exiting program...");
            throw new RuntimeException(e);
        }

        //Initialization of mining site
        CyclicBarrier ferry = new CyclicBarrier(parameterCarrier.getCapFerry());
        Lorry firstLorry = new Lorry(parameterCarrier.getCapLorry(), parameterCarrier.gettLorry(), ferry, reporter);
        MineDock dock = new MineDock(firstLorry);
        Mine mine = new Mine(dock);
        Leader shiftLeader = new Leader(parameterCarrier.getInputFile(), mine, new InfoWorkers(parameterCarrier.gettWorker(),
                parameterCarrier.getcWorker()), reporter);

        //Leader inspecting mining site
        try {
            shiftLeader.inspectMiningSite();
        } catch (FileNotFoundException e) {
            System.out.println("Could not initialize mining site, exiting program...");
            throw new RuntimeException(e);
        }

        //Initialization of mine workers
        while (!mine.isEmpty()) {
            shiftLeader.organizeWorkers();
            shiftLeader.startMining();
        }

    }

    private static ParameterCarrier parseInput(String[] args){
        return new ParameterCarrier(args[1], args[3], Integer.parseInt(args[5]),
                Integer.parseInt(args[7]), Integer.parseInt(args[9]),
                Integer.parseInt(args[11]), Integer.parseInt(args[13]));
    }
}
