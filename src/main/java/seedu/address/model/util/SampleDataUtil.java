package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Github;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Telegram;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Adam Oh"),
                    new Telegram("adam_oh"),
                    new Github("moreTriangles"),
                    new Phone("91031282"),
                    new Email("adam@example.com"),
                    new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                    getTagSet("friends", "mod-cs2103t"),
                    false),
            new Person(new Name("Aishwarya Radhakrishnan Nair"),
                    new Telegram("aishwarya"),
                    new Github("aishh12"),
                    new Phone("87438807"),
                    new Email("aishwarya@example.com"),
                    new Address("Blk 30 Geylang Street 29, #06-40"),
                    getTagSet("friends", "event-hackathon2021", "mod-cs2103t"),
                    false),
            new Person(new Name("Cheong Yee Ming"),
                    new Telegram("yee_ming"),
                    new Github("CheongYeeMing"),
                    new Phone("93210283"),
                    new Email("yeeming@example.com"),
                    new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                    getTagSet("neighbours", "event-hackathon2021", "mod-cs2103t"),
                    false),
            new Person(new Name("Damith C. Rajapakse"),
                    new Telegram("damith_c"),
                    new Github("damithc"),
                    new Phone("93432011"),
                    new Email("damith@example.com"),
                    new Address("Blk 48 Tampines Street 20, #10-32"),
                    getTagSet("colleagues", "mod-cs2103t"),
                    true),
            new Person(new Name("Jai Lulla"),
                    new Telegram("jai_lulla"),
                    new Github("Jai2501"),
                    new Phone("92492021"),
                    new Email("jai@example.com"),
                    new Address("Blk 47 Tampines Street 20, #17-35"),
                    getTagSet("classmates", "mod-cs2103t"),
                    false)
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
