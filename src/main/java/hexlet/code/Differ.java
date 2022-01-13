package hexlet.code;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Objects;

import static hexlet.code.Parser.toMap;

public class Differ {

    public static String generate(Path filepath1, Path filepath2, String formatName) throws IOException {
        //Получаем формат файлов
        String fileformat1 = formatCheck(String.valueOf(filepath1));
        String fileformat2 = formatCheck(String.valueOf(filepath2));

        //Получаем мапу данных из файлов
        Map<?, ?> firstMap = toMap(getDataFromFile(String.valueOf(filepath1)), fileformat1);
        Map<?, ?> secondMap = toMap(getDataFromFile(String.valueOf(filepath2)), fileformat2);

        //Получаем отсортированные ключи
        TreeSet<Object> sortedKeys = getSortedKeys(firstMap, secondMap);

        //Получаем список мап со статусами
        List<Map<?, ?>> list = generateDiff(firstMap, secondMap, sortedKeys);
        return Formatter.formatter(formatName, list);
    }

    public static String generate(Path filepath1, Path filepath2) throws IOException {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String fileAsAString(Path filepath) throws IOException {
        return Files.readString(filepath);
    }

    public static String formatCheck(String filepath) {
        return filepath.substring(filepath.lastIndexOf('.') + 1);
    }

    public static List<Map<?, ?>> generateDiff(Map<?, ?> first, Map<?, ?> second, TreeSet<Object> getSortedKeys) {
        List<Map<?, ?>> result = new ArrayList<>();
        for (Object key : getSortedKeys) {
            Map<String, Object> map = new HashMap<>();
            map.put("field", key);
            if (!second.containsKey(key)) {
                map.put("status", "remove");
            } else if (!first.containsKey(key)) {
                map.put("status", "added");
            } else if (first.containsKey(key) && second.containsKey(key)
                    && Objects.equals(first.get(key), second.get(key))) {
                map.put("status", "nothing");
            } else {
                map.put("status", "update");
            }
            map.put("value1", first.get(key));
            map.put("value2", second.get(key));
            result.add(map);
        }
        return result;
    }

    //Собираем и сортируем ключи мап в один отсортированный set
    public static TreeSet<Object> getSortedKeys(Map<?, ?> file1, Map<?, ?> file2) {
        Set<?> keys1 = file1.keySet();
        Set<?> keys2 = file2.keySet();
        TreeSet<Object> mergedKeys = new TreeSet<>();
        mergedKeys.addAll(keys1);
        mergedKeys.addAll(keys2);
        return mergedKeys;
    }

    public static String getDataFromFile(String path) {
        StringBuilder result = new StringBuilder();
        try (FileReader reader = new FileReader(path)) {
            int c;
            while ((c = reader.read()) != -1) {
                result.append((char) c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return result.toString();
    }

}
