package foxes_rabbits_v2;

import java.util.List;
import java.util.Iterator;
import java.util.Random;
/**
 * Creates a Robot Laser Bear that ages, and eats foxes
 * @author Stephen Zoyac
 * @version 2021.04.06
 *
 */
public class RobotLaserBear extends Animal
{

	private static final int FOX_FOOD_VALUE = 18;
	
	private static final Random rand = Randomizer.getRandom();
	
	 private int foodLevelRLB;
	 
	 /**
	     * Create a Robot Laser Bear (RLB for short). A RLB can be created as a new robot (age zero
	     * and not hungry) or with a random age and food level.
	     * 
	     * @param randomAge If true, the RLB will have random age and hunger level.
	     * @param field The field currently occupied.
	     * @param location The location within the field.
	     */
	    public RobotLaserBear(boolean randomAge, Field field, Location location,int age)
	    {
	        super(field,location,age);
	        if(randomAge) {
	            age = rand.nextInt(getMaxAge());
	            foodLevelRLB = rand.nextInt(FOX_FOOD_VALUE);
	        }
	        else {
	            age = 0;
	            foodLevelRLB = FOX_FOOD_VALUE;
	        }
	    }
	    
	    /**
	     * This is what the Robot Laser Bears does most of the time: it hunts for
	     * foxes. In the process, it might breed, die of hunger,
	     * or expiration date.
	     * @param field The field currently occupied.
	     * @param newRLB A list to return newly made Robo Laser Bears.
	     */
	    public void act(List<Animal> newRLB)
	    {
	    	
	        incrementAge();
	        incrementHunger();
	        if(isAlive()) {
	            giveBirth(newRLB);
	            
	            
	           // Move towards a source of food if found.
	            Location location = getLocation();
	            Location newLocation = findFood();
	            if(newLocation == null) { 
	                // No food found - try to move to a free location.
	                newLocation = getField().freeAdjacentLocation(location);
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
	     * Make the Robo Laser Bear want food. This could result in the Robo Laser Bear's destruction.
	     */
	    private void incrementHunger()
	    {
	        foodLevelRLB--;
	        if(foodLevelRLB <= 0) {
	            setDead();
	        }
	    }
	    
	    /**
	     * Look for foxes adjacent to the current location.
	     * Only the first live foxes is eaten.
	     * @return Where food was found, or null if it wasn't.
	     */
	    private Location findFood()
	    {
	        Field field = getField();
	        List<Location> adjacent = field.adjacentLocations(getLocation());
	        Iterator<Location> it = adjacent.iterator();
	        while(it.hasNext()) {
	            Location where = it.next();
	            Object animal3 = field.getObjectAt(where);
	            if(animal3 instanceof Fox) {
	                Animal fox = (Fox) animal3;
	                if(fox.isAlive()) { 
	                    fox.setDead();
	                    foodLevelRLB = FOX_FOOD_VALUE;
	                    // Remove the dead rabbit from the field.
	                    return where;
	                }
	            }
	        }
	        return null;
	    }
	     
	    /**
	     * A Robot Laser Bear can reproduce if it has reached the reproducing level.
	     */
	    protected int getBreedingAge() {
	    	
	    	return 10;
	    }
	    /**
	     * Experation Date for Robot Laser Bear
	     */
	    protected int getMaxAge() {
	    	
	    	return 4000;
	    }
	    /**
	     * Probability a new robot will be create
	     */
	    protected double getBreedingProbability() {
	    	
	    	return 0.10;
	    }
	    /**
	     * Max Robot Bear produced
	     */
	    protected int getMaxLitterSize() {
	    
	    	return 3;
	    }
	    
	
}
