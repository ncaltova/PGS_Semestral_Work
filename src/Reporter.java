import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Reporter {
/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/
    /**
     * Writer used to write reports to report file.
     */
    private final FileWriter fileWriter;

    /**
     * Report file to write reports to.
     */
    private final File reportFile;

    /**
     * Current time of simulation.
     */
    private int simTime;
/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    public Reporter(String reportFileName) throws IOException {
        this.reportFile = new File(reportFileName);
        this.fileWriter = new FileWriter(reportFile);
        this.simTime = 0;
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    public synchronized void report(String log) throws IOException {
        this.fileWriter.write(log);
        System.out.println(log);
    }

/*______________________________________________________GETTERS_______________________________________________________*/

/*______________________________________________________SETTERS_______________________________________________________*/
}
