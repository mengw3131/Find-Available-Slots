import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class FileScanner {

    public static File getLastModified(String directoryFilePath)
    {
        File directory = new File(directoryFilePath);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null)
        {
            for (File file : files)
            {
                if (file.lastModified() > lastModifiedTime && !file.isHidden())
                {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        return chosenFile;
    }

    public static void main(String[] args) {
        while(true) {
            File file = getLastModified("/Users/meng/Downloads");
            if (!file.getName().contains("txt")) {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    continue;
                } catch (Exception e) {
                    System.out.println("delay error!");
                }
            }
            try {
                Scanner in = new Scanner(file);
                boolean isFound = false;
                boolean pageIsFound = false;
                while (in.hasNextLine()) {
                    String line = in.nextLine();
                    if (line.contains("Limited availability") || line.contains("2-hour")) {
                        Runtime runtime = Runtime.getRuntime();
                        String[] arr = {"osascript", "-e", "display notification \"New time slot is AVAILABLE!\" with title \"Amazon Fresh\" sound name \"purr\""};
                        Process process = runtime.exec(arr);
                        isFound = true;
                        //String command = """display notification "New time slot is AVAILABLE!" with title "Amazon Fresh" sound name "Purr" """;
                    }
                }
                if (!isFound) {
                    file.delete();
                }
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }



}
