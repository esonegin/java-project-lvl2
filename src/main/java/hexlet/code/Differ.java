package hexlet.code;

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

    public static String generate(String filepath1, String filepath2, String formatName) throws IOException {
        //Получаем формат файлов
        String fileformat1 = getFormat(filepath1);
        String fileformat2 = getFormat(filepath2);

        //Получаем мапу данных из файлов
        Map<?, ?> firstMap = toMap(fileAsAString(filepath1), fileformat1);
        Map<?, ?> secondMap = toMap(fileAsAString(filepath2), fileformat2);

        //Получаем отсортированные ключи
        TreeSet<Object> sortedKeys = getSortedKeys(firstMap, secondMap);

        //Получаем список мап со статусами
        List<Map<?, ?>> diff = generateDiff(firstMap, secondMap, sortedKeys);
        return Formatter.formatter(formatName, diff);
    }

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate(filepath1, filepath2, "stylish");
    }

    public static String fileAsAString(String filepath) throws IOException {
        return Files.readString(Path.of(filepath));
    }

    public static String getFormat(String filepath) {
        return filepath.substring(filepath.lastIndexOf('.') + 1);
    }

    public static List<Map<?, ?>> generateDiff(Map<?, ?> first, Map<?, ?> second, TreeSet<Object> getSortedKeys) {
        List<Map<?, ?>> result = new ArrayList<>();
        for (Object key : getSortedKeys) {
            Map<String, Object> node = new HashMap<>();
            node.put("field", key);
            if (!second.containsKey(key)) {
                node.put("status", "remove");
            } else if (!first.containsKey(key)) {
                node.put("status", "added");
            } else if (first.containsKey(key) && second.containsKey(key)
                    && Objects.equals(first.get(key), second.get(key))) {
                node.put("status", "nothing");
            } else {
                node.put("status", "update");
            }
            node.put("value1", first.get(key));
            node.put("value2", second.get(key));
            result.add(node);
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
}
