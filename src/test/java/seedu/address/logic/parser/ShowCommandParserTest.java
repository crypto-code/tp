package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ShowCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class ShowCommandParserTest {

    private ShowCommandParser parser = new ShowCommandParser();

    @Test
    public void parse_invalidName_throwsParseException() {
        String invalidName = "Ch4rlo++#";
        assertParseFailure(parser, invalidName, ShowCommand.MESSAGE_INVALID_NAME);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsShowCommand() {
        // no leading and trailing whitespaces
        ShowCommand expectedShowCommandOne =
                new ShowCommand(new NameContainsKeywordsPredicate(Collections.singletonList("Alice")), "Name");
        assertParseSuccess(parser, "Alice", expectedShowCommandOne);

        // multiple whitespaces between keywords
        ShowCommand expectedShowCommandTwo =
                new ShowCommand(new NameContainsKeywordsPredicate(Collections.singletonList("Alice Pauline")), "Name");
        assertParseSuccess(parser, " \n Alice \n \t Pauline  \t", expectedShowCommandTwo);
    }

}
