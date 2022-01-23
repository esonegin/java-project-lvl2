package hexlet.code.formatters;

import java.util.List;
import java.util.Map;

public class Stylish {
    public static String stylish(List<Map<?, ?>> diff) {
        StringBuilder res = new StringBuilder("{\n");
        for (Map<?, ?> node : diff) {
            if (node.get("status").equals("nothing")) {
                res.append("    ").append(node.get("field")).append(": ").append(node.get("value1")).append("\n");
            } else if (node.get("status").equals("update")) {
                res.append("  - ").append(node.get("field")).append(": ").append(node.get("value1")).append("\n");
                res.append("  + ").append(node.get("field")).append(": ").append(node.get("value2")).append("\n");
            } else if (node.get("status").equals("remove")) {
                res.append("  - ").append(node.get("field")).append(": ").append(node.get("value1")).append("\n");
            } else if (node.get("status").equals("added")) {
                res.append("  + ").append(node.get("field")).append(": ").append(node.get("value2")).append("\n");
            }
        }
        res.append("}");
        return res.toString();
    }
}
