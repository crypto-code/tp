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
import seedu.address.model.person.Person;


/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonDetails extends UiPart<Region> {

    private static final String FXML = "PersonDetails.fxml";
    private static final String TELEGRAM_URL_PREFIX = "https://t.me/";

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
    private ImageView profileView;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonDetails(Person person) {
        super(FXML);
        this.setPerson(person);
    }

    public void setPerson(Person person) {
        this.person = person;
        if (person == null) {
            cardPane.setVisible(false);
            return;
        }
        cardPane.setVisible(true);
        name.setText(person.getName().fullName);
        String teleUrl = TELEGRAM_URL_PREFIX + person.getTelegram();
        telegram.setText("@" + person.getTelegram().value);
        github.setText(person.getGithub().value);
        tags.getChildren().removeIf(c -> true);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
        telegram.setOnMouseClicked((event) -> openTelegram(teleUrl));
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
        String userName = person.getGithub().toString();
        Image userGitHubProfilePicture = GitHubUtil.getProfilePicture(userName);
        profileView.setEffect(new DropShadow(20, Color.BLACK));
        profileView.setImage(userGitHubProfilePicture);
    }

    /**
     * Opens the t.me link to telegram in the default web browser on
     * the system.
     */
    @FXML
    public void openTelegram(String teleUrl) {
        try {
            Desktop.getDesktop().browse(new URL(teleUrl).toURI());
        } catch (IOException e) {
            logger.severe("Could not open browser to show link to telegram.");
        } catch (URISyntaxException e) {
            logger.severe("URL to telegram not formatted well.");
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
