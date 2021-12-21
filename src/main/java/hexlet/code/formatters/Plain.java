package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Plain {
    //Конвертируем map в JSON строку plain
    public static String plain(List<Map<?, ?>> list) {
        StringBuilder res = new StringBuilder();
        for (Map map : list) {
            if (map.get("value1") != null && map.get("value1").getClass().toString().equals("class java.lang.String")) {
                map.put("value1", "'" + map.get("value1") + "'");
            }
            if (map.get("value2") != null && map.get("value2").getClass().toString().equals("class java.lang.String")) {
                map.put("value2", "'" + map.get("value2") + "'");
            }
            if (map.get("value1") != null
                    && (map.get("value1").getClass().toString().equals("class java.util.ArrayList")
                    | (map.get("value1").getClass().toString().equals("class java.util.LinkedHashMap")))) {
                map.put("value1", "[complex value]");
            }
            if (map.get("value2") != null
                    && (map.get("value2").getClass().toString().equals("class java.util.ArrayList")
                    | (map.get("value2").getClass().toString().equals("class java.util.LinkedHashMap")))) {
                map.put("value2", "[complex value]");
            }

            if (map.get("status").equals("update")) {
                res.append("Property '").append(map.get("field"))
                        .append("' was updated. From ")
                        .append(map.get("value1")).append(" to ")
                        .append(map.get("value2")).append("\n");
            } else if (map.get("status").equals("remove")) {
                res.append("Property '")
                        .append(map.get("field"))
                        .append("' was removed")
                        .append("\n");
            } else if (map.get("status").equals("added")) {
                res.append("Property '")
                        .append(map.get("field"))
                        .append("' was added with value: ")
                        .append(map.get("value2"))
                        .append("\n");
            }
        }
        res.deleteCharAt(res.length() - 1);
        return res.toString();
    }
}
