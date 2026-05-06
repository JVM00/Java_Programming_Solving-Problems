import edu.duke.*;

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

    public StorageResource getAllGenes(String dna) {
        StorageResource genes = new StorageResource();
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
            genes.add(gene);
            startIndex = minStop + 3;
        }
        return genes;
    }

    public void testGetAllGenes() {
        FileResource fr = new FileResource("GRch38dnapart.fa");

        // Convert file content to uppercase
        String dna = fr.asString().toUpperCase();

        StorageResource genes = getAllGenes(dna);
        boolean foundGene = false;

        System.out.println(dna);

        for (String gene : genes.data()) {
            foundGene = true;
            System.out.println(gene);
        }

        if (!foundGene) {
            System.out.println("No genes found.");
        }
    }

    public float cgRatio(String dna) {
        if (dna.length() == 0) {
            return 0.0f;
        }
        int count = 0;
        for (int i = 0; i < dna.length(); i++) {
            char ch = Character.toUpperCase(dna.charAt(i));
            if (ch == 'C' || ch == 'G') {
                count++;
            }
        }
        return (float) count / dna.length();
    }

    public int countCTG(String dna) {
        int count = 0;
        int index = dna.indexOf("CTG");
        while (index != -1) {
            count++;
            index = dna.indexOf("CTG", index + 3);
        }
        return count;
    }

    public void processGenes(StorageResource sr) {
        int countLongerThan60 = 0;
        int countCgHigh = 0;
        int longest = 0;
        boolean foundGene = false;
        for (String gene : sr.data()) {
            foundGene = true;
            if (gene.length() > 60) {
                System.out.println(gene);
                countLongerThan60++;
            }
            if (cgRatio(gene) > 0.35f) {
                System.out.println(gene);
                countCgHigh++;
            }
            if (gene.length() > longest) {
                longest = gene.length();
            }
        }
        if (!foundGene) {
            System.out.println("No genes found.");
        }
        System.out.println("Number of genes longer than 60: " + countLongerThan60);
        System.out.println("Number of genes with CG ratio > 0.35: " + countCgHigh);
        System.out.println("Length of longest gene: " + longest);
    }
    

    public void testProcessGenes() {
        String dna1 = "ATGAAAAAATAAATGCCCCCCCTAA";
        String dna2 = "ATGAAATAAATGTTTTAA";
        String dna3 = "ATGCGCGCGCGTAATAG";
        String dna4 = "ATGAAATTTGGGAAATGA";
        String dna5 = "ATGCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCTAA";

        //processGenes(getAllGenes(dna1));
        //processGenes(getAllGenes(dna2));
        //processGenes(getAllGenes(dna3));
        //processGenes(getAllGenes(dna4));
        //processGenes(getAllGenes(dna5));

        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString().toUpperCase();
        processGenes(getAllGenes(dna));
    }

    public int countGenes(StorageResource genes) {
        int count = 0;
        for (String gene : genes.data()) {
            count++;
        }
        return count;
    }

    public void testCountGenesInFile() {
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString().toUpperCase();
        StorageResource genes = getAllGenes(dna);
        System.out.println("Total genes in file: " + countGenes(genes));
    }

    public void testCountCTGInFile() {
        FileResource fr = new FileResource("GRch38dnapart.fa");
        String dna = fr.asString();
        System.out.println("CTG count in file: " + countCTG(dna));
    }

    public static void main(String[] args) {
        Part1 p = new Part1();
        //p.testFindStopCodon();
        //p.testFindGene();
        //p.printAllGenes("acaagtttgtacaaaaaagcagaagggccgtcaaggcccaccatgcctattggatccaaagagaggccaacattttttgaaatttttaagacacgctgcaacaaagcagatttaggaccaataagtcttaattggtttg");
        p.testGetAllGenes();
        //System.out.println(p.cgRatio("ATGCCATAG"));
        //System.out.println(p.countCTG("ATGCTGAAACTGTTTCTG"));
        p.testProcessGenes();
        p.testCountGenesInFile();
        p.testCountCTGInFile();
    }
}
