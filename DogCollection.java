import java.util.ArrayList;

public class DogCollection {

    private ArrayList<Dog> dogs = new ArrayList<>();

    public boolean addDog(Dog dog){
        if(containsDog(dog.getName())){
            return false;
        }
        dogs.add(dog);
        return true;
    }
    public boolean removeDog(String dogName){
        if(!containsDog(dogName)){
            return false;
        } else {
            Dog dogToRemove = getDog(dogName);

            // Kontrollera om hunden har en ägare
            if (dogToRemove.getOwner() != null) {
                return false;
            }

            // Om hunden inte har någon ägare, fortsätt med borttagning
            for (int i = 0; i < dogs.size(); i++) {
                Dog currentDog = dogs.get(i);
                if (currentDog.getName().equals(dogName)) {
                    Owner dogOwner = currentDog.getOwner();
                    if (dogOwner != null) {
                        dogs.remove(currentDog);
                        dogOwner.removeDog(currentDog);
                        return true;
                    } else {
                        dogs.remove(currentDog);
                        return true;
                    }
                }
            }

            return false;
        }
    }

    public boolean removeDog(Dog dogObj){

        if(dogObj.getOwner() != null) {
            return false;
        }
        return removeDog(dogObj.getName());
    }

    public boolean containsDog(String dogName){
        for(Dog currentDog : dogs){
            if (currentDog.getName().equalsIgnoreCase(dogName)){
                return true;
            }
        }
        return false;
    }

    public boolean containsDog(Dog dogObj){

       if(dogObj == null){
            return false;
        }
        return containsDog(dogObj.getName());
    }

    public Dog getDog(String dogName){
        for(Dog dog: dogs){
            if(dog.getName().equalsIgnoreCase(dogName)){
                return dog;
            }
        }
        return null;
    }

    public ArrayList<Dog> getDogs(){
        ArrayList<Dog> sortedDogs = new ArrayList<>(dogs);
        DogSorter.sortDogs(new DogNameComparator(), sortedDogs);
        return sortedDogs;

    }

    public ArrayList<Dog> getDogsWithACertainTailLength(double dogsTailLength) {
        ArrayList<Dog> copyOfDogs = new ArrayList<>();

        for(int i = 0; i < dogs.size(); i++) {
            if(dogs.get(i).getTailLength() >= dogsTailLength){
                copyOfDogs.add(dogs.get(i));
            }
        }
        DogSorter.sortDogs(new DogTailNameComparator(), copyOfDogs);
        return copyOfDogs;
    }
}
