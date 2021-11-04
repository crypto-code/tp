package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GITHUB_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_BOB;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ALICE_ADDRESS;
import static seedu.address.testutil.TypicalPersons.ALICE_EMAIL;
import static seedu.address.testutil.TypicalPersons.ALICE_GITHUB;
import static seedu.address.testutil.TypicalPersons.ALICE_NAME;
import static seedu.address.testutil.TypicalPersons.ALICE_PHONE;
import static seedu.address.testutil.TypicalPersons.ALICE_TAG_1;
import static seedu.address.testutil.TypicalPersons.ALICE_TELEGRAM;
import static seedu.address.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonTest {

    @Test
    public void getName() {
        Name expectedName = new Name(ALICE_NAME);
        assertEquals(expectedName, ALICE.getName());
    }

    @Test
    public void getTelegram() {
        Telegram expectedTelegram = new Telegram(ALICE_TELEGRAM);
        assertEquals(expectedTelegram, ALICE.getTelegram());
    }

    @Test
    public void getGithub() {
        Github expectedGithub = new Github(ALICE_GITHUB);
        assertEquals(expectedGithub, ALICE.getGithub());
    }

    @Test
    public void getPhone() {
        Phone expectedPhone = new Phone(ALICE_PHONE);
        assertEquals(expectedPhone, ALICE.getPhone());
    }

    @Test
    public void getEmail() {
        Email expectedEmail = new Email(ALICE_EMAIL);
        assertEquals(expectedEmail, ALICE.getEmail());
    }

    @Test
    public void getAddress() {
        Address expectedAddress = new Address(ALICE_ADDRESS);
        assertEquals(expectedAddress, ALICE.getAddress());
    }

    @Test
    public void isFavourite_defaultAlice() {
        ALICE.setIsNotFavourite();
        assertFalse(ALICE.isFavourite());
    }

    @Test
    public void isFavourite_modifyAliceOnce() {
        ALICE.setIsFavourite();
        assertTrue(ALICE.isFavourite());
    }

    @Test
    public void isFavourite_modifyAliceTwice() {
        ALICE.setIsFavourite();
        ALICE.setIsNotFavourite();
        assertFalse(ALICE.isFavourite());
    }

    @Test
    public void compareTo() {
        int expected = -1;
        assertEquals(expected, ALICE.compareTo(BOB));
    }

    @Test
    public void hashTest() {
        int expected = 1772994055;
        assertEquals(expected, ALICE.hashCode());
    }

    @Test
    public void profilePic() {
        assertNotEquals(null, ALICE.getProfilePicture());
    }

    @Test
    public void toStringTest() {
        String expectedString = ALICE_NAME
                + "; Telegram: "
                + ALICE_TELEGRAM
                + "; Github: "
                + ALICE_GITHUB
                + "; Phone: "
                + ALICE_PHONE
                + "; Email: "
                + ALICE_EMAIL
                + "; Address: "
                + ALICE_ADDRESS
                + "; Tags: "
                + "[" + ALICE_TAG_1 + "]";
        assertEquals(expectedString, ALICE.toString());
    }

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new PersonBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        assertTrue(ALICE.isSamePerson(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSamePerson(null));

        // same name, github and telegram, all other attributes different -> returns true
        Person editedAlice =
                new PersonBuilder(ALICE)
                        .withPhone(VALID_PHONE_BOB)
                        .withEmail(VALID_EMAIL_BOB)
                        .withAddress(VALID_ADDRESS_BOB)
                        .withTags(VALID_TAG_HUSBAND)
                        .build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different name, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different github, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withGithub(VALID_GITHUB_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // different telegram, all other attributes same -> returns true
        editedAlice = new PersonBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB).build();
        assertTrue(ALICE.isSamePerson(editedAlice));

        // name differs in case, all other attributes same -> returns true
        Person editedBob = new PersonBuilder(BOB).withName(VALID_NAME_BOB.toLowerCase()).build();
        assertTrue(BOB.isSamePerson(editedBob));

        // name has trailing spaces, all other attributes same -> returns true
        String nameWithTrailingSpaces = VALID_NAME_BOB + " ";
        editedBob = new PersonBuilder(BOB).withName(nameWithTrailingSpaces).build();
        assertTrue(BOB.isSamePerson(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new PersonBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different person -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns true
        Person editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different telegram -> returns true
        editedAlice = new PersonBuilder(ALICE).withTelegram(VALID_TELEGRAM_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different github -> returns true
        editedAlice = new PersonBuilder(ALICE).withGithub(VALID_GITHUB_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different phone -> returns true
        editedAlice = new PersonBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different email -> returns true
        editedAlice = new PersonBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different address -> returns true
        editedAlice = new PersonBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertTrue(ALICE.equals(editedAlice));

        // different tags -> returns true
        editedAlice = new PersonBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.equals(editedAlice));

        // different name, telegram and github -> returns false
        editedAlice = new PersonBuilder(ALICE).withName(VALID_NAME_BOB)
                .withGithub(VALID_GITHUB_BOB)
                .withTelegram(VALID_TELEGRAM_BOB)
                .build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
