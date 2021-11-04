package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final String ALICE_NAME = "Alice Pauline";
    public static final String ALICE_TELEGRAM = "alice_pauline";
    public static final String ALICE_GITHUB = "alice";
    public static final String ALICE_PHONE = "94351253";
    public static final String ALICE_EMAIL = "alice@example.com";
    public static final String ALICE_ADDRESS = "123, Jurong West Ave 6, #08-111";
    public static final String ALICE_TAG_1 = "friends";
    public static final boolean ALICE_IS_FAVOURITE = false;
    public static final Person ALICE =
            new PersonBuilder()
                    .withName(ALICE_NAME)
                    .withTelegram(ALICE_TELEGRAM)
                    .withGithub(ALICE_GITHUB)
                    .withPhone(ALICE_PHONE)
                    .withEmail(ALICE_EMAIL)
                    .withAddress(ALICE_ADDRESS)
                    .withTags(ALICE_TAG_1)
                    .withIsFavourite(ALICE_IS_FAVOURITE)
                    .build();
    public static final String BENSON_NAME = "Benson Meier";
    public static final String BENSON_TELEGRAM = "benson_meier";
    public static final String BENSON_GITHUB = "benson";
    public static final String BENSON_PHONE = "98765432";
    public static final String BENSON_EMAIL = "johnd@example.com";
    public static final String BENSON_ADDRESS = "311, Clementi Ave 2, #02-25";
    public static final String BENSON_TAG_1 = "owesmoney";
    public static final String BENSON_TAG_2 = "friends";
    public static final boolean BENSON_IS_FAVOURITE = false;
    public static final Person BENSON =
            new PersonBuilder()
                    .withName(BENSON_NAME)
                    .withTelegram(BENSON_TELEGRAM)
                    .withGithub(BENSON_GITHUB)
                    .withPhone(BENSON_PHONE)
                    .withEmail(BENSON_EMAIL)
                    .withAddress(BENSON_ADDRESS)
                    .withTags(BENSON_TAG_1, BENSON_TAG_2)
                    .withIsFavourite(BENSON_IS_FAVOURITE)
                    .build();
    public static final String CARL_NAME = "Carl Kurz";
    public static final String CARL_TELEGRAM = "carl_kurz";
    public static final String CARL_GITHUB = "carl";
    public static final String CARL_PHONE = "95352563";
    public static final String CARL_EMAIL = "heinz@example.com";
    public static final String CARL_ADDRESS = "wall street";
    public static final boolean CARL_IS_FAVOURITE = false;
    public static final Person CARL =
            new PersonBuilder()
                    .withName(CARL_NAME)
                    .withTelegram(CARL_TELEGRAM)
                    .withGithub(CARL_GITHUB)
                    .withPhone(CARL_PHONE)
                    .withEmail(CARL_EMAIL)
                    .withAddress(CARL_ADDRESS)
                    .withIsFavourite(CARL_IS_FAVOURITE)
                    .build();
    public static final String DANIEL_NAME = "Daniel Meier";
    public static final String DANIEL_TELEGRAM = "daniel_meier";
    public static final String DANIEL_GITHUB = "daniel-meier";
    public static final String DANIEL_PHONE = "87652533";
    public static final String DANIEL_EMAIL = "cornelia@example.com";
    public static final String DANIEL_ADDRESS = "10th street";
    public static final String DANIEL_TAG_1 = "friends";
    public static final boolean DANIEL_IS_FAVOURITE = false;
    public static final Person DANIEL =
            new PersonBuilder()
                    .withName(DANIEL_NAME)
                    .withTelegram(DANIEL_TELEGRAM)
                    .withGithub(DANIEL_GITHUB)
                    .withPhone(DANIEL_PHONE)
                    .withEmail(DANIEL_EMAIL)
                    .withAddress(DANIEL_ADDRESS)
                    .withTags(DANIEL_TAG_1)
                    .withIsFavourite(DANIEL_IS_FAVOURITE)
                    .build();
    public static final String ELLE_NAME = "Elle Meyer";
    public static final String ELLE_TELEGRAM = "elle_meyer";
    public static final String ELLE_GITHUB = "elle-meyer";
    public static final String ELLE_PHONE = "9482224";
    public static final String ELLE_EMAIL = "werner@example.com";
    public static final String ELLE_ADDRESS = "michegan ave";
    public static final String ELLE_TAG_1 = "family";
    public static final boolean ELLE_IS_FAVOURITE = false;
    public static final Person ELLE =
            new PersonBuilder()
                    .withName(ELLE_NAME)
                    .withTelegram(ELLE_TELEGRAM)
                    .withGithub(ELLE_GITHUB)
                    .withPhone(ELLE_PHONE)
                    .withEmail(ELLE_EMAIL)
                    .withAddress(ELLE_ADDRESS)
                    .withTags(ELLE_TAG_1)
                    .withIsFavourite(ELLE_IS_FAVOURITE)
                    .build();
    public static final String FIONA_NAME = "Fiona Kunz";
    public static final String FIONA_TELEGRAM = "fiona_kunz";
    public static final String FIONA_GITHUB = "fiona-kunz";
    public static final String FIONA_PHONE = "9482427";
    public static final String FIONA_EMAIL = "lydia@example.com";
    public static final String FIONA_ADDRESS = "little tokyo";
    public static final boolean FIONA_IS_FAVOURITE = false;
    public static final Person FIONA =
            new PersonBuilder()
                    .withName(FIONA_NAME)
                    .withTelegram(FIONA_TELEGRAM)
                    .withGithub(FIONA_GITHUB)
                    .withPhone(FIONA_PHONE)
                    .withEmail(FIONA_EMAIL)
                    .withAddress(FIONA_ADDRESS)
                    .withIsFavourite(FIONA_IS_FAVOURITE)
                    .build();
    public static final String GEORGE_NAME = "George Best";
    public static final String GEORGE_TELEGRAM = "george_best";
    public static final String GEORGE_GITHUB = "george-best";
    public static final String GEORGE_PHONE = "9482442";
    public static final String GEORGE_EMAIL = "anna@example.com";
    public static final String GEORGE_ADDRESS = "4th street";
    public static final boolean GEORGE_IS_FAVOURITE = false;
    public static final Person GEORGE =
            new PersonBuilder()
                    .withName(GEORGE_NAME)
                    .withTelegram(GEORGE_TELEGRAM)
                    .withGithub(GEORGE_GITHUB)
                    .withPhone(GEORGE_PHONE)
                    .withEmail(GEORGE_EMAIL)
                    .withAddress(GEORGE_ADDRESS)
                    .withIsFavourite(GEORGE_IS_FAVOURITE)
                    .build();

    // Manually added
    public static final String HOON_NAME = "Hoon Meier";
    public static final String HOON_TELEGRAM = "hoon_meier";
    public static final String HOON_GITHUB = "hoon-meier";
    public static final String HOON_PHONE = "8482424";
    public static final String HOON_EMAIL = "stefan@example.com";
    public static final String HOON_ADDRESS = "little india";
    public static final Person HOON =
            new PersonBuilder()
                    .withName(HOON_NAME)
                    .withTelegram(HOON_TELEGRAM)
                    .withGithub(HOON_GITHUB)
                    .withPhone(HOON_PHONE)
                    .withEmail(HOON_EMAIL)
                    .withAddress(HOON_ADDRESS)
                    .build();
    public static final String IDA_NAME = "Ida Mueller";
    public static final String IDA_TELEGRAM = "ida_mueller";
    public static final String IDA_GITHUB = "ida-mueller";
    public static final String IDA_PHONE = "8482131";
    public static final String IDA_EMAIL = "hans@example.com";
    public static final String IDA_ADDRESS = "chicago ave";
    public static final Person IDA =
            new PersonBuilder()
                    .withName(IDA_NAME)
                    .withTelegram(IDA_TELEGRAM)
                    .withGithub(IDA_GITHUB)
                    .withPhone(IDA_PHONE)
                    .withEmail(IDA_EMAIL)
                    .withAddress(IDA_ADDRESS)
                    .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY =
            new PersonBuilder()
                    .withName(VALID_NAME_AMY)
                    .withTelegram(VALID_TELEGRAM_AMY)
                    .withGithub(VALID_GITHUB_AMY)
                    .withPhone(VALID_PHONE_AMY)
                    .withEmail(VALID_EMAIL_AMY)
                    .withAddress(VALID_ADDRESS_AMY)
                    .withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB =
            new PersonBuilder()
                    .withName(VALID_NAME_BOB)
                    .withTelegram(VALID_TELEGRAM_BOB)
                    .withGithub(VALID_GITHUB_BOB)
                    .withPhone(VALID_PHONE_BOB)
                    .withEmail(VALID_EMAIL_BOB)
                    .withAddress(VALID_ADDRESS_BOB)
                    .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
                    .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Person person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Person> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
