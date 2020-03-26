package draganddrop.studdybuddy.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests JSON Read and Write
 */
public class JsonUtilTest {

    private static final Path SERIALIZATION_FILE = draganddrop.studdybuddy.testutil.TestUtil.getFilePathInSandboxFolder("serialize.json");

    @Test
    public void serializeObjectToJsonFile_noExceptionThrown() throws IOException {
        draganddrop.studdybuddy.testutil.SerializableTestClass serializableTestClass = new draganddrop.studdybuddy.testutil.SerializableTestClass();
        serializableTestClass.setTestValues();

        JsonUtil.serializeObjectToJsonFile(SERIALIZATION_FILE, serializableTestClass);

        Assertions.assertEquals(FileUtil.readFromFile(SERIALIZATION_FILE), draganddrop.studdybuddy.testutil.SerializableTestClass.JSON_STRING_REPRESENTATION);
    }

    @Test
    public void deserializeObjectFromJsonFile_noExceptionThrown() throws IOException {
        FileUtil.writeToFile(SERIALIZATION_FILE, draganddrop.studdybuddy.testutil.SerializableTestClass.JSON_STRING_REPRESENTATION);

        draganddrop.studdybuddy.testutil.SerializableTestClass serializableTestClass = JsonUtil
            .deserializeObjectFromJsonFile(SERIALIZATION_FILE, draganddrop.studdybuddy.testutil.SerializableTestClass.class);

        org.junit.jupiter.api.Assertions.assertEquals(serializableTestClass.getName(), draganddrop.studdybuddy.testutil.SerializableTestClass.getNameTestValue());
        org.junit.jupiter.api.Assertions.assertEquals(serializableTestClass.getListOfLocalDateTimes(), draganddrop.studdybuddy.testutil.SerializableTestClass.getListTestValues());
        org.junit.jupiter.api.Assertions.assertEquals(serializableTestClass.getMapOfIntegerToString(), draganddrop.studdybuddy.testutil.SerializableTestClass.getHashMapTestValues());
    }

    //TODO: @Test jsonUtil_readJsonStringToObjectInstance_correctObject()

    //TODO: @Test jsonUtil_writeThenReadObjectToJson_correctObject()
}
