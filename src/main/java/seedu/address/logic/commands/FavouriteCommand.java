package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class FavouriteCommand extends Command {
    public static final String COMMAND_WORD = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVOURITE_PERSON_SUCCESS = " has been favourited successfully!";

    public static final String MESSAGE_ALREADY_FAVOURITE_PERSON = " has already been favourited.";

    private final Index targetIndex;

    public FavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToFavourite = lastShownList.get(targetIndex.getZeroBased());
        if (personToFavourite.isFavourite()) {
            String errorMessage = personToFavourite.getName().toString()
                    + MESSAGE_ALREADY_FAVOURITE_PERSON;
            throw new CommandException(errorMessage);
        }
        model.favouritePerson(personToFavourite);
        model.getPersonListControl().refreshPersonListUI();
        String successMessage = personToFavourite.getName().toString()
                + MESSAGE_FAVOURITE_PERSON_SUCCESS;
        return new CommandResult(successMessage);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteCommand // instanceof handles nulls
                && targetIndex.equals(((FavouriteCommand) other).targetIndex)); // state check
    }
}
