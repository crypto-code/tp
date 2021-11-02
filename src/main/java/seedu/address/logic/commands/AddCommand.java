package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GITHUB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "<NAME> "
            + PREFIX_TELEGRAM + "<TELEGRAM> "
            + PREFIX_GITHUB + "<GITHUB> "
            + "[" + PREFIX_PHONE + "<PHONE>] "
            + "[" + PREFIX_EMAIL + "<EMAIL>] "
            + "[" + PREFIX_ADDRESS + "<ADDRESS>] "
            + "[" + PREFIX_TAG + "<TAG>]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_TELEGRAM + "@john_doe_123"
            + PREFIX_GITHUB + "john-doe"
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New person added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_ALL_COMPULSORY_FIELDS_MISSING =
            "The Name, Github and Telegram fields, are compulsory!";
    public static final String MESSAGE_NAME_GITHUB_FIELDS_MISSING = "The Name and Github fields are compulsory!";
    public static final String MESSAGE_NAME_TELEGRAM_FIELDS_MISSING = "The Name and Telegram fields are compulsory!";
    public static final String MESSAGE_GITHUB_TELEGRAM_FIELDS_MISSING =
            "The Github and Telegram fields are compulsory!";
    public static final String MESSAGE_NAME_FIELD_MISSING = "The Name field is compulsory!";
    public static final String MESSAGE_GITHUB_FIELD_MISSING = "The GitHub field is compulsory!";
    public static final String MESSAGE_TELEGRAM_FIELD_MISSING = "The Telegram field is compulsory!";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPerson(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.addPerson(toAdd);
        if (model.getPersonListControl() != null) {
            model.setTabIndex(0);
            model.getPersonListControl().setSelectedIndex(model.getFilteredPersonList().indexOf(toAdd));
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
