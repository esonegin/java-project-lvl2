package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Stylish {
    public static String stylish(List<Map<?, ?>> list) {
        StringBuilder res = new StringBuilder("{\n");
        for (Map<?, ?> map : list) {
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
}
