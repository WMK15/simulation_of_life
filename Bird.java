import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

/**
 * A bird cell that has a hunger level that depletes unless it eats a chameleon.
 * 
 * FULFILS Challenge Tasks 2.2 - PARASITIC relationship with the Chameleon class.
 *
 * @author Izzie Yip, Waseef Khan
 * @version 2024.03.01
 */

public class Bird extends Cell {
    private int hunger;
    private static final double DIFF_COLOR_EAT_PROB = 0.75;
    private static final double SAME_COLOR_EAT_PROB = 0.25;
    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Bird(Field field, Location location, Color col) {
        super(field, location, col);
        hunger = 5;
    }

    /**
    * This is how the Mycoplasma decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        
        // Increment the age of the cell.
        incAge();
        int currentAge = getAge();
        
        if (isAlive()) {
            if (getInfected()) {
                infectedAct();
                return;
            }
            // every 5 generations, the hunger decrements.
            if (currentAge % 5 == 0) {
                hunger--;
            }
            // Filter out the chameleon neighbours of the bird.
            List<Cell> chameleonNeighbours = getSpecificNeighbours(neighbours, Chameleon.class);
            if (!chameleonNeighbours.isEmpty()) {
                // Find the first chameleon in the list.
                Cell cham = chameleonNeighbours.get(0);
                Random rand = Randomizer.getRandom();
                // If the chameleon has the same color of the bird, it will have
                // a lesser chance get eaten.
                if (cham.getColor() == getColor() && rand.nextDouble() <= SAME_COLOR_EAT_PROB) {
                    cham.setNextState(false);
                    hunger++;
                } else if (rand.nextDouble() <= DIFF_COLOR_EAT_PROB) {
                    cham.setNextState(false);
                    hunger++;
                }
            }
            
            // As long as the bird's hunger is not 0, it will live.
            if (hunger > 0) {
                setNextState(true);
            }
        } else {
            // If the dead cell has more than or equal to five neighbours, the cell is revived.
            if (neighbours.size() >= 5) {
                setColor(Color.LIGHTBLUE);
                resetAge();
                resetInfected();
                setNextState(true);
            }
        }
    }
}
