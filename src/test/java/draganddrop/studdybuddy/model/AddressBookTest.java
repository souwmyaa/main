package draganddrop.studdybuddy.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static draganddrop.studdybuddy.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import draganddrop.studdybuddy.model.task.Task;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import draganddrop.studdybuddy.model.module.Module;
import draganddrop.studdybuddy.model.person.Person;
import draganddrop.studdybuddy.model.person.exceptions.DuplicatePersonException;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = draganddrop.studdybuddy.testutil.TypicalPersons.getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withAddress(draganddrop.studdybuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB).withTags(draganddrop.studdybuddy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND)
            .build();
        List<Person> newPersons = Arrays.asList(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE, editedAlice);
        AddressBookStub newData = new AddressBookStub(newPersons);

        draganddrop.studdybuddy.testutil.Assert.assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        assertTrue(addressBook.hasPerson(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        Person editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withAddress(draganddrop.studdybuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB).withTags(draganddrop.studdybuddy.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND)
            .build();
        assertTrue(addressBook.hasPerson(editedAlice));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Task> tasks = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        @Override
        public ObservableList<Task> getTaskList() {
            return tasks;
        }

        @Override
        public ObservableList<Task> getArchivedList() {
            return null;
        }

        @Override
        public ObservableList<Task> getDueSoonList() {
            return null;
        }

        public ObservableList<Module> getModuleList() {
            return getModuleList();
        }
    }

}
