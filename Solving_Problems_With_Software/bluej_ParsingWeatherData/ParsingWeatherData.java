import edu.duke.*;
import java.io.File;
import org.apache.commons.csv.*;

/**
 * Parsing weather data from CSV files.
 */
public class ParsingWeatherData {

    public CSVRecord hottestHourInFile(CSVParser parser) {
        CSVRecord hottest = null;
        for (CSVRecord record : parser) {
            Double temp = getTemperature(record);
            if (temp == null) {
                continue;
            }
            if (hottest == null) {
                hottest = record;
            } else if (temp > getTemperature(hottest)) {
                hottest = record;
            }
        }
        return hottest;
    }

    public void testHottestHourInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord hottest = hottestHourInFile(parser);
        if (hottest != null) {
            System.out.println("Hottest temperature was " + hottest.get("TemperatureF")
                    + " at " + hottest.get("DateUTC"));
        }
    }

    public CSVRecord hottestInManyFiles() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord hottest = null;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord current = hottestHourInFile(fr.getCSVParser());
            if (current == null) {
                continue;
            }
            if (hottest == null) {
                hottest = current;
            } else if (getTemperature(current) > getTemperature(hottest)) {
                hottest = current;
            }
        }
        return hottest;
    }

    public void testHottestInManyFiles() {
        CSVRecord hottest = hottestInManyFiles();
        if (hottest != null) {
            System.out.println("Hottest temperature was " + hottest.get("TemperatureF")
                    + " at " + hottest.get("DateUTC"));
        }
    }

    public CSVRecord coldestHourInFile(CSVParser parser) {
        CSVRecord coldest = null;
        for (CSVRecord record : parser) {
            Double temp = getTemperature(record);
            if (temp == null) {
                continue;
            }
            if (coldest == null) {
                coldest = record;
            } else if (temp < getTemperature(coldest)) {
                coldest = record;
            }
        }
        return coldest;
    }

    public void testColdestHourInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord coldest = coldestHourInFile(parser);
        if (coldest != null) {
            System.out.println("Coldest temperature was " + coldest.get("TemperatureF")
                    + " at " + coldest.get("DateUTC"));
        }
    }

    public String fileWithColdestTemperature() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord coldest = null;
        File coldestFile = null;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord current = coldestHourInFile(fr.getCSVParser());
            if (current == null) {
                continue;
            }
            if (coldest == null) {
                coldest = current;
                coldestFile = f;
            } else if (getTemperature(current) < getTemperature(coldest)) {
                coldest = current;
                coldestFile = f;
            }
        }
        return coldestFile == null ? null : coldestFile.getName();
    }

    public void testFileWithColdestTemperature() {
        String fileName = fileWithColdestTemperature();
        if (fileName == null) {
            return;
        }
        FileResource fr = new FileResource(fileName);
        CSVRecord coldest = coldestHourInFile(fr.getCSVParser());
        System.out.println("Coldest day was in file " + fileName);
        if (coldest != null) {
            System.out.println("Coldest temperature on that day was " + coldest.get("TemperatureF"));
        }
        System.out.println("All the Temperatures on the coldest day were:");
        CSVParser parser = fr.getCSVParser();
        for (CSVRecord record : parser) {
            System.out.println(record.get("DateUTC") + ": " + record.get("TemperatureF"));
        }
    }
    
    public String fileWithColdestTemperatureInFolder(String folderRelativeToProject) {
        CSVRecord coldest = null;
        File coldestFile = null;
    
        File[] files = new File(folderRelativeToProject).listFiles();
        if (files == null) return null;
    
        for (File f : files) {
            if (!f.isFile() || !f.getName().toLowerCase().endsWith(".csv")) continue;
    
            FileResource fr = new FileResource(f);
            CSVRecord current = coldestHourInFile(fr.getCSVParser());
            if (current == null) continue;
    
            if (getTemperature(current) == Double.POSITIVE_INFINITY) continue;
    
            if (coldest == null || getTemperature(current) < getTemperature(coldest)) {
                coldest = current;
                coldestFile = f;
            }
        }
        return (coldestFile == null) ? null : coldestFile.getName();
    }

    public void testfileWithColdestTemperatureInFolder() {
        String filename = fileWithColdestTemperatureInFolder("nc_weather/nc_weather/2013");
        System.out.println("Coldest day file: " + filename);
    }
    public CSVRecord lowestHumidityInFile(CSVParser parser) {
        CSVRecord lowest = null;
        for (CSVRecord record : parser) {
            String humidity = record.get("Humidity");
            if (humidity.equals("N/A")) {
                continue;
            }
            if (lowest == null) {
                lowest = record;
            } else if (Integer.parseInt(humidity) < Integer.parseInt(lowest.get("Humidity"))) {
                lowest = record;
            }
        }
        return lowest;
    }
    
    public void coldestDaySummary(String folder) {
    String coldestFileName = fileWithColdestTemperatureInFolder(folder);
    if (coldestFileName == null) return;

    FileResource fr = new FileResource(folder + "/" + coldestFileName);
    CSVRecord coldestHour = coldestHourInFile(fr.getCSVParser());

    System.out.println("Coldest day was in file: " + coldestFileName);
    System.out.println("Coldest temperature on that day was: "
                        + getTemperature(coldestHour));
    }   

    public void testColdestDaySummary() {
    coldestDaySummary("nc_weather/nc_weather/2013");
    }
    public void testLowestHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        CSVRecord lowest = lowestHumidityInFile(parser);
        if (lowest != null) {
            System.out.println("Lowest Humidity was " + lowest.get("Humidity")
                    + " at " + lowest.get("DateUTC"));
        }
    }

    public CSVRecord lowestHumidityInManyFiles() {
        DirectoryResource dr = new DirectoryResource();
        CSVRecord lowest = null;
        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            CSVRecord current = lowestHumidityInFile(fr.getCSVParser());
            if (current == null) {
                continue;
            }
            if (lowest == null) {
                lowest = current;
            } else if (Integer.parseInt(current.get("Humidity"))
                    < Integer.parseInt(lowest.get("Humidity"))) {
                lowest = current;
            }
        }
        return lowest;
    }

    public void testLowestHumidityInManyFiles() {
        CSVRecord lowest = lowestHumidityInManyFiles();
        if (lowest != null) {
            System.out.println("Lowest Humidity was " + lowest.get("Humidity")
                    + " at " + lowest.get("DateUTC"));
        }
    }

     public double averageTemperatureInFile(CSVParser parser) {
        double total = 0.0;
        int count = 0;
    
        for (CSVRecord rec : parser) {
            double temp = Double.parseDouble(rec.get("TemperatureF"));
            if (temp == -9999) continue;   // skip invalid readings
            total += temp;
            count++;
        }
        if (count == 0) return 0.0;
        return total / count;
    }

    public void testAverageTemperatureInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avg = averageTemperatureInFile(parser);
        System.out.println("Average temperature in file is " + avg);
    }

    public double averageTempWithHighHumidityInFile(CSVParser parser, int humidityThreshold) {
        double total = 0.0;
        int count = 0;
    
        for (CSVRecord rec : parser) {
            String humStr = rec.get("Humidity");
            if (humStr.equals("N/A")) continue;
    
            int hum = Integer.parseInt(humStr);
            if (hum < humidityThreshold) continue;
    
            double temp = Double.parseDouble(rec.get("TemperatureF"));
            if (temp == -9999) continue;
    
            total += temp;
            count++;
        }
        if (count == 0) return 0.0;
        return total / count;
    }

    public void testAverageTemperatureWithHighHumidityInFile() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
        double avg = averageTempWithHighHumidityInFile(parser, 80);
        if (avg == 0.0) {
            System.out.println("No temperatures with that humidity");
        } else {
            System.out.println("Average Temp when high Humidity is " + avg);
        }
    }

    private Double getTemperature(CSVRecord record) {
        String tempStr = record.get("TemperatureF");
        double temp = Double.parseDouble(tempStr);
        if (temp == -9999) {
            return null;
        }
        return temp;
    }
}
