
package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.util.concurrent.Callable;


@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "app 1.0",
        description = "Prints output the difference between the data as a string")

public class App implements Callable<String> {

    @Parameters(index = "0", description = "path to first file")
    private static Path filepath1;
    // = "/Users/user/Hexlet/java-project-lvl2/src/test/resources/filepath1.json"

    @Parameters(index = "1", description = "path to second file")
    private static Path filepath2;
    // = "/Users/user/Hexlet/java-project-lvl2/src/test/resources/filepath2.json"

    @Parameters(index = "2", description = "output format")
    private static String formatname;
    // = "/Users/user/Hexlet/java-project-lvl2/src/test/resources/filepath1.json"

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;

    @Option(names = {"-f", "--format=format"}, description = "output format [default: stylish]")
    private boolean format = false;

    @Option(names = {"-v", "--version "}, description = "Print version information and exit.")
    private boolean versionRequested = false;

    public static void main(String[] args) throws Exception {
        new CommandLine(new App()).execute(args);
        Differ.generate(getFilepath1(), getFilepath2(), getFormatName());
    }

    @Override
    public String call() throws Exception {
        return "0";
    }

    public static String getFilepath1() {
        return String.valueOf(filepath1);
    }

    public static String getFilepath2() {
        return String.valueOf(filepath2);
    }

    public static String getFormatName() {
        return formatname;
    }
}
