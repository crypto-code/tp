package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DOUBLE_FILENAME_TXT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_FILENAME_TXT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILENAME_CSV;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FILENAME_JSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ImportCommand;

public class ImportCommandParserTest {

    private final ImportCommandParser parser = new ImportCommandParser();

    @Test
    public void parse_fileNamePresentJson_success() {
        // file name is .json
        assertParseSuccess(parser, VALID_FILENAME_JSON, new ImportCommand(VALID_FILENAME_JSON));
    }

    @Test
    public void parse_fileNamePresentCsv_success() {
        // file name is .csv
        assertParseSuccess(parser, VALID_FILENAME_CSV, new ImportCommand(VALID_FILENAME_CSV));
    }

    @Test
    public void parse_fileNameWrongExtension_failure() {
        // file name present, but is not .json or .csv
        assertParseFailure(parser, INVALID_FILENAME_TXT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_fileNameDoubleFile_failure() {
        // file name present, but is not .json or .csv
        assertParseFailure(parser, INVALID_DOUBLE_FILENAME_TXT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_fileNameMissing_failure() {
        // file name missing
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE));
    }
}
