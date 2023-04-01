public class SandMan {

    public static void waitFor(int waitTime) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;

        while (elapsedTime < waitTime) {
            Thread.sleep(waitTime - elapsedTime);
            elapsedTime = System.currentTimeMillis() - startTime;
        }
    }

}
