
package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import java.util.concurrent.Callable;

import java.nio.file.Path;


@SuppressWarnings("checkstyle:RegexpSingleline")
@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "app 1.0",
        description = "Prints output the difference between the data as a string")

public final class App implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        Differ.generate(getFilepath1(), getFilepath2(), getFormatName());
        return 0;
    }
    public static void main(String[] args) throws Exception {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Parameters(index = "0", description = "path to first file")
    private static Path filepath1;


    @Parameters(index = "1", description = "path to second file")
    private static Path filepath2;

    @Parameters(index = "2", description = "output format")
    private static String formatname;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;

    @Option(names = {"-f", "--format=format"}, description = "output format [default: stylish]")
    private String format = "stylish";

    @Option(names = {"-v", "--version "}, description = "Print version information and exit.")
    private boolean versionRequested = false;

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
