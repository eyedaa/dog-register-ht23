import java.util.Comparator;
public class DogNameComparator implements Comparator<Dog> {

    public int compare(Dog first, Dog second ){
        // return  first.getName().compareTo(second.getName());
        int result = first.getName().compareTo(second.getName());

        if (result < 0){
            return -1;
        }
        else if (result > 0){
            return 1;
        }
        else{
            return 0;
        }
    }

}

