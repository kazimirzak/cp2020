package week18.inClass;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Kenny Brink - kebri18@student.sdu.dk
 */
public class ThreadsExercise20 {

    /*
    Modify ThreadsExercise19 such that the computed map contains only
    entries for files that have at least 10 lines.
     */
    public static void main() {
        try {
            Map< Path, FileInfo> occurrences
                    = Files
                            .walk(Paths.get("data"))
                            .filter(Files::isRegularFile)
                            .collect(Collectors.toList())
                            .parallelStream()
                            .map(textFile -> {
                                FileInfo fileInfo;
                                try {
                                    fileInfo = new FileInfo(textFile, Files.size(textFile));
                                    Files.lines(textFile).forEach(line -> {
                                        fileInfo.incrementLines();
                                        if (line.startsWith("L")) {
                                            fileInfo.incrementFirstLetterL();
                                        }
                                    });
                                } catch (IOException ex) {
                                    //return new FileInfo(Paths.get(""), 0);
                                    return null;
                                }
                                return fileInfo;
                            }).filter(fileInfo -> fileInfo != null)
                            .filter(fileInfo -> fileInfo.getLines() >= 10)
                            .collect(Collectors.toMap(
                                    fileInfo -> fileInfo.getPath(),
                                    fileInfo -> fileInfo
                            ));
            occurrences.forEach((path, fileInfo) -> System.out.println(fileInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class FileInfo {

        private final Path path;
        private final long size;
        private int lines;
        private int firstLetterL;

        public FileInfo(Path path, long size) {
            this.path = path;
            this.size = size;
            this.lines = 0;
            this.firstLetterL = 0;
        }

        public Path getPath() {
            return path;
        }

        public long getSize() {
            return size;
        }

        public int getLines() {
            return lines;
        }

        public int getFirstLetterL() {
            return firstLetterL;
        }

        public void incrementLines() {
            lines++;
        }

        public void incrementFirstLetterL() {
            firstLetterL++;
        }

        @Override
        public String toString() {
            return "File: " + path.toString()
                    + "\n\tSize -> " + size
                    + "\n\tLines -> " + lines
                    + "\n\t Number of lines starting with L -> " + firstLetterL;
        }

    }
}
