import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class OwnerCollection {
    private  Owner[] owners = new Owner[0];

    public boolean addOwner(Owner owner){
        if(containsOwner(owner.getName())){
            return false;

        }
        else{
            owners = Arrays.copyOf(owners, owners.length +1);
            owners[owners.length - 1] = owner;
            return true;
        }

    }
    public boolean removeOwner(String ownerName){
        if(!containsOwner(ownerName)){
            return false;
        } else {
            Owner ownerToRemove = getOwner(ownerName);

            // Kontrollera om ägaren har hundar
            if (!ownerToRemove.getDogs().isEmpty()) {
                return false;
            }

            // Om ägaren inte har några hundar, fortsätt med borttagning
            Owner[] ownerCopy = new Owner[owners.length - 1];
            int counter = 0;

            for (int i = 0; i < owners.length; i++) {
                if (!owners[i].getName().equalsIgnoreCase(ownerName)) {
                    ownerCopy[counter] = owners[i];
                    counter++;
                }
            }
            owners = ownerCopy;
            return true;
        }
    }

    public boolean removeOwner(Owner ownerObj){
        if (!ownerObj.getDogs().isEmpty()) {
            return false;
        }
        return removeOwner(ownerObj.getName());
    }

    public boolean containsOwner(String ownerName) {
        for(Owner currentOwner : owners) {
            if(currentOwner.getName().equalsIgnoreCase(ownerName)){
               return true;
            }
        }

        return false;
    }

    public boolean containsOwner(Owner ownerObj){
        if(ownerObj == null){
            return false;
        }
        return containsOwner(ownerObj.getName());

    }
    public Owner getOwner(String ownerName) {
        if (containsOwner(ownerName)) {
            for (Owner currentOwner : owners) {
                if (currentOwner.getName().equalsIgnoreCase(ownerName)) {
                    return currentOwner;
                }
            }
        }
        return null;
    }

    public ArrayList<Owner> getOwners() {
        ArrayList<Owner> ownerList = new ArrayList<>(Arrays.asList(owners));

        Collections.sort(ownerList);
        return ownerList;
    }
}
