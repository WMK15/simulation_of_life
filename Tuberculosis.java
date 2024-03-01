import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * A disease cell that infects neighbouring cells, but dies after 5 generations.
 * Fun Fact: About one quarter of the world's population is infected with tuberculosis (TB)
 * bacteria. Only a small proportion of those infected will become sick with TB.
 * 
 * FULFILS CHALLENGE TASK 3.1
 * Check Cell class for Challenge Task 3.2
 *
 * @author Izzie Yip, Waseef Khan
 * @version 2024.03.01
 */

public class Tuberculosis extends Cell {

    /**
     * Create a new Tuberculosis.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param color The color of the cell.
     */
    public Tuberculosis(Field field, Location location, Color col) {
        super(field, location, col);
        setInfected();
    }

    /**
    * This is how the Tuberculosis decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        
        // Increment the current age of the cell.
        incAge();
        int currentAge = getAge();
        
        if (isAlive()) {
            // Infect a neighbouring cell.
            infectedAct();
            // It dies after 5 generations.
            if (currentAge < 5) {
                setNextState(true);
            }
        } else {
            // After it dies, it has a 0.00001 chance to get revived.
            Random rand = Randomizer.getRandom();
            if (rand.nextDouble() <= 0.00005) {
                resetInfected();
                setInfected();
                resetAge();
                setNextState(true);
            }
        }
    }
}
