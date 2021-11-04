package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


public class UnfavouriteCommand extends Command {
    public static final String COMMAND_WORD = "unfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unfavourites the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNFAVOURITE_PERSON_SUCCESS = "Unfavourited Person: %1$s";

    public static final String MESSAGE_ALREADY_UNFAVOURITE_PERSON = "This person has already been unfavourited.";

    private final Index targetIndex;

    public UnfavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUnfavourite = lastShownList.get(targetIndex.getZeroBased());
        if (!personToUnfavourite.isFavourite()) {
            throw new CommandException(MESSAGE_ALREADY_UNFAVOURITE_PERSON);
        }
        model.unfavouritePerson(personToUnfavourite);
        model.getPersonListControl().refreshPersonListUI();
        return new CommandResult(String.format(MESSAGE_UNFAVOURITE_PERSON_SUCCESS, personToUnfavourite));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnfavouriteCommand // instanceof handles nulls
                && targetIndex.equals(((UnfavouriteCommand) other).targetIndex)); // state check
    }
}
