package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.GitHubUtil;
import seedu.address.logic.ai.ThreadProcessor;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person implements Comparable<Person> {

    // Identity fields
    private final Name name;
    private final Telegram telegram;
    private final Github github;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();

    // Status fields
    private boolean isFavourite;

    // Extra fields
    private Image profilePicture;
    private Thread getProfilePicThread;
    private Thread getStatsThread;
    private HashMap<String, Double> gitStats;
    private ArrayList<String> commonLanguages = new ArrayList<>();
    private double simScore = 0.0;

    private String findHighlight = null;

    private final Logger logger = LogsCenter.getLogger(getClass());

    /**
     * Every field must be present and not null.
     */
    public Person(Name name, Telegram telegram, Github github,
                  Phone phone, Email email, Address address, Set<Tag> tags) {
        requireAllNonNull(name, telegram, github, phone, email, address, tags);
        this.name = name;
        this.telegram = telegram;
        this.github = github;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.isFavourite = false;
        this.profilePicture = GitHubUtil.DEFAULT_USER_PROFILE_PICTURE;
        this.getProfilePicThread = new Thread(() -> {
            profilePicture = GitHubUtil.getProfilePicture(github.value);
        });
        getProfilePicThread.setPriority(Thread.MAX_PRIORITY);
        getProfilePicThread.start();
        this.gitStats = new HashMap<>();
        startGetStatThread();
    }

    /**
     * Overloaded constructor used when loading contacts from Json file.
     */
    public Person(Name name, Telegram telegram, Github github,
                  Phone phone, Email email, Address address, Set<Tag> tags, boolean isFavourite) {
        this(name, telegram, github, phone, email, address, tags);
        this.isFavourite = isFavourite;
    }

    /**
     * Overloaded constructor used when loading contacts from Json file with Image.
     */
    public Person(Name name, Telegram telegram, Github github,
                  Phone phone, Email email, Address address, Set<Tag> tags, boolean isFavourite, Image image) {
        requireAllNonNull(name, telegram, github, phone, email, address, tags);
        this.name = name;
        this.telegram = telegram;
        this.github = github;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.isFavourite = isFavourite;
        this.profilePicture = image;
        this.gitStats = new HashMap<>();
        startGetStatThread();
    }

    /**
     * Overloaded constructor used when loading contacts from Json file with Image.
     */
    public Person(Name name, Telegram telegram, Github github,
                  Phone phone, Email email, Address address, Set<Tag> tags, boolean isFavourite,
                  Image image, HashMap<String, Double> gitStats) {
        requireAllNonNull(name, telegram, github, phone, email, address, tags);
        this.name = name;
        this.telegram = telegram;
        this.github = github;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.isFavourite = isFavourite;
        this.profilePicture = image;
        this.gitStats = gitStats;
    }

    private void startGetStatThread() {
        this.getStatsThread = new Thread(() -> {
            this.gitStats = GitHubUtil.getUserStats(github.value);
            logger.info("Stats for " + name.fullName + ": " + gitStats);
        });
        ThreadProcessor.addThread(getStatsThread);
    }

    public void setCommonLanguages(ArrayList<String> commonLanguages) {
        this.commonLanguages = commonLanguages;
    }

    public void setSimScore(double simScore) {
        this.simScore = simScore;
    }

    public Name getName() {
        return name;
    }

    public Telegram getTelegram() {
        return telegram;
    }

    public Github getGithub() {
        return github;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public HashMap<String, Double> getGitStats() {
        return gitStats;
    }

    public void setFindHighlight(String findHighlight) {
        this.findHighlight = findHighlight;
    }

    public String getFindHighlight() {
        return findHighlight;
    }

    public void clearHighlights() {
        this.findHighlight = null;
    }

    /**
     * Returns a boolean flag to tell whether the Person
     * is "Favourite" or not.
     */
    public boolean isFavourite() {
        return isFavourite;
    }

    public void setIsFavourite() {
        this.isFavourite = true;
    }

    public void setIsNotFavourite() {
        this.isFavourite = false;
    }

    public Image getProfilePicture() {
        return profilePicture;
    }

    public ArrayList<String> getCommonLanguages() {
        return commonLanguages;
    }

    public double getSimScore() {
        return simScore;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        return this.equals(otherPerson);
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getTelegram().equals(getTelegram())
                || otherPerson.getGithub().equals(getGithub());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, telegram, github, phone, email, address, tags);
    }

    @Override
    public int compareTo(Person p) {
        return name.compareTo(p.getName());
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append("; Telegram: ")
                .append(getTelegram())
                .append("; Github: ")
                .append(getGithub())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Email: ")
                .append(getEmail())
                .append("; Address: ")
                .append(getAddress());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
