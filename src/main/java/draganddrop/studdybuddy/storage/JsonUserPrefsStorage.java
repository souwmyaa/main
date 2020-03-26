package draganddrop.studdybuddy.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

/**
 * A class to access UserPrefs stored in the hard disk as a json file
 */
public class JsonUserPrefsStorage implements UserPrefsStorage {

    private Path filePath;

    public JsonUserPrefsStorage(Path filePath) {
        this.filePath = filePath;
    }

    @Override
    public Path getUserPrefsFilePath() {
        return filePath;
    }

    @Override
    public void saveUserPrefs(draganddrop.studdybuddy.model.ReadOnlyUserPrefs userPrefs) throws IOException {
        draganddrop.studdybuddy.commons.util.JsonUtil.saveJsonFile(userPrefs, filePath);
    }

    /**
     * Similar to {@link #readUserPrefs()}
     *
     * @param prefsFilePath location of the data. Cannot be null.
     * @throws draganddrop.studdybuddy.commons.exceptions.DataConversionException if the file format is not as expected.
     */
    public Optional<draganddrop.studdybuddy.model.UserPrefs> readUserPrefs(Path prefsFilePath) throws draganddrop.studdybuddy.commons.exceptions.DataConversionException {
        return draganddrop.studdybuddy.commons.util.JsonUtil.readJsonFile(prefsFilePath, draganddrop.studdybuddy.model.UserPrefs.class);
    }

    @Override
    public Optional<draganddrop.studdybuddy.model.UserPrefs> readUserPrefs() throws draganddrop.studdybuddy.commons.exceptions.DataConversionException {
        return readUserPrefs(filePath);
    }
}
