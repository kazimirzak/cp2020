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
public class ThreadsExercise19 {

    /*
    Modify Threads/cp/WalkParallelStream5 to compute a Map< Path, FileInfo >
    that maps each file (the Path) to a FileInfo object that contains
    	- the Path of the file;
    	- the size of the file;
    	- the number of lines contained in the file;
    	- the number of lines starting with the uppercase letter "L".
     */
    public static void main() {
        try {
            Map<Path, FileInfo> fileInfos
                    = Files
                            .walk(Paths.get("data"))
                            .filter(Files::isRegularFile)
                            .collect(Collectors.toList())
                            .parallelStream()
                            // Once we have a parallelStream we simply map each file to a fileInfo containing the right information.
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
                                    // If we get an IOExecption during any of this we just return a basically empty FileInfo.
                                    // We have to do this such that the program can continue if only 1 file fails.
                                    // If we return null here we get a null pointer exception in the Collectors.toMap
                                    // below.
                                    return new FileInfo(Paths.get(""), 0);
                                }
                            })
                            // Here we collect all the fileinfos into a map. We don't need a merge function, since we shouldn't get two paths that are the same.
                            .collect(Collectors.toMap(
                                    fileInfo -> fileInfo.getPath(),
                                    fileInfo -> fileInfo
                            ));
            fileInfos.forEach((path, fileInfo) -> System.out.println(fileInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Simple implementation of the fileInfo described by Fabrizio. 
     * We could make it immutable, but for the task at hand i think its easier to deal with it, 
     * if we can increment number of lines and number of L lines.
     */
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

        @Override
        public String toString() {
            return filePath.toString() + ":\n\t-> FileSize: " + fileSize + "\n\t-> Number of lines: " + numOfLines + "\n\t-> Number of lines starting with L: " + numOfLLines;
        }
    }
}
