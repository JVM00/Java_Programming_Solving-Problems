
import edu.duke.URLResource;

public class Part4 {
    public void findURLs() {
        URLResource resource = new URLResource("http://www.dukelearntoprogram.com/course2/data/manylinks.html");
        for (String word : resource.words()) {
            String lowerWord = word.toLowerCase();
            int youtubeIndex = lowerWord.indexOf("youtube.com");
            if (youtubeIndex != -1) {
                int beginIndex = word.lastIndexOf("\"", youtubeIndex);
                int endIndex = word.indexOf("\"", youtubeIndex);
                if (beginIndex != -1 && endIndex != -1 && endIndex > beginIndex) {
                    String url = word.substring(beginIndex + 1, endIndex);
                    System.out.println(url);
                }
            }
        }
    }

    public static void main(String[] args) {
        Part4 p = new Part4();
        p.findURLs();
    }
}
