package hexlet.code;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.*;


public class Differ {

    public static String generate(String filepath1, String filepath2, String formatName) throws IOException {
        //Получаем формат файла
        String fileformat = "";
        String filename = String.valueOf(filepath1);
        //String file2 = String.valueOf(filepath2));
        if (filename.contains("json")) {
            fileformat = "json";
        } else if (String.valueOf(filepath1).contains("yml")) {
            fileformat = "yml";
        }
        Parser parser = new Parser(fileformat, formatName);

        Map<Object, Object> firstMap = parser.toMapConverter(Path.of(filepath1));
        Map<Object, Object> secondMap = parser.toMapConverter(Path.of(filepath2));

        TreeSet<Object> sortedKeys = getSortedKeys(firstMap, secondMap);
        List<Map<String, Object>> list = listGenerate(firstMap, secondMap, sortedKeys);

        return Formatter.createFormatter(formatName, list).formatter(formatName, list);
    }

    public static String generate(String filepath1, String filepath2) throws IOException {
        //Получаем формат файла
        String fileformat = "";
        String filename = String.valueOf(filepath1);
        if (filename.contains("json")) {
            fileformat = "json";
        } else if (String.valueOf(filepath1).contains("yml")) {
            fileformat = "yml";
        }
        Parser parser = new Parser(fileformat, "stylish");

        Map<Object, Object> firstMap = parser.toMapConverter(Path.of(filepath1));
        Map<Object, Object> secondMap = parser.toMapConverter(Path.of(filepath2));

        TreeSet<Object> sortedKeys = getSortedKeys(firstMap, secondMap);
        List<Map<String, Object>> list = listGenerate(firstMap, secondMap, sortedKeys);

        return Formatter.createFormatter("stylish", list).formatter("stylish", list);
    }


    public static List<Map<String, Object>> listGenerate(Map<Object, Object> first, Map<Object, Object> second,
                                                         TreeSet<Object> getSortedKeys) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Object key : getSortedKeys) {
            Map<String, Object> map = new HashMap<>();
            map.put("field", key);
            if (first.containsKey(key) && second.containsKey(key)
                    && Objects.equals(first.get(key), second.get(key))) {
                map.put("status", "nothing");
                map.put("value1", first.get(key));
                map.put("value2", second.get(key));
            } else if (first.containsKey(key) && second.containsKey(key)
                    && !Objects.equals(first.get(key), second.get(key))) {
                map.put("status", "update");
                map.put("value1", first.get(key));
                map.put("value2", second.get(key));
            } else if (first.containsKey(key) && !second.containsKey(key)) {
                map.put("status", "remove");
                map.put("value1", first.get(key));
                map.put("value2", second.get(key));
            } else if (!first.containsKey(key) && second.containsKey(key)) {
                map.put("status", "added");
                map.put("value1", first.get(key));
                map.put("value2", second.get(key));
            }
            result.add(map);
        }
        return result;
    }

    //Собираем и сортируем ключи мап в один отсортированный set
    public static TreeSet<Object> getSortedKeys(Map<Object, Object> file1, Map<Object, Object> file2) {
        Set<Object> keys1 = file1.keySet();
        Set<Object> keys2 = file2.keySet();
        TreeSet<Object> mergedKeys = new TreeSet<>();
        mergedKeys.addAll(keys1);
        mergedKeys.addAll(keys2);
        return mergedKeys;
    }
}
