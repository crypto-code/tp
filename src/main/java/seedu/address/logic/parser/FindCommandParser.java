package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.person.TelegramHandleContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static String tagIdentifier = PREFIX_TAG.getPrefix();
    private static String telegramHandleIdentifier = PREFIX_TELEGRAM.getPrefix();

    /**
     * Parses the list of names to be searched for in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @return FindCommand object for execution.
     * @throws ParseException if user has not entered at least 1 name to search for
     */
    public FindCommand parseFindName(String trimmedArgs) throws ParseException {
        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    /**
     * Parses the list of tags to be searched for in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @return FindCommand object for execution.
     * @throws ParseException if user has not entered at least 1 tag to search for
     */
    public FindCommand parseFindTag(String trimmedArgs) throws ParseException {
        String tags = trimmedArgs.substring(2).trim();
        if (tags.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        String[] tagKeywords = tags.split("\\s+");
        return new FindCommand(new TagContainsKeywordsPredicate(Arrays.asList(tagKeywords)));
    }

    /**
     * Parses the list of telegram handles to be searched for in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @return FindCommand object for execution.
     * @throws ParseException if user has not entered at least 1 telegram handle to search for
     */
    public FindCommand parseFindTelegramHandle(String trimmedArgs) throws ParseException {
        String telegramHandles = trimmedArgs.substring(3).trim();
        if (telegramHandles.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
        String[] telegramHandleKeywords = telegramHandles.split("\\s+");
        return new FindCommand(new TelegramHandleContainsKeywordsPredicate(Arrays
                .asList(telegramHandleKeywords)));
    }
    private boolean isValidFormat(String trimmedArgs) {
        boolean isTag = trimmedArgs.indexOf(tagIdentifier) == 0;
        boolean isTelegram = trimmedArgs.indexOf(telegramHandleIdentifier) == 0;
        boolean isName = !trimmedArgs.contains("/") && Character.isLetter(trimmedArgs.charAt(0));
        return isTag || isTelegram || isName;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || !isValidFormat(trimmedArgs)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (trimmedArgs.indexOf(tagIdentifier) == 0) {
            return parseFindTag(trimmedArgs);
        } else if (trimmedArgs.indexOf(telegramHandleIdentifier) == 0) {
            return parseFindTelegramHandle(trimmedArgs);
        } else {
            return parseFindName(trimmedArgs);
        }
    }
}
