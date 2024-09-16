import java.util.Comparator;

public class DogTailNameComparator implements Comparator<Dog> {

    private final DogNameComparator dogName = new DogNameComparator();
    private final DogTailComparator dogTail = new DogTailComparator();

    // Jämför först hundarnas svanslängd med hjälp av DogTailComparator.
    // Om svanslängden är olika returneras antingen -1 (för mindre) eller 1 (för större).
    // Om svanslängden är densamma, jämförs hundarnas namn med DogNameComparator.
    // Returnerar -1, 1 eller 0 beroende på hur namnen jämförs.


    public int compare(Dog first, Dog second ) {
        int tailResult = dogTail.compare(first, second);

        if(tailResult == -1 ) {
            return -1;
        }
        else if(tailResult == 1){
            return 1;
        }


        int nameResult = dogName.compare(first, second);

        if(nameResult == -1){
            return -1;
        }
        else if(nameResult == 1){
            return 1;
        }
        else if(nameResult == 0){
            return 0;
        }

        return tailResult;
    }
}
