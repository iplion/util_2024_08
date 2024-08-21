package iplion.shift;

import java.util.Optional;

public class Util {
    public static void processFiles(Config config, Statistics stats) {
        ResourceFileReader reader = new ResourceFileReader(config.getFilesToProcessing());
        String path = config.getResultPath();
        if (!path.isEmpty() && !path.endsWith("/")) {
            path += "/";
        }
        DataWriter intFileWriter = new DataWriter(path + config.getResultFilePrefix() + "integers.txt", config.isAppendFiles());
        DataWriter floatFileWriter = new DataWriter(path + config.getResultFilePrefix() + "floats.txt", config.isAppendFiles());
        DataWriter stringFileWriter = new DataWriter(path + config.getResultFilePrefix() + "strings.txt", config.isAppendFiles());

        for (String line : reader) {
            Optional<Long> longVal = parseLong(line);
            if (longVal.isPresent()) {
                stats.addRecord(longVal.get());
                intFileWriter.write(line);

                continue;
            }

            Optional<Double> doubleVal = parseDouble(line);
            if (doubleVal.isPresent()) {
                stats.addRecord(doubleVal.get());
                floatFileWriter.write(line);

                continue;
            }

            stats.addRecord(line);
            System.out.println("line " + line);
            stringFileWriter.write(line);
        }

        intFileWriter.close();
        floatFileWriter.close();
        stringFileWriter.close();
    }

    private static Optional<Long> parseLong(String s) {
        try {

            return Optional.of(Long.parseLong(s));
        } catch (NumberFormatException e) {

            return Optional.empty();
        }
    }

    private static Optional<Double> parseDouble(String s) {
        try {

            return Optional.of(Double.parseDouble(s));
        } catch (NumberFormatException e) {

            return Optional.empty();
        }
    }

    public static void printHelp() {
        System.out.println("""
                Usage:
                java -jar util.jar [options] <inFile 1> [inFile 2] ... [inFile N]
                    Possible [options]:
                        -s  display short statistics (default)
                        -f  display full statistics
                        -a  append result files (overwrite by default)
                        -o  result files path (current directory by default)
                        -p  result filenames prefix (none by default)
                        -h  --help  this message
            """);
    }


//    private static boolean isInteger(String line) {
//        final String INTEGER_REGEXP = "^[+-]?\\d+$";
//        Pattern pattern = Pattern.compile(INTEGER_REGEXP);
//        Matcher matcher = pattern.matcher(line);
//
//        return matcher.matches();
//    }
//
//    private static boolean isFloat(String line) {
//        final String INTEGER_REGEXP = "^[+-]?\\d+(\\.\\d+)?$";
//        Pattern pattern = Pattern.compile(INTEGER_REGEXP);
//        Matcher matcher = pattern.matcher(line);
//
//        return matcher.matches();
//    }
}
