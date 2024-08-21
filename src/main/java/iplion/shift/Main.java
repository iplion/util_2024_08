package iplion.shift;

import java.io.IOException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Config config = new Config(args);

        if (config.getFilesToProcessing().isEmpty()) {
            Util.printHelp();

            return;
        }

        Statistics stats = new Statistics(config.isFullStats());

        Util.processFiles(config, stats);

        stats.printStatistics();
    }
}