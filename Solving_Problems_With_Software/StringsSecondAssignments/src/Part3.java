
public class Part3 {

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

    public int countGenes(String dna) {
        int count = 0;
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

            count++;
            startIndex = minStop + 3;
        }
        return count;
    }

    public void testCountGenes() {
        System.out.println(countGenes("ATGTAAGATGCCCTAGT"));
        System.out.println(countGenes("CCCGGG"));
        System.out.println(countGenes("AATGCCCTAA"));
        System.out.println(countGenes("ATGAAACCCTTT"));
        System.out.println(countGenes("ATGAAATGAATGCCCTAG"));
    }

    public static void main(String[] args) {
        Part3 p = new Part3();
        p.testCountGenes();
    }
}
