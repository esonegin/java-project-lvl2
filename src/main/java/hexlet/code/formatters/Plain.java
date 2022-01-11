package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Plain {
    //Конвертируем map в JSON строку plain
    public static String plain(List<Map<?, ?>> list) {
        StringBuilder res = new StringBuilder();
        for (Map map : list) {
            map.put("value1", checkingOwnershipObject(map.get("value1")));
            map.put("value2", checkingOwnershipObject(map.get("value2")));

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
        if (res.length() > 0) {
            res.deleteCharAt(res.length() - 1);
        }
        return res.toString();
    }

    public static Object checkingOwnershipObject(Object obj) {
        Object result = null;
        if (obj instanceof String) {
            result = "'" + obj + "'";
        }
        if (obj instanceof ArrayList<?> | obj instanceof Map<?, ?>) {
            result = "[complex value]";
        }
        if (obj instanceof Boolean | obj instanceof Integer) {
            result = obj;
        }
        return result;
    }
}
