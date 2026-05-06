
public class Part1 {

    public int findStopCodon(String dna, int startIndex, String stopCodon) {
        int currIndex = dna.indexOf(stopCodon, startIndex + 3);
        while (currIndex != -1) {
            if ((currIndex - startIndex) % 3 == 0) {
                return currIndex;
            }
            currIndex = dna.indexOf(stopCodon, currIndex + 1);
        }
        return dna.length();
    }

    public void testFindStopCodon() {
        String dna1 = "AATGCTAACTAGCTGACTAAT";
        System.out.println(findStopCodon(dna1, dna1.indexOf("ATG"), "TAA"));

        String dna2 = "AATGCTAGCTAGCTAA";
        System.out.println(findStopCodon(dna2, dna2.indexOf("ATG"), "TAA"));

        String dna3 = "AATGCCCTGACCC";
        System.out.println(findStopCodon(dna3, dna3.indexOf("ATG"), "TGA"));

        String dna4 = "ATGAAATAA";
        System.out.println(findStopCodon(dna4, dna4.indexOf("ATG"), "TAA"));
    }

    public String findGene(String dna) {
        int startIndex = dna.indexOf("ATG");
        if (startIndex == -1) {
            return "";
        }

        int taaIndex = findStopCodon(dna, startIndex, "TAA");
        int tagIndex = findStopCodon(dna, startIndex, "TAG");
        int tgaIndex = findStopCodon(dna, startIndex, "TGA");

        int minStop = Math.min(taaIndex, Math.min(tagIndex, tgaIndex));
        if (minStop == dna.length()) {
            return "";
        }
        return dna.substring(startIndex, minStop + 3);
    }

    public void testFindGene() {
        String dna1 = "CCCGGG";
        System.out.println(dna1);
        System.out.println(findGene(dna1));

        String dna2 = "AATGCCCTAA";
        System.out.println(dna2);
        System.out.println(findGene(dna2));

        String dna3 = "GGGATGAAATAGTTTTGA";
        System.out.println(dna3);
        System.out.println(findGene(dna3));

        String dna4 = "ATGAAACCCTTT";
        System.out.println(dna4);
        System.out.println(findGene(dna4));

        String dna5 = "TTTATGCCCTGATAAGGGATGAAATGA";
        System.out.println(dna5);
        System.out.println(findGene(dna5));
    }

    public void printAllGenes(String dna) {
        int startIndex = 0;
        while (true) {
            int atgIndex = dna.indexOf("ATG", startIndex);
            if (atgIndex == -1) {
                break;
            }

            int taaIndex = findStopCodon(dna, atgIndex, "TAA");
            int tagIndex = findStopCodon(dna, atgIndex, "TAG");
            int tgaIndex = findStopCodon(dna, atgIndex, "TGA");
            int minStop = Math.min(taaIndex, Math.min(tagIndex, tgaIndex));

            if (minStop == dna.length()) {
                startIndex = atgIndex + 3;
                continue;
            }

            String gene = dna.substring(atgIndex, minStop + 3);
            System.out.println(gene);
            startIndex = minStop + 3;
        }
    }

    public static void main(String[] args) {
        Part1 p = new Part1();
        p.testFindStopCodon();
        p.testFindGene();
        p.printAllGenes("TTTATGCCCTGATAAGGGATGAAATGA");
    }
}
