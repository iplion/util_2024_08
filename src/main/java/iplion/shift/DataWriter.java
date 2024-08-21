package iplion.shift;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DataWriter {
    private BufferedWriter writer;
    private final String filename;
    private final boolean append;

    public DataWriter(String filename, boolean append) {
        this.filename = filename;
        this.append = append;
    }

    public void write(String line) {
        if (writer == null) {
            try {
                Path path = Paths.get(filename);
                Path parentDir = path.getParent();
                if (parentDir != null) {
                    Files.createDirectories(parentDir);
                }
                writer = new BufferedWriter(new FileWriter(filename, append));
                System.out.println("opened " + filename);
            } catch (IOException e) {
                System.out.println("Error opening file " + filename);

                return;
            }
        }
        try {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file " + filename);
        }
    }

    public void close() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("Error closing file " + filename);
        }
    }
}
