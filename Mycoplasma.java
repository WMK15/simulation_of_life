import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life.  A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 * 
 * FULFILS Base Task 1
 *
 * @author Izzie Yip, Waseef Khan
 * @version 2024.03.01
 */

public class Mycoplasma extends Cell {

    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
    * This is how the Mycoplasma decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
    
        if (isAlive()) {
            if (getInfected()) {
                infectedAct();
                return;
            }
            if (neighbours.size() == 2 || neighbours.size() == 3) {
                setNextState(true);
            }
        } else {
            if (neighbours.size() == 3) {
                setColor(Color.ORANGE);
                resetAge();
                resetInfected();
                setNextState(true);
            }
        }
    }
}