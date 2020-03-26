package draganddrop.studdybuddy.ui.interactiveprompt.view;

/**
 * The interactive prompt for the help command. Returns the help message immediately
 */
public class HelpInteractivePrompt extends draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt {
    private static final String END_OF_COMMAND_MSG =
        "Here are the list of available commands:" + System.lineSeparator()
            + "1. add  2. delete  3. edit  4. bye  5. sort  6. find  7. done  8. delete duplicates  "
            + "9. sort  10. archive  11. help  12. list  13. clear 14. create mods"
            + System.lineSeparator() + System.lineSeparator()
            + "User Guide: https://ay1920s2-cs2103t-w16-3.github.io/main/UserGuide.html";


    public HelpInteractivePrompt() {
        super();
        this.interactivePromptType = draganddrop.studdybuddy.ui.interactiveprompt.InteractivePromptType.HELP;
    }

    public static String getHelpMessage() {
        return END_OF_COMMAND_MSG;
    }

    /**
     * Returns the help message to the user immediately
     *
     * @param userInput the help keyword
     * @return the help message
     */
    @Override
    public String interact(String userInput) {
        endInteract(END_OF_COMMAND_MSG);
        return reply;
    }

}

