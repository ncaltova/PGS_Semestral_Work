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
/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    public Reporter(String reportFileName) throws IOException {
        this.reportFile = new File(reportFileName);
        this.fileWriter = new FileWriter(reportFile);
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    public synchronized void report(String log) {
        try {
            this.fileWriter.write(log);
        } catch (IOException e) {
            System.out.println("Reporting to output file has failed, exiting program ...");
            throw new RuntimeException(e);
        }
        System.out.println(log);
    }

    public void closeReporter(){
        try {
            this.fileWriter.close();
        } catch (IOException e) {
            System.out.println("Closing reporter instance has failed, exiting program ...");
            throw new RuntimeException(e);
        }
    }

/*______________________________________________________GETTERS_______________________________________________________*/

/*______________________________________________________SETTERS_______________________________________________________*/
}
