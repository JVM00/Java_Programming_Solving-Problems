import java.util.*;
import edu.duke.*;

public class VigenereBreaker {
    public String sliceString(String message, int whichSlice, int totalSlices) {
        StringBuilder slice = new StringBuilder();
        for (int i = whichSlice; i < message.length(); i += totalSlices) {
            slice.append(message.charAt(i));
        }
        return slice.toString();
    }

    public int[] tryKeyLength(String encrypted, int klength, char mostCommon) {
        int[] key = new int[klength];
        CaesarCracker cracker = new CaesarCracker(mostCommon);
        for (int i = 0; i < klength; i++) {
            String slice = sliceString(encrypted, i, klength);
            key[i] = cracker.getKey(slice);
        }
        return key;
    }

    public HashSet<String> readDictionary(FileResource fr) {
        HashSet<String> dictionary = new HashSet<String>();
        for (String line : fr.lines()) {
            dictionary.add(line.toLowerCase());
        }
        return dictionary;
    }

    public int countWords(String message, HashSet<String> dictionary) {
        int count = 0;
        String[] words = message.split("\\W+");
        for (String word : words) {
            if (dictionary.contains(word.toLowerCase())) {
                count++;
            }
        }
        return count;
    }

    public char mostCommonCharIn(HashSet<String> dictionary) {
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        int[] counts = new int[26];
        for (String word : dictionary) {
            for (int i = 0; i < word.length(); i++) {
                int index = alphabet.indexOf(word.charAt(i));
                if (index != -1) {
                    counts[index]++;
                }
            }
        }

        int maxIndex = 0;
        for (int i = 0; i < counts.length; i++) {
            if (counts[i] > counts[maxIndex]) {
                maxIndex = i;
            }
        }
        return alphabet.charAt(maxIndex);
    }

    public String breakForLanguage(String encrypted, HashSet<String> dictionary) {
        int maxValidWords = 0;
        String bestDecryption = "";
        char mostCommon = mostCommonCharIn(dictionary);
        for (int klength = 1; klength <= 100; klength++) {
            int[] key = tryKeyLength(encrypted, klength, mostCommon);
            VigenereCipher cipher = new VigenereCipher(key);
            String decrypted = cipher.decrypt(encrypted);
            int validWords = countWords(decrypted, dictionary);
            if (validWords > maxValidWords) {
                maxValidWords = validWords;
                bestDecryption = decrypted;
            }
        }
        return bestDecryption;
    }

    public void breakForAllLangs(String encrypted, HashMap<String, HashSet<String>> languages) {
        int maxValidWords = 0;
        String bestLanguage = "";
        String bestDecryption = "";
        for (String language : languages.keySet()) {
            HashSet<String> dictionary = languages.get(language);
            String decrypted = breakForLanguage(encrypted, dictionary);
            int validWords = countWords(decrypted, dictionary);
            System.out.println(language + ": " + validWords + " valid words");
            if (validWords > maxValidWords) {
                maxValidWords = validWords;
                bestLanguage = language;
                bestDecryption = decrypted;
            }
        }
        System.out.println("Best language: " + bestLanguage);
        System.out.println(bestDecryption);
    }

    public void breakVigenere () {
        FileResource fr = new FileResource();
        String encrypted = fr.asString();
        HashMap<String, HashSet<String>> languages = new HashMap<String, HashSet<String>>();
        String[] languageNames = {
            "Danish", "Dutch", "English", "French",
            "German", "Italian", "Portuguese", "Spanish"
        };
        for (String language : languageNames) {
            System.out.println("Reading " + language + " dictionary");
            FileResource dictionaryResource = new FileResource("dictionaries/" + language);
            languages.put(language, readDictionary(dictionaryResource));
        }
        breakForAllLangs(encrypted, languages);
    }
    
}
