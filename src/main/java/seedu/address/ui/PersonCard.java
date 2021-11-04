package seedu.address.ui;

import java.util.Comparator;
import java.util.Locale;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import seedu.address.commons.util.GitHubUtil;
import seedu.address.logic.ai.ThreadProcessor;
import seedu.address.model.person.Person;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    // Images for favorite button
    private static final Image FAVORITE = new Image(
            PersonCard.class.getResourceAsStream("/images/fav.png"));
    private static final Image NOT_FAVORITE = new Image(
            PersonCard.class.getResourceAsStream("/images/unfav.png"));

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private TextFlow name;
    @FXML
    private Label id;
    @FXML
    private FlowPane tags;
    @FXML
    private ImageView profileView;
    @FXML
    private ImageView favBtn;


    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");

        setName(person);

        String[] tagClasses = new String[] {"tag-general", "tag-event", "tag-mod"};
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> {
                    Label temp = new Label(tag.tagName);
                    temp.setId(tagClasses[tag.getIntType()]);
                    tags.getChildren().add(temp);
                });
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
            temp.setPriority(Thread.MAX_PRIORITY);
            ThreadProcessor.addThread(temp);
        }

        // set favBtn here based on isFavorite
        if (person.isFavourite()) {
            favBtn.setImage(FAVORITE);
        } else {
            favBtn.setImage(NOT_FAVORITE);
        }
    }

    public void setName(Person person) {
        if (person.getFindHighlight() != null) {
            String nameString = person.getName().fullName.toLowerCase(Locale.ROOT);
            int start = nameString.indexOf(person.getFindHighlight());
            int end = start + person.getFindHighlight().length();
            Text startText = new Text(person.getName().fullName.substring(0, start));
            Text midText = new Text(person.getName().fullName.substring(start, end));
            Text endText = new Text(person.getName().fullName.substring(end));
            startText.getStyleClass().add("big_label");
            midText.getStyleClass().add("big_label");
            endText.getStyleClass().add("big_label");
            startText.setFill(Color.WHITE);
            midText.setFill(Color.LIME);
            endText.setFill(Color.WHITE);
            name.getChildren().addAll(startText, midText, endText);
        } else {
            Text nameText = new Text(person.getName().fullName);
            nameText.getStyleClass().add("big_label");
            nameText.setFill(Color.WHITE);
            name.getChildren().add(nameText);
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && person.equals(card.person);
    }
}
