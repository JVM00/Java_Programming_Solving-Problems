
public class Part3 {
    public boolean twoOccurrences(String stringa, String stringb) {
        int firstIndex = stringb.indexOf(stringa);
        if (firstIndex == -1) {
            return false;
        }
        int secondIndex = stringb.indexOf(stringa, firstIndex + stringa.length());
        return secondIndex != -1;
    }

    public String lastPart(String stringa, String stringb) {
        int index = stringb.indexOf(stringa);
        if (index == -1) {
            return stringb;
        }
        return stringb.substring(index + stringa.length());
    }

    public void testing() {
        String[][] occurrenceTests = {
            {"by", "A story by Abby Long"},
            {"a", "banana"},
            {"atg", "ctgtatgta"},
            {"xyz", "no match here"},
            {"ana", "banana"}
        };

        for (String[] test : occurrenceTests) {
            String stringa = test[0];
            String stringb = test[1];
            boolean result = twoOccurrences(stringa, stringb);
            System.out.println("twoOccurrences(\"" + stringa + "\", \"" + stringb + "\") = " + result);
        }

        String[][] lastPartTests = {
            {"an", "banana"},
            {"zoo", "forest"},
            {"by", "A story by Abby Long"},
            {"atg", "ctgtatgta"}
        };

        for (String[] test : lastPartTests) {
            String stringa = test[0];
            String stringb = test[1];
            String result = lastPart(stringa, stringb);
            System.out.println("The part of the string after " + stringa + " in " + stringb + " is " + result + ".");
        }
    }

    public static void main(String[] args) {
        Part3 p = new Part3();
        p.testing();
    }
}
