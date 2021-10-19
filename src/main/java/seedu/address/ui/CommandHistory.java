package seedu.address.ui;

import java.util.ArrayList;

/**
 * Class which stores previous commands which the user has entered.
 */
public class CommandHistory {
    public static final int EMPTY_INDEX = -1;
    public static final int FIRST_INDEX = 0;

    /** ArrayList of all the previously entered userStrings. **/
    private final ArrayList<String> commands = new ArrayList<>();

    /**
     * Current index of userString that is being pointed to.
     * -1 means userStrings is empty.
     */
    private int currentIndex = EMPTY_INDEX;

    /**
     * Adds a new string to the user string history.
     *
     * @param s String to be added.
     */
    public void add(String s) {
        commands.add(s);
        currentIndex++;
    }

    /**
     * Gets the previous command the user entered.
     *
     * @return previous command.
     */
    public String getPrevious() {
        if (currentIndex == EMPTY_INDEX) {
            return "";
        } else if (currentIndex == FIRST_INDEX) {
            return commands.get(FIRST_INDEX);
        }
        String currentString = commands.get(currentIndex);
        currentIndex--;
        return currentString;
    }

    /**
     * Gets the next command the user entered.
     *
     * @return next command.
     */
    public String getNext() {
        int lastIndex = commands.size() - 1;
        if (currentIndex == EMPTY_INDEX) {
            return "";
        } else if (currentIndex == lastIndex) {
            return "";
        }
        currentIndex++;
        return commands.get(currentIndex);

    }

    /**
     * Resets the current index point to the latest user command.
     */
    public void resetIndex() {
        currentIndex = commands.size() - 1;
    }

}
