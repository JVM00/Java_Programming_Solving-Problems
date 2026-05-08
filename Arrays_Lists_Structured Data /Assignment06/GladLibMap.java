import edu.duke.*;
import java.util.*;

public class GladLibMap {
    private HashMap<String, ArrayList<String>> myMap;
    private ArrayList<String> usedWords;
    private ArrayList<String> usedCategories;

    private Random myRandom;
    private int replacedCount;
    private String mySource;

    private static String dataSourceURL = "http://dukelearntoprogram.com/course3/data";
    private static String dataSourceDirectory = "data";

    public GladLibMap() {
        mySource = dataSourceDirectory;
        initializeFromSource(dataSourceDirectory);
        myRandom = new Random();
    }

    public GladLibMap(String source) {
        mySource = source;
        initializeFromSource(source);
        myRandom = new Random();
    }

    private void initializeFromSource(String source) {
        myMap = new HashMap<String, ArrayList<String>>();

        String[] categories = {
            "adjective", "noun", "color", "country", "name",
            "animal", "timeframe", "verb", "fruit"
        };

        for (String category : categories) {
            String fileName = source + "/" + category + ".txt";
            myMap.put(category, readIt(fileName));
        }

        usedWords = new ArrayList<String>();
        usedCategories = new ArrayList<String>();
    }

    private String randomFrom(ArrayList<String> source) {
        int index = myRandom.nextInt(source.size());
        return source.get(index);
    }

    private String getSubstitute(String label) {
        if (label.equals("number")) {
            return "" + (myRandom.nextInt(50) + 5);
        }

        if (myMap.containsKey(label)) {
            if (!usedCategories.contains(label)) {
                usedCategories.add(label);
            }
            return randomFrom(myMap.get(label));
        }

        return "**UNKNOWN**";
    }

    private String processWord(String w) {
        int first = w.indexOf("<");
        int last = w.indexOf(">", first);
        if (first == -1 || last == -1) {
            return w;
        }

        String prefix = w.substring(0, first);
        String suffix = w.substring(last + 1);
        String label = w.substring(first + 1, last);
        String sub = getSubstitute(label);

        while (!sub.equals("**UNKNOWN**") && !label.equals("number") && usedWords.contains(sub)) {
            sub = getSubstitute(label);
        }

        if (!sub.equals("**UNKNOWN**") && !label.equals("number")) {
            usedWords.add(sub);
        }

        replacedCount++;
        return prefix + sub + suffix;
    }

    private void printOut(String s, int lineWidth) {
        int charsWritten = 0;
        for (String w : s.split("\\s+")) {
            if (charsWritten + w.length() > lineWidth) {
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w + " ");
            charsWritten += w.length() + 1;
        }
    }

    private String fromTemplate(String source) {
        String story = "";
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for (String word : resource.words()) {
                story = story + processWord(word) + " ";
            }
        } else {
            FileResource resource = new FileResource(source);
            for (String word : resource.words()) {
                story = story + processWord(word) + " ";
            }
        }
        return story;
    }

    private ArrayList<String> readIt(String source) {
        ArrayList<String> list = new ArrayList<String>();
        if (source.startsWith("http")) {
            URLResource resource = new URLResource(source);
            for (String line : resource.lines()) {
                list.add(line);
            }
        } else {
            FileResource resource = new FileResource(source);
            for (String line : resource.lines()) {
                list.add(line);
            }
        }
        return list;
    }

    public int totalWordsInMap() {
        int total = 0;
        for (String category : myMap.keySet()) {
            total += myMap.get(category).size();
        }
        return total;
    }

    public int totalWordsConsidered() {
        int total = 0;
        for (String category : usedCategories) {
            total += myMap.get(category).size();
        }
        return total;
    }

    public void makeStory() {
        System.out.println("\n");
        usedWords.clear();
        usedCategories.clear();
        replacedCount = 0;

        String story = fromTemplate(mySource + "/madtemplate2.txt");
        printOut(story, 60);
        System.out.println();
        System.out.println("Total words replaced: " + replacedCount);
        System.out.println("Total words in map: " + totalWordsInMap());
        System.out.println("Total words considered: " + totalWordsConsidered());
    }

    public static void main(String[] args) {
        GladLibMap gladLib = new GladLibMap();
        gladLib.makeStory();
    }
}
