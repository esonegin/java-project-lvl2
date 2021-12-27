package hexlet.code;


import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.io.IOException;
import java.util.List;
import java.util.Map;


final class Formatter {

    private static String formatName = null;
    private static List<Map<?, ?>> list = null;

    Formatter(String format, List<Map<?, ?>> mapslist) {
        formatName = format;
        list = mapslist;
    }

    public static String formatter() throws IOException {
        switch (formatName) {
            case "stylish" -> {
                return Stylish.stylish(list);
            }
            case "plain" -> {
                return Plain.plain(list);
            }
            case "json" -> {
                return Json.json(list);
            }
            default -> throw new IllegalStateException("Unexpected value: " + formatName);
        }
    }
}
