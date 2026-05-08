
/**
 * Write a description of class LogAnalyzer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;
import edu.duke.*;

public class LogAnalyzer
{
     private ArrayList<LogEntry> records;
     
     public LogAnalyzer() {
         records = new ArrayList<LogEntry>();
     }
        
     public void readFile(String filename) {
         FileResource fr = new FileResource(filename);
         for (String line : fr.lines()) {
             LogEntry le = WebLogParser.parseEntry(line);
             records.add(le);
         }
     }
        
     public void printAll() {
         for (LogEntry le : records) {
             System.out.println(le);
         }
     }
     
     public int countUniqueIPs() {
         ArrayList<String> uniqueIPs = new ArrayList<String>();
         for (LogEntry le : records) {
             String ipAddress = le.getIpAddress();
             if (!uniqueIPs.contains(ipAddress)) {
                 uniqueIPs.add(ipAddress);
             }
         }
         return uniqueIPs.size();
     }
     
     public void printAllHigherThanNum(int num) {
         for (LogEntry le : records) {
             if (le.getStatusCode() > num) {
                 System.out.println(le);
             }
         }
     }
     
     public ArrayList<Integer> uniqueStatusCodesHigherThanNum(int num) {
         ArrayList<Integer> statusCodes = new ArrayList<Integer>();
         for (LogEntry le : records) {
             int statusCode = le.getStatusCode();
             if (statusCode > num && !statusCodes.contains(statusCode)) {
                 statusCodes.add(statusCode);
             }
         }
         return statusCodes;
     }
     
     public ArrayList<Integer> uniqueStatusCodesHigherThanNum(String filename, int num) {
         records.clear();
         readFile(filename);
         return uniqueStatusCodesHigherThanNum(num);
     }
     
     public void printAllHigherThanNumWithStatusCodes(int num) {
         ArrayList<Integer> statusCodes = new ArrayList<Integer>();
         for (LogEntry le : records) {
             int statusCode = le.getStatusCode();
             if (statusCode > num) {
                 //System.out.println(le);
                 if (!statusCodes.contains(statusCode)) {
                     statusCodes.add(statusCode);
                 }
             }
         }
         System.out.println("Status codes printed: " + statusCodes);
     }
     
     public void printAllHigherThanNumWithStatusCodes(String filename, int num) {
         records.clear();
         readFile(filename);
         printAllHigherThanNumWithStatusCodes(num);
     }
     
     public ArrayList<String> uniqueIPVisitsOnDay(String someday) {
         ArrayList<String> uniqueIPs = new ArrayList<String>();
         for (LogEntry le : records) {
             String date = le.getAccessTime().toString();
             String ipAddress = le.getIpAddress();
             if (date.contains(someday) && !uniqueIPs.contains(ipAddress)) {
                 uniqueIPs.add(ipAddress);
             }
         }
         return uniqueIPs;
     }
     
     public ArrayList<String> uniqueIPVisitsOnDay(String filename, String someday) {
         records.clear();
         readFile(filename);
         ArrayList<String> uniqueIPs = uniqueIPVisitsOnDay(someday);
         System.out.println("Unique IP visits on " + someday + ": " + uniqueIPs.size());
         return uniqueIPs;
     }
     
     public int countUniqueIPsInRange(int low, int high) {
         ArrayList<String> uniqueIPs = new ArrayList<String>();
         for (LogEntry le : records) {
             int statusCode = le.getStatusCode();
             String ipAddress = le.getIpAddress();
             if (statusCode >= low && statusCode <= high && !uniqueIPs.contains(ipAddress)) {
                 uniqueIPs.add(ipAddress);
             }
         }
         return uniqueIPs.size();
     }
     
     public int countUniqueIPsInRange(String filename, int low, int high) {
         records.clear();
         readFile(filename);
         return countUniqueIPsInRange(low, high);
     }
     
     public HashMap<String, Integer> countVisitsPerIP() {
         HashMap<String, Integer> counts = new HashMap<String, Integer>();
         for (LogEntry le : records) {
             String ipAddress = le.getIpAddress();
             if (!counts.containsKey(ipAddress)) {
                 counts.put(ipAddress, 1);
             }
             else {
                 counts.put(ipAddress, counts.get(ipAddress) + 1);
             }
         }
         return counts;
     }
     
     public int mostNumberVisitsByIP(HashMap<String, Integer> counts) {
         int maxVisits = 0;
         for (String ipAddress : counts.keySet()) {
             int visits = counts.get(ipAddress);
             if (visits > maxVisits) {
                 maxVisits = visits;
             }
         }
         return maxVisits;
     }
     
     public ArrayList<String> iPsMostVisits(HashMap<String, Integer> counts) {
         ArrayList<String> mostVisits = new ArrayList<String>();
         int maxVisits = mostNumberVisitsByIP(counts);
         for (String ipAddress : counts.keySet()) {
             if (counts.get(ipAddress) == maxVisits) {
                 mostVisits.add(ipAddress);
             }
         }
         return mostVisits;
     }
     
     public HashMap<String, ArrayList<String>> iPsForDays() {
         HashMap<String, ArrayList<String>> ipsByDay = new HashMap<String, ArrayList<String>>();
         for (LogEntry le : records) {
             String date = le.getAccessTime().toString();
             String day = date.substring(4, 10);
             String ipAddress = le.getIpAddress();
             if (!ipsByDay.containsKey(day)) {
                 ipsByDay.put(day, new ArrayList<String>());
             }
             ipsByDay.get(day).add(ipAddress);
         }
         return ipsByDay;
     }
     
     public String dayWithMostIPVisits(HashMap<String, ArrayList<String>> ipsByDay) {
         String mostVisitedDay = "";
         int mostVisits = 0;
         for (String day : ipsByDay.keySet()) {
             int visits = ipsByDay.get(day).size();
             if (visits > mostVisits) {
                 mostVisits = visits;
                 mostVisitedDay = day;
             }
         }
         return mostVisitedDay;
     }
     
     public ArrayList<String> iPsWithMostVisitsOnDay(HashMap<String, ArrayList<String>> ipsByDay, String day) {
         HashMap<String, Integer> counts = new HashMap<String, Integer>();
         ArrayList<String> ips = ipsByDay.get(day);
         if (ips == null) {
             return new ArrayList<String>();
         }
         for (String ipAddress : ips) {
             if (!counts.containsKey(ipAddress)) {
                 counts.put(ipAddress, 1);
             }
             else {
                 counts.put(ipAddress, counts.get(ipAddress) + 1);
             }
         }
         return iPsMostVisits(counts);
     }
     
     
}
