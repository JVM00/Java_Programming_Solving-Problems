import edu.duke.*;
import java.util.HashMap;

public class CodonCount {
    private HashMap<String, Integer> codonCounts;

    public CodonCount() {
        codonCounts = new HashMap<String, Integer>();
    }

    public void buildCodonMap(int start, String dna) {
        codonCounts.clear();

        for (int i = start; i + 3 <= dna.length(); i += 3) {
            String codon = dna.substring(i, i + 3);
            if (codonCounts.containsKey(codon)) {
                codonCounts.put(codon, codonCounts.get(codon) + 1);
            } else {
                codonCounts.put(codon, 1);
            }
        }
    }

    public String getMostCommonCodon() {
        String mostCommon = "";
        int maxCount = 0;

        for (String codon : codonCounts.keySet()) {
            int currentCount = codonCounts.get(codon);
            if (currentCount > maxCount) {
                maxCount = currentCount;
                mostCommon = codon;
            }
        }

        return mostCommon;
    }

    public int getCount(String codon) {
        if (codon == null || !codonCounts.containsKey(codon)) {
            return 0;
        }
        return codonCounts.get(codon);
    }

    public void printCodonCounts(int start, int end) {
        for (String codon : codonCounts.keySet()) {
            int count = codonCounts.get(codon);
            if (count >= start && count <= end) {
                System.out.println(codon + "\t" + count);
            }
        }
    }

    public void tester() {
        FileResource fr = new FileResource();
        String dna = fr.asString().toUpperCase().trim();

        for (int frame = 0; frame < 3; frame++) {
            buildCodonMap(frame, dna);
            String mostCommon = getMostCommonCodon();
            int mostCommonCount = getCount(mostCommon);

            System.out.println("Reading frame starting with " + frame
                    + " results in " + codonCounts.size() + " unique codons");

            if (!mostCommon.isEmpty()) {
                System.out.println("and most common codon is " + mostCommon
                        + " with count " + mostCommonCount);
                System.out.println("Number of occurrences of the codon that occurs most often"
                        + " for reading frame " + frame + ": " + mostCommonCount);
            } else {
                System.out.println("and most common codon is not available");
            }

            System.out.println("Counts of codons between 1 and 5 inclusive are:");
            printCodonCounts(1, 5);
            System.out.println();
        }
    }

    public void tester(int frame, int countWanted) {
        FileResource fr = new FileResource();
        String dna = fr.asString().toUpperCase().trim();

        buildCodonMap(frame, dna);
        System.out.println("Using reading frame " + frame + ", codons that occur "
                + countWanted + " times are:");

        for (String codon : codonCounts.keySet()) {
            if (codonCounts.get(codon) == countWanted) {
                System.out.println(codon);
            }
        }
    }

    public static void main(String[] args) {
        CodonCount tester = new CodonCount();
        tester.tester();
    }
}
