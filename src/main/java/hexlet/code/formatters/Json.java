package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class Json {
    public static String json(List<Map<?, ?>> list) throws JsonProcessingException {
        String res = "";
        for (Map map : list) {
            res += new ObjectMapper().writeValueAsString(map);
        }
        return res;
    }
}
