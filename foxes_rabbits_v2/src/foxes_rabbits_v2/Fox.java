package foxes_rabbits_v2;

import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * A simple model of a fox.
 * Foxes age, move, eat rabbits, and die.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 */
public class Fox extends Animal
{

    // The food value of a single rabbit. In effect, this is the
    // number of steps a fox can go before it has to eat again.
    private static final int RABBIT_FOOD_VALUE = 9;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    
    // The fox's food level, which is increased by eating rabbits.
    private int foodLevelFox;

  
    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fox(boolean randomAge, Field field, Location location,int age)
    {
        super(field,location,age);
        if(randomAge) {
            age = rand.nextInt(getMaxAge());
            foodLevelFox = rand.nextInt(RABBIT_FOOD_VALUE);
        }
        else {
            age = 0;
            foodLevelFox = RABBIT_FOOD_VALUE;
        }
    }
    
    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newFoxes A list to return newly born foxes.
     */
    public void act(List<Animal> newFoxes)
    {
    	
        incrementAge();
        incrementHunger();
        if(isAlive()) {
            giveBirth(newFoxes);
            
            // Move towards a source of food if found.
            Location newLocation = findFood();
            if(newLocation == null) { 
                // No food found - try to move to a free location.
                newLocation = getField().freeAdjacentLocation(getLocation());
            }
            // See if it was possible to move.
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
     * Make this fox more hungry. This could result in the fox's death.
     */
    private void incrementHunger()
    {
        foodLevelFox--;
        if(foodLevelFox <= 0) {
            setDead();
        }
    }
    
    /**
     * Look for rabbits adjacent to the current location.
     * Only the first live rabbit is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    private Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();
        while(it.hasNext()) {
            Location where = it.next();
            Object animal2 = field.getObjectAt(where);
            if(animal2 instanceof Rabbit) {
                Animal rabbit = (Rabbit) animal2;
                if(rabbit.isAlive()) { 
                    rabbit.setDead();
                    foodLevelFox = RABBIT_FOOD_VALUE;
                    // Remove the dead rabbit from the field.
                    return where;
                }
            }
        }
        return null;
    }
        
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
   
    /**
     * A fox can breed if it has reached the breeding age.
     */
    protected int getBreedingAge() {
    	
    	return 15;
    }
    /**
     * Max age of a fox
     */
    protected int getMaxAge() {
    	
    	return 150;
    }
    /**
     * Probability a fox will breed
     */	
    protected double getBreedingProbability() {
    	
    	return 0.08;
    }
    /**
     * Max number of babies a fox will have
     */
    protected int getMaxLitterSize() {
    
    	return 2;
    }
    
    
}