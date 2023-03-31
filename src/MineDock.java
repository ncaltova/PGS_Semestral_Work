
public class MineDock {
/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /**
     * Current lorry waiting in the dock to be loaded.
     */
    private Lorry currentLorry;

/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of one dock in the mine with the first lorry
     * waiting in the dock to be loaded.
     * @param firstLorry First lorry waiting in the dock to be loaded.
     */
    public MineDock(Lorry firstLorry) {
        this.currentLorry = firstLorry;
    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    public void dockAtDock(){

        while (!this.currentLorry.isDispatched()){
            this.currentLorry.sleep(500);
        }

        this.currentLorry = null;

    }

/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Getter that returns current lorry waiting in the dock to be loaded.
     * @return Current lorry waiting in the dock to be loaded.
     */
    public Lorry getCurrentLorry() {
        return currentLorry;
    }

/*______________________________________________________SETTERS_______________________________________________________*/

    /**
     * Setter that sets the current lorry waiting in the dock to be loaded.
     * @param currentLorry Current lorry waiting in the dock to be loaded.
     */
    public void setCurrentLorry(Lorry currentLorry) {
        this.currentLorry = currentLorry;
    }
}
