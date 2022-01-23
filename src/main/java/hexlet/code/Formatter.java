package hexlet.code;


import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.io.IOException;
import java.util.List;
import java.util.Map;


final class Formatter {
    public static String formatter(String formatName, List<Map<?, ?>> diff) throws IOException {
        return switch (formatName) {
            case "stylish" -> Stylish.stylish(diff);
            case "plain" -> Plain.plain(diff);
            case "json" -> Json.json(diff);
            default -> throw new IllegalArgumentException("Unexpected value: " + formatName);
        };
    }
}
