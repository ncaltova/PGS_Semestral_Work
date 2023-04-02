import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Class representing static parser of input file.
 * @author Nikol Caltova
 */
public class Parser {

/*__________________________________________________STATIC_METHODS____________________________________________________*/

    /**
     * Method that inspects input file and returns number of work block and their size.
     * @param fileName Name of the input file.
     * @return List of found work blocks.
     * @throws FileNotFoundException If said input file cannot be found.
     */
    public static List<WorkBlock> parseIntoBlocks(String fileName) throws FileNotFoundException {
        File inputFile = new File(fileName);
        Scanner fileReader = new Scanner(inputFile);
        ArrayList<WorkBlock> outputList = new ArrayList<>();

        while (fileReader.hasNext()){
            String[] blocks = fileReader.nextLine().split(" ");

            for (String block : blocks) {
                WorkBlock newBlock = new WorkBlock(block.length());
                outputList.add(newBlock);
            }
        }

        return outputList;
    }
}
