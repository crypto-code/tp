package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_COMMAND_DESCRIPTION_CANNOT_BE_EMPTY;
import static seedu.address.commons.core.Messages.MESSAGE_COMMAND_DOES_NOT_TAKE_PARAMETERS;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_PARSE_COMMAND_ERROR;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.ExportCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GithubCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ImportCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ShowCommand;
import seedu.address.logic.commands.TagCommand;
import seedu.address.logic.commands.TelegramCommand;
import seedu.address.logic.commands.UnfavouriteCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);
    private ArrayList<String> commandsWithDescription =
            new ArrayList<>(Arrays.asList(AddCommand.COMMAND_WORD, EditCommand.COMMAND_WORD,
                    DeleteCommand.COMMAND_WORD, FavouriteCommand.COMMAND_WORD,
                    UnfavouriteCommand.COMMAND_WORD, FindCommand.COMMAND_WORD, ExportCommand.COMMAND_WORD,
                    ShowCommand.COMMAND_WORD, ImportCommand.COMMAND_WORD, TagCommand.COMMAND_WORD)
            );
    private ArrayList<String> commandsWithoutDescription =
            new ArrayList<>(Arrays.asList(ClearCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD,
                    HelpCommand.COMMAND_WORD, TelegramCommand.COMMAND_WORD, GithubCommand.COMMAND_WORD,
                    ListCommand.COMMAND_WORD)
            );

    /**
     * Parses user input into command (that doesn't accept arguments) for execution.
     * @param commandWord command type
     * @return the command based on the user input
     */
    public Command parseCommandWithoutDescription(String commandWord) throws ParseException {
        switch (commandWord) {
        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();
        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();
        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();
        case TelegramCommand.COMMAND_WORD:
            return new TelegramCommand();
        case GithubCommand.COMMAND_WORD:
            return new GithubCommand();
        case ListCommand.COMMAND_WORD:
            return new ListCommand();
        default :
            throw new ParseException(MESSAGE_PARSE_COMMAND_ERROR);
        }
    }

    /**
     * Parses user input into command (that accepts arguments) for execution.
     * @param commandWord command type
     * @return the command based on the user input
     */
    public Command parseCommandWithDescription(String commandWord, String arguments) throws ParseException {
        switch (commandWord) {
        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);
        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);
        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);
        case FavouriteCommand.COMMAND_WORD:
            return new FavouriteCommandParser().parse(arguments);
        case UnfavouriteCommand.COMMAND_WORD:
            return new UnfavouriteCommandParser().parse(arguments);
        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);
        case ExportCommand.COMMAND_WORD:
            return new ExportCommandParser().parse(arguments);
        case ShowCommand.COMMAND_WORD:
            return new ShowCommandParser().parse(arguments);
        case ImportCommand.COMMAND_WORD:
            return new ImportCommandParser().parse(arguments);
        case TagCommand.COMMAND_WORD:
            return new TagCommandParser().parse(arguments);
        default:
            throw new ParseException(MESSAGE_PARSE_COMMAND_ERROR);
        }
    }

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        logger.info("----------------[USER INPUT][" + userInput + "]");
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        logger.info("----------------[COMMAND WORD][" + commandWord + "]");
        final String arguments = matcher.group("arguments");
        logger.info("----------------[ARGUMENTS][" + arguments + "]");
        final boolean isCommandWithDescription = commandsWithDescription.contains(commandWord);
        final boolean isCommandWithoutDescription = commandsWithoutDescription.contains(commandWord);
        if (isCommandWithDescription) {
            if (arguments.isEmpty()) {
                throw new ParseException(commandWord + MESSAGE_COMMAND_DESCRIPTION_CANNOT_BE_EMPTY);
            }
            return parseCommandWithDescription(commandWord, arguments);
        } else if (isCommandWithoutDescription) {
            if (!arguments.isEmpty()) {
                throw new ParseException(commandWord + MESSAGE_COMMAND_DOES_NOT_TAKE_PARAMETERS);
            }
            return parseCommandWithoutDescription(commandWord);
        } else {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }
}
