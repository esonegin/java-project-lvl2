package hexlet.code.formatters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Plain {
    //Конвертируем Map в JSON строку plain
    public static String plain(List<Map<?, ?>> list) {
        StringBuilder res = new StringBuilder();

        for (Map<?, ?> node : list) {
            Object value1 = stringify(node.get("value1"));
            Object value2 = stringify(node.get("value2"));
            if (node.get("status").equals("update")) {
                res.append("Property '").append(node.get("field"))
                        .append("' was updated. From ")
                        .append(value1)
                        .append(" to ")
                        .append(value2).append("\n");
            } else if (node.get("status").equals("remove")) {
                res.append("Property '")
                        .append(node.get("field"))
                        .append("' was removed")
                        .append("\n");
            } else if (node.get("status").equals("added")) {
                res.append("Property '")
                        .append(node.get("field"))
                        .append("' was added with value: ")
                        .append(value2)
                        .append("\n");
            }
        }
        if (res.length() > 0) {
            res.deleteCharAt(res.length() - 1);
        }
        return res.toString();
    }

    public static Object stringify(Object obj) {
        //Object result = null;
        if (obj instanceof String) {
            return "'" + obj + "'";
        }
        if (obj instanceof ArrayList<?> | obj instanceof Map<?, ?>) {
            return "[complex value]";
        }
        return obj;
    }
}
