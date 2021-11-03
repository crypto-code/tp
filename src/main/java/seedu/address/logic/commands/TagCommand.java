package seedu.address.logic.commands;
import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

public class TagCommand extends Command {
    public static final String COMMAND_WORD = "tag";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds/removes the specified tag(s) for the required contact.\n"
            + "Parameters: INDEX (must be a positive integer) a/TAG [r/TAG] [MORE_TAGS]..\n"
            + "Example: " + COMMAND_WORD + " 1 a/friends ";

    public static final String MESSAGE_TAGGED_PERSON_SUCCESS = "Successfully added/removed required tag(s) to %1$s!";
    public static final String MESSAGE_INVALID_TAG_FORMAT = "Invalid Tag Format in Command";

    private final Index targetIndex;
    private final ArrayList<Tag> toAdd;
    private final ArrayList<Tag> toRemove;

    /**
     * @param index of the person in the filtered person list to edit
     * @param toAdd tags to add to the person's existing tags
     * @param toRemove tags to remove from the person's existing tags
     */
    public TagCommand(Index index, ArrayList<Tag> toAdd, ArrayList<Tag> toRemove) {
        this.targetIndex = index;
        this.toAdd = toAdd;
        this.toRemove = toRemove;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(targetIndex.getZeroBased());

        Set<Tag> newTags = new HashSet<>(personToEdit.getTags());
        newTags.removeIf(toRemove::contains);
        newTags.addAll(toAdd);

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getTelegram(), personToEdit.getGithub(),
                personToEdit.getPhone(), personToEdit.getEmail(), personToEdit.getAddress(), newTags,
                personToEdit.isFavourite(), personToEdit.getProfilePicture(), personToEdit.getGitStats());

        model.setPerson(personToEdit, editedPerson);
        model.getPersonListControl().refreshPersonListUI();
        return new CommandResult(String.format(MESSAGE_TAGGED_PERSON_SUCCESS, editedPerson));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TagCommand // instanceof handles nulls
                && targetIndex.equals(((TagCommand) other).targetIndex)); // state check
    }
}
