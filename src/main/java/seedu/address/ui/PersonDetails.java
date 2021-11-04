package seedu.address.ui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Comparator;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.GitHubUtil;
import seedu.address.commons.util.LangColorUtil;
import seedu.address.model.person.Person;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonDetails extends UiPart<Region> {

    private static final String FXML = "PersonDetails.fxml";
    private static final String TELEGRAM_URL_PREFIX = "https://t.me/";
    private static final String GITHUB_URL_PREFIX = "https://github.com/";

    private static final Logger logger = LogsCenter.getLogger(HelpWindow.class);
    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    private Person person;

    @FXML
    private VBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label telegram;
    @FXML
    private Label github;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label simScore;
    @FXML
    private FlowPane commonLang;
    @FXML
    private ImageView profileView;
    @FXML
    private FlowPane tags;
    @FXML
    private VBox detailOptional;
    @FXML
    private VBox similarityOptional;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonDetails(Person person) {
        super(FXML);
        this.setPerson(person, false);
        this.getRoot().setPrefHeight(Region.USE_PREF_SIZE);
        detailOptional.managedProperty().bind(detailOptional.visibleProperty());
        similarityOptional.managedProperty().bind(similarityOptional.visibleProperty());
    }

    public void setPerson(Person person, boolean showSimilarity) {
        this.person = person;
        if (person == null) {
            cardPane.setVisible(false);
            return;
        }
        cardPane.setVisible(true);
        name.setText(person.getName().fullName);
        telegram.setText("@" + person.getTelegram().value);
        github.setText(person.getGithub().value);
        tags.getChildren().removeIf(c -> true);
        String[] tagClasses = new String[] {"tag-general", "tag-event", "tag-mod"};
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label temp = new Label(tag.tagName);
                    temp.setId(tagClasses[tag.getIntType()]);
                    tags.getChildren().add(temp);
                });
        telegram.setOnMouseClicked((event) -> openTelegram());
        github.setOnMouseClicked((event) -> openGithub());
        if (person.getPhone().value.isBlank()) {
            phone.setText("-");
        } else {
            phone.setText(person.getPhone().value);
        }
        if (person.getAddress().value.isBlank()) {
            address.setText("-");
        } else {
            address.setText(person.getAddress().value);
        }
        if (person.getEmail().value.isBlank()) {
            email.setText("-");
        } else {
            email.setText(person.getEmail().value);
        }
        Rectangle clip = new Rectangle(
                profileView.getFitWidth(), profileView.getFitHeight()
        );
        clip.setArcWidth(profileView.getFitWidth() / 2);
        clip.setArcHeight(profileView.getFitHeight() / 2);
        profileView.setClip(clip);

        // To Obtain the user GitHub username and to fetch and display it.
        Image userGitHubProfilePicture = person.getProfilePicture();
        profileView.setEffect(new DropShadow(20, Color.BLACK));
        profileView.setImage(userGitHubProfilePicture);
        if (userGitHubProfilePicture == GitHubUtil.DEFAULT_USER_PROFILE_PICTURE) {
            Thread temp = new Thread(() -> {
                while (person.getProfilePicture() == GitHubUtil.DEFAULT_USER_PROFILE_PICTURE && !MainWindow.isDone()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        //e.printStackTrace();
                    }
                }
                profileView.setImage(person.getProfilePicture());
            });
            temp.start();
        }

        simScore.setText(person.getSimScore() + "%");
        commonLang.getChildren().removeIf(l -> true);
        person.getCommonLanguages().forEach(l -> {
            Label temp = new Label(l);
            String style = "-fx-background-color: " + LangColorUtil.getHex(LangColorUtil.getBackColor(l)) + "; ";
            style += "-fx-text-fill: " + LangColorUtil.getHex(LangColorUtil.getFontColor(l));
            temp.setStyle(style);
            commonLang.getChildren().add(temp);
        });

        if (showSimilarity) {
            detailOptional.setVisible(false);
            detailOptional.getChildren().parallelStream().forEach(n -> n.setVisible(false));
            similarityOptional.setVisible(true);
            similarityOptional.getChildren().parallelStream().forEach(n -> n.setVisible(true));
        } else {
            detailOptional.setVisible(true);
            detailOptional.getChildren().parallelStream().forEach(n -> n.setVisible(true));
            similarityOptional.setVisible(false);
            similarityOptional.getChildren().parallelStream().forEach(n -> n.setVisible(false));
        }
    }

    /**
     * Opens the t.me link to telegram in the default web browser on
     * the system.
     */
    @FXML
    public void openTelegram() {
        String teleUrl = TELEGRAM_URL_PREFIX + person.getTelegram();
        try {
            Desktop.getDesktop().browse(new URL(teleUrl).toURI());
        } catch (IOException e) {
            logger.severe("Could not open browser to show link to Telegram.");
        } catch (URISyntaxException e) {
            logger.severe("URL to Telegram not formatted well.");
        }
    }

    /**
     * Opens the link to the person's GitHub profile in the default web browser on
     * the system.
     */
    @FXML
    public void openGithub() {
        String githubUrl = GITHUB_URL_PREFIX + person.getGithub();
        try {
            Desktop.getDesktop().browse(new URL(githubUrl).toURI());
        } catch (IOException e) {
            logger.severe("Could not open browser to show link to Github.");
        } catch (URISyntaxException e) {
            logger.severe("URL to Github not formatted well.");
        }
    }
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonDetails)) {
            return false;
        }

        // state check
        PersonDetails card = (PersonDetails) other;
        return person.equals(card.person);
    }
}
