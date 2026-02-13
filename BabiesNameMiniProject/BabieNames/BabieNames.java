import edu.duke.DirectoryResource;
import edu.duke.FileResource;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;

/**
 * Baby names analysis methods.
 */
public class BabieNames {

    /**
     * Prints total births, total girl births, total boy births,
     * and the number of unique girl/boy names in the selected file.
     */
    public void totalBirths() {
        FileResource fr = new FileResource();
        int totalBirths = 0;
        int totalGirls = 0;
        int totalBoys = 0;
        int girlNames = 0;
        int boyNames = 0;

        for (CSVRecord rec : fr.getCSVParser(false)) {
            int numBorn = Integer.parseInt(rec.get(2));
            String gender = rec.get(1);
            totalBirths += numBorn;
            if (gender.equals("F")) {
                totalGirls += numBorn;
                girlNames++;
            } else if (gender.equals("M")) {
                totalBoys += numBorn;
                boyNames++;
            }
        }

        int totalNames = girlNames + boyNames;
        System.out.println("Total births = " + totalBirths);
        System.out.println("Total girls births = " + totalGirls);
        System.out.println("Total boys births = " + totalBoys);
        System.out.println("Unique girls names = " + girlNames);
        System.out.println("Unique boys names = " + boyNames);
        System.out.println("Total unique names = " + totalNames);
    }

    /**
     * Returns the rank of the given name and gender in the given year.
     * Rank 1 is highest (most births). Returns -1 if not found.
     */
    public int getRank(int year, String name, String gender) {
        FileResource fr = new FileResource("/home/jvm/Projects/JAVA/BabiesNameMiniProject/us_babynames/us_babynames_by_year/yob" + year + ".csv");
        int rank = 0;
        int result = -1;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                rank++;
                if (rec.get(0).equals(name)) {
                    result = rank;
                    break;
                }
            }
        }
        System.out.println("Rank for " + name + " (" + gender + ") in " + year + " = " + result);
        return result;
    }

    /**
     * Returns the name at the given rank for the given year and gender.
     * Returns "NO NAME" if the rank does not exist.
     */
    public String getName(int year, int rank, String gender) {
        FileResource fr = new FileResource("/home/jvm/Projects/JAVA/BabiesNameMiniProject/us_babynames/us_babynames_by_year/yob" + year + ".csv");
        int currentRank = 0;
        String result = "NO NAME";
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                currentRank++;
                if (currentRank == rank) {
                    result = rec.get(0);
                    break;
                }
            }
        }
        System.out.println("Name at rank " + rank + " for " + gender + " in " + year + " = " + result);
        return result;
    }

    /**
     * Prints what a name would be in a different year at the same rank.
     */
    public void whatIsNameInYear(String name, int year, int newYear, String gender) {
        int rank = getRank(year, name, gender);
        if (rank == -1) {
            System.out.println(name + " is not ranked in year " + year + ".");
            return;
        }
        String newName = getName(newYear, rank, gender);
        String pronoun = gender.equals("M") ? "he" : (gender.equals("F") ? "she" : "they");
        System.out.println(name + " born in " + year + " would be " + newName +
                " if " + pronoun + " was born in " + newYear + ".");
    }

    /**
     * Returns the year with the highest rank for the given name and gender
     * among selected files. Returns -1 if not found in any file.
     */
    public int yearOfHighestRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int bestYear = -1;
        int bestRank = Integer.MAX_VALUE;

        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            int rank = getRankInParser(fr.getCSVParser(false), name, gender);
            if (rank != -1 && rank < bestRank) {
                bestRank = rank;
                bestYear = getYearFromFileName(f.getName());
            }
        }
        System.out.println("Year of highest rank for " + name + " (" + gender + ") = " + bestYear);
        return bestYear;
    }

    /**
     * Returns average rank of the given name and gender over selected files.
     * Returns -1.0 if not ranked in any file.
     */
    public double getAverageRank(String name, String gender) {
        DirectoryResource dr = new DirectoryResource();
        int totalRank = 0;
        int count = 0;

        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            int rank = getRankInParser(fr.getCSVParser(false), name, gender);
            if (rank != -1) {
                totalRank += rank;
                count++;
            }
        }

        double result = -1.0;
        if (count != 0) {
            result = (double) totalRank / count;
        }
        System.out.println("Average rank for " + name + " (" + gender + ") = " + result);
        return result;
    }

    /**
     * Returns total births of names ranked higher than the given name.
     */
    public int getTotalBirthsRankedHigher(int year, String name, String gender) {
        FileResource fr = new FileResource();
        int total = 0;
        boolean found = false;
        for (CSVRecord rec : fr.getCSVParser(false)) {
            if (rec.get(1).equals(gender)) {
                if (rec.get(0).equals(name)) {
                    found = true;
                    break;
                }
                total += Integer.parseInt(rec.get(2));
            }
        }
        int result = total;
        System.out.println("Total births ranked higher than " + name + " (" + gender + ") in " + year + " = " + result);
        return result;
    }

    private int getRankInParser(CSVParser parser, String name, String gender) {
        int rank = 0;
        for (CSVRecord rec : parser) {
            if (rec.get(1).equals(gender)) {
                rank++;
                if (rec.get(0).equals(name)) {
                    return rank;
                }
            }
        }
        return -1;
    }

    private int getYearFromFileName(String fileName) {
        // Expects file name like "yob2012.csv"
        int start = fileName.indexOf("yob") + 3;
        int end = fileName.lastIndexOf('.');
        if (start >= 3 && end > start) {
            return Integer.parseInt(fileName.substring(start, end));
        }
        return -1;
    }
}
