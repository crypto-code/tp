package seedu.address.commons.core;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "Unknown command";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided is invalid";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_INVALID_NAME = "Invalid name! Names should contain only"
            + " alphabetical characters.";
    public static final String MESSAGE_TELEGRAM_FIELD_CANNOT_BE_EMPTY = "Invalid format! Telegram handle field cannot "
            + "be empty.";
    public static final String MESSAGE_TAG_FIELD_CANNOT_BE_EMPTY = "Invalid format! Tag field cannot "
            + "be empty.";
    public static final String MESSAGE_GITHUB_FIELD_CANNOT_BE_EMPTY = "Invalid format! Github field cannot "
            + "be empty.";
    public static final String MESSAGE_COMMAND_DESCRIPTION_CANNOT_BE_EMPTY = " command description cannot be empty!";
    public static final String MESSAGE_COMMAND_DOES_NOT_TAKE_PARAMETERS = " command doesn't take parameters!";
    public static final String MESSAGE_PARSE_COMMAND_ERROR = "Error encountered while parsing command!";
}
