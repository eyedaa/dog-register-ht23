import java.util.ArrayList;

public class Owner implements Comparable<Owner>{

    private String name;
    private ArrayList<Dog> dogs;

    public Owner(String name) {
        // Konstruktorn för Owner-klassen som tar emot ägarens namn
        // och skapar en tom lista av hundar.
        this.name = normalizeToUpperCase(name);// Normaliserar ägarens namn till stora bokstäver.
        this.dogs = new ArrayList<>(); // Skapar en ny tom lista av hundar för ägaren.
    }

    public String getName(){
        return this.name;
    }

    public int compareTo(Owner other){
        // Jämför ägare baserat på deras namn (används för att jämföra ägare vid sortering).
        return name.compareTo(other.getName());
    }

    public boolean addDog(Dog dog){
        // Lägger till en hund i ägarens lista av hundar, om hunden inte redan finns i listan och inte har en ägare.
    if(dog != null && !dogs.contains(dog) && dog.getOwner() == null) { // Kontrollerar om hunden redan finns i listan
        dogs.add(dog);
        dog.setOwner(this); // Sätter ägaren för hunden till den aktuella ägaren.
        return true;
    }
    return false; // Returnerar falskt om hunden redan finns i listan eller har en ägare.

    }

    public boolean removeDog(Dog dog) {
        if (dog == null) {
            return false;
        }

    //När en hund tas bort från ägaren bör den också tas bort från ägarens lista över hundar.
        if (dogs.contains(dog)) {
            dogs.remove(dog); // Tar bort hunden från ägarens lista.
            if (dog.getOwner() == this) {
                dog.setOwner(null); // tar bort huden.

            }
            return true;
        }
        return false; // Returnerar falskt om hunden inte fanns i listan.
    }

    public ArrayList<Dog> getDogs(){
        // Returnerar en kopia av ägarens lista av hundar.
        ArrayList<Dog> sortedDogs = new ArrayList<>(dogs);
        DogSorter.sortDogs(new DogNameComparator(), sortedDogs);
        return sortedDogs;
    }
    private String normalizeToUpperCase(String text){
        // Omvandlar text till stora bokstäver och returnerar den normaliserade strängen.
        return text.toUpperCase();
    }

    @Override
    public String toString(){
        // Returnerar en strängrepresentation av ägaren inklusive dess hundar.
        return "%s %s".formatted(name, getDogs().toString());
    }
}
