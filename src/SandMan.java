/**
 * Class serving as tool to safely put threads to sleep.
 */
public class SandMan {

    /**
     * Method that puts thread to sleep and checks for spurious wake-ups.
     * @param waitTime Time said thread should be asleep for.
     */
    public static void waitFor(long waitTime) {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        //While condition not met keep thread asleep
        while (elapsedTime < waitTime) {
            try {
                /*If thread is interrupted elapsed time is not updated
                 and thread is put to sleep again*/
                Thread.sleep(waitTime - elapsedTime);
                elapsedTime = System.currentTimeMillis() - startTime;

            } catch (InterruptedException ignored) {}
        }
    }

}
