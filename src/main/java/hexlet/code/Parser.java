package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.Map;

public class Parser {
    //Конвертируем содержимое JSON файла в Map
    public static Map<?, ?> toMap(String content, String format) throws IOException {
        return getObjectMapper(format).readValue(content, Map.class);
    }

    public static ObjectMapper getObjectMapper(String format) {
        return switch (format) {
            case "json" -> new ObjectMapper();
            case "yml", "yaml" -> new ObjectMapper(new YAMLFactory());
            default -> throw new RuntimeException("Unsupported format: " + format);
        };
    }
}
