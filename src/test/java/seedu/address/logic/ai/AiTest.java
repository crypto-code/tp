package seedu.address.logic.ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.testutil.TypicalPersons;

public class AiTest {

    private static final HashMap<String, Double> point1 = new HashMap<>();
    private static final HashMap<String, Double> point2 = new HashMap<>();
    private static final HashMap<String, Double> point3 = new HashMap<>();
    private static final HashMap<String, HashMap<String, Double>> others = new HashMap<>();

    @BeforeAll
    public static void setUp() {
        point1.put("Java", 0.3);
        point1.put("Python", 0.4);
        point1.put("repo-count", 0.2);

        point2.put("Python", 0.9);
        point2.put("C++", 0.8);
        point2.put("repo-count", 0.8);

        point3.put("Python", 0.2);
        point3.put("Java", 0.3);
        point3.put("repo-count", 0.2);

        others.put("p2", point2);
        others.put("p3", point3);
    }

    @Test
    public void getCosineSimilarity_validFeatures_success() {
        Assertions.assertTrue(1 - Ai.getCosineSimilarity(point1, point1) < 0.000001);
        Assertions.assertTrue(Ai.getCosineSimilarity(point1, point3) > Ai.getCosineSimilarity(point1, point2));
    }

    @Test
    public void getEuclideanDistance_validFeatures_success() {
        Assertions.assertEquals(0, Ai.getEuclideanDistance(point1, point1));
        Assertions.assertTrue(Ai.getEuclideanDistance(point1, point3) < Ai.getEuclideanDistance(point1, point2));
    }

    @Test
    public void getManhattanDistance_validFeatures_success() {
        Assertions.assertEquals(0, Ai.getManhattanDistance(point1, point1));
        Assertions.assertTrue(Ai.getManhattanDistance(point1, point3) < Ai.getManhattanDistance(point1, point2));
    }

    @Test
    public void getDistanceMetric_validFeatures_success() {
        Assertions.assertTrue(Ai.getDistanceMetric(point1, point1) < 0.000001);
        Assertions.assertTrue(Ai.getDistanceMetric(point1, point3) < Ai.getDistanceMetric(point1, point2));
    }

    @Test
    public void getCommonLanguages_validFeatures_success() {
        HashMap<String, ArrayList<String>> commons = Ai.getCommonLanguages(point1, others);

        ArrayList<String> expectedCommonP2 = new ArrayList<>(List.of("Python"));
        Assertions.assertTrue(commons.get("p2").containsAll(expectedCommonP2));

        ArrayList<String> expectedCommonP3 = new ArrayList<>(List.of("Python", "Java"));
        Assertions.assertTrue(commons.get("p3").containsAll(expectedCommonP3));
    }

    @Test
    public void getSimilarityScore_featureList_success() {
        HashMap<String, Double> scores = Ai.getSimilarityScore(point1, others);
        String[] expectedOrder = new String[] {"p3", "p2"};
        String[] actualOrder = scores.keySet().toArray(new String[0]);
        Assertions.assertEquals(expectedOrder[0], actualOrder[0], "Incorrect 1st Element");
        Assertions.assertEquals(expectedOrder[1], actualOrder[1], "Incorrect 2nd Element");
    }

    @Test
    public void sortProfiles_featureList_success() {
        ObservableList<Person> list = FXCollections.observableArrayList();
        list.addAll(TypicalPersons.BENSON, TypicalPersons.CARL);
        if (!ThreadProcessor.isEmpty()) {
            Assertions.assertFalse(Ai.sortProfiles(TypicalPersons.ALICE, list));
        }
        while (!ThreadProcessor.isEmpty()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Assertions.assertTrue(Ai.sortProfiles(TypicalPersons.ALICE, list));
    }
}
