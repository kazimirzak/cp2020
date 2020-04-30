package week18.mySolutions;

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
            Map<Path, FileInfo> fileInfos
                    = Files
                            .walk(Paths.get("data"))
                            .filter(Files::isRegularFile)
                            .collect(Collectors.toList())
                            .parallelStream()
                            .map(file -> {
                                try {
                                    FileInfo fileInfo = new FileInfo(file, Files.size(file));
                                    Files.lines(file).forEach(line -> {
                                        fileInfo.incrementLines();
                                        if (line.startsWith("L")) {
                                            fileInfo.incrementLLines();
                                        }
                                    });
                                    return fileInfo;
                                } catch (IOException ex) {
                                    ex.printStackTrace();
                                    return new FileInfo(Paths.get(""), 0);
                                }
                            })
                            // Here we simply just filter out the files that have less than 10 lines.
                            // The rest is exactly as in ThreadsExercise19.
                            .filter(fileInfo -> fileInfo.getNumOfLines() >= 10)
                            .collect(Collectors.toMap(
                                    fileInfo -> fileInfo.getPath(),
                                    fileInfo -> fileInfo
                            ));
            fileInfos.forEach((path, fileInfo) -> System.out.println(fileInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static class FileInfo {

        private final Path filePath;
        private final long fileSize;
        private long numOfLines;
        private long numOfLLines;

        public FileInfo(Path filePath, long fileSize) {
            this.filePath = filePath;
            this.fileSize = fileSize;
            numOfLines = 0;
            numOfLLines = 0;
        }

        private void incrementLines() {
            numOfLines++;
        }

        private void incrementLLines() {
            numOfLLines++;
        }

        private Path getPath() {
            return filePath;
        }
        
        private long getNumOfLines() {
            return numOfLines;
        }

        @Override
        public String toString() {
            return filePath.toString() + ":\n\t-> FileSize: " + fileSize + "\n\t-> Number of lines: " + numOfLines + "\n\t-> Number of lines starting with L: " + numOfLLines;
        }
    }
}
