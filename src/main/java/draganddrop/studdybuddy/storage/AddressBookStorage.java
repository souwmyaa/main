package draganddrop.studdybuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link draganddrop.studdybuddy.model.AddressBook}.
 */
public interface AddressBookStorage {

    /**
     * Returns the file path of the data file.
     */
    Path getAddressBookFilePath();

    /**
     * Returns AddressBook data as a {@link draganddrop.studdybuddy.model.ReadOnlyAddressBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws draganddrop.studdybuddy.commons.exceptions.DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<draganddrop.studdybuddy.model.ReadOnlyAddressBook> readAddressBook() throws draganddrop.studdybuddy.commons.exceptions.DataConversionException, IOException;

    /**
     * @see #getAddressBookFilePath()
     */
    Optional<draganddrop.studdybuddy.model.ReadOnlyAddressBook> readAddressBook(Path filePath) throws draganddrop.studdybuddy.commons.exceptions.DataConversionException, IOException;

    /**
     * Saves the given {@link draganddrop.studdybuddy.model.ReadOnlyAddressBook} to the storage.
     *
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveAddressBook(draganddrop.studdybuddy.model.ReadOnlyAddressBook addressBook) throws IOException;

    /**
     * @see #saveAddressBook(draganddrop.studdybuddy.model.ReadOnlyAddressBook)
     */
    void saveAddressBook(draganddrop.studdybuddy.model.ReadOnlyAddressBook addressBook, Path filePath) throws IOException;

}
