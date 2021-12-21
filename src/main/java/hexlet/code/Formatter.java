package hexlet.code;


import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.io.IOException;
import java.util.List;
import java.util.Map;


final class Formatter {

    private final String formatName;
    private final List<Map<?, ?>> list;

    Formatter(String format, List<Map<?, ?>> mapslist) {
        this.formatName = format;
        this.list = mapslist;
    }

    public static Formatter createFormatter(String formatName, List<Map<?, ?>> list) {
        return new FormatterBuilder().setFormatName(formatName).setList(list).createFormatter();
    }

    public String formatter() throws IOException {
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

    private String getFormatName() {
        return this.formatName;
    }

    private List<Map<?, ?>> getFormatList() {
        return this.list;
    }
}
