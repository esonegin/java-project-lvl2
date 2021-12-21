package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

public class Parser {

    private static String fileformat = null;

    public Parser(String format) {
        Parser.fileformat = format;

    }

    //Конвертируем содержимое JSON файла в map
    public static Map<?, ?> toMapConverter(Path file) throws IOException {
        Map<?, ?> map = null;
        if (fileformat.equals("json")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                map = mapper.readValue(file.toFile(), Map.class);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        } else if (fileformat.equals("yml")) {
            InputStream inputStream = new FileInputStream(String.valueOf(file));
            Yaml yaml = new Yaml();
            map = yaml.load(inputStream);
            return map;
        }
        return map;
    }
}
