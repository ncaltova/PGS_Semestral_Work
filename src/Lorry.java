import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class Lorry implements Runnable{

/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Maximum capacity of lorry.
     */
    private final int lorryCapacity;

    /**
     * Current capacity of lorry.
     */
    private int currentCapacity;

    /**
     * Time it takes lorry to get from work block to ferry
     * and vice versa.
     */
    private final int lorryTime;

    /**
     * Indicator if lorry is fully loaded.
     */
    private boolean isFull;

    /**
     * Assigned dock with waiting ferry.
     */
    private CyclicBarrier assignedFerry;

    private Reporter reporter;

    /*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of Lorry
     * with specified capacity, time it takes to get to ferry (and vice versa) and target ferry.
     * @param lorryCapacity Maximum capacity of lorry.
     * @param lorryTime Time it takes lorry to get to ferry (and vice versa).
     */
    public Lorry(int lorryCapacity, int lorryTime, CyclicBarrier assignedFerry, Reporter reporter) {
        this.lorryCapacity = lorryCapacity;
        this.currentCapacity = lorryCapacity;
        this.lorryTime = lorryTime;
        this.isFull = false;
        this.assignedFerry = assignedFerry;
        this.reporter = reporter;
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    @Override
    public void run() {
        try {

            SandMan.waitFor(this.generateFerryTime());
            this.assignedFerry.await();
            SandMan.waitFor(this.generateFinishTime());

        } catch (InterruptedException e) {
            System.out.println("Thread id#"+Thread.currentThread().getId()+" was interrupted during its work, exiting program ...");
            throw new RuntimeException(e);

        } catch (BrokenBarrierException e) {
            System.out.println("Barrier has been broken by one of running threads during its work, exiting program ...");
            throw new RuntimeException(e);
        }
    }

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

    /**
     * Methos representing loading lorry with mined material.
     */
    public synchronized void load(){
        this.currentCapacity--;
        if (this.currentCapacity == 0) this.isFull = true;
    }

/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter, that returns indicator if lorry is fully loaded.
     * @return Indicator if lorry is fully loaded.
     */
    public boolean isFull() {
        return isFull;
    }

/*______________________________________________________SETTERS_______________________________________________________*/

}
