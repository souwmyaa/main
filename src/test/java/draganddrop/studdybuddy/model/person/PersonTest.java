package draganddrop.studdybuddy.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static draganddrop.studdybuddy.testutil.Assert.assertThrows;

import draganddrop.studdybuddy.logic.commands.CommandTestUtil;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PersonTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Person person = new draganddrop.studdybuddy.testutil.PersonBuilder().build();
        draganddrop.studdybuddy.testutil.Assert.assertThrows(UnsupportedOperationException.class, () -> person.getTags().remove(0));
    }

    @Test
    public void isSamePerson() {
        // same object -> returns true
        Assertions.assertTrue(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.isSamePerson(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE));

        // null -> returns false
        Assertions.assertFalse(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.isSamePerson(null));

        // different phone and email -> returns false
        Person editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withPhone(CommandTestUtil.VALID_PHONE_BOB).withEmail(CommandTestUtil.VALID_EMAIL_BOB).build();
        Assertions.assertFalse(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.isSamePerson(editedAlice));

        // different name -> returns false
        editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.isSamePerson(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withEmail(CommandTestUtil.VALID_EMAIL_BOB).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
            .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.isSamePerson(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withPhone(CommandTestUtil.VALID_PHONE_BOB).withAddress(CommandTestUtil.VALID_ADDRESS_BOB)
            .withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.isSamePerson(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND).build();
        Assertions.assertTrue(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.isSamePerson(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Person aliceCopy = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).build();
        Assertions.assertTrue(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.equals(aliceCopy));

        // same object -> returns true
        Assertions.assertTrue(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.equals(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE));

        // null -> returns false
        Assertions.assertFalse(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.equals(null));

        // different type -> returns false
        Assertions.assertFalse(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.equals(5));

        // different person -> returns false
        Assertions.assertFalse(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.equals(draganddrop.studdybuddy.testutil.TypicalPersons.BOB));

        // different name -> returns false
        Person editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withName(CommandTestUtil.VALID_NAME_BOB).build();
        Assertions.assertFalse(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withPhone(CommandTestUtil.VALID_PHONE_BOB).build();
        Assertions.assertFalse(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withEmail(CommandTestUtil.VALID_EMAIL_BOB).build();
        Assertions.assertFalse(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).build();
        Assertions.assertFalse(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE.equals(editedAlice));

    }
}
