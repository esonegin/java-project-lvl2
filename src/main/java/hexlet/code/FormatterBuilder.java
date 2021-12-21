package hexlet.code;

import java.util.List;
import java.util.Map;

public class FormatterBuilder {
    private String formatName;
    private List<Map<?, ?>> list;

    final FormatterBuilder setFormatName(String format) {
        this.formatName = format;
        return this;
    }

    final FormatterBuilder setList(List<Map<?, ?>> mapslist) {
        this.list = mapslist;
        return this;
    }

    final Formatter createFormatter() {
        return new Formatter(formatName, list);
    }
}
