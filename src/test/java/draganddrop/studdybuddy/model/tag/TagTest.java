package draganddrop.studdybuddy.model.tag;

import static draganddrop.studdybuddy.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TagTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> new Tag(null));
    }

    @Test
    public void constructor_invalidTagName_throwsIllegalArgumentException() {
        String invalidTagName = "";
        draganddrop.studdybuddy.testutil.Assert.assertThrows(IllegalArgumentException.class, () -> new Tag(invalidTagName));
    }

    @Test
    public void isValidTagName() {
        // null tag name
        draganddrop.studdybuddy.testutil.Assert.assertThrows(NullPointerException.class, () -> Tag.isValidTagName(null));
    }

}
