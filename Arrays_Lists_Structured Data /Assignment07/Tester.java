
/**
 * Write a description of class Tester here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.*;

public class Tester
{
    public void testLogEntry() {
        LogEntry le = new LogEntry("1.2.3.4", new Date(), "example request", 200, 500);
        System.out.println(le);
        LogEntry le2 = new LogEntry("1.2.100.4", new Date(), "example request 2", 300, 400);
        System.out.println(le2);
    }
    
    public void testLogAnalyzer() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        la.printAll();
    }
    
    public void testUniqueIP() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        int uniqueIPs = la.countUniqueIPs();
        System.out.println("There are " + uniqueIPs + " unique IPs");
    }
    
    public void testPrintAllHigherThanNum() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("short-test_log");
        la.printAllHigherThanNum(400);
    }
    
    public void testRunPrintAllHigherThan400() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog1_log");
        la.printAllHigherThanNum(400);
    }
    
    public void testStatusCodesHigherThan400() {
        LogAnalyzer la = new LogAnalyzer();
        ArrayList<Integer> statusCodes = la.uniqueStatusCodesHigherThanNum("weblog1_log", 400);
        System.out.println("Status codes higher than 400: " + statusCodes);
    }
    
    public void testPrintAllHigherThan400WithStatusCodes() {
        LogAnalyzer la = new LogAnalyzer();
        la.printAllHigherThanNumWithStatusCodes("weblog1_log", 400);
    }
    
    public void testUniqueIPVisitsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        ArrayList<String> uniqueIPsSep14 = la.uniqueIPVisitsOnDay("weblog-short_log", "Sep 14");
        System.out.println(uniqueIPsSep14);
        
        ArrayList<String> uniqueIPsSep30 = la.uniqueIPVisitsOnDay("weblog-short_log", "Sep 30");
        System.out.println(uniqueIPsSep30);
    }
    
    public void testUniqueIPVisitsOnMar24() {
        LogAnalyzer la = new LogAnalyzer();
        ArrayList<String> uniqueIPs = la.uniqueIPVisitsOnDay("weblog1_log", "Mar 24");
        System.out.println(uniqueIPs);
    }
    
    public void testCountUniqueIPsInRange() {
        LogAnalyzer la = new LogAnalyzer();
        System.out.println("Unique IPs with status 200-299: " + la.countUniqueIPsInRange("short-test_log", 200, 299));
        System.out.println("Unique IPs with status 300-399: " + la.countUniqueIPsInRange("short-test_log", 300, 399));
    }
    
    public void testCountUniqueIPsInRangeWeblog1() {
        LogAnalyzer la = new LogAnalyzer();
        System.out.println("Unique IPs with status 300-399: " + la.countUniqueIPsInRange("weblog1_log", 300, 399));
    }
    
    public void testCountVisitsPerIP() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, Integer> counts = la.countVisitsPerIP();
        System.out.println(counts);
    }
    
    public void testMostNumberVisitsByIP() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, Integer> counts = la.countVisitsPerIP();
        int maxVisits = la.mostNumberVisitsByIP(counts);
        System.out.println("Most visits by one IP: " + maxVisits);
    }
    
    public void testIPsMostVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, Integer> counts = la.countVisitsPerIP();
        ArrayList<String> ips = la.iPsMostVisits(counts);
        System.out.println("IPs with most visits: " + ips);
    }
    
    public void testIPsForDays() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, ArrayList<String>> ipsByDay = la.iPsForDays();
        System.out.println(ipsByDay);
    }
    
    public void testDayWithMostIPVisits() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, ArrayList<String>> ipsByDay = la.iPsForDays();
        String day = la.dayWithMostIPVisits(ipsByDay);
        System.out.println("Day with most IP visits: " + day);
    }
    
    public void testIPsWithMostVisitsOnDay() {
        LogAnalyzer la = new LogAnalyzer();
        la.readFile("weblog3-short_log");
        HashMap<String, ArrayList<String>> ipsByDay = la.iPsForDays();
        ArrayList<String> ips = la.iPsWithMostVisitsOnDay(ipsByDay, "Sep 30");
        System.out.println("IPs with most visits on Sep 30: " + ips);
    }
}
