package seedu.address.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javafx.scene.image.Image;
import seedu.address.commons.core.LogsCenter;

/**
 * Helps in obtaining data from the GitHub API.
 */
public class GitHubUtil {
    private static final Logger logger = LogsCenter.getLogger(GitHubUtil.class);

    private static final String API_URL_PREFIX = "https://api.github.com/";
    private static final String GITHUB_URL_PREFIX = "https://www.github.com/";
    private static final Image defaultUserProfilePicture = new Image(
            GitHubUtil.class.getResourceAsStream("/images/profile.png"));
    private static int responseCode;
    private static URL url;

    /**
     * Initializes a GitHubUtil Object.
     */
    private GitHubUtil() {
    }

    /**
     * Establishes a connection to the server, and
     * updates the response code accordingly.
     *
     * @param extension The extension of the url to go to.
     */
    private static void establishConnectionForWebsite(String extension) {
        try {
            url = new URL(GITHUB_URL_PREFIX + extension);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) "
                    + "Gecko/20100316 Firefox/3.6.2");
            conn.connect();

            responseCode = conn.getResponseCode();
        } catch (MalformedURLException e) {
            logger.severe("Improper URL Format.");
        } catch (IOException e) {
            logger.severe("A Connection with the server could not be established.");
        }
    }

    /**
     * Establishes a connection to the server via API, and
     * updates the response code accordingly.
     *
     * @param userName The name of the user.
     */
    private static void establishConnectionForApi(String userName) {
        String userUrl = API_URL_PREFIX + userName;

        try {
            url = new URL(userUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            responseCode = conn.getResponseCode();
        } catch (MalformedURLException e) {
            logger.severe("Improper URL Format.");
        } catch (IOException e) {
            logger.severe("A Connection with the server could not be established.");
        }
    }

    /**
     * Returns a {@code JSONObject} consisting of the user data
     * obtained from the GitHub API.
     *
     * @param userName The name of the user.
     * @return A {@code JSONObject} consisting of the user data.
     * @throws RuntimeException If the server did not respond well.
     */
    public static JSONObject getProfile(String userName) throws RuntimeException {
        assert userName != null && !userName.equals("") : "No UserName Found";

        establishConnectionForApi("users/" + userName);
        JSONObject data = null;

        if (responseCode != 200) {
            logger.severe("Server responded with error code " + responseCode);
            throw new RuntimeException("Data could not be obtained.");
        } else {
            String inline = "";

            try {
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    inline += scanner.nextLine();
                }

                scanner.close();
            } catch (IOException e) {
                logger.severe("Data could not be obtained from the URL.");
            }

            try {
                JSONParser jsonParser = new JSONParser();
                data = (JSONObject) jsonParser.parse(inline);
            } catch (ParseException e) {
                logger.severe("Data obtained could not be parsed into JSON Format.");
            }
        }
        return data;
    }

    /**
     * Returns the profile picture of the requested
     * user on GitHub.
     *
     * @param userName The name of the user.
     * @return An {@code Image} object with the users profile picture in it.
     */
    public static Image getProfilePicture(String userName) {
        assert userName != null && !userName.equals("") : "No UserName Found";
        JSONObject jsonObject = null;

        try {
            jsonObject = getProfile(userName);
        } catch (RuntimeException e) {
            logger.severe("Profile Picture Could not be obtained. Using default.");
            return defaultUserProfilePicture;
        }

        assert jsonObject != null : "No Data Found";

        String userProfileUrl = (String) jsonObject.get("avatar_url");
        Image image = new Image(userProfileUrl);

        return image;
    }

    /**
     * Returns a list of user repositories present on their GitHub.
     *
     * @param userName The name of the user.
     * @param page The page to start to get data from.
     * @return An {@code ArrayList<String>} consisting of user repositories.
     */
    public static ArrayList<String> getRepoNames(String userName, int page) throws RuntimeException {
        establishConnectionForApi("users/" + userName + "/repos?per_page=100&page=" + page);

        JSONArray data = null;

        if (responseCode != 200) {
            logger.severe("Server responded with error code " + responseCode);
            throw new RuntimeException("Data could not be obtained.");
        } else {
            StringBuilder inline = new StringBuilder();

            try {
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }

                scanner.close();
            } catch (IOException e) {
                logger.severe("Data could not be obtained from the URL.");
            }

            try {
                JSONParser jsonParser = new JSONParser();
                data = (JSONArray) jsonParser.parse(inline.toString());
            } catch (ParseException e) {
                logger.severe("Data obtained could not be parsed into JSON Format.");
            }
        }
        ArrayList<String> repos = new ArrayList<>();
        assert data != null : "No Data Found";

        for (Object o : data) {
            JSONObject repo = (JSONObject) o;
            repos.add(repo.get("name").toString());
        }

        if (repos.size() == 100) {
            ArrayList<String> extras = getRepoNames(userName, page + 1);
            repos.addAll(extras);
        }

        return repos;
    }

    /**
     * Returns a boolean indicating whether the programming language has
     * already been added to the list or not.
     *
     * @param language The language to check for.
     * @param listOfLanguages The list consisting of programming languages.
     * @return A boolean, indicating whether the language is present in the list or not.
     */
    public static boolean isProgrammingLanguagePresent(String language, ArrayList<String> listOfLanguages) {
        for (int j = 0; j < listOfLanguages.size(); j++) {
            if (listOfLanguages.get(j).equals(language)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a list of programming languages the user has used
     * and is familiar with.
     *
     * @param userName The name of the user.
     * @return An {@code ArrayList<String>} consisting of programming languages.
     */
    public static ArrayList<String> getFamiliarProgrammingLanguages(String userName) throws RuntimeException {
        ArrayList<String> programmingLanguages = new ArrayList<>();
        ArrayList<String> userRepos = getRepoNames(userName, 0);

        for (int i = 0; i < userRepos.size(); i++) {
            establishConnectionForApi("repos/" + userName + "/" + userRepos.get(i) + "/languages");

            JSONObject data = null;

            if (responseCode != 200) {
                logger.severe("Server responded with error code " + responseCode);
                throw new RuntimeException("Data could not be obtained.");
            } else {
                StringBuilder inline = new StringBuilder();

                try {
                    Scanner scanner = new Scanner(url.openStream());

                    while (scanner.hasNext()) {
                        inline.append(scanner.nextLine());
                    }

                    scanner.close();
                } catch (IOException e) {
                    logger.severe("Data could not be obtained from the URL.");
                }

                try {
                    JSONParser jsonParser = new JSONParser();
                    data = (JSONObject) jsonParser.parse(inline.toString());
                } catch (ParseException e) {
                    logger.severe("Data obtained could not be parsed into JSON Format.");
                }
            }
            assert data != null : "No Data Found";

            Iterator<String> languages = data.keySet().iterator();
            while (languages.hasNext()) {
                String languageToAdd = (String) languages.next();
                if (!isProgrammingLanguagePresent(languageToAdd, programmingLanguages)) {
                    programmingLanguages.add(languageToAdd);
                }
            }
        }
        return programmingLanguages;
    }

    /**
     * Returns the total number of commits/contributions a person on GitHub
     * has made to date.
     *
     * @param userName The name of the user.
     * @return The total contributions found on GitHub else -1, if any error occurred.
     * @throws RuntimeException If the server did not respond well.
     */
    public static int getContributionsCount(String userName) throws RuntimeException {
        establishConnectionForWebsite(userName);

        if (responseCode != 200) {
            logger.severe("Server responded with error code " + responseCode);
            throw new RuntimeException("Data could not be obtained.");
        } else {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                String inputLine;
                StringBuilder html = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    html.append(inputLine);
                }

                in.close();

                String htmlString = html.toString();

                Pattern p = Pattern.compile("(?<=<h2 class=\"f4 text-normal mb-2\">)(.*?)(?=</h2>)");
                Matcher m = p.matcher(htmlString);
                String target = "";

                while (m.find()) {
                    target = m.group();
                }

                return Integer.parseInt(target.strip().split("\\s+")[0]);
            } catch (IOException e) {
                logger.severe("Contribution Count Could not be obtained.");
                return -1;
            }
        }
    }

    /**
     * Returns the total number of repos a person on GitHub
     * has made to date.
     *
     * @param userName The name of the user.
     * @return The total number of repos found on GitHub else -1, if any error occurred.
     * @throws RuntimeException If the server did not respond well.
     */
    public static int getRepoCount(String userName) throws RuntimeException {
        establishConnectionForWebsite(userName);

        if (responseCode != 200) {
            logger.severe("Server responded with error code " + responseCode);
            throw new RuntimeException("Data could not be obtained.");
        } else {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                String inputLine;
                StringBuilder html = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    html.append(inputLine);
                }

                in.close();

                String htmlString = html.toString();

                Pattern p = Pattern.compile("(?<=Repositories)(.*?)(?=</span>)");
                Matcher m = p.matcher(htmlString);
                String target = "";

                while (m.find()) {
                    target = m.group();
                }

                return Integer.parseInt(target.split("class=\"Counter\">")[1]);
            } catch (IOException e) {
                logger.severe("Repo Count Could not be obtained.");
                return -1;
            }
        }
    }

    //Add Storing capabilities for avatar_url
}
