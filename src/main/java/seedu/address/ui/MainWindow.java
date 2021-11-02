package seedu.address.ui;

import java.io.IOException;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.ai.ThreadProcessor;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private static boolean isDone = false;

    private final Logger logger = LogsCenter.getLogger(getClass());

    private final Stage primaryStage;
    private final Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    private ResultDisplay resultDisplay;
    private final HelpWindow helpWindow;
    private final UserProfileWindow userProfileWindow;
    private TabPaneHeader tabPaneHeader;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private MenuItem profileMenuItem;

    @FXML
    private MenuItem contactsMenuItem;

    @FXML
    private MenuItem favoritesMenuItem;

    @FXML
    private MenuItem eventsMenuItem;

    @FXML
    private MenuItem findABuddyMenuItem;

    @FXML
    private StackPane personListPanelPlaceholder;

    @FXML
    private StackPane personCardPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private StackPane progressPlaceHolder;

    @FXML
    private StackPane tabPanePlaceholder;

    @FXML
    private VBox mainWindow;

    @FXML
    private MenuBar menuBar;

    @FXML
    private HBox menuHolder;

    @FXML
    private HBox mainHBox;

    @FXML
    private HBox footerHBar;

    @FXML
    private ImageView eventsIcon;

    @FXML
    private HBox userDetails;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        // Fix dimension based on screen resolution
        primaryStage.setMinHeight(800);
        primaryStage.setMaxHeight(800);
        primaryStage.setMinWidth(950);

        // Configure all keyboard shortcuts
        setAccelerators();

        // Initialize Help window
        helpWindow = new HelpWindow();

        userProfileWindow = new UserProfileWindow(logic);

        // Configure User Profile Window to be shown when clicked
        userDetails.setOnMouseClicked(event -> {
            handleUserProfileWindow();
        });

        // Configure Events Icon
        eventsIcon.setOnMouseClicked(event -> {
            tabPaneHeader.getTabPane().getSelectionModel().select(2);
        });
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Configures individual keyboard shortcuts.
     */
    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
        setAccelerator(contactsMenuItem, KeyCombination.valueOf("Shortcut+1"));
        setAccelerator(favoritesMenuItem, KeyCombination.valueOf("Shortcut+2"));
        setAccelerator(eventsMenuItem, KeyCombination.valueOf("Shortcut+3"));
        setAccelerator(findABuddyMenuItem, KeyCombination.valueOf("Shortcut+4"));
        setAccelerator(profileMenuItem, KeyCombination.valueOf("Shortcut+P"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator.
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
        PersonDetails personDetails = new PersonDetails(null);
        ProgressIndicatorRegion progressIndicator = new ProgressIndicatorRegion();
        tabPaneHeader = new TabPaneHeader(logic, progressIndicator);
        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), personDetails, tabPaneHeader);
        logic.setPersonList(personListPanel);

        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        personCardPlaceholder.getChildren().add(personDetails.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());
        personListPanelPlaceholder.getChildren().add(progressIndicator.getRoot());
        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());
        tabPanePlaceholder.getChildren().add(tabPaneHeader.getRoot());
        personListPanel.setTabPaneHeader(tabPaneHeader);

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the User Data to be displayed on the
     * Menu Bar.
     */
    public void setUserProfileInMenuBar() {
        if (logic.isProfilePresent() && userDetails.getChildren().isEmpty()) {
            UserProfileInMenuBar userProfileInMenuBar = new UserProfileInMenuBar(logic);
            userDetails.getChildren().add(userProfileInMenuBar.getRoot());
        }
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
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
            helpWindow.getRoot().toFront();
        }
    }

    /**
     * Opens the current user's Telegram.
     */
    public void handleTelegram() {
        personListPanel.openTelegram();
    }

    /**
     * Opens the current user's GitHub.
     */
    public void handleGithub() {
        personListPanel.openGithub();
    }

    /**
     * Opens the user profile window or focuses on it if it's already opened.
     */
    @FXML
    public void handleUserProfileWindow() {
        if (!userProfileWindow.isShowing()) {
            userProfileWindow.show();
        } else {
            userProfileWindow.focus();
            userProfileWindow.getRoot().toFront();
        }
    }

    /**
     * Switches to the Contacts tab.
     */
    @FXML
    public void handleContacts() {
        tabPaneHeader.activateContacts(logic);
    }

    /**
     * Activates the Favorites tab.
     */
    @FXML
    public void handleFavorites() {
        tabPaneHeader.activateFavorites(logic);
    }

    /**
     * Switches to the Events tab.
     */
    @FXML
    public void handleEvents() {
        tabPaneHeader.activateEvents(logic);
    }

    /**
     * Switches to the Find A Buddy Tab.
     */
    @FXML
    public void handleFindABuddy() {
        tabPaneHeader.activateFindABuddy(logic);
    }

    /**
     * Launches the {@code ProfileWindow}.
     */
    public void show() {
        ProfileSetUpWindow profileSetUpWindow = new ProfileSetUpWindow(new Stage(), this, logic);
        profileSetUpWindow.start();
    }

    /**
     * Shows the {@code MainWindow}.
     */
    public void start() {
        setUserProfileInMenuBar();
        primaryStage.show();
    }

    public static boolean isDone() {
        return isDone;
    }

    public static void setDone(boolean isDone) {
        MainWindow.isDone = isDone;
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        try {
            logic.saveAllData();
        } catch (IOException e) {
            e.printStackTrace();
            logger.severe("Unable to save Address Book to Memory");
        }
        helpWindow.hide();
        userProfileWindow.hide();
        primaryStage.hide();
        tabPaneHeader.stopFabLoader();
        isDone = true;
        ThreadProcessor.stopAllThreads();
    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isTelegram()) {
                handleTelegram();
            }

            if (commandResult.isGithub()) {
                handleGithub();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
