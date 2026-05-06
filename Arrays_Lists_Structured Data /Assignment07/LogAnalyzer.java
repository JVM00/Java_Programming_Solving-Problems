
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
     
     
}
