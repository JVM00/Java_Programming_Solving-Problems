
import edu.duke.*;

public class WordLengths {
    public void countWordLengths(FileResource resource, int[] counts) {
        for (String word : resource.words()) {
            int wordLength = word.length();

            if (wordLength == 0) {
                continue;
            }

            if (!Character.isLetter(word.charAt(0))) {
                wordLength--;
            }
            if (wordLength > 0 && !Character.isLetter(word.charAt(word.length() - 1))) {
                wordLength--;
            }

            if (wordLength <= 0) {
                continue;
            }

            if (wordLength >= counts.length) {
                counts[counts.length - 1]++;
            } else {
                counts[wordLength]++;
            }
        }
    }

    public int indexOfMax(int[] values) {
        int maxIndex = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] > values[maxIndex]) {
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public void testCountWordLengths() {
        FileResource fr = new FileResource();
        int[] counts = new int[31];

        countWordLengths(fr, counts);

        for (int i = 0; i < counts.length; i++) {
            System.out.println("Words of length " + i + ": " + counts[i]);
        }

        int maxIndex = indexOfMax(counts);
        System.out.println("The most common word length is: " + maxIndex);
    }
}
