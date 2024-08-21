package iplion.shift;

import lombok.Getter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

@Getter
public class Config {
    private String resultPath = "";
    private String resultFilePrefix = "";
    private boolean appendFiles = false;
    private boolean fullStats = false;
    private final ArrayList<String> filesToProcessing = new ArrayList<>();

    private static String validateParameter(String[] args, int i) {
        if (i+1 >= args.length) {

            return "Missing parameter after \"" + args[i] + "\"";
        }
        if (Validator.isFilenameValid(args[i + 1])) {

            return "Mismatch parameter after \"" + args[i] + "\"";
        }

        return "";
    }

    public Config(String[] args) {
        if (args.length == 0 || Objects.equals(args[0], "-h") || Objects.equals(args[0], "--help")) {

            return;
        }

        for (int i=0; i<args.length; i++) {
            switch (args[i]) {
                case "-o":
                    String validatePathResult = validateParameter(args, i);
                    if (!validatePathResult.isEmpty()) {
                        System.out.println(validatePathResult);

                        break;
                    }
                    resultPath = args[++i];

                    break;
                case "-p":
                    String validatePrefixResult = validateParameter(args, i);
                    if (!validatePrefixResult.isEmpty()) {
                        System.out.println(validatePrefixResult);

                        break;
                    }
                    resultFilePrefix = args[++i];

                    break;
                case "-a":
                    appendFiles = true;

                    break;
                case "-f":
                    fullStats = true;

                    break;
                case "-s":
                    fullStats = false;

                    break;
                default:
                    String filename = args[i];
                    if (Validator.isFilenameValid(filename)) {
                        System.out.printf("Mismatch parameter \"" + args[i] + "\"");

                        break;
                    }

                    Path path = Path.of(args[i]);
                    if (!Files.exists(path) || !Files.isRegularFile(path)) {
                        System.out.println("The file \"" + args[i] + "\" does not exist");

                        break;
                    }

                    filesToProcessing.add(args[i]);
            }
        }
    }

}
