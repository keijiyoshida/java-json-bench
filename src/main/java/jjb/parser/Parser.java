package jjb.parser;

import java.io.IOException;

public interface Parser {
    Result parse(byte[] jsonText, String field) throws IOException;
}
