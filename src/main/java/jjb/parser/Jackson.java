package jjb.parser;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;

import java.io.IOException;

public class Jackson implements Parser {
    private JsonFactory factory = new JsonFactory();

    public Result parse(byte[] jsonText, String field) throws IOException {
        Result result = new Result();
        long start = System.nanoTime();
        JsonParser parser = factory.createParser(jsonText);
        while (parser.nextToken() != null) {
            JsonToken token = parser.currentToken();
            if (token != JsonToken.FIELD_NAME) {
                continue;
            }
            String fieldName = parser.getCurrentName();
            if (!field.equals(fieldName)) {
                continue;
            }
            result.found = parser.nextTextValue();
            break;
        }
        long end = System.nanoTime();
        result.elapsed = end - start;
        return result;
    }
}
