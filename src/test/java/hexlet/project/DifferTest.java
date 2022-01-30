package hexlet.project;

import com.fasterxml.jackson.core.JsonParseException;

import hexlet.code.Differ;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.HashSet;

import static hexlet.code.Differ.fileAsAString;
import static hexlet.code.Parser.toMap;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class DifferTest {

    private final int overVal = 1000;
    private final int firstIn1000 = 500;
    private final int secondIn1000 = 501;
    private final int overIndex = 1002;
    private final int putInt = 50;

    @BeforeClass
    public static void globalSetUp() {

    }

    @Before
    public void setUp() {

    }

    final String getPathToTestFile(String name) {
        return switch (name) {
            case "filepath1.json" -> "src/test/resources/json/filepath1.json";
            case "filepath2.json" -> "src/test/resources/json/filepath2.json";
            case "nestingfilepath1.json" -> "src/test/resources/json/nestingfilepath1.json";
            case "nestingfilepath2.json" -> "src/test/resources/json/nestingfilepath2.json";
            case "empty.json" -> "src/test/resources/json/empty.json";
            case "juststring.json" -> "src/test/resources/json/juststring.json";
            case "break.json" -> "src/test/resources/json/break.json";
            case "filepath1.yml" -> "src/test/resources/yml/filepath1.yml";
            case "filepath2.yml" -> "src/test/resources/yml/filepath2.yml";
            case "empty.yml" -> "src/test/resources/yml/empty.yml";
            case "nestingfilepath1.yml" -> "src/test/resources/yml/nestingfilepath1.yml";
            case "nestingfilepath2.yml" -> "src/test/resources/yml/nestingfilepath2.yml";
            case "filepath.json" -> "src/test/resources/yml/filepath.json";
            default -> throw new RuntimeException("Unsupported format: " + name);
        };
    }

    final String getExpected(String name) throws IOException {
        return switch (name) {
            case "json1json2" -> Files.readString(Paths.get(
                    "src/test/resources/expected/json1json2"));
            case "nestingjson1json2stylish" -> Files.readString(Paths.get(
                    "src/test/resources/expected/nestingjson1json2stylish"));
            case "nestingjson1json2plain" -> Files.readString(Paths.get(
                    "src/test/resources/expected/nestingjson1json2plain"));
            case "json1empty" -> Files.readString(Paths.get(
                    "src/test/resources/expected/json1empty"));
            case "emptyjson1" -> Files.readString(Paths.get(
                    "src/test/resources/expected/emptyjson1"));
            case "nestingjson1json2json" -> Files.readString(Paths.get(
                    "src/test/resources/expected/nestingjson1json2json"));
            default -> throw new RuntimeException("Unsupported format: " + name);
        };
    }

    @Test
    public void generalJSONGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("filepath1.json")),
                (getPathToTestFile("filepath2.json")),
                "stylish");
        String expected = getExpected("json1json2");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void plainNestingGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("nestingfilepath1.json")),
                (getPathToTestFile("nestingfilepath2.json")),
                "stylish");
        String expected = getExpected("nestingjson1json2stylish");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void plainNestingWithoutFormatGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("nestingfilepath1.json")),
                (getPathToTestFile("nestingfilepath2.json")));
        String expected = getExpected("nestingjson1json2stylish");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void plainJSONestingGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("nestingfilepath1.json")),
                (getPathToTestFile("nestingfilepath2.json")),
                "plain");
        String expected = getExpected("nestingjson1json2plain");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generalJSONMultiRunTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("filepath1.json")),
                (getPathToTestFile("filepath2.json")),
                "stylish");
        String expected = getExpected("json1json2");
        Assert.assertEquals(expected, actual);
        for (int i = 0; i < overVal; i++) {
            actual = Differ.generate(
                    (getPathToTestFile("filepath1.json")),
                    (getPathToTestFile("filepath2.json")),
                    "stylish");
            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void emptyJSONSecondGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("filepath1.json")),
                (getPathToTestFile("empty.json")),
                "stylish");
        String expected = getExpected("json1empty");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyJSONFirstGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("empty.json")),
                (getPathToTestFile("filepath1.json")),
                "stylish");
        String expected = getExpected("emptyjson1");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyJSONFirstAndSecondGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("empty.json")),
                (getPathToTestFile("empty.json")),
                "stylish");
        String expected = "{\n}";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notJSONTGenerateest() {
        Throwable thrown = assertThrows(JsonParseException.class, () ->
                Differ.generate(
                        (getPathToTestFile("empty.json")),
                        (getPathToTestFile("juststring.json")),
                        "stylish"));
        assertNotNull(thrown.getMessage());

    }

    @Test
    public void breakJSONGenerateTest() {
        Throwable thrown = assertThrows(JsonParseException.class, () ->
                Differ.generate(
                        (getPathToTestFile("filepath1.json")),
                        (getPathToTestFile("break.json")),
                        "stylish"));
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void breakJSONPathGenerateTest() {
        Throwable thrown = assertThrows(NoSuchFileException.class, () ->
                Differ.generate(
                        (getPathToTestFile("filepath.json")),
                        (getPathToTestFile("filepath2.json")),
                        "stylish"));
        assertNotNull(thrown.getMessage());
    }


    //getSortedKeys
    @Test
    public void getSortedKeysIntegerTest() {
        Map<Object, Object> first = new LinkedHashMap<>();
        Map<Object, Object> second = new LinkedHashMap<>();
        for (int i = firstIn1000, k = secondIn1000; i > -1; i--, k++) {
            first.put(i, "test");
            second.put(k, "test");
        }
        TreeSet<Object> actual = Differ.getSortedKeys(first, second);
        Integer[] arrvalues = new Integer[overIndex];
        for (int i = 0; i < overIndex; i++) {
            arrvalues[i] = i;
        }
        Set<Integer> expected = new HashSet<>(Arrays.asList(arrvalues));
        assertEquals(expected, actual);
    }

    //toMapConverter
    @Test
    public void getMapConverterTest() throws Exception {
        LinkedHashMap<Object, Object> expected = new LinkedHashMap<>();
        expected.put("host", "hexlet.io");
        expected.put("timeout", putInt);
        expected.put("proxy", "123.234.53.22");
        expected.put("follow", false);
        Map<?, ?> actual = toMap(fileAsAString((getPathToTestFile("filepath1.json"))),
                "json");
        assertEquals(expected, actual);
    }

    @Test
    public void getEmptyMapConverterTest() throws Exception {
        LinkedHashMap<String, Object> expected = new LinkedHashMap<>();
        Map<?, ?> actual = toMap(fileAsAString((getPathToTestFile("empty.json"))),
                "json");
        assertEquals(0, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void generalYmlGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("filepath1.yml")),
                (getPathToTestFile("filepath2.yml")),
                "stylish");
        String expected = getExpected("json1json2");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generaYmllMultiRunTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("filepath1.yml")),
                (getPathToTestFile("filepath2.yml")),
                "stylish");
        String expected = getExpected("json1json2");
        Assert.assertEquals(expected, actual);
        for (int i = 0; i < overVal; i++) {
            actual = Differ.generate(
                    (getPathToTestFile("filepath1.yml")),
                    (getPathToTestFile("filepath2.yml")),
                    "stylish");
            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void emptyYmlSecondGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("filepath1.yml")),
                (getPathToTestFile("empty.yml")),
                "stylish");
        String expected = getExpected("json1empty");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyYmlFirstGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("empty.yml")),
                (getPathToTestFile("filepath1.yml")),
                "stylish");
        String expected = getExpected("emptyjson1");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyYmlFirstAndSecondGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("empty.yml")),
                (getPathToTestFile("empty.yml")),
                "stylish");
        String expected = "{\n}";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void breakYmlPathGenerateTest() {
        Throwable thrown = assertThrows(RuntimeException.class, () ->
                Differ.generate(
                        (getPathToTestFile("filepath.yml")),
                        (getPathToTestFile("filepath2.yml")),
                        "stylish"));
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void generalYmlNestingGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("nestingfilepath1.yml")),
                (getPathToTestFile("nestingfilepath2.yml")),
                "stylish");
        String expected = getExpected("nestingjson1json2stylish");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generalJSONDefaultGenerateTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("filepath1.json")),
                (getPathToTestFile("filepath2.json")),
                "json");
        String expected = getExpected("nestingjson1json2json");

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyJSONAndYmlPlainTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("empty.json")),
                (getPathToTestFile("empty.yml")),
                "plain");
        String expected = "";
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyJSONAndYmlJsonTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("empty.json")),
                (getPathToTestFile("empty.yml")),
                "json");
        String expected = "[]";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyJSONAndYmlStylishTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("empty.json")),
                (getPathToTestFile("empty.yml")),
                "json");
        String expected = "[]";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void ymlToJSONTest() throws Exception {
        String actual = Differ.generate(
                (getPathToTestFile("filepath1.yml")),
                (getPathToTestFile("filepath2.yml")),
                "json");
        String expected = getExpected("nestingjson1json2json");

        Assert.assertEquals(expected, actual);
    }
}
