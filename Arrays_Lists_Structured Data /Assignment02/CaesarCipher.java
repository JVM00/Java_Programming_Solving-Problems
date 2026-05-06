public class CaesarCipher {
    public String encrypt(String input, int key) {
        StringBuilder encrypted = new StringBuilder();
        int normalizedKey = ((key % 26) + 26) % 26;

        for (int i = 0; i < input.length(); i++) {
            encrypted.append(shiftChar(input.charAt(i), normalizedKey));
        }

        return encrypted.toString();
    }

    public void testCaesar() {
        //int key = 23;
        int key = 15;
        try {
            Class<?> fileResourceClass = Class.forName("edu.duke.FileResource");
            Object fr = fileResourceClass.getDeclaredConstructor().newInstance();
            String message = (String) fileResourceClass.getMethod("asString").invoke(fr);
            String encrypted = encrypt(message, key);
            System.out.println("key is " + key + "\n" + encrypted);
        } catch (Exception ex) {
            System.out.println("edu.duke.FileResource is not available in this runtime.");
            System.out.println("Run testCaesar in BlueJ/Coursera with the Duke library loaded.");
        }
    }

    public String encryptTwoKeys(String input, int key1, int key2) {
        StringBuilder encrypted = new StringBuilder();
        int normalizedKey1 = ((key1 % 26) + 26) % 26;
        int normalizedKey2 = ((key2 % 26) + 26) % 26;

        for (int i = 0; i < input.length(); i++) {
            char current = input.charAt(i);
            if (i % 2 == 0) {
                encrypted.append(shiftChar(current, normalizedKey1));
            } else {
                encrypted.append(shiftChar(current, normalizedKey2));
            }
        }

        return encrypted.toString();
    }

    public void testEncrypt() {
        System.out.println("encrypt(\"FIRST LEGION ATTACK EAST FLANK!\", 23) -> "
                + encrypt("FIRST LEGION ATTACK EAST FLANK!", 23));
                
              System.out.println("encrypt(\"At noon be in the conference room with your hat on for a surprise party. YELL LOUD!\", 15) -> "
                + encrypt("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!", 15));

        System.out.println("encrypt(\"First Legion\", 23) -> "
                + encrypt("First Legion", 23));

        System.out.println("encrypt(\"First Legion\", 17) -> "
                + encrypt("First Legion", 17));
    }

    public void testEncryptTwoKeys() {
        System.out.println("encryptTwoKeys(\"First Legion\", 23, 17) -> "
                + encryptTwoKeys("First Legion", 23, 17));
                
             System.out.println("encryptTwoKeys(\"At noon be in the conference room with your hat on for a surprise party. YELL LOUD!\", 8, 21) -> "
                + encryptTwoKeys("At noon be in the conference room with your hat on for a surprise party. YELL LOUD!", 8, 21));
    }

    private char shiftChar(char ch, int key) {
        if (Character.isUpperCase(ch)) {
            return (char) ('A' + (ch - 'A' + key) % 26);
        }

        if (Character.isLowerCase(ch)) {
            return (char) ('a' + (ch - 'a' + key) % 26);
        }

        return ch;
    }

    public static void main(String[] args) {
        CaesarCipher cc = new CaesarCipher();
        cc.testEncrypt();
        cc.testEncryptTwoKeys();
        // cc.testCaesar(); // Uncomment to choose a file and encrypt it.
    }
}
