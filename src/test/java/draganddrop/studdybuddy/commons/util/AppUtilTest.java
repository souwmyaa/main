package draganddrop.studdybuddy.commons.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static draganddrop.studdybuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AppUtilTest {

    @Test
    public void getImage_exitingImage() {
        assertNotNull(AppUtil.getImage("/images/study_buddy.png"));
    }

    @Test
    public void getImage_nullGiven_throwsNullPointerException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> AppUtil.getImage(null));
    }

    @Test
    public void checkArgument_true_nothingHappens() {
        AppUtil.checkArgument(true);
        AppUtil.checkArgument(true, "");
    }

    @Test
    public void checkArgument_falseWithoutErrorMessage_throwsIllegalArgumentException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(IllegalArgumentException.class, () -> AppUtil.checkArgument(false));
    }

    @Test
    public void checkArgument_falseWithErrorMessage_throwsIllegalArgumentException() {
        String errorMessage = "error message";
        draganddrop.studdybuddy.testutil.Assert.assertThrows(IllegalArgumentException.class, errorMessage, () -> AppUtil.checkArgument(false, errorMessage));
    }
}
