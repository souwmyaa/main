package draganddrop.studdybuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Represents a storage for {@link draganddrop.studdybuddy.model.UserPrefs}.
 */
public interface UserPrefsStorage {

    /**
     * Returns the file path of the UserPrefs data file.
     */
    Path getUserPrefsFilePath();

    /**
     * Returns UserPrefs data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws draganddrop.studdybuddy.commons.exceptions.DataConversionException if the data in storage is not in the expected format.
     * @throws IOException             if there was any problem when reading from the storage.
     */
    Optional<draganddrop.studdybuddy.model.UserPrefs> readUserPrefs() throws draganddrop.studdybuddy.commons.exceptions.DataConversionException, IOException;

    /**
     * Saves the given {@link draganddrop.studdybuddy.model.ReadOnlyUserPrefs} to the storage.
     *
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPrefs(draganddrop.studdybuddy.model.ReadOnlyUserPrefs userPrefs) throws IOException;

}
