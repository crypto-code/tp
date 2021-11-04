package seedu.address.model.person;

import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Person> {
    private final List<String> keywords;
    private final boolean isHighlighted;

    /**
     * Creates predicate for name containing the following keywords
     */
    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
        this.isHighlighted = false;
    }

    /**
     * Creates predicate for name containing the following keywords
     * and also can enable highlighting
     */
    public NameContainsKeywordsPredicate(List<String> keywords, boolean isHighlighted) {
        this.keywords = keywords;
        this.isHighlighted = isHighlighted;
    }

    @Override
    public boolean test(Person person) {
        boolean result = keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(person.getName().fullName, keyword));
        if (isHighlighted && result) {
            String name = person.getName().fullName;
            for (String k : keywords) {
                if (StringUtil.containsWordIgnoreCase(name, k)) {
                    person.setFindHighlight(k.toLowerCase(Locale.ROOT));
                    break;
                }
            }
        } else {
            person.clearHighlights();
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
