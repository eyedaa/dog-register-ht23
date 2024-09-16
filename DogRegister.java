//Ida Åhlander idah8312
import java.util.ArrayList;
import java.util.Collections;

// Klassen som hanterar hundregistret
public class DogRegister {

    // Definierar konstanter för olika kommandon

    private static final String REGISTER_NEW_OWNER = "REGISTER NEW OWNER";
    private static final String REMOVE_OWNER = "REMOVE OWNER";
    private static final String REGISTER_NEW_DOG = "REGISTER NEW DOG";
    private static final String REMOVE_DOG = "REMOVE DOG";
    private static final String LIST_DOG = "LIST DOGS";
    private static final String LIST_OWNERS = "LIST OWNERS";
    private static final String INCREASE_AGE = "INCREASE AGE";
    private static final String GIVE_DOG_TO_OWNER = "GIVE DOG TO OWNER";
    private static final String REMOVE_DOG_FROM_OWNER = "REMOVE DOG FROM OWNER";
    private static final String EXIT = "EXIT";

    // Instanser för att lagra ägare och hundar
    private OwnerCollection owners = new OwnerCollection();
    private DogCollection dogs = new DogCollection();


    // Används för att läsa input
    private InputReader input = new InputReader(System.in);

    public static void main(String[] args) {
        new DogRegister().start();
    }

    // Startar programmet
    private void start() {
        initialize();
        runCommandLoop();

    }

    // Initialiserar programmet
    private void initialize() {

        System.out.println("Initializing program");
    }

    // Loop för att läsa och hantera kommandon tills "EXIT" anges
    private void runCommandLoop() {
        System.out.println("Running command loop");

        String command;
        do {
            command = readCommand().trim().toUpperCase();
            handleCommand(command);
        } while (!command.equals(EXIT));

    }


    // Läser in användarkommandon
    private String readCommand() {
        //System.out.println("Enter command?>");

        return input.readString("Enter command");

    }

    // Hanterar de olika kommandona baserat på input
    private void handleCommand(String command) {
        // En switch-sats som utför olika åtgärder beroende på det angivna kommandot
        switch (command) {
            case REGISTER_NEW_OWNER:
                registerNewOwner();
                break;
            case REMOVE_OWNER:
                removeOwner();
                break;
            case REGISTER_NEW_DOG:
                registerNewDog();

                break;
            case REMOVE_DOG:
                removeDog();

                break;
            case LIST_DOG:
                listDogs();

                break;
            case LIST_OWNERS:
                listOwners();

                break;
            case INCREASE_AGE:
                increaseAge();

                break;
            case GIVE_DOG_TO_OWNER:
                giveDogToOwner();

                break;
            case REMOVE_DOG_FROM_OWNER:
                removeDogFromOwner();

                break;
            case EXIT:
                shutDown();
                break;
            default:
                System.out.println("Error");
        }
    }

    // Metod för att registrera en ny ägare
    private void registerNewOwner() {

            String ownerName = validateAndRetrieveOwnerName();
            if (ownerName != null) {
                System.out.println("The owner " + ownerName + " has been added to the register");
                owners.addOwner(new Owner(ownerName));
                }
            }

    // Metod för att ta bort en ägare från registret
    private void removeOwner() {
        // Kontrollerar om det inte finns några ägare i registret och avslutar om så är fallet
        if (ownerRegisterIsEmpty()) {
            return;
        }

        // Läser in namnet på ägaren som ska tas bort från användaren
        String removeName = input.readString("Enter owner name");
        // Loopar tills ett giltigt ägarnamn anges, hanterar tomma strängar
        while (checkIfStringIsBlank(removeName)){
            removeName = input.readString("Enter owner name");
        }

        // Kontrollerar om ägaren finns i registret
        if (!owners.containsOwner(removeName)) {
            System.out.println("Error: the owner " + removeName + " does not exist");
            return;
        }
        // Hämtar ägaren som ska tas bort från registret
        Owner owner = owners.getOwner(removeName);
        // Om ägaren har hundar, ta bort dem från ägaren och från hundregistret
        if (!owner.getDogs().isEmpty()) {
            for (Dog dog : owner.getDogs()) {
                owner.removeDog(dog); // Tar bort hunden från ägaren
                dogs.removeDog(dog); // Tar bort hunden från hundregistret
            }
        }
        // Tar bort ägaren från ägarregistret
        owners.removeOwner(removeName);
        System.out.println("The owner " + removeName + " has been removed from the register");
    }

    private void registerNewDog() {
        // Kontrollerar om hundnamnet är giltigt
        String dogName = getValidDogName();
        if (dogName == null) {
            return; // Avslutar om hundnamnet inte är giltigt
        }

        // Läser in hundens ras, ålder och vikt från användaren
        String dogBreed = inputStringUntilValid("Enter dogs breed");
        int dogAge = input.readInt("Enter dog age");
        int dogWeight = input.readInt("Enter dogs Weight");

        // Skapar en ny hund med angiven information
        Dog dog = new Dog(dogName, dogBreed, dogAge, dogWeight);
        // Lägger till den nya hunden i hundregistret
        dogs.addDog(dog);
        System.out.println("The dog " + dogName + " has been added to the register");

    }

    private void removeDog() {
        // Kontrollerar om det inte finns några hundar i registret och avslutar om så är fallet
        if (noDogInRegister()) {
            return;
        }

        // Läser in namnet på hunden som ska tas bort från användaren
        String removeName = inputStringUntilValid("Enter dog name");

        // Kontrollerar om hunden finns i registret
        if (!dogs.containsDog(removeName)) {
            System.out.println("Error: the dog " + removeName + " does not exist");
            return;
        }
        // Hämtar den hund som ska tas bort från registret
        Dog removeDog = dogs.getDog(removeName);

        // Om hunden har en ägare, ta bort hunden från ägarens lista över hundar och sätt ägaren till null
        if(removeDog.getOwner() != null){
            Owner owner = removeDog.getOwner();
            owner.removeDog(removeDog);
            removeDog.setOwner(null);
        }
        // Hämtar hunden igen (eftersom den kanske redan ändrats om den hade en ägare)
        Dog dog = dogs.getDog(removeName);

        // Försöker ta bort hunden från hundregistret och ger ett felmeddelande om det misslyckas
        boolean removed = dogs.removeDog(dog);
        if (!removed) {
            System.out.println("Error: failed to remove " + removeName + " from the register");
            return;
        }
        System.out.println("The owner " + removeName + " has been removed from the register");
    }

    private void listOwners() {
        // Kontrollerar om det inte finns några ägare i registret och avslutar om så är fallet
        if (ownerRegisterIsEmpty()) {
            return;
        } else {
            // Skapar en kopia av listan med ägare och sorterar den
            ArrayList<Owner> sortedOwner = new ArrayList<>(owners.getOwners());
            Collections.sort(sortedOwner);

            // Skriver ut rubriken för ägare och deras hundar
            System.out.println("\nNAME" + repeatString(" ", 5) + "DOGS");
            System.out.println(repeatString("=", "name".length() + "dogs".length() + 5));

            // Loopar genom sorterade ägare och skriver ut deras information (använder Owner's toString())
            for (Owner owner : sortedOwner) {
                System.out.println(owner.toString());
            }
            System.out.println();
        }
    }

    private void listDogs() {
        double minTailLength = 0;

        // Om det inte finns några hundar i registret avslutas metoden
        if (noDogInRegister()) {
            return;
        } else {
            // Läser in den minimala svanslängden från användaren
            minTailLength = input.readDouble("Enter minimum tail length");

            // Skapar en rubrik för hundinformation och skriver ut den
            String intro = "NAME BREED AGE WEIGHT TAIL OWNER";
            System.out.println("\n" + intro);

            // Skriver ut strecket som separerar rubriken från innehållet
            System.out.println(repeatString("=", intro.length()));
            // Loopar genom hundarna med en svanslängd som är längre än eller lika med den angivna minimala svanslängden
            for (Dog dog : dogs.getDogsWithACertainTailLength(minTailLength)) {
                System.out.println(dog.toString());
            }
            System.out.println();
        }
    }
    private void increaseAge() {
        // Om det inte finns några hundar i registret avslutas metoden
        if (noDogInRegister()) {
            return;
        }
        // Läser in namnet på hunden från användaren och normaliserar till stor bokstav
        String dogName = normalizeToFormat(input.readString("Enter dog name"));
        // Kontrollerar om hunden finns i registret
        if (dogExists(dogName)) {
            Dog dog = dogs.getDog(dogName);
            // Ökar hundens ålder med ett år
            dog.increaseAge(1);
            System.out.println("The dog " + dogName + " is now one year older");
        }
    }

    private void giveDogToOwner() {
        // Om det inte finns några hundar eller ägare i registret avslutas metoden
        if (noDogInRegister() || ownerRegisterIsEmpty()) {
            return;
        }
        //Skapa ny variabler och hämta huden från listan
        String dogName = inputStringUntilValid("Enter dogs name");
        Dog dogToGive = dogs.getDog(dogName);


        Dog dog = dogs.getDog(dogName);
        // Kontrollerar om hunden finns i registret och är inte null
        if (!dogs.containsDog(dogName) || dog == null) {
            System.out.println("Error: The dog " + dogName + " doesn't exist");
            return;
        }
        // Kontrollerar om hunden redan har en ägare
        if (dogToGive.getOwner() != null) {
            System.out.println("Error: The dog " + dogName + " already have an owner");
            return;
        }
        String ownerName = inputStringUntilValid("Enter owner name");
        // Skapa en ny variable med datatypen owner och hämta ägaren från listan.
        Owner ownerToGive = owners.getOwner(ownerName);
        // Kontrollerar om ägaren finns i registret
        if (!owners.containsOwner(ownerName)) {
            System.out.println("Error: The owner " + ownerName + " doesn't exist");
            return;
        }
             else {
                // Här sätter jag ägaren till hunden.
                dogToGive.setOwner(ownerToGive);
                ownerToGive.addDog(dogToGive);
                System.out.println("The dog " + dogToGive.getName() + " is now owned by " + ownerToGive.getName());
            }
        }


    private void removeDogFromOwner() {
        // Om det inte finns några hundar i registret avslutas metoden
        if (noDogInRegister()) {
            return;
        }
        // Om det inte finns några ägare i registret avslutas metoden
        if (ownerRegisterIsEmpty()) {
            return;
        }
        String dogName = inputStringUntilValid("Enter dog name");
        // Kontrollerar om hunden finns i registret
        if (!dogs.containsDog(dogName)) {
            System.out.println("Error: The dog " + dogName + " doesn't exist");
            return;
        }
        // Hämtar hunden som ska tas bort från registret och dess ägare
        Dog dogToRemove = dogs.getDog(dogName);
        Owner ownerOfdog = dogToRemove.getOwner();

        // Tar bort hunden från ägarens lista över hundar och sätter ägaren till null
        ownerOfdog.removeDog(dogToRemove);
        dogToRemove.setOwner(null);
        System.out.println("The dog " + dogName + " already has no owner");
    }

    private void shutDown(){
        System.out.println("Dog register shut down");
    }

    private String validateAndRetrieveOwnerName() {
        while (true) {
            String ownerName = input.readString("Enter owners name: ");
            if (ownerName.trim().isEmpty()) {
                System.out.println("Error: A blank string is not allowed, try again ");
            }
            else if (owners.containsOwner(ownerName)) {
                System.out.println("Error: The owner " + ownerName + "already exist");
                return null;
            } else {
                return ownerName;
            }
        }
    }

    private String getValidDogName() {
        while (true) {
            String dogName = input.readString("Enter dog name:");
            if (dogName.trim().isEmpty()) {
                System.out.println("Error: A blank string is not allowed, try again ");
            }
            else if (dogs.containsDog(dogName)) {
                System.out.println("Error: The dog " + dogName + "already exist");
                return null;
            } else {
                return dogName;
            }
        }
    }
    private boolean checkIfStringIsBlank(String text) {

        if (text == null || text.isEmpty()) {
            System.out.println("Error: A blank string is not allowed, try again ");
            return true;
        } else {
            return false;
        }
    }

    private String inputStringUntilValid(String prompt) {
        while (true) {
            String result = normalizeToFormat(input.readString(prompt).trim());
            if (!checkIfStringIsBlank(result)) {
                return result;
            }
        }
    }

    private boolean dogExists(String dogName) {
        dogName = normalizeToFormat(dogName);
        if (dogs.containsDog(dogName)) {
            return true;
        } else {
            System.out.println("Error: The dog " + dogName + " does not exist");
            return false;
        }
    }

    private boolean noDogInRegister() {
        if (dogs.getDogs().isEmpty()) {
            System.out.println("Error: No dog in register");
            return true;
        }
        return false;
    }

    private boolean ownerRegisterIsEmpty() {
        if (owners.getOwners().isEmpty()) {
            System.out.println("Error: No owner in register");
            return true;
        }
        return false;
    }

    private String repeatString(String string, int times) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < times + 1; i++) {
            stringBuilder.append(string);
        }
        return stringBuilder.toString();
    }

    private String normalizeToFormat(String text) {
        return text.toUpperCase();
    }
}
