package draganddrop.studdybuddy.model;

import static draganddrop.studdybuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

}
