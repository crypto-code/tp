package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_GITHUB_FIELD_CANNOT_BE_EMPTY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_NAME;
import static seedu.address.commons.core.Messages.MESSAGE_TAG_FIELD_CANNOT_BE_EMPTY;
import static seedu.address.commons.core.Messages.MESSAGE_TELEGRAM_FIELD_CANNOT_BE_EMPTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.GithubContainsKeywordsPredicate;
import seedu.address.model.person.Name;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.person.TelegramHandleContainsKeywordsPredicate;


/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static String tagIdentifier = PREFIX_TAG.getPrefix();
    private static String telegramHandleIdentifier = PREFIX_TELEGRAM.getPrefix();
    private static String githubUsernameIdentifier = PREFIX_GITHUB.getPrefix();

    /**
     * Parses the list of Github username(s) to be searched for in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @return FindCommand object for execution.
     * @throws ParseException if user has not entered at least 1 tag to search for
     */
    public FindCommand parseFindGithubUsername(String trimmedArgs) throws ParseException {
        assert(!trimmedArgs.isEmpty());
        String githubUsernames = trimmedArgs.substring(2).trim();
        if (githubUsernames.isEmpty()) {
            throw new ParseException(MESSAGE_GITHUB_FIELD_CANNOT_BE_EMPTY);
        }
        String[] githubUsernameKeywords = githubUsernames.split("\\s+");
        return new FindCommand(new GithubContainsKeywordsPredicate(Arrays
                .asList(githubUsernameKeywords)));
    }
    /**
     * Parses the list of name(s) to be searched for in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @return FindCommand object for execution.
     * @throws ParseException if user has not entered at least 1 name to search for
     */
    public FindCommand parseFindName(String trimmedArgs) throws ParseException {
        assert(!trimmedArgs.isEmpty());
        String[] nameKeywords = trimmedArgs.split("\\s+");
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords), true));
    }

    /**
     * Parses the list of tag(s) to be searched for in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @return FindCommand object for execution.
     * @throws ParseException if user has not entered at least 1 tag to search for
     */
    public FindCommand parseFindTag(String trimmedArgs) throws ParseException {
        assert(!trimmedArgs.isEmpty());
        String tags = trimmedArgs.substring(2).trim();
        if (tags.isEmpty()) {
            throw new ParseException(MESSAGE_TAG_FIELD_CANNOT_BE_EMPTY);
        }
        String[] tagKeywords = tags.split("\\s+");
        return new FindCommand(new TagContainsKeywordsPredicate(Arrays
                .asList(tagKeywords)));
    }

    /**
     * Parses the list of Telegram handle(s) to be searched for in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @return FindCommand object for execution.
     * @throws ParseException if user has not entered at least 1 telegram handle to search for
     */
    public FindCommand parseFindTelegramHandle(String trimmedArgs) throws ParseException {
        assert(!trimmedArgs.isEmpty());
        String telegramHandles = trimmedArgs.substring(3).trim();
        if (telegramHandles.isEmpty()) {
            throw new ParseException(MESSAGE_TELEGRAM_FIELD_CANNOT_BE_EMPTY);
        }
        String[] telegramHandleKeywords = telegramHandles.split("\\s+");
        return new FindCommand(new TelegramHandleContainsKeywordsPredicate(Arrays
                .asList(telegramHandleKeywords)));
    }
    private boolean isValidFormat(String trimmedArgs) throws ParseException {
        assert(!trimmedArgs.isEmpty());
        boolean isTag = trimmedArgs.indexOf(tagIdentifier) == 0;
        boolean isTelegram = trimmedArgs.indexOf(telegramHandleIdentifier) == 0;
        boolean isGithub = trimmedArgs.indexOf(githubUsernameIdentifier) == 0;
        boolean isName = Name.isValidName(trimmedArgs);
        if (!isName && !trimmedArgs.contains("/")) {
            throw new ParseException(String.format(MESSAGE_INVALID_NAME,
                    FindCommand.MESSAGE_USAGE));
        }
        return isTag || isTelegram || isGithub || isName;
    }

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        assert(!args.contains(FindCommand.COMMAND_WORD));
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty() || !isValidFormat(trimmedArgs)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    FindCommand.MESSAGE_USAGE));
        }
        if (trimmedArgs.indexOf(tagIdentifier) == 0) {
            return parseFindTag(trimmedArgs);
        } else if (trimmedArgs.indexOf(telegramHandleIdentifier) == 0) {
            return parseFindTelegramHandle(trimmedArgs);
        } else if (trimmedArgs.indexOf(githubUsernameIdentifier) == 0) {
            return parseFindGithubUsername(trimmedArgs);
        } else {
            return parseFindName(trimmedArgs);
        }
    }
}

