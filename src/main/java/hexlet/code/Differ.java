package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Objects;

import static hexlet.code.Parser.toMapConverter;

public class Differ {

    public static String generate(String formatName, String filepath1, String filepath2) throws IOException {
        //Получаем формат файла
        String fileformat = "";
        String filename = String.valueOf(filepath1);
        if (filename.contains("json")) {
            fileformat = "json";
        } else if (String.valueOf(filepath1).contains("yml")) {
            fileformat = "yml";
        }
        Map<?, ?> firstMap = toMapConverter(Path.of(filepath1), fileformat);
        Map<?, ?> secondMap = toMapConverter(Path.of(filepath2), fileformat);

        TreeSet<Object> sortedKeys = getSortedKeys(firstMap, secondMap);
        List<Map<?, ?>> list = generateDiff(firstMap, secondMap, sortedKeys);

        return new Formatter(formatName, list).formatter();
    }

    public static String generate(String filepath1, String filepath2) throws IOException {
        return generate("stylish", filepath1, filepath2);
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
}
