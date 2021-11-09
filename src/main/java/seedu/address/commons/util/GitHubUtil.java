package seedu.address.commons.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.image.Image;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Github;

/**
 * Helps in obtaining data from the GitHub API.
 */
public class GitHubUtil {
    public static final Image DEFAULT_USER_PROFILE_PICTURE = new Image(
            GitHubUtil.class.getResourceAsStream("/images/profile.png"));

    private static final Logger logger = LogsCenter.getLogger(GitHubUtil.class);

    private static final String GITHUB_URL_PREFIX = "https://github.com/";
    private static final HttpClient httpClient = HttpClient.newBuilder().priority(1).build();

    /**
     * Establishes a connection to the server, and
     * updates the response code accordingly.
     *
     * @param extension The extension of the url to go to.
     */
    private static String establishConnectionForWebsite(String extension) {
        try {
            String url = GITHUB_URL_PREFIX + extension;
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();
            HttpResponse<String> httpResponse = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (httpResponse.statusCode() == 429) {
                Thread.sleep(1000 + new Random().nextInt(500));
                return establishConnectionForWebsite(extension);
            }

            if (httpResponse.statusCode() == 403) {
                logger.severe("Call Limit Reached: " + httpResponse.statusCode());
                return null;
            }

            if (httpResponse.statusCode() == 404) {
                logger.severe("User Not Found: " + httpResponse.statusCode());
                return null;
            }

            if (httpResponse.statusCode() != 200) {
                logger.severe("Server responded with Error Code: " + httpResponse.statusCode());
                return null;
            }

            return StringUtil.clean(httpResponse.body());
        } catch (MalformedURLException e) {
            logger.severe("Improper URL Format.");
        } catch (IOException | InterruptedException e) {
            logger.severe("A Connection with the server could not be established.");
        }
        return null;
    }

    /**
     * Returns the profile picture of the requested
     * user on GitHub.
     *
     * @param userName The name of the user.
     * @return An {@code Image} object with the users profile picture in it.
     * @throws RuntimeException If invalid username.
     */
    public static Image getProfilePicture(String userName) throws RuntimeException {
        assert userName != null && !userName.equals("") : "No UserName Found";

        if (!Github.isValidGithub(userName)) {
            logger.severe("Invalid GitHib Account");
        }

        String htmlString = establishConnectionForWebsite(userName);
        if (htmlString == null) {
            return DEFAULT_USER_PROFILE_PICTURE;
        } else {
            Pattern p = Pattern.compile(
                    "(?<=class=avatar avatar-user width-full)(.*?)(?=\\s*/>)");
            Matcher m = p.matcher(htmlString);
            String target = "";

            while (m.find()) {
                target = m.group();
            }

            target = target.split("src=")[1];
            URL url = null;

            try {
                url = new URL(target);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            InputStream in = null;

            try {
                in = new BufferedInputStream(url.openStream());
            } catch (IOException e) {
                System.out.println(target.toString());
                e.printStackTrace();
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buf = new byte[1024];
            int n = 0;

            while (true) {
                try {
                    if (-1 == (n = in.read(buf))) {
                        break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                out.write(buf, 0, n);
            }

            try {
                out.close();
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            byte[] response = out.toByteArray();
            return new Image(new ByteArrayInputStream(response));
        }
    }


    /**
     * Returns the list of the most recently contributed repositories
     * on a person's GitHub.
     *
     * @param userName The name of the user.
     * @return a list of repository names
     * @throws RuntimeException If invalid username or the server did not respond well.
     */
    public static ArrayList<String> getRepoNames(String userName) throws RuntimeException {

        if (!Github.isValidGithub(userName)) {
            throw new RuntimeException("Invalid GitHib Account");
        }

        String htmlString = establishConnectionForWebsite(userName + "?tab=repositories");

        if (htmlString == null) {
            throw new RuntimeException("Data could not be obtained.");
        } else {
            Pattern p = Pattern.compile("(?<=codeRepository)(.*?)(?=</a>)");
            Matcher m = p.matcher(htmlString);
            ArrayList<String> repos = new ArrayList<>();
            while (m.find()) {
                repos.add(StringUtil.clean(m.group(), ">"));
            }

            return repos;
        }
    }

    /**
     * Returns a {@code HashMap} consisting of the programming
     * languages that the user has been using.
     *
     * @param userName The user whose data is to be obtained.
     * @param repoName The repo from which the languages are to be obtained.
     * @return A {@code HashMap} consisting of the languages.
     */
    private static HashMap<String, Double> getRepoLanguages(String userName, String repoName) {
        if (!Github.isValidGithub(userName)) {
            throw new RuntimeException("Invalid GitHib Account");
        }

        String htmlString = establishConnectionForWebsite(userName + "/" + repoName);
        if (htmlString == null) {
            logger.severe("Data could not be obtained.");
            return null;
        } else {
            Pattern p = Pattern.compile("(?<=text-bold mr-1)(.*?)(?=</a>)");
            Matcher m = p.matcher(htmlString);
            HashMap<String, Double> repoLanguages = new HashMap<>();

            while (m.find()) {
                String[] texts = StringUtil.clean(m.group(), ">").split("</span");
                String language = texts[0];
                String value = texts[1].replace(" <span", "").replace("%", "");
                repoLanguages.put(language, Double.parseDouble(value) / 100);
            }

            return repoLanguages;
        }
    }

    private static void normalize(HashMap<String, Double> features) {
        double sum = features.values().parallelStream().mapToDouble(k -> k).sum();
        features.keySet().parallelStream().forEach(k -> {
            features.replace(k, features.get(k) / sum);
        });
    }

    private static void updateLanguageStats(HashMap<String, Double> total, HashMap<String, Double> stats) {
        stats.keySet().parallelStream().forEach(k -> {
            if (total.containsKey(k)) {
                total.replace(k, total.get(k) + stats.get(k));
            } else {
                total.put(k, stats.get(k));
            }
        });
    }

    /**
     * Returns the % of contributions by language for a GitHub username in the given repositories
     *
     * @param userName The name of the user.
     * @param repoNames a list of repositories to parse
     * @return the stats on the languages used
     * @throws RuntimeException If invalid username or the server did not respond well.
     */
    public static HashMap<String, Double> getLanguageStats(String userName, ArrayList<String> repoNames)
            throws RuntimeException {
        if (!Github.isValidGithub(userName)) {
            throw new RuntimeException("Invalid GitHib Account");
        }

        HashMap<String, Double> languageStats = new HashMap<>();
        repoNames.parallelStream().forEach(repo -> {
            HashMap<String, Double> tempStats = getRepoLanguages(userName, repo);
            if (tempStats != null) {
                updateLanguageStats(languageStats, tempStats);
            }
        });
        normalize(languageStats);

        return languageStats;
    }

    /**
     * Returns the total number of repositories a person on GitHub
     * has made to date.
     *
     * @param userName The name of the user.
     * @return The total number of repos found on GitHub else -1, if any error occurred.
     * @throws RuntimeException If invalid username or the server did not respond well.
     */
    public static int getRepoCount(String userName) throws RuntimeException {
        if (!Github.isValidGithub(userName)) {
            throw new RuntimeException("Invalid GitHib Account");
        }

        String htmlString = establishConnectionForWebsite(userName);

        if (htmlString == null) {
            throw new RuntimeException("Data could not be obtained.");
        } else {
            Pattern p = Pattern.compile("(?<=Repositories)(.*?)(?=</span>)");
            Matcher m = p.matcher(htmlString);
            String target = "";

            while (m.find()) {
                target = m.group();
            }

            if (target.isBlank()) {
                return 0;
            }

            return Integer.parseInt(target.split("class=Counter>")[1]);
        }
    }

    /**
     * Returns a {@code HashMap} object with the stats of the user including
     * % of language contributions and number of repositories.
     * @param userName The name of the user.
     *
     * @return stats on the user
     * @throws RuntimeException If invalid username or the server did not respond well.
     */
    public static HashMap<String, Double> getUserStats(String userName) throws RuntimeException {
        if (!Github.isValidGithub(userName)) {
            throw new RuntimeException("Invalid GitHib Account");
        }

        ArrayList<String> repos = getRepoNames(userName);
        HashMap<String, Double> stats = getLanguageStats(userName, repos);
        stats.put("repo-count", (double) getRepoCount(userName));
        return stats;
    }
}
