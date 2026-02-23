import edu.duke.DirectoryResource;
import edu.duke.FileResource;

public class WordPlay {
    public boolean isVowel(char ch) {
        char lower = Character.toLowerCase(ch);
        return lower == 'a' || lower == 'e' || lower == 'i' || lower == 'o' || lower == 'u';
    }

    public String replaceVowels(String phrase, char ch) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < phrase.length(); i++) {
            char current = phrase.charAt(i);
            if (isVowel(current)) {
                sb.append(ch);
            } else {
                sb.append(current);
            }
        }

        return sb.toString();
    }

    public String emphasize(String phrase, char ch) {
        StringBuilder sb = new StringBuilder();
        char target = Character.toLowerCase(ch);

        for (int i = 0; i < phrase.length(); i++) {
            char current = phrase.charAt(i);

            if (Character.toLowerCase(current) == target) {
                if ((i + 1) % 2 == 1) {
                    sb.append('*');
                } else {
                    sb.append('+');
                }
            } else {
                sb.append(current);
            }
        }

        return sb.toString();
    }

    public void test() {
        System.out.println("isVowel('F') -> " + isVowel('F')); // false
        System.out.println("isVowel('a') -> " + isVowel('a')); // true

        System.out.println("replaceVowels(\"Hello World\", '*') -> "
                + replaceVowels("Hello World", '*'));

        System.out.println("emphasize(\"dna ctgaaactga\", 'a') -> "
                + emphasize("dna ctgaaactga", 'a'));

        System.out.println("emphasize(\"Mary Bella Abracadabra\", 'a') -> "
                + emphasize("Mary Bella Abracadabra", 'a'));
    }

    public static void main(String[] args) {
        WordPlay wp = new WordPlay();
        wp.test();
    }
}
