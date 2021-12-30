
package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;

@SuppressWarnings("checkstyle:RegexpSingleline")
@Command(name = "gendiff", mixinStandardHelpOptions = true, version = "app 1.0",
        description = "Prints output the difference between the data as a string")

public final class App implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println(Differ.generate(filepath1, filepath2, formatname));
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }

    @Parameters(index = "0", description = "path to first file")
    private static String filepath1;

    @Parameters(index = "1", description = "path to second file")
    private static String filepath2;

    @Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    private boolean helpRequested = false;

    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String formatname = "stylish";

    @Option(names = {"-v", "--version "}, description = "Print version information and exit.")
    private boolean versionRequested = false;
}
