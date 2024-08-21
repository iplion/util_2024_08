package iplion.shift;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ResourceFileReader implements Iterable<String> {
    private final ArrayList<BufferedReader> resourceFiles = new ArrayList<>();

    public ResourceFileReader(List<String> filenames) {
        for (String filename : filenames) {
            try {
                resourceFiles.add(new BufferedReader(new FileReader(filename)));
            } catch (FileNotFoundException e) {
                System.out.printf("File %s not found\n", filename);
            }
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new ResourceFileIterator();
    }

    private class ResourceFileIterator implements Iterator<String> {
        private int currentFileIndex = 0;
        String nextLine;

        public ResourceFileIterator() {
            advanceToNextLine();
        }

        private void closeCurrentFile() {
            try {
                resourceFiles.get(currentFileIndex).close();
            } catch (IOException e) {
                System.out.println("I/O error while closing file");
            }
            resourceFiles.remove(currentFileIndex);

            if (resourceFiles.size() < currentFileIndex + 1) {
                currentFileIndex = 0;
            }
        }

        public void advanceToNextLine() {
            nextLine = null;

            while (!resourceFiles.isEmpty()) {
                try {
                    nextLine = resourceFiles.get(currentFileIndex).readLine();
                } catch (IOException e) {
                    System.out.println("I/O error while reading file");
                    closeCurrentFile();

                    continue;
                }

                if (nextLine == null) {
                    closeCurrentFile();

                    continue;
                }

                if (resourceFiles.size() == currentFileIndex + 1) {
                    currentFileIndex = 0;
                } else {
                    currentFileIndex++;
                }

                break;
            }
        }

        @Override
        public boolean hasNext() {
            return nextLine != null;
        }

        @Override
        public String next() {
            if (nextLine == null) {
                throw new NoSuchElementException();
            }

            String ret = nextLine;
            advanceToNextLine();

            return ret;
        }
    }


}
