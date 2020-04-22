package week17;

import java.io.IOException;
import java.nio.channels.ClosedByInterruptException;
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
public class ThreadsExercise17 {

    /*
    Adapt your program from ThreadsExercise15 to stop as soon as a task that has processed a file with more than 10 lines is completed.
     */
    public static void main() {
        // word -> number of times it appears over all files
        
        // We just add a boolean used as a flag for when a task has processed 
        // a file with 10 or more lines
        boolean fileWith10Lines = false;
        
        Map<Path, FileInfo> mapping = new HashMap<>();
        ExecutorService executor = Executors.newWorkStealingPool();

        ExecutorCompletionService<FileInfo> completionService
                = new ExecutorCompletionService<>(executor);
        try {
            long pendingTasks
                    = Files.walk(Paths.get("data"))
                            .filter(Files::isRegularFile)
                            .map(filepath
                                    -> completionService.submit(() -> computeFileInfo(filepath))
                            )
                            .count();

            // We then just add an extra check to this guard that will 
            // Make the while loop stop as soon as one of these files is found.
            // We then just make a slight change to the executor to shutdownNow
            // Because if we reach that statement we either went through all files
            // or we found the file and we just need to stop anything else running.
            while (pendingTasks > 0 && !fileWith10Lines) {
                FileInfo fileInfo = completionService.take().get();
                mapping.put(fileInfo.getPath(), fileInfo);
                pendingTasks--;
                if(fileInfo.getLines() > 10) {
                    fileWith10Lines = true;
                }
            }
        } catch (IOException | InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        try {
            executor.shutdownNow();
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

        } catch(ClosedByInterruptException e) {
            // We get closedByInterruptExeception if Files.lines is getting files
            // When we call executor.shutdownNow() so we just print interrupted
            // instead of filling the console with printStackTrace.
            // We actually want it to close because of the interruption, so this is
            // an expection that we actually want/need.
            // Its part of IOException however IOException can be so many other things
            // So we check if ClosedBy first and if its not that we check IOExceptions efter.
            System.out.println("Interrupted!");
            return null;
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
