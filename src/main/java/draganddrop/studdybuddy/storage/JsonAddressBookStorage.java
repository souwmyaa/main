package draganddrop.studdybuddy.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * A class to access AddressBook data stored as a json file on the hard disk.
 */
public class JsonAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = draganddrop.studdybuddy.commons.core.LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public JsonAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    public Path getAddressBookFilePath() {
        return filePath;
    }

    @Override
    public Optional<draganddrop.studdybuddy.model.ReadOnlyAddressBook> readAddressBook() throws draganddrop.studdybuddy.commons.exceptions.DataConversionException {
        return readAddressBook(filePath);
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @param filePath location of the data. Cannot be null.
     * @throws draganddrop.studdybuddy.commons.exceptions.DataConversionException if the file is not in the correct format.
     */
    public Optional<draganddrop.studdybuddy.model.ReadOnlyAddressBook> readAddressBook(Path filePath) throws draganddrop.studdybuddy.commons.exceptions.DataConversionException {
        requireNonNull(filePath);

        Optional<JsonSerializableAddressBook> jsonAddressBook = draganddrop.studdybuddy.commons.util.JsonUtil.readJsonFile(
            filePath, JsonSerializableAddressBook.class);
        if (!jsonAddressBook.isPresent()) {
            return Optional.empty();
        }

        try {
            return Optional.of(jsonAddressBook.get().toModelType());
        } catch (draganddrop.studdybuddy.commons.exceptions.IllegalValueException ive) {
            logger.info("Illegal values found in " + filePath + ": " + ive.getMessage());
            throw new draganddrop.studdybuddy.commons.exceptions.DataConversionException(ive);
        }
    }

    @Override
    public void saveAddressBook(draganddrop.studdybuddy.model.ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(draganddrop.studdybuddy.model.ReadOnlyAddressBook)}.
     *
     * @param filePath location of the data. Cannot be null.
     */
    public void saveAddressBook(draganddrop.studdybuddy.model.ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        draganddrop.studdybuddy.commons.util.FileUtil.createIfMissing(filePath);
        draganddrop.studdybuddy.commons.util.JsonUtil.saveJsonFile(new JsonSerializableAddressBook(addressBook), filePath);
    }

}
