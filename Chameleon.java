import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * A cell that changes color every generation.
 * Fun Fact: There are more than 200 chameleon species!
 * 
 * FULFILS Base Tasks 2.1
 * FULFILS Challenge Tasks 2.2 - PARASITIC relationship with the Bird class.
 *
 * @author Izzie Yip, Waseef Khan
 * @version 2024.03.01
 */

public class Chameleon extends Cell {
    private static final Color[] chameleonColors = {Color.RED, Color.PURPLE, Color.BLUE, Color.GREY, Color.LIGHTBLUE};

    /**
     * Create a new Chameleon.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Chameleon(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
    * This is how the Chameleon decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        // Increment the age of the cell.
        incAge();
        int currentAge = getAge();
        
        setNextState(false);
        
        if (isAlive()) {
            // Check if the cell is infected or not.
            if (getInfected()) {
                infectedAct();
                return;
            }
            // If it has 4 neighbours, it will survive and change colours.
            if (neighbours.size() == 4) {
                chamColor();
                setNextState(true);
            }
        } else {
            // If it has 4 neighbours, it will be reborn.
            if (neighbours.size() == 4) {
                chamColor();
                resetAge();
                resetInfected();
                setNextState(true);
            }
        }
    }
    
    /**
     * Randomly change the color of the Chameleon.
     */
    private void chamColor() {
        Random rand = Randomizer.getRandom();
        setColor(chameleonColors[rand.nextInt(chameleonColors.length)]);
    }
}
