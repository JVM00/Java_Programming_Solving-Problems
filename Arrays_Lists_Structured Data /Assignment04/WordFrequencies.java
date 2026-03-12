
import edu.duke.*;
import java.util.ArrayList;

public class WordFrequencies {
    private ArrayList<String> myWords;
    private ArrayList<Integer> myFreqs;

    public WordFrequencies() {
        myWords = new ArrayList<String>();
        myFreqs = new ArrayList<Integer>();
    }

    public void findUnique() {
        myWords.clear();
        myFreqs.clear();

        FileResource fr = new FileResource();
        for (String word : fr.words()) {
            String lowerWord = word.toLowerCase();
            int index = myWords.indexOf(lowerWord);

            if (index == -1) {
                myWords.add(lowerWord);
                myFreqs.add(1);
            } else {
                myFreqs.set(index, myFreqs.get(index) + 1);
            }
        }
    }

    public int findIndexOfMax() {
        int maxIndex = 0;

        for (int i = 1; i < myFreqs.size(); i++) {
            if (myFreqs.get(i) > myFreqs.get(maxIndex)) {
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    public void tester() {
        findUnique();
        System.out.println("Number of unique words: " + myWords.size());
        System.out.println("Total number of unique words: " + myWords.size());

        //for (int i = 0; i < myWords.size(); i++) {
        //   System.out.println(myFreqs.get(i) + " " + myWords.get(i));
        //}

        int maxIndex = findIndexOfMax();
        System.out.println("The word that occurs most often and its count are: "
                + myWords.get(maxIndex) + " " + myFreqs.get(maxIndex));
    }
}
