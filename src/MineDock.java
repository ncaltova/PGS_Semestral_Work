import java.util.ArrayList;

/**
 * Instances of this class represent dock for lorries that takes care of lorry traffic.
 * @author Nikol Caltova
 * @version 1.0
 */
public class MineDock {
/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Current lorry waiting in the dock to be loaded.
     */
    private volatile Lorry currentLorry;

    /**
     * Information about lorries.
     */
    private final InfoLorry infoLorry;

    /**
     * Instance of reporter for reporting key events.
     */
    private final Reporter reporter;

    /**
     * List of all dispatched lorries
     */
    private final ArrayList<Lorry> dispatchedLorries;

    /**
     * Number of all dispatched material.
     */
    private int allDispatchedMaterial;

    /**
     * Starting time of the whole simulation.
     */
    private final long startTime;

    /*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of one dock in the mine with the first lorry
     * waiting in the dock to be loaded.
     * @param startTime Starting time of the whole simulation.
     * @param infoLorry Information about lorries.
     * @param reporter Instance of reporter for reporting key events.
     */
    public MineDock(InfoLorry infoLorry, Reporter reporter, long startTime) {

        this.infoLorry = infoLorry;
        this.reporter = reporter;
        this.startTime = startTime;
        this.currentLorry = new Lorry(infoLorry.getLorryCapacity(), infoLorry.getLorryTime(),
                infoLorry.getAssignedFerry(), reporter, startTime);
        this.dispatchedLorries = new ArrayList<>();
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method serving for dispatching full lorries by workers during simulation.
     */
    public synchronized void dispatchLorry(){
        if (!this.currentLorry.isFull()) return;

        //Adding lorry to the list of dipatched lorries
        this.dispatch();

        this.currentLorry = new Lorry(this.infoLorry.getLorryCapacity(), this.infoLorry.getLorryTime(),
                this.infoLorry.getAssignedFerry(), this.reporter, this.startTime);
    }

    /**
     * Method serving for dispatching current lorry.
     */
    private synchronized void dispatch() {

        Thread dispatchedLorry = new Thread(this.currentLorry);

        try {
            dispatchedLorry.start();
            this.dispatchedLorries.add(currentLorry);
        }
        catch (IllegalThreadStateException e) {
            this.dispatch();
        }

    }

    /**
     * Method representing loading current lorry with one minefield.
     */
    public synchronized void loadLorry(){
        this.currentLorry.load();
    }

    /**
     * Method that counts all dispatched material.
     */
    public void countAllDispatched(){
        for (Lorry lorry: this.dispatchedLorries) {
            if (lorry.isDone()) this.allDispatchedMaterial += lorry.getLorryCapacity();
        }
    }


/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter that returns indicator of whether current lorry is full or not.
     * @return indicator of whether current lorry is full or not.
     */
    public boolean isLorryFull(){
        return this.currentLorry.isFull();
    }

    /**
     * Getter that returns indicator of whether all lorries are done with their work.
     * @return indicator of whether all lorries are done with their work.
     */
    public boolean areLorriesDone(){
        for (Lorry lorry: this.dispatchedLorries) {
            if (!lorry.isDone()) return false;
        }
        return true;
    }

/*______________________________________________________REPORTERS_______________________________________________________*/

    /**
     * Method that reports total dispatched material by this dock.
     */
    public void reportTotalDispatched(){
        this.reporter.reportToConsole("Time: " + (System.currentTimeMillis() - this.startTime) + ", Role: Dock, ThreadID: undef," +
                " Message: Total dispatched fields: "+this.allDispatchedMaterial);
    }

}
