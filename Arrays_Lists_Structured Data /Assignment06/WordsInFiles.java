import edu.duke.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class WordsInFiles {
    private HashMap<String, ArrayList<String>> wordFileMap;

    public WordsInFiles() {
        wordFileMap = new HashMap<String, ArrayList<String>>();
    }

    private void addWordsFromFile(File f) {
        FileResource fr = new FileResource(f);
        String fileName = f.getName();

        for (String word : fr.words()) {
            String lowerWord = word.toLowerCase();

            if (!wordFileMap.containsKey(lowerWord)) {
                ArrayList<String> files = new ArrayList<String>();
                files.add(fileName);
                wordFileMap.put(lowerWord, files);
            } else {
                ArrayList<String> files = wordFileMap.get(lowerWord);
                if (!files.contains(fileName)) {
                    files.add(fileName);
                }
            }
        }
    }

    public void buildWordFileMap() {
        wordFileMap.clear();
        DirectoryResource dr = new DirectoryResource();

        for (File f : dr.selectedFiles()) {
            addWordsFromFile(f);
        }
    }

    public int maxNumber() {
        int max = 0;

        for (String word : wordFileMap.keySet()) {
            int count = wordFileMap.get(word).size();
            if (count > max) {
                max = count;
            }
        }

        return max;
    }

    public ArrayList<String> wordsInNumFiles(int number) {
        ArrayList<String> words = new ArrayList<String>();

        for (String word : wordFileMap.keySet()) {
            if (wordFileMap.get(word).size() == number) {
                words.add(word);
            }
        }

        return words;
    }

    public void printFilesIn(String word) {
        String lowerWord = word.toLowerCase();

        if (!wordFileMap.containsKey(lowerWord)) {
            System.out.println("Word not found: " + word);
            return;
        }

        for (String fileName : wordFileMap.get(lowerWord)) {
            System.out.println(fileName);
        }
    }

    public void tester() {
        buildWordFileMap();

        int max = maxNumber();
        ArrayList<String> words = wordsInNumFiles(max);

        System.out.println("Maximum number of files any word appears in: " + max);
        System.out.println("Number of words that appear in " + max + " files: " + words.size());
        System.out.println("Words that appear in " + max + " files:");

        //for (String word : words) {
        //   System.out.println(word);
         //   printFilesIn(word);
        //    System.out.println();
        //}
    }

    public void tester(int number) {
        buildWordFileMap();

        ArrayList<String> words = wordsInNumFiles(number);

        System.out.println("Number of words that appear in " + number + " files: " + words.size());
        System.out.println("Words that appear in " + number + " files:");

        //for (String word : words) {
        //    System.out.println(word);
        //    printFilesIn(word);
        //    System.out.println();
        //}
    }

    public void tester(String word) {
        buildWordFileMap();

        System.out.println("Files containing the word \"" + word + "\":");
        printFilesIn(word);
    }

    private boolean wordAppearsExactlyInFile(File f, String targetWord) {
        FileResource fr = new FileResource(f);
        for (String word : fr.words()) {
            if (word.equals(targetWord)) {
                return true;
            }
        }
        return false;
    }

    public void testerNotIn(String word) {
        DirectoryResource dr = new DirectoryResource();
        boolean foundMissingFile = false;

        System.out.println("Files in which the exact word \"" + word + "\" does NOT appear:");
        for (File f : dr.selectedFiles()) {
            if (!wordAppearsExactlyInFile(f, word)) {
                System.out.println(f.getName());
                foundMissingFile = true;
            }
        }

        if (!foundMissingFile) {
            System.out.println("The word appears in all selected files.");
        }
    }

    public void testerExactIn(String word) {
        DirectoryResource dr = new DirectoryResource();
        boolean foundFile = false;

        System.out.println("Files in which the exact word \"" + word + "\" appears:");
        for (File f : dr.selectedFiles()) {
            if (wordAppearsExactlyInFile(f, word)) {
                System.out.println(f.getName());
                foundFile = true;
            }
        }

        if (!foundFile) {
            System.out.println("The word does not appear in any selected file.");
        }
    }

    public static void main(String[] args) {
        WordsInFiles tester = new WordsInFiles();
        tester.tester();
    }
}
