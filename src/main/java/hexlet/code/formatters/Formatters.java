package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Formatters {
    //Конвертируем map в JSON строку stylish
    public static String stylish(List<Map<String, Object>> list) {
        StringBuilder res = new StringBuilder("{\n");
        for (Map<String, Object> map : list) {
            if (map.get("status").equals("nothing")) {
                res.append("    ").append(map.get("field")).append(": ").append(map.get("value1")).append("\n");
            } else if (map.get("status").equals("update")) {
                res.append("  - ").append(map.get("field")).append(": ").append(map.get("value1")).append("\n");
                res.append("  + ").append(map.get("field")).append(": ").append(map.get("value2")).append("\n");
            } else if (map.get("status").equals("remove")) {
                res.append("  - ").append(map.get("field")).append(": ").append(map.get("value1")).append("\n");
            } else if (map.get("status").equals("added")) {
                res.append("  + ").append(map.get("field")).append(": ").append(map.get("value2")).append("\n");
            }
        }
        res.append("}");
        return res.toString();
    }

    //Конвертируем map в JSON строку plain
    public static String plain(List<Map<String, Object>> list) {
        StringBuilder res = new StringBuilder();
        for (Map<String, Object> map : list) {
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
        return res.toString();
    }

    public static String json(List<Map<String, Object>> list) throws JsonProcessingException {
        String res = "";
        for (Map map : list) {
            res += new ObjectMapper().writeValueAsString(map);
        }
        return res;
    }
}
