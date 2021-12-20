package hexlet.code;

import java.util.List;
import java.util.Map;

public class FormatterBuilder {
    private String formatName;
    private List<Map<String, Object>> list;

    public FormatterBuilder setFormatName(String formatName) {
        this.formatName = formatName;
        return this;
    }

    public FormatterBuilder setList(List<Map<String, Object>> list) {
        this.list = list;
        return this;
    }

    public Formatter createFormatter() {
        return new Formatter(formatName, list);
    }
}