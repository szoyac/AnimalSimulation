package foxes_rabbits_v2;

import java.util.List;
import java.util.Random;

/**
 * Animal.java
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling
 * @version 2011.07.31
 * Updated by Stephen Zoyac
 * @version 2021.04.06
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    
    private static final Random rand = Randomizer.getRandom();

    
    private int age;
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(Field field, Location location,int age)
    {
    	
        alive = true;
        this.field = field;
        setLocation(location);
        this.age = 0;
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    protected int getAge() {
    	
    return age;	
    
    }

	protected void setAge(int age) {
		this.age = age;
	}
	/**
	 * An animal can breed if it has reached the breeding age.
	 * @return true if the animal can breed
	 */
    protected boolean canBreed() {
    	
    	return age >= getBreedingAge();
    }
    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
    public void giveBirth(List<Animal> newAnimals)
    {
        // New Animals are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = breed();
        for(int b = 0; b < births && free.size() > 0; b++) {
        	Location loc = free.remove(0);
        	
        	Animal young = this;
			if (young instanceof RobotLaserBear) {
        
                 young = new RobotLaserBear(false, field, loc,0);
        		newAnimals.add(young);
        	}
			else if(young instanceof Fox) {
        
        		young = new Fox(false, field, loc,0);
            newAnimals.add(young);
            
        	}
			else if(young instanceof Rabbit) {
        		young = new Rabbit(false, field, loc,0);
        		newAnimals.add(young);
        	}
        	
        }
    }
    /**
     * Return the breeding age of this animal.
     * @return The breeding age of this animal
     */
    abstract protected int getBreedingAge();
    /**
    * Increase the age. This could result in the fox's death.
    */
     protected void incrementAge() {
    
        setAge(getAge()+1);
        if(getAge() > getMaxAge()) {
            setDead();
        }
    }
    
    abstract protected int getMaxAge();
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    protected int breed(){
    
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProbability()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    abstract protected double getBreedingProbability();
    
    abstract protected int getMaxLitterSize();
}
