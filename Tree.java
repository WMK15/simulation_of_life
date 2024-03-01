import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * A tree that grows for generations. But if it has no human neighbour to "water" it.
 * 
 * FULFILS CHALLENGE TASK 2.1 - MUTUALISTIC Relationship with Human
 *
 * @author Izzie Yip, Waseef Khan
 * @version 2024.03.01
 */

public class Tree extends Cell {
    
    /**
     * Create a new Tree.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Tree(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
    * This is how the Tree decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        List<Cell> humanNeighbours = getSpecificNeighbours(neighbours, Human.class);
        
        // Increment the age of the cell.
        incAge();
        int currentAge = getAge();
        
        setNextState(false);
        
        // We first check if the tree being is alive.
        if (isAlive()) {
            if (getInfected()) {
                infectedAct();
                return;
            }
            // If the current age is below 150 AND it has min. one human neighbour, it will live.
            if (!humanNeighbours.isEmpty() && currentAge < 150) {
                setNextState(true);
            }
        } else {
            // If the tree's current age is 750 or it has atleast one human neighbour,
            // the tree revives and its age gets reset.
            if (humanNeighbours.size() > 2 || neighbours.size() > 5 || currentAge == 200) {
                setColor(Color.GREEN);
                resetAge();
                resetInfected();
                setNextState(true);
            }
        }
    }
}
