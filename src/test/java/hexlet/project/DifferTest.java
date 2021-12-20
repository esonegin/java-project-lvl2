package hexlet.project;

import com.fasterxml.jackson.core.JsonParseException;
import hexlet.code.Differ;
import hexlet.code.Parser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


public class DifferTest {

    @BeforeClass
    public static void globalSetUp() {

    }

    @Before
    public void setUp() {

    }

    @Test
    public void generalJSONGenerateTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath1.json"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath2.json"), "stylish");
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void plainNestingGenerateTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/nestingfilepath1.json"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/nestingfilepath2.json"), "stylish");
        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void plainJSONestingGenerateTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/nestingfilepath1.json"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/nestingfilepath2.json"), "plain");
        String expected = """
                Property 'chars2' was updated. From [complex value] to false
                Property 'checked' was updated. From false to true
                Property 'default' was updated. From null to [complex value]
                Property 'id' was updated. From 45 to null
                Property 'key1' was removed
                Property 'key2' was added with value: 'value2'
                Property 'numbers2' was updated. From [complex value] to [complex value]
                Property 'numbers3' was removed
                Property 'numbers4' was added with value: [complex value]
                Property 'obj1' was added with value: [complex value]
                Property 'setting1' was updated. From 'Some value' to 'Another value'
                Property 'setting2' was updated. From 200 to 300
                Property 'setting3' was updated. From true to 'none'
                """;

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generalJSONMultiRunTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath1.json"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath2.json"), "stylish");
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        Assert.assertEquals(expected, actual);
        for (int i = 0; i < 1000; i++) {
            actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath1.json"),
                    Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath2.json"), "stylish");
            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void emptyJSONSecondGenerateTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath1.json"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/empty.json"), "stylish");
        String expected = """
                {
                  - follow: false
                  - host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                }""";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyJSONFirstGenerateTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/empty.json"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath1.json"), "stylish");
        String expected = """
                {
                  + follow: false
                  + host: hexlet.io
                  + proxy: 123.234.53.22
                  + timeout: 50
                }""";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyJSONFirstAndSecondGenerateTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/empty.json"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/empty.json"), "stylish");
        String expected = "{\n" +
                "}";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void notJSONTGenerateest() {
        Throwable thrown = assertThrows(JsonParseException.class, () -> Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/empty.json"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/juststring.json"), "stylish"));
        assertNotNull(thrown.getMessage());

    }

    @Test
    public void breakJSONGenerateTest() {
        Throwable thrown = assertThrows(JsonParseException.class, () -> Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath1.json"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/break.json"), "stylish"));
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void breakJSONPathGenerateTest() {
        Throwable thrown = assertThrows(FileNotFoundException.class, () -> Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath.json"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath2.json"), "stylish"));
        assertNotNull(thrown.getMessage());
    }



    //getSortedKeys
    @Test
    public void getSortedKeysIntegerTest() {
        Map<Object, Object> first = new LinkedHashMap<>();
        Map<Object, Object> second = new LinkedHashMap<>();
        for (int i = 500, k = 501; i > -1; i--, k++) {
            first.put(i, "test");
            second.put(k, "test");
        }
        TreeSet<Object> actual = Differ.getSortedKeys(first, second);
        Integer[] arrvalues = new Integer[1002];
        for (int i = 0; i < 1002; i++) {
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
        expected.put("timeout", 50);
        expected.put("proxy", "123.234.53.22");
        expected.put("follow", false);
        Map<Object, Object> actual = Parser.toMapConverter(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath1.json"));
        assertEquals(expected, actual);
    }

    @Test
    public void getEmptyMapConverterTest() throws Exception {
        LinkedHashMap<String, Object> expected = new LinkedHashMap<>();
        Map<Object, Object> actual = Parser.toMapConverter(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/empty.json"));
        assertEquals(0, actual.size());
        assertEquals(expected, actual);
    }

    @Test
    public void generalYmlGenerateTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/filepath1.yml"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/filepath2.yml"), "stylish");
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        Assert.assertEquals(expected, actual);
    }
    @Test
    public void generaYmllMultiRunTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/filepath1.yml"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/filepath2.yml"), "stylish");
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        Assert.assertEquals(expected, actual);
        for (int i = 0; i < 1000; i++) {
            actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/filepath1.yml"),
                    Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/filepath2.yml"), "stylish");
            Assert.assertEquals(expected, actual);
        }
    }

    @Test
    public void emptyYmlSecondGenerateTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/filepath1.yml"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/empty.yml"), "stylish");
        String expected = """
                {
                  - follow: false
                  - host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                }""";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyYmlFirstGenerateTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/empty.yml"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/filepath1.yml"), "stylish");
        String expected = """
                {
                  + follow: false
                  + host: hexlet.io
                  + proxy: 123.234.53.22
                  + timeout: 50
                }""";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void emptyYmlFirstAndSecondGenerateTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/empty.yml"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/empty.yml"), "stylish");
        String expected = "{\n" +
                "}";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void breakYmlPathGenerateTest() {
        Throwable thrown = assertThrows(FileNotFoundException.class, () -> Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/filepath.yml"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/filepath2.yml"), "stylish"));
        assertNotNull(thrown.getMessage());
    }

    @Test
    public void generalYmlNestingGenerateTest() throws Exception {
        String actual = Differ.generate(Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/nestingfilepath1.yml"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/yml/nestingfilepath2.yml"), "stylish");
        String expected = """
                {
                    chars1: [a, b, c]
                  - chars2: [d, e, f]
                  + chars2: false
                  - checked: false
                  + checked: true
                  - default: null
                  + default: [value1, value2]
                  - id: 45
                  + id: null
                  - key1: value1
                  + key2: value2
                    numbers1: [1, 2, 3, 4]
                  - numbers2: [2, 3, 4, 5]
                  + numbers2: [22, 33, 44, 55]
                  - numbers3: [3, 4, 5]
                  + numbers4: [4, 5, 6]
                  + obj1: {nestedKey=value, isNested=true}
                  - setting1: Some value
                  + setting1: Another value
                  - setting2: 200
                  + setting2: 300
                  - setting3: true
                  + setting3: none
                }""";

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void generalJSONDefaultGenerateTest() throws Exception {
        String actual = Differ.generate
                (Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath1.json"),
                Path.of("/Users/user/Hexlet/java-project-lvl2/src/test/resources/json/filepath2.json"), "json");
        String expected = "{\"field\":\"follow\",\"value2\":null,\"value1\":false,\"status\":\"remove\"}" +
                "{\"field\":\"host\",\"value2\":\"hexlet.io\",\"value1\":\"hexlet.io\",\"status\":\"nothing\"}" +
                "{\"field\":\"proxy\",\"value2\":null,\"value1\":\"123.234.53.22\",\"status\":\"remove\"}" +
                "{\"field\":\"timeout\",\"value2\":20,\"value1\":50,\"status\":\"update\"}" +
                "{\"field\":\"verbose\",\"value2\":true,\"value1\":null,\"status\":\"added\"}";

        Assert.assertEquals(expected, actual);
    }

}