package seedu.address.commons.util;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

public class CsvUtil {

    @JsonRootName(value = "addressbook")
    private static class PersonWrapper<T> {
        private final List<T> persons = new ArrayList<>();
        /**
         * Constructs a {@code JsonSerializableAddressBook} with the given persons.
         */
        @JsonCreator
        public PersonWrapper(@JsonProperty("persons") List<T> persons) {
            this.persons.addAll(persons);
        }
    }

    /**
     * Convert CSV File to JSON File
     *
     * @param constructorOfObjectToDeserialize The constructor for T object
     * @param filePath path of CSV file
     * @param <T> The generic type to create an instance of
     * @throws IOException if there was an error reading file
     */
    public static <T> ArrayList<T> csvToPersonData(Constructor<T> constructorOfObjectToDeserialize, Path filePath)
            throws IOException {
        ArrayList<String[]> csvData = readCsv(filePath);
        String[] headers = csvData.get(0);
        HashMap<String, Integer> headerIndex = new HashMap<>();
        for (int i = 0; i < headers.length; i++) {
            headerIndex.put(headers[i], i);
        }

        ArrayList<T> list = new ArrayList<T>();
        for (int i = 1; i < csvData.size(); i++) {
            String[] fields = csvData.get(i);
            T temp;
            try {
                String name = fields[headerIndex.get("Name")];
                String tele = StringUtil.clean(fields[headerIndex.get("Telegram")], "@");
                String github = StringUtil.clean(fields[headerIndex.get("Github")], "@");
                String phone = fields[headerIndex.get("Phone Number")];
                String email = fields[headerIndex.get("Email")];
                String address = StringUtil.clean(fields[headerIndex.get("Address")], "\"");
                List<String> tags = Arrays.asList(fields[headerIndex.get("Tags")].split(" "));
                temp = constructorOfObjectToDeserialize.newInstance(name, tele, github, phone, email, address, tags);
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
                temp = null;
            }
            list.add(temp);
        }
        return list;
    }

    /**
     * Converts Data in CSV file to a JSON String
     *
     * @param constructorOfObjectToDeserialize The constructor for T object
     * @param filePath path of CSV file
     * @param <T> The generic type to create an instance of
     * @return a JSON String
     * @throws IOException if there was an error reading file
     */
    public static <T> String csvToJsonString(Constructor<T> constructorOfObjectToDeserialize, Path filePath)
            throws IOException {
        PersonWrapper<T> data = new PersonWrapper<>(csvToPersonData(constructorOfObjectToDeserialize, filePath));
        return JsonUtil.toJsonString(data);
    }

    /***
     * Reads data in CSV file
     *
     * @param filePath path of CSV file to read
     * @return an ArrayList of String Array containing data split by comma
     * @throws IOException if there was an error reading file
     */
    public static ArrayList<String[]> readCsv(Path filePath) throws IOException {
        String file = FileUtil.readFromFile(filePath);
        String regex = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";
        return Arrays.stream(file.split("\n"))
                .map(s1 -> Arrays.stream(s1.split(regex)).sequential()
                        .map(StringUtil::clean)
                        .collect(Collectors.toList()).toArray(new String [0]))
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Converts a CSV File to a JSON File
     *
     * @param constructorOfObjectToDeserialize The constructor for T object
     * @param csvFilePath path of CSV file
     * @param jsonFilePath path of JSON file
     * @param <T> The generic type to create an instance of
     * @throws IOException if there was an error reading file
     */
    public static <T> void csvToJson(Constructor<T> constructorOfObjectToDeserialize,
                                     Path csvFilePath, Path jsonFilePath) throws IOException {
        saveToJson(csvToJsonString(constructorOfObjectToDeserialize, csvFilePath), jsonFilePath);
    }

    /**
     * Saves to JSON File
     *
     * @param jsonString String of JSON Object
     * @param targetFilePath path of JSON file
     * @throws IOException if there was an error reading file
     */
    private static void saveToJson(String jsonString, Path targetFilePath) throws IOException {
        FileUtil.writeToFile(targetFilePath, jsonString);
    }
}
