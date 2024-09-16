import java.util.ArrayList;
import java.util.Arrays;

public class Dog {

    private static final double DACHSHUND_TAIL_LENGTH = 3.7d;
    private static final double TAIL_LENGTH_DENOMINATOR = 10.0;
    private static final double MAX_YEARS_FOR_A_DOG = 100;

    private String name;
    private String breed;
    private int age;
    private int weight;
    private Owner owner;

    public Dog(String name, String breed, int age, int weight){
        this.name = normalizeToUpperCase(name);
        this.breed = normalizeToUpperCase(breed);
        this.age = age;
        this.weight = weight;
    }

    public String getName(){
        return name;
    }
    public String getBreed(){
        return breed;
    }
    public int getAge(){
        return age;
    }
    public int getWeight(){
        return weight;
    }

    public double getTailLength(){
        ArrayList<String> dogBreeds = new ArrayList<>(Arrays.asList("TAX", "DACHSHUND", "MÄYRÄKOIRA", "TECKEL"));
        if(dogBreeds.contains(breed)){
            return DACHSHUND_TAIL_LENGTH;
        }
        else {
            double taillength =  (age * weight) / TAIL_LENGTH_DENOMINATOR;
            return taillength;
        }
    }

    public void increaseAge(int years){
    if(years >= 0 && age <= MAX_YEARS_FOR_A_DOG){
            age += years;
        }
    }

    public Owner getOwner(){
        return this.owner;
    }

    public boolean setOwner(Owner newOwner) {

        // Ta bort hunden då newOwner är null
        if (newOwner == null) {
            // Om hunden har en ägare
            if (owner != null) {
                // Ta bort hunden ifrån ägaren
                owner.removeDog(this);
            }
            // Sätt att hunden inte har någon ägare
            owner = null;
            return true;
        }

        // Om det inte finns någon ägare
        else if (owner == null) {
            newOwner.addDog(this);
            owner = newOwner;
            return true;
        }

        return false;
    }

    private String normalizeToUpperCase(String text){
        return text.toUpperCase();
    }

    public String toString(){
        String ownerName = "";
        if (owner != null) {
            ownerName = this.getOwner().getName();
        }

        return "%s %s %d %d %.2f %s".formatted(name, breed, age, weight, getTailLength(), ownerName);

    }
}
