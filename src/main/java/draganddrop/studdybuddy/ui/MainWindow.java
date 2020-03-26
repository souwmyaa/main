package draganddrop.studdybuddy.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = draganddrop.studdybuddy.commons.core.LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private draganddrop.studdybuddy.logic.Logic logic;

    // Independent Ui parts residing in this Ui container
    private TaskListPanel taskListPanel;
    private TaskSummaryPanel taskSummaryPanel;
    private DueSoonListPanel dueSoonListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private HBox taskListHolder;

    @FXML
    private StackPane taskListPanelPlaceholder;

    @FXML
    private StackPane dueSoonListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane timeLeftbarPlaceholder;

    @FXML
    private StackPane taskSummaryHolder;

    @FXML
    private StackPane modulePaneHolder;

    public MainWindow(Stage primaryStage, draganddrop.studdybuddy.logic.Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());

        taskSummaryPanel = new TaskSummaryPanel(logic.getFilteredTaskList(),
            logic.getFilteredArchivedTaskList());
        taskSummaryHolder.getChildren().add(taskSummaryPanel.getRoot());
        taskSummaryHolder.setVisible(false);
        taskSummaryHolder.setManaged(false);

        dueSoonListPanel = new DueSoonListPanel(logic.getFilteredDueSoonTaskList());
        dueSoonListPanelPlaceholder.getChildren().add(dueSoonListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(draganddrop.studdybuddy.commons.core.GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    /**
     * Shows the task summaries.
     */
    @FXML
    public void handleShowTaskSummary() {
        if (!taskSummaryHolder.isManaged()) {
            toggleTaskSummaryHolder();
            toggleTaskListHolder();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        draganddrop.studdybuddy.commons.core.GuiSettings guiSettings = new draganddrop.studdybuddy.commons.core.GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
            (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Shows all tasks.
     */
    @FXML
    private void handleShowAllTasks() {
        toggleHolder();
        taskListPanel = new TaskListPanel(logic.getFilteredTaskList());
        taskListPanelPlaceholder.getChildren().add(taskListPanel.getRoot());
        handleDueSoonTasks();
    }

    @FXML
    private void handleDueSoonTasks() {
        dueSoonListPanel = new DueSoonListPanel(logic.getFilteredDueSoonTaskList());
        dueSoonListPanelPlaceholder.getChildren().add(dueSoonListPanel.getRoot());
    }

    /**
     * Shows all archived tasks.
     */
    @FXML
    private void handleShowArchivedTasks() {
        toggleHolder();
        TaskListPanel archiveListPanel = new TaskListPanel(logic.getFilteredArchivedTaskList());
        taskListPanelPlaceholder.getChildren().add(archiveListPanel.getRoot());
        handleDueSoonTasks();
    }

    /**
     * handles modules to be displayed
     */
    @FXML
    private void handleShowModules() {
        toggleHolder();
        ObservableList<draganddrop.studdybuddy.model.module.Module> modulesToBeDisplayed = logic.getFilteredModuleList();
        ModuleListPanel moduleListPanel = new ModuleListPanel(modulesToBeDisplayed);
        taskListPanelPlaceholder.getChildren().add(moduleListPanel.getRoot());
        handleDueSoonTasks();
    }



    @FXML
    private void handleShowCalendar() {
        toggleHolder();
        CalendarBox calendar = new CalendarBox(logic.getFilteredTaskList(), dueSoonListPanelPlaceholder);
        taskListPanelPlaceholder.getChildren().add(calendar.getRoot());
    }

    public TaskListPanel getPersonListPanel() {
        return taskListPanel;
    }

    public DueSoonListPanel getDueSoonListPanel() {
        return dueSoonListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see draganddrop.studdybuddy.logic.Logic#execute(String)
     */
    private draganddrop.studdybuddy.logic.commands.CommandResult executeCommand(draganddrop.studdybuddy.ui.interactiveprompt.InteractivePrompt currentInteractivePrompt, String commandText)
        throws draganddrop.studdybuddy.logic.commands.exceptions.CommandException, draganddrop.studdybuddy.logic.parser.exceptions.ParseException {

        currentInteractivePrompt.setLogic(logic);
        String reply = currentInteractivePrompt.interact(commandText);
        resultDisplay.setFeedbackToUser(reply);
        if (currentInteractivePrompt.isQuit()) {
            handleExit();
        }

        if (currentInteractivePrompt.isEndOfCommand()) {
            return new draganddrop.studdybuddy.logic.commands.CommandResult("Set current interactive to null", false, false);
        }
        return null;
    }

    /**
     * Toggle function wrapper.
     */
    private void toggleHolder() {
        if (taskSummaryHolder.isManaged()) {
            toggleTaskSummaryHolder();
            toggleTaskListHolder();
        }
    }

    /**
     * Toggles the taskSummaryHolder's visibility.
     */
    private void toggleTaskSummaryHolder() {
        if (taskSummaryHolder.isManaged()) {
            taskSummaryHolder.setVisible(false);
            taskSummaryHolder.setManaged(false);
        } else {
            taskSummaryHolder.setManaged(true);
            taskSummaryHolder.setVisible(true);
        }
    }

    /**
     * Toggles the taskListHolder's visibility.
     */
    private void toggleTaskListHolder() {
        if (taskListHolder.isManaged()) {
            taskListHolder.setVisible(false);
            taskListHolder.setManaged(false);
        } else {
            taskListHolder.setManaged(true);
            taskListHolder.setVisible(true);
        }
    }
}
