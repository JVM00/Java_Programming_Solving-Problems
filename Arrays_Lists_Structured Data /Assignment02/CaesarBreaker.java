
public class CaesarBreaker {
    public int[] countLetters(String message) {
        int[] counts = new int[26];
        String alphabet = "abcdefghijklmnopqrstuvwxyz";

        for (int i = 0; i < message.length(); i++) {
            char ch = Character.toLowerCase(message.charAt(i));
            int index = alphabet.indexOf(ch);
            if (index != -1) {
                counts[index]++;
            }
        }

        return counts;
    }

    public int maxIndex(int[] values) {
        int maxIdx = 0;
        for (int i = 1; i < values.length; i++) {
            if (values[i] > values[maxIdx]) {
                maxIdx = i;
            }
        }
        return maxIdx;
    }

    public String decrypt(String encrypted) {
        int[] freqs = countLetters(encrypted);
        int maxDex = maxIndex(freqs);
        int key = maxDex - 4;
        if (maxDex < 4) {
            key = 26 - (4 - maxDex);
        }

        CaesarCipher cc = new CaesarCipher();
        String message = cc.encrypt(encrypted, 26 - key);
        return message;
    }

    public void testDecrypt() {
        String original = "Here is a simple example sentence where we see several letters repeated, especially the letter e.";
        int key = 15;
        CaesarCipher cc = new CaesarCipher();
        String encrypted = cc.encrypt(original, key);
        System.out.println("Encrypted (key " + key + "): " + encrypted);
        System.out.println("Decrypted: " + decrypt(encrypted));
    }

    public String halfOfString(String message, int start) {
        StringBuilder half = new StringBuilder();
        for (int i = start; i < message.length(); i += 2) {
            half.append(message.charAt(i));
        }
        return half.toString();
    }

    public int getKey(String s) {
        int[] freqs = countLetters(s);
        int maxDex = maxIndex(freqs);
        int key = maxDex - 4;
        if (maxDex < 4) {
            key = 26 - (4 - maxDex);
        }
        return key;
    }

    private int englishScore(String message) {
        String lower = message.toLowerCase();
        String[] words = lower.split("[^a-z]+");
        String[] common = {
            "the", "and", "that", "to", "of", "in", "is", "for", "you", "as",
            "run", "away", "fast", "can", "have", "with"
        };

        int score = 0;
        for (String word : words) {
            if (word.isEmpty()) {
                continue;
            }
            for (String c : common) {
                if (word.equals(c)) {
                    score += 4;
                }
            }
            if (word.length() >= 3) {
                score += 1;
            }
        }

        int vowels = 0;
        int letters = 0;
        for (int i = 0; i < lower.length(); i++) {
            char ch = lower.charAt(i);
            if (ch >= 'a' && ch <= 'z') {
                letters++;
                if ("aeiou".indexOf(ch) != -1) {
                    vowels++;
                }
            }
        }
        if (letters > 0) {
            double ratio = (double) vowels / letters;
            if (ratio >= 0.28 && ratio <= 0.48) {
                score += 3;
            }
        }

        return score;
    }

    private int[] recoverTwoKeys(String encrypted) {
        String firstHalf = halfOfString(encrypted, 0);
        String secondHalf = halfOfString(encrypted, 1);
        CaesarCipher cc = new CaesarCipher();

        int freqKey1 = getKey(firstHalf);
        int freqKey2 = getKey(secondHalf);
        String freqDecrypted = cc.encryptTwoKeys(encrypted, 26 - freqKey1, 26 - freqKey2);
        int bestScore = englishScore(freqDecrypted);
        int bestKey1 = freqKey1;
        int bestKey2 = freqKey2;

        for (int k1 = 0; k1 < 26; k1++) {
            for (int k2 = 0; k2 < 26; k2++) {
                String candidate = cc.encryptTwoKeys(encrypted, 26 - k1, 26 - k2);
                int score = englishScore(candidate);
                if (score > bestScore) {
                    bestScore = score;
                    bestKey1 = k1;
                    bestKey2 = k2;
                }
            }
        }
        return new int[]{bestKey1, bestKey2};
    }

    public String decryptTwoKeys(String encrypted) {
        int[] keys = recoverTwoKeys(encrypted);
        int bestKey1 = keys[0];
        int bestKey2 = keys[1];
        CaesarCipher cc = new CaesarCipher();
        String bestMessage = cc.encryptTwoKeys(encrypted, 26 - bestKey1, 26 - bestKey2);

        System.out.println("Key 1: " + bestKey1);
        System.out.println("Key 2: " + bestKey2);
        return bestMessage;
    }

    public void decryptTwoKeys_file() {
        try {
            Class<?> fileResourceClass = Class.forName("edu.duke.FileResource");
            Object fr = fileResourceClass.getDeclaredConstructor().newInstance();
            String encrypted = (String) fileResourceClass.getMethod("asString").invoke(fr);

            int[] keys = recoverTwoKeys(encrypted);
            int key1 = keys[0];
            int key2 = keys[1];

            CaesarCipher cc = new CaesarCipher();
            String decrypted = cc.encryptTwoKeys(encrypted, 26 - key1, 26 - key2);
            java.nio.file.Path out = java.nio.file.Paths.get("decrypted_two_keys_output.txt");
            java.nio.file.Files.writeString(out, decrypted);

            System.out.println("Recovered Key 1: " + key1);
            System.out.println("Recovered Key 2: " + key2);
            System.out.println("Decrypted text saved to: " + out.toAbsolutePath());
        } catch (ClassNotFoundException ex) {
            System.out.println("edu.duke.FileResource is not available in this runtime.");
            System.out.println("Run decryptTwoKeys_file in BlueJ/Coursera with Duke library loaded.");
        } catch (Exception ex) {
            System.out.println("Could not decrypt/write file: " + ex.getMessage());
        }
    }

    public void testHalfOfString() {
        String sample = "Qbkm Zgis";
        System.out.println("halfOfString(\"Qbkm Zgis\", 0): " + halfOfString(sample, 0));
        System.out.println("halfOfString(\"Qbkm Zgis\", 1): " + halfOfString(sample, 1));
    }

    public void testDecryptTwoKeys() {
        //String encrypted = "Gwpv c vbuq pvokki yfve iqqu qc bgbgbgbgbgbgbgbgbu";
        String encrypted = "Akag tjw Xibhr awoa aoee xakex znxag xwko";
        //String encrypted = "Top ncmy qkff vi vguv vbg ycpx";
        
        System.out.println("Encrypted: " + encrypted);
        System.out.println("Decrypted: " + decryptTwoKeys(encrypted));
        
    }

    public static void main(String[] args) {
        CaesarBreaker cb = new CaesarBreaker();
        cb.testDecrypt();
        cb.testHalfOfString();
        cb.testDecryptTwoKeys();
        // cb.decryptTwoKeys_file(); // Uncomment in BlueJ/Coursera to choose an input file and write output.
    }
}
