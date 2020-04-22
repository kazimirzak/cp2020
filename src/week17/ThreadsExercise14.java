package week17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class ThreadsExercise14 {

    /*
    Modify Threads/cp/WalkExecutorFuture such that, instead of word occurrences,
    it computes a map of type Map< Path, FileInfo >, which maps the Path of each file found in "data"
    to an object of type FileInfo that contains:
    	- the size of the file;
    	- the number of lines contained in the file;
    	- the number of lines starting with the uppercase letter "L".
     */
    public static void main() {
        // word -> number of times it appears over all files
        
        // First we change the map such that it can store the FileInfo object,
        // that is described at the bottom of this file.
        Map<Path, FileInfo> mapping = new HashMap<>();
        ExecutorService executor = Executors.newWorkStealingPool();
        
        // Collecting the futures once they are done is a simple as adding them to the map.
        // We don't have to worry about having two of the same file, since we used
        // files.Walk and never manipulate with the files other than removing folder
        // There is never a case where we have 2 of the same file.
        
        try {
            List< Future< FileInfo>> futures
                    = Files.walk(Paths.get("data"))
                            .filter(Files::isRegularFile)
                            .map(filepath
                                    -> executor.submit(() -> computeFileInfo(filepath))
                            )
                            .collect(Collectors.toList());
            for(Future<FileInfo> future : futures) {
                FileInfo fileInfo = future.get();
                mapping.put(fileInfo.getPath(), fileInfo);
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mapping.forEach((path, fileInfo) -> System.out.println(fileInfo));
    }
    
    /*
     * Method used to extract all information about a file.
     * It first creates the FileInfo object with the path and the size of the path.
     * Files has a nice method called size that takes a path and returns the size of the file.
     * We then for each line increment the line counter, if we find a line starting with L
     * We increment that counter aswell.
     * You could try to to just get the total lines first such that we don't have to increment
     * a counter for each line, however we need to go through each line anyway and increment
     * is not exactly an expensive operation.
     */

    private static FileInfo computeFileInfo(Path textFile) {
        FileInfo result;
        try {
            result = new FileInfo(textFile, Files.size(textFile));
            Files.lines(textFile).forEach(line -> {
                result.incrementLines();
                if(line.startsWith("L"))
                    result.incrementFirstLetterL();
            });
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /*
     * Simple class to store information about a file.
     * Note we use increment methods to get around the "effectively final" 
     * part of the lambda in the above code.
     */
    private static class FileInfo {

        private Path path;
        private long size;
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
