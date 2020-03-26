package draganddrop.studdybuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * API of the Storage component
 */
public interface Storage extends AddressBookStorage, UserPrefsStorage {

    @Override
    Optional<draganddrop.studdybuddy.model.UserPrefs> readUserPrefs() throws draganddrop.studdybuddy.commons.exceptions.DataConversionException, IOException;

    @Override
    void saveUserPrefs(draganddrop.studdybuddy.model.ReadOnlyUserPrefs userPrefs) throws IOException;

    @Override
    Path getAddressBookFilePath();

    @Override
    Optional<draganddrop.studdybuddy.model.ReadOnlyAddressBook> readAddressBook() throws draganddrop.studdybuddy.commons.exceptions.DataConversionException, IOException;

    @Override
    void saveAddressBook(draganddrop.studdybuddy.model.ReadOnlyAddressBook addressBook) throws IOException;

}
