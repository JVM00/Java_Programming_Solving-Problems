
import edu.duke.*;
import java.util.ArrayList;

public class CharactersInPlay {
    private ArrayList<String> names;
    private ArrayList<Integer> counts;

    public CharactersInPlay() {
        names = new ArrayList<String>();
        counts = new ArrayList<Integer>();
    }

    public void update(String person) {
        int index = names.indexOf(person);

        if (index == -1) {
            names.add(person);
            counts.add(1);
        } else {
            counts.set(index, counts.get(index) + 1);
        }
    }

    public void findAllCharacters() {
        names.clear();
        counts.clear();

        FileResource fr = new FileResource();
        for (String line : fr.lines()) {
            int periodIndex = line.indexOf(".");
            if (periodIndex != -1) {
                String person = line.substring(0, periodIndex).trim();
                if (!person.isEmpty()) {
                    update(person);
                }
            }
        }
    }

    public void charactersWithNumParts(int num1, int num2) {
        for (int i = 0; i < names.size(); i++) {
            int count = counts.get(i);
            if (count >= num1 && count <= num2) {
                System.out.println(names.get(i) + "\t" + count);
            }
        }
    }

    public void printTopCharacters(int howMany) {
        ArrayList<Integer> usedIndexes = new ArrayList<Integer>();
        int limit = Math.min(howMany, names.size());

        for (int rank = 0; rank < limit; rank++) {
            int bestIndex = -1;

            for (int i = 0; i < counts.size(); i++) {
                if (usedIndexes.contains(i)) {
                    continue;
                }

                if (bestIndex == -1 || counts.get(i) > counts.get(bestIndex)) {
                    bestIndex = i;
                }
            }

            if (bestIndex != -1) {
                usedIndexes.add(bestIndex);
                System.out.println(names.get(bestIndex) + "\t" + counts.get(bestIndex));
            }
        }
    }

    public void tester() {
        findAllCharacters();
        System.out.println("Main characters with speaking parts between 10 and 15:");
        charactersWithNumParts(10, 15);
        //charactersWithNumParts(10, 15);
        System.out.println("First 5 main characters with the most speaking parts:");
        printTopCharacters(5);
    }
}
