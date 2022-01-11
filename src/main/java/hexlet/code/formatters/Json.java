package hexlet.code.formatters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class Json {
    public static String json(List<Map<?, ?>> list) throws JsonProcessingException {
        String res = "";
        res += new ObjectMapper().writeValueAsString(list);
        return res;
    }
}
