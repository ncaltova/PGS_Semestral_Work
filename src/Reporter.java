import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Instances of this class represent reporter of key events to output file and stdout.
 * @author Nikol Caltova
 * @version 1.0
 */
public class Reporter {
/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/
    /**
     * Writer used to write reports to report file.
     */
    private final FileWriter fileWriter;

/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Creates instance of reporter that reports to given output file and stdout.
     * @param reportFileName Given output file.
     * @throws IOException If given output file cannot be created.
     */
    public Reporter(String reportFileName) throws IOException {

        File reportFile = new File(reportFileName);

        this.fileWriter = new FileWriter(reportFile);
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method serving for reporting key events to given output file and stdout.
     * @param log Log to report.
     */
    public synchronized void report(String log) {
        try {
            this.fileWriter.write(log + "\n");
        } catch (IOException e) {
            System.out.println("Reporting to output file has failed, exiting program ...");
            throw new RuntimeException(e);
        }
        System.out.println(log);
    }

    /**
     * Closing file stream after the end of reporting.
     */
    public void closeReporter(){
        try {
            this.fileWriter.close();
        } catch (IOException e) {
            System.out.println("Closing reporter instance has failed, exiting program ...");
            throw new RuntimeException(e);
        }
    }

}
