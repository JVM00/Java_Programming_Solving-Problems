import edu.duke.*;

public class TestCaesarCipherTwo {
    public String halfOfString(String message, int start) {
        StringBuilder half = new StringBuilder();
        for (int i = start; i < message.length(); i += 2) {
            half.append(message.charAt(i));
        }
        return half.toString();
    }

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

    private int getKey(String s) {
        int[] freqs = countLetters(s);
        int maxDex = maxIndex(freqs);
        int key = maxDex - 4;
        if (maxDex < 4) {
            key = 26 - (4 - maxDex);
        }
        return key;
    }

    public String breakCaesarCipher(String input) {
        String half0 = halfOfString(input, 0);
        String half1 = halfOfString(input, 1);

        int key1 = getKey(half0);
        int key2 = getKey(half1);

        CaesarCipherTwo cc = new CaesarCipherTwo(key1, key2);
        return cc.decrypt(input);
    }

    public void simpleTests() {
        FileResource fr = new FileResource();
        String input = fr.asString();

        CaesarCipherTwo cc = new CaesarCipherTwo(17, 3);
        String encrypted = cc.encrypt(input);
        System.out.println("Encrypted text:\n" + encrypted);

        String decrypted = cc.decrypt(encrypted);
        System.out.println("\nDecrypted with known keys:\n" + decrypted);

        String autoDecrypted = breakCaesarCipher(encrypted);
        System.out.println("\nAutomatically decrypted:\n" + autoDecrypted);
    }

    public static void main(String[] args) {
        TestCaesarCipherTwo tester = new TestCaesarCipherTwo();
        tester.simpleTests();
    }
}
