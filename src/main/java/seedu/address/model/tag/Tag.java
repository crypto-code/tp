package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag {
    enum Type {
        GENERAL,
        EVENT,
        MODULE
    }

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    private static final String[] stringTypes = new String[] {"", "event-", "mod-"};
    public static final String VALIDATION_REGEX =
            "\\p{Alnum}+|" + stringTypes[1] + "\\s*\\p{Alnum}+|" + stringTypes[2] + "\\s*\\p{Alnum}+";


    public final String tagName;
    public final Type type;

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagDescription A valid tag description.
     */
    public Tag(String tagDescription) {
        requireNonNull(tagDescription);
        checkArgument(isValidTagName(tagDescription));
        this.type = parseTagType(tagDescription);
        this.tagName = parseTagName(type, tagDescription);
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if tag is of event type
     *
     * @return true, if tag is an event
     */
    public boolean isEvent() {
        return type == Type.EVENT;
    }

    public String getStringType() {
        return stringTypes[getIntType()];
    }

    public int getIntType() {
        return type.ordinal();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Tag // instanceof handles nulls
                && tagName.equals(((Tag) other).tagName) // name check
                && type.equals(((Tag) other).type)); // type check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return '[' + tagName + ']';
    }

    /**
     * Parses the tag type based on the given tag description.
     *
     * @param tagDescription A valid tag description.
     * @return Type of tag represented by tag description.
     */
    public Type parseTagType(String tagDescription) {
        assert(!tagDescription.isEmpty());
        if (tagDescription.indexOf(stringTypes[1]) == 0) {
            return Type.EVENT;
        } else if (tagDescription.indexOf(stringTypes[2]) == 0) {
            return Type.MODULE;
        } else {
            return Type.GENERAL;
        }
    }

    /**
     * Parses the tag name based on the given tag type and description.
     *
     * @param tagType Type of tag.
     * * @param tagDescription A valid tag description.
     * @return Name of tag represented by tag description.
     */
    public String parseTagName(Type tagType, String tagDescription) {
        return tagDescription.substring(stringTypes[tagType.ordinal()].length()).trim();
    }
}

