package week18.inClass;

import Week12.mySolution.Words;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


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
