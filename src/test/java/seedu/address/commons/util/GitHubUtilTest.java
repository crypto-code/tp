package seedu.address.commons.util;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import javafx.scene.image.Image;

public class GitHubUtilTest {
    @Test
    public void getProfile_invalidUserName_runtimeException() {
        boolean runtimeExceptionThrown = false;
        try {
            GitHubUtil.getProfile("/");
        } catch (RuntimeException e) {
            runtimeExceptionThrown = true;
        }
        Assert.assertTrue(runtimeExceptionThrown);
    }

    @Test
    public void getProfile_validUserName_noException() {
        boolean runtimeExceptionThrown = false;
        try {
            GitHubUtil.getProfile("jai2501");
        } catch (RuntimeException e) {
            runtimeExceptionThrown = true;
        }
        Assert.assertTrue(!runtimeExceptionThrown);
    }

    @Test
    public void getProfilePicture_invalidUserName_noException() {
        Image image = GitHubUtil.getProfilePicture("/");
        Assert.assertTrue(image != null);
    }

    /*
    // This fails as some initializations haven't happened yet.
    @Test
    public void getProfilePicture_validUserName_noException() {
        GitHubUtil.getProfilePicture("jai2501");
        Assert.assertTrue(true);
    }
     */

    @Test
    public void getRepoNames_invalidUserName_runtimeException() {
        boolean runtimeExceptionThrown = false;
        try {
            GitHubUtil.getRepoNames("/", 0);
        } catch (RuntimeException e) {
            runtimeExceptionThrown = true;
        }
        Assert.assertTrue(runtimeExceptionThrown);
    }

    @Test
    public void getRepoNames_validUserName_noException() {
        boolean runtimeExceptionThrown = false;
        try {
            GitHubUtil.getRepoNames("jai2501", 0);
        } catch (RuntimeException e) {
            runtimeExceptionThrown = true;
        }
        Assert.assertTrue(!runtimeExceptionThrown);
    }

    @Test
    public void isProgrammingLanguagePresent_aLanguageNotPresentInList_pass() {
        ArrayList<String> sampleList = new ArrayList<>();
        sampleList.add("Python");
        sampleList.add("Java");
        sampleList.add("C++");
        boolean isPresent = GitHubUtil.isProgrammingLanguagePresent("PHP", sampleList);
        Assert.assertTrue(!isPresent);
    }

    @Test
    public void isProgrammingLanguagePresent_aLanguagePresentInList_pass() {
        ArrayList<String> sampleList = new ArrayList<>();
        sampleList.add("Python");
        sampleList.add("Java");
        sampleList.add("C++");
        boolean isPresent = GitHubUtil.isProgrammingLanguagePresent("Python", sampleList);
        Assert.assertTrue(isPresent);
    }

    @Test
    public void getFamiliarProgrammingLanguages_invalidUserName_runtimeException() {
        boolean runtimeExceptionThrown = false;
        try {
            GitHubUtil.getFamiliarProgrammingLanguages("/");
        } catch (RuntimeException e) {
            runtimeExceptionThrown = true;
        }
        Assert.assertTrue(runtimeExceptionThrown);
    }

    @Test
    public void getFamiliarProgrammingLanguages_validUserName_noException() {
        boolean runtimeExceptionThrown = false;
        try {
            GitHubUtil.getFamiliarProgrammingLanguages("jai2501");
        } catch (RuntimeException e) {
            runtimeExceptionThrown = true;
        }
        Assert.assertTrue(!runtimeExceptionThrown);
    }

    @Test
    public void getContributionsCount_invalidUserName_runtimeException() {
        boolean runtimeExceptionThrown = false;
        try {
            GitHubUtil.getContributionsCount("/");
        } catch (RuntimeException e) {
            runtimeExceptionThrown = true;
        }
        Assert.assertTrue(runtimeExceptionThrown);
    }

    @Test
    public void getContributionsCount_validUserName_noException() {
        boolean runtimeExceptionThrown = false;
        try {
            GitHubUtil.getContributionsCount("jai2501");
        } catch (RuntimeException e) {
            runtimeExceptionThrown = true;
        }
        Assert.assertTrue(!runtimeExceptionThrown);
    }

    @Test
    public void getRepoCount_invalidUserName_runtimeException() {
        boolean runtimeExceptionThrown = false;
        try {
            GitHubUtil.getRepoCount("/");
        } catch (RuntimeException e) {
            runtimeExceptionThrown = true;
        }
        Assert.assertTrue(runtimeExceptionThrown);
    }

    @Test
    public void getRepoCount_validUserName_noException() {
        boolean runtimeExceptionThrown = false;
        try {
            GitHubUtil.getRepoCount("jai2501");
        } catch (RuntimeException e) {
            runtimeExceptionThrown = true;
        }
        Assert.assertTrue(!runtimeExceptionThrown);
    }
}
