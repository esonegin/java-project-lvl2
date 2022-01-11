package hexlet.code;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.util.Map;

public class Parser {
    //Конвертируем содержимое JSON файла в map
    public static Map<?, ?> toMap(String filecontents, String fileformat) throws IOException {
        Map<?, ?> map = null;
        if (fileformat.equals("json")) {
            map = getObjectMapper(null).readValue(filecontents, Map.class);

        } else if (fileformat.equals("yml")) {
            map = (Map<?, ?>) getObjectMapper(new YAMLFactory()).readValue(filecontents, Map.class);
        }
        return map;
    }

    public static ObjectMapper getObjectMapper(Object format) {
        return new ObjectMapper((JsonFactory) format);
    }
}
