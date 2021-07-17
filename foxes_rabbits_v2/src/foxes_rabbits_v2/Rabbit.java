package foxes_rabbits_v2;

import java.util.List;
import java.util.Random;

/**
 * A simple model of a rabbit.
 * Rabbits age, move, breed, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 * Updated by Stephen Zoyac
 * @version 2021.04.08
 */
public class Rabbit extends Animal
{
   
 
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
   

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location,int age)
    {
        super(field, location,age);
        if(randomAge) {
            age = rand.nextInt(getMaxAge());
        }
    }
    
    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits.
     */
    public void act(List<Animal> newRabbits)
    {
    	incrementAge();
        if(isAlive()) {
            giveBirth(newRabbits);            
            // Try to move into a free location.
            Location newLocation = getField().freeAdjacentLocation(getLocation());
            if(newLocation != null) {
                setLocation(newLocation);
            }
            else {
                // Overcrowding.
                setDead();
                
            }
        }
    }

   

    /**
     * A rabbit can breed if it has reached the breeding age.
     * @return true if the rabbit can breed, false otherwise.
     */
    protected int getBreedingAge(){
    	
    	return 5;
    }
    /**
     * Max age of a rabbit
     */
    protected int getMaxAge() {
    	
    	return 40;
    }
    /**
     * Probabilty of breeding for a rabbit
     */
 protected double getBreedingProbability() {
    	
    	return 0.12;
    }
    /**
     * Max liter of a Rabbit
     */
    protected int getMaxLitterSize() {
    
    	return 4;
    }
    
}