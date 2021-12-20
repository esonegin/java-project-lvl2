package hexlet.code;


import hexlet.code.formatters.Json;
import hexlet.code.formatters.Plain;
import hexlet.code.formatters.Stylish;

import java.io.IOException;
import java.util.List;
import java.util.Map;


final class Formatter {

    private String formatName;
    private List<Map<String, Object>> list;

    Formatter(String formatName, List<Map<String, Object>> list) {
        this.formatName = formatName;
        this.list = list;
    }

    public static Formatter createFormatter(String formatName, List<Map<String, Object>> list) {
        return new FormatterBuilder().setFormatName(formatName).setList(list).createFormatter();
    }

    public String formatter(String formatName, List<Map<String, Object>> list) throws IOException {
        String res = "";
        switch (formatName) {
            case "stylish" -> res = Stylish.stylish(list);
            case "plain" -> res = Plain.plain(list);
            case "json" -> res = Json.json(list);
        }
        return res;
    }

    private String getFormatName() {
        return this.formatName;
    }

    private List<Map<String, Object>> getFormatList() {
        return this.list;
    }
}
