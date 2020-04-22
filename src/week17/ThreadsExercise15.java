package week17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Kenny Brink - Kebri18@student.sdu.dk
 */
public class ThreadsExercise15 {

    /*
    Adapt your program from ThreadsExercise14 to use an ExecutorCompletionService, as in Threads/cp/WalkCompletionService.
     */
    public static void main() {
        // word -> number of times it appears over all files

        Map<Path, FileInfo> mapping = new HashMap<>();
        ExecutorService executor = Executors.newWorkStealingPool();
        
        // We create the completion service just like in fabrizios example.
        ExecutorCompletionService<FileInfo> completionService =
                new ExecutorCompletionService<>(executor);
        try {
            // We then do the same walk filter map, however we submit the tasks
            // to the completionService instead. We then count the mapping and store the long.
            // We do this is we can always call take() on the completionService, however
            // if there is no task to take it will block untill
            // one is avaiable, therefore we need to make sure not to call take
            // unless we know there is going to be another task/future.
            long pendingTasks
                    = Files.walk(Paths.get("data"))
                            .filter(Files::isRegularFile)
                            .map(filepath
                                    -> completionService.submit(() -> computeFileInfo(filepath))
                            )
                            .count();
            
            //Empty out the completionService, just like in fabrizios example.
            while(pendingTasks > 0) {
                FileInfo fileInfo = completionService.take().get();
                mapping.put(fileInfo.getPath(), fileInfo);
                pendingTasks--;
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
