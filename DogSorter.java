import java.util.ArrayList;
import java.util.Comparator;

public class DogSorter {

    public static int sortDogs(Comparator<Dog> comparator,  ArrayList<Dog> dogList){

        int numSwaps = 0;
        for(int i = 0; i < dogList.size() -1 ; i++){

            int currentMinIndex = nextDog(comparator, dogList, i);
            if(currentMinIndex != i){
                swapDogs(dogList, i, currentMinIndex);
                numSwaps++;
            }
        }
        return numSwaps;
    }
    private static int nextDog(Comparator<Dog> comparator, ArrayList<Dog> dogList, int startIndex){
       int currentMinIndex = startIndex;

       for(int j = startIndex + 1; j < dogList.size(); j++){
           if (comparator.compare(dogList.get(j), dogList.get(currentMinIndex)) < 0){
               currentMinIndex = j;
           }
       }
       return currentMinIndex;
    }

    private static void swapDogs(ArrayList<Dog> dogList, int dogIndexOne, int dogIndexTwo){
        Dog tempDog = dogList.get(dogIndexTwo);
        dogList.set(dogIndexTwo, dogList.get(dogIndexOne));
        dogList.set(dogIndexOne, tempDog);
    }
}
