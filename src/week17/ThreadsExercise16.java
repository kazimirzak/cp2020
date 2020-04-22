package week17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class ThreadsExercise16 {

    /*
    Adapt your program from ThreadsExercise15 to use CompletableFuture, as in Threads/cp/WalkCompletableFuture.
     */
    public static void main() {
        // word -> number of times it appears over all files

        Map<Path, FileInfo> mapping = new ConcurrentHashMap<>();
        // Here we convert the map to concurrentHashMap. 
        // We then create an array of completableFutures by mapping each filepath
        // In this mapping we supplyAsync on the completableFuture.
        // What this does is add the lambda to an executor that completable future has.
        // Once the lambda then finishes we use thenAccept in order to add the fileInfo
        // To then mapping. Since we use thenAccept we now use the mapping concurrently
        // And therefore we need the concurrentHashMap.
        // We then collect all of these futures into an array.
        try {
            CompletableFuture<Void>[] futures = 
                    Files.walk(Paths.get("data"))
                        .filter(Files::isRegularFile)
                        .map(filepath ->
                            CompletableFuture.supplyAsync(() -> computeFileInfo(filepath))
                                    .thenAccept(fileInfo -> mapping.put(fileInfo.getPath(), fileInfo))
                    ).collect( Collectors.toList()).toArray(new CompletableFuture[0]);
            // We now use CompletableFuture.allOf and supply it with the array.
            // We then call join to wait for them all to finish.
            CompletableFuture
                    .allOf(futures)
                    .join();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        mapping.forEach((path, fileInfo) -> System.out.println(fileInfo));
    }

    private static FileInfo computeFileInfo(Path textFile) {
        FileInfo result;
        try {
            result = new FileInfo(textFile, Files.size(textFile));
            Files.lines(textFile).forEach(line -> {
                result.incrementLines();
                if (line.startsWith("L")) {
                    result.incrementFirstLetterL();
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return result;
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
