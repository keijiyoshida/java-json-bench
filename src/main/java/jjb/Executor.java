package jjb;

import jjb.parser.Jackson;
import jjb.parser.Parser;
import jjb.parser.Result;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

class Executor {
    @Option(name = "-parser", usage = "JSON parser", required=true)
    private ParserType parserType;

    @Option(name = "-file", usage ="JSON file path to be parsed", required = true)
    private String filePath;

    @Option(name="-field", usage="JSON field name to be extracted")
    private String field;

    private Parser parser;

    Executor(String[] args) throws CmdLineException {
        CmdLineParser parser = new CmdLineParser(this);
        parser.parseArgument(args);
        if (parserType == ParserType.JACKSON) {
            this.parser = new Jackson();
        }
    }

    void run() throws IOException {
        long cnt = 0;
        long total = 0;
        long max = 0;
        long min = Long.MAX_VALUE;
        double avg = 0;
        long blackHole = 0;

        List<String> list = Files.lines(Paths.get(filePath)).collect(Collectors.toList());
        for (String line : list) {
            Result result = parser.parse(line.getBytes("UTF-8"), field);
            cnt++;
            total += result.elapsed;
            max = Math.max(max, result.elapsed);
            min = Math.min(min, result.elapsed);
            blackHole += result.found.length();
        }

        avg = (double)total / cnt;

        System.out.println("cnt: " + cnt);
        System.out.println("total: " + total);
        System.out.println("max: " + max);
        System.out.println("min: " + min);
        System.out.println("avg: " + avg);
    }
}
