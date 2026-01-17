

public class Part1 {
    public String findSimpleGene(String dna) {
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1) {
            return "";
        }
        int stopIndex = dna.indexOf("TAA", startIndex + 3);
        if (stopIndex == -1) {
            return "";
        }
        int geneLength = stopIndex + 3 - startIndex;
        if (geneLength % 3 == 0) {
            return dna.substring(startIndex, stopIndex + 3);
        }
        return "";
    }

    public void testSimpleGene() {
        String[] tests = {
            "CCGGTGAAC",           // no ATG
            "ATGCCCGGG",           // no TAA
            "CCGGTGAACGTT",        // no ATG or TAA
            "AATGCCCTAACTG",       // valid gene (ATG...TAA)
            "AATGCCCTAAGG"         // ATG...TAA not multiple of 3
        };

        for (String dna : tests) {
            System.out.println("DNA: " + dna);
            String gene = findSimpleGene(dna);
            System.out.println("Gene: " + gene);
        }
    }

    public static void main(String[] args) {
        Part1 p = new Part1();
        p.testSimpleGene();
    }
}
