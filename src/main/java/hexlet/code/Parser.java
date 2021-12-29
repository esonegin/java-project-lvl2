package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class Parser {
    //Конвертируем содержимое JSON файла в map
    public static Map<?, ?> toMapConverter(Path file, String fileformat) throws IOException {
        Map<?, ?> map = null;
        if (fileformat.equals("json"))  {
            try {
                ObjectMapper mapper = new ObjectMapper();
                map = mapper.readValue(file.toFile(), Map.class);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        } else if (fileformat.equals("yml")) {
            try {
                ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
                mapper.findAndRegisterModules();
                map = (Map<?, ?>) mapper.readValue(file.toFile(), Map.class);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        return map;
    }
}
