import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Instances of this class represent lorry waiting to be loaded at mine and taking the cargo to final destination.
 * @author Nikol Caltova
 * @version 1.0
 */
public class Lorry implements Runnable{

/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Current capacity of lorry.
     */
    private volatile int currentCapacity;

    /**
     * Time it takes lorry to get from work block to ferry
     * and vice versa.
     */
    private final int lorryTime;

    /**
     * Indicator if lorry is fully loaded.
     */
    private volatile boolean isFull;

    /**
     * Assigned dock with waiting ferry.
     */
    private final CyclicBarrier assignedFerry;

    /**
     * Instance of reporter for reporting key events.
     */
    private final Reporter reporter;

    /**
     * Starting time of the whole simulation.
     */
    private final long startTime;

    /**
     * Time of creation of this instance.
     */
    private final long timeCreated;

    /**
     * Indicator of weather this instance is done with its work or not.
     */
    private boolean isDone;

    /**
     * Indicator of weather this instance has been at least partially loaded.
     */
    private boolean isLoaded;

/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of Lorry
     * with specified capacity, time it takes to get to ferry (and vice versa) and target ferry.
     * @param lorryCapacity Maximum capacity of lorry.
     * @param lorryTime Time it takes lorry to get to ferry (and vice versa).
     */
    public Lorry(int lorryCapacity, int lorryTime, CyclicBarrier assignedFerry, Reporter reporter, long startTime) {

        this.currentCapacity = lorryCapacity;
        this.lorryTime = lorryTime;
        this.isFull = false;
        this.assignedFerry = assignedFerry;
        this.reporter = reporter;
        this.startTime = startTime;
        this.timeCreated = System.currentTimeMillis();
        this.isLoaded = false;

    }

/*____________________________________________________METHOD_RUN______________________________________________________*/

    /**
     * Method representing the work of one lorry after being loaded.
     * @throws IllegalThreadStateException If the lorry is interrupted either on its way to ferry or its way from ferry
     * to finish.
     */
    @Override
    public void run() throws IllegalThreadStateException{

        try {

            this.reportLoaded(System.currentTimeMillis() - this.timeCreated);
            this.goToFerry();
            this.goToFinish();

        } catch (BrokenBarrierException e) {

            System.out.println("Barrier has been broken during its work, trying to resolve issue...");
            assignedFerry.reset();

            //Passing message of broken barrier to others.
            throw new IllegalThreadStateException();

        } catch (InterruptedException e) {

            System.out.println("Thread id#"+Thread.currentThread().getId()+" was interrupted during its way to final destination" +
                    ", trying to resolve issue...");

            this.isDone = false;

            //Passing message of interruption to others.
            throw new IllegalThreadStateException();

        }

    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method representing lorry going from ferry to finish.
     */
    private void goToFinish() {

        //Time it will take this lorry to get to finish
        long finishTime = this.generateFinishTime();

        //Lorry going to finish
        SandMan.waitFor(finishTime);

        //Lorry reporting reaching the final destination.
        this.reportFinish(finishTime);

        this.isDone = true;
    }

    /**
     * Method representing lorry going from mine dock to ferry and waiting on ferry to be transfered by ferry to the
     * other side of river.
     * @throws InterruptedException If lorry is interrupted during its waiting on ferry.
     * @throws BrokenBarrierException If ferry is broken during the wait of all lorries.
     */
    private void goToFerry() throws InterruptedException, BrokenBarrierException {

        //Time it will take this lorry to go to the ferry.
        long ferryTime = this.generateFerryTime();

        //Indicator whether this lorry is the first lorry on the ferry.
        boolean firstOnFerry = false;

        //The start of ferry waiting to be fully loaded.
        long ferryStartTime = 0;

        //Lorry going to ferry.
        SandMan.waitFor(ferryTime);

        //Lorry reporting reaching ferry
        this.reportFerry(ferryTime);

        /*If this lorry is first on ferry indicator is set to true and
        time starts to measure. */
        if (assignedFerry.getNumberWaiting() == 0) {
            firstOnFerry = true;
            ferryStartTime = System.currentTimeMillis();
        }

        //Lorry getting on ferry.
        this.assignedFerry.await();

        //If indicator is set to true report the transfer of loaded lorries by ferry.
        if (firstOnFerry) reportFerryDepart(System.currentTimeMillis() - ferryStartTime);
    }

    /**
     * Method representing loading lorry with mined material.
     */
    public synchronized void load(){
        this.currentCapacity--;
        this.isLoaded = true;
        if (this.currentCapacity == 0) this.isFull = true;
    }


/*__________________________________________________TIME_GENERATORS___________________________________________________*/

    /**
     * Method generating time it takes
     * lorry to get to its final destination.
     * @return Time it takes lorry to get to its final destination.
     */
    private int generateFinishTime(){
        Random rd = new Random();

        return rd.nextInt(this.lorryTime - 1) + 1;
    }

    /**
     * Method generating time it takes
     * lorry to get to dock with waiting ferry.
     * @return Time it takes lorry to get to dock with waiting ferry.
     */
    private int generateFerryTime(){
        Random rd = new Random();

        return rd.nextInt(this.lorryTime - 2) + 1;
    }

/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter that returns indicator if lorry is fully loaded.
     * @return Indicator if lorry is fully loaded.
     */
    public synchronized boolean isFull() {
        return isFull;
    }

    /**
     * Getter that returns indicator whether lorry reached final destination.
     * @return indicator whether lorry reached final destination.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Getter that returns indicator of weather this instance has been at least partially loaded.
     * @return Indicator of weather this instance has been at least partially loaded.
     */
    public boolean isLoaded() {
        return isLoaded;
    }

    /*______________________________________________________REPORTS_______________________________________________________*/

    /**
     * Method serving as report of this lorry fully loaded event.
     * @param timeElapsed Time it took to fully load this lorry.
     */
    private void reportLoaded(long timeElapsed) {
        this.reporter.report("Time: " + (System.currentTimeMillis() - this.startTime) + ", Role: Lorry, ThreadID: " +
                Thread.currentThread().getId() + ", Message: Current lorry has been fully loaded," +
                " Time elapsed: "+ timeElapsed);
    }

    /**
     * Method serving as report of this lorry reaching ferry.
     * @param timeElapsed Time it took this lorry to reach ferry.
     */
    private void reportFerry(long timeElapsed) {
        this.reporter.report("Time: " + (System.currentTimeMillis() - this.startTime) + ", Role: Lorry, ThreadID: " +
                Thread.currentThread().getId() + ", Message: Lorry successfully reached ferry," +
                " Time elapsed: "+ timeElapsed);
    }

    /**
     * Method serving as report of this lorry reaching finish.
     * @param timeElapsed Time it took this lorry to reach finish.
     */
    private void reportFinish(long timeElapsed) {
        this.reporter.report("Time: " + (System.currentTimeMillis() - this.startTime) + ", Role: Lorry, ThreadID: " +
                Thread.currentThread().getId() + ", Message: Lorry successfully reached its finish," +
                " Time elapsed: "+ timeElapsed);
    }

    /**
     * Method serving as report of ferry being fully loaded and transferring to the other side.
     * @param timeElapsed Time it took ferry to fully load.
     */
    private void reportFerryDepart(long timeElapsed) {
        this.reporter.report("Time: " + (System.currentTimeMillis() - this.startTime) + ", Role: Ferry, ThreadID: undef," +
                " Message: Ferry has departed, Time elapsed: "+ timeElapsed);
    }
}
