import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Fun form of life!
 * Fun Fact: Chameleons can change colour :>
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06
 */

public class Chameleon extends Cell {
    private static final Color[] chameleonColors = {Color.GREEN, Color.PURPLE, Color.BLUE, Color.LIGHTGREEN};
    private int age;

    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Chameleon(Field field, Location location, Color col) {
        super(field, location, col);
        age = 0;
    }

    /**
    * This is how the Mycoplasma decides if it's alive or not
    */
    public void act() {
        age++;
        
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        Random rand = Randomizer.getRandom();
        
        if (isAlive()) {
            if (neighbours.size() < 3) {
                setNextState(true);
            }
            if (age % 10 == 0) {
                setColor(chameleonColors[rand.nextInt(chameleonColors.length)]);
                setNextState(true);
            }
        } else {
            if (neighbours.size() == 2) {
                setNextState(true);
            }
        }
    }
}
