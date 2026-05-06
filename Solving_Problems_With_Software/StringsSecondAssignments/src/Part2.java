
public class Part2 {

    public int howMany(String stringa, String stringb) {
        if (stringa.isEmpty()) {
            return 0;
        }
        int count = 0;
        int index = 0;
        while (true) {
            int found = stringb.indexOf(stringa, index);
            if (found == -1) {
                break;
            }
            count++;
            index = found + stringa.length();
        }
        return count;
    }

    public void testHowMany() {
        System.out.println(howMany("GAA", "ATGAACGAATTGAATC"));
        System.out.println(howMany("AA", "ATAAAA"));
        System.out.println(howMany("ATG", "ATGATGATG"));
        System.out.println(howMany("CAT", "CATCATG"));
        System.out.println(howMany("XYZ", "ABCDE"));
    }

    public static void main(String[] args) {
        Part2 p = new Part2();
        p.testHowMany();
    }
}
