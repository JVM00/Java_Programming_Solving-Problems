import edu.duke.*;

public class TestCaesarCipher {
    public int[] countLetters(String message) {
        String alph = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];

        for (int i = 0; i < message.length(); i++) {
            char ch = Character.toLowerCase(message.charAt(i));
            int index = alph.indexOf(ch);
            if (index != -1) {
                counts[index]++;
            }
        }
        return counts;
    }

    public int maxIndex(int[] vals) {
        int maxDex = 0;
        for (int k = 0; k < vals.length; k++) {
            if (vals[k] > vals[maxDex]) {
                maxDex = k;
            }
        }
        return maxDex;
    }

    public String breakCaesarCipher(String input) {
        int[] freqs = countLetters(input);
        int maxDex = maxIndex(freqs);
        int key = maxDex - 4;
        if (maxDex < 4) {
            key = 26 - (4 - maxDex);
        }

        CaesarCipher cc = new CaesarCipher(key);
        return cc.decrypt(input);
    }

    public void simpleTests() {
        FileResource fr = new FileResource();
        String input = fr.asString();

        CaesarCipher cc = new CaesarCipher(18);
        String encrypted = cc.encrypt(input);
        System.out.println("Encrypted text:\n" + encrypted);

        String decrypted = cc.decrypt(encrypted);
        System.out.println("\nDecrypted with known key:\n" + decrypted);

        String autoDecrypted = breakCaesarCipher(encrypted);
        System.out.println("\nAutomatically decrypted:\n" + autoDecrypted);
    }

    public static void main(String[] args) {
        TestCaesarCipher tester = new TestCaesarCipher();
        tester.simpleTests();
    }
}
