package draganddrop.studdybuddy.ui.interactiveprompt;

import static org.junit.jupiter.api.Assertions.assertEquals;

import draganddrop.studdybuddy.ui.interactiveprompt.view.HelpInteractivePrompt;
import org.junit.jupiter.api.Test;

class HelpInteractivePromptTest {
    private HelpInteractivePrompt hip = new HelpInteractivePrompt();

    @Test
    public void interact() {
        assertEquals(hip.interact("help"), HelpInteractivePrompt.getHelpMessage());
    }
}
