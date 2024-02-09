import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Simplest form of life.
 * Fun Fact: Human are one of the simplest forms of life.  A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06
 */

public class Human extends Cell {
    private int age;
    private static final double ACCIDENTAL_DEATH_PROB = 0.05;
    private static final double OLD_AGE_DEATH = 0.4;
    
    /**
     * Create a new Human.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Human(Field field, Location location, Color col) {
        super(field, location, col);
        age = 0;
    }

    /**
    * This is how the Human decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        
        Random rand = Randomizer.getRandom();
        age++;
    
        // We first check if the human being is alive.
        if (isAlive()) {
            // Then we check if it has at least one neighbour. It will die if it has no neighbours.
            if (neighbours.size() > 0 && neighbours.size() < 8)
                setNextState(true);
            // While the human's age is less than 80, it will live. Otherwise, it will die.
            if (age < 80)
                setNextState(true);
            // After the human has turned 13, it has a 5% chance of dying by accident.
            if (age < 13 || rand.nextDouble() > ACCIDENTAL_DEATH_PROB)
                setNextState(true);
            if (age > 2) {
                Location currentLocation = getLocation();
                Field cellField = getField();
                Location adjacentLocation = cellField.randomAdjacentLocation(currentLocation);
                
                setLocation(adjacentLocation);
                cellField.clear(currentLocation);
                setNextState(true);

            }
        }
    }
}
