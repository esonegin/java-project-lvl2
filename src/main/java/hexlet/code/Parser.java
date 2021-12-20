package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Map;

public class Parser {

    private static String fileformat = null;
    private static String formatname = null;

    public Parser(String fileformat, String formatname) {
        this.fileformat = fileformat;
        this.formatname = formatname;
    }

    //Конвертируем содержимое JSON файла в map
    public static Map<Object, Object> toMapConverter(Path file) throws IOException {
        Map<Object, Object> map = null;
        if (fileformat.equals("json")) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                map = mapper.readValue(file.toFile(), Map.class);
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        } else if (fileformat.equals("yml")) {
            InputStream inputStream = new FileInputStream(new File(String.valueOf(file)));
            Yaml yaml = new Yaml();
            map = yaml.load(inputStream);
            return map;
        }
        return map;
    }
}
