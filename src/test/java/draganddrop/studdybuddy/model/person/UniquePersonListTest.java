package draganddrop.studdybuddy.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static draganddrop.studdybuddy.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import draganddrop.studdybuddy.logic.commands.CommandTestUtil;
import draganddrop.studdybuddy.model.person.exceptions.DuplicatePersonException;
import draganddrop.studdybuddy.model.person.exceptions.PersonNotFoundException;

import org.junit.jupiter.api.Test;

public class UniquePersonListTest {

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    @Test
    public void contains_nullPerson_throwsNullPointerException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.contains(null));
    }

    @Test
    public void contains_personNotInList_returnsFalse() {
        assertFalse(uniquePersonList.contains(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE));
    }

    @Test
    public void contains_personInList_returnsTrue() {
        uniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        assertTrue(uniquePersonList.contains(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE));
    }

    @Test
    public void contains_personWithSameIdentityFieldsInList_returnsTrue() {
        uniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        Person editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
            .build();
        assertTrue(uniquePersonList.contains(editedAlice));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.add(null));
    }

    @Test
    public void add_duplicatePerson_throwsDuplicatePersonException() {
        uniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        draganddrop.studdybuddy.testutil.Assert.assertThrows(DuplicatePersonException.class, () -> uniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE));
    }

    @Test
    public void setPerson_nullTargetPerson_throwsNullPointerException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(null, draganddrop.studdybuddy.testutil.TypicalPersons.ALICE));
    }

    @Test
    public void setPerson_nullEditedPerson_throwsNullPointerException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.setPerson(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE, null));
    }

    @Test
    public void setPerson_targetPersonNotInList_throwsPersonNotFoundException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(PersonNotFoundException.class, () -> uniquePersonList.setPerson(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE, draganddrop.studdybuddy.testutil.TypicalPersons.ALICE));
    }

    @Test
    public void setPerson_editedPersonIsSamePerson_success() {
        uniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        uniquePersonList.setPerson(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE, draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasSameIdentity_success() {
        uniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        Person editedAlice = new draganddrop.studdybuddy.testutil.PersonBuilder(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE).withAddress(CommandTestUtil.VALID_ADDRESS_BOB).withTags(CommandTestUtil.VALID_TAG_HUSBAND)
            .build();
        uniquePersonList.setPerson(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE, editedAlice);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(editedAlice);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasDifferentIdentity_success() {
        uniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        uniquePersonList.setPerson(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE, draganddrop.studdybuddy.testutil.TypicalPersons.BOB);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPerson_editedPersonHasNonUniqueIdentity_throwsDuplicatePersonException() {
        uniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        uniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.BOB);
        draganddrop.studdybuddy.testutil.Assert.assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPerson(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE, draganddrop.studdybuddy.testutil.TypicalPersons.BOB));
    }

    @Test
    public void remove_nullPerson_throwsNullPointerException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.remove(null));
    }

    @Test
    public void remove_personDoesNotExist_throwsPersonNotFoundException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(PersonNotFoundException.class, () -> uniquePersonList.remove(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE));
    }

    @Test
    public void remove_existingPerson_removesPerson() {
        uniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        uniquePersonList.remove(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullUniquePersonList_throwsNullPointerException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((UniquePersonList) null));
    }

    @Test
    public void setPersons_uniquePersonList_replacesOwnListWithProvidedUniquePersonList() {
        uniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.BOB);
        uniquePersonList.setPersons(expectedUniquePersonList);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_nullList_throwsNullPointerException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> uniquePersonList.setPersons((List<Person>) null));
    }

    @Test
    public void setPersons_list_replacesOwnListWithProvidedList() {
        uniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        List<Person> personList = Collections.singletonList(draganddrop.studdybuddy.testutil.TypicalPersons.BOB);
        uniquePersonList.setPersons(personList);
        UniquePersonList expectedUniquePersonList = new UniquePersonList();
        expectedUniquePersonList.add(draganddrop.studdybuddy.testutil.TypicalPersons.BOB);
        assertEquals(expectedUniquePersonList, uniquePersonList);
    }

    @Test
    public void setPersons_listWithDuplicatePersons_throwsDuplicatePersonException() {
        List<Person> listWithDuplicatePersons = Arrays.asList(draganddrop.studdybuddy.testutil.TypicalPersons.ALICE, draganddrop.studdybuddy.testutil.TypicalPersons.ALICE);
        draganddrop.studdybuddy.testutil.Assert.assertThrows(DuplicatePersonException.class, () -> uniquePersonList.setPersons(listWithDuplicatePersons));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(UnsupportedOperationException.class, ()
            -> uniquePersonList.asUnmodifiableObservableList().remove(0));
    }
}
