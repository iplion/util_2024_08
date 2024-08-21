package iplion.shift;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.InvalidPathException;

public class Validator {

    public static boolean isFilenameValid(String filename) {
        FileSystem fs = FileSystems.getDefault();
        try {
            fs.getPath(filename);
        } catch (InvalidPathException e) {

            return true;
        }

        return false;
    }
}
