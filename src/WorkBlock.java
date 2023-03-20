public class WorkBlock {

/*_________________________________________________CLASS_ATTRIBUTES___________________________________________________*/

    /** Number of fields in work block */
    private final int fieldCount;
    /** Number of fields left in work block */
    private int fieldLeftCount;
    /** Boolean indicating whether the work block is empty or not */
    private boolean isEmpty;

/*___________________________________________________CONSTRUCTORS_____________________________________________________*/

    /**
     * Constructor, that creates instance of
     * working block with number of fields specified in parameter.
     * @param fieldCount Number of fields in working block.
     */
    public WorkBlock(int fieldCount) {

        this.fieldCount = fieldCount;
        this.fieldLeftCount = fieldCount;

    }

/*_________________________________________________INSTANCE_METHODS___________________________________________________*/

    /**
     * Method that represents mining out one field of work block.
     * Decrements field count and sets isEmpty indicator to the corresponding value.
     */
    public void mineField(){
        this.fieldLeftCount--;
        if (this.fieldLeftCount == 0) this.isEmpty = true;
    }

/*______________________________________________________GETTERS_______________________________________________________*/

    /**
     * Returns number of fields left in work block.
     * @return Number of fields left in work block.
     */
    public int getFieldCount() {
        return fieldCount;
    }

    /**
     * Returns boolean indicating whether the work block is empty or not.
     * @return True if there are still fields left in work block,
     * if not returns false.
     */
    public boolean isEmpty() {
        return isEmpty;
    }

}
