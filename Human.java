import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import java.util.stream.Collectors;


/**
 * A human cell whos behaviour changes over time.
 * 
 * If they have no neighbours, they will die of loneliness.
 * After reaching its teenage years, they will have a higher probability of dying by accident.
 * After reaching age 70, they will have a 50% probability of dying of old age.
 * If it does not have at least one tree neighbour, it will die.
 * 
 * However, if the dead cell has two human neighbours who are 18+, a new human cell is born.
 * OR, if the dead cell has four neighbours, it will be reborn.
 * 
 * FULFILS BASE TASK 2.2
 * FULFILS CHALLENGE TASK 1
 * FULFILS CHALLENGE TASK 2.1 - MUTUALISTIC Relationship with Tree
 * 
 * @author Izzie Yip, Waseef Khan
 * @version 2024.03.01
 */

public class Human extends Cell {
    private static final double ACCIDENTAL_DEATH_PROB = 0.1;
    private static final double OLD_AGE_DEATH = 0.5;
    
    /**
     * Create a new Human.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Human(Field field, Location location, Color col) {
        super(field, location, col);
    }

    /**
    * This is how the Human decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        
        incAge();
        int currentAge = getAge();

        List<Cell> treeNeighbours = getSpecificNeighbours(neighbours, Tree.class);
    
        // We first check if the human being is alive.
        if (isAlive()) {
            if (getInfected()) {
                infectedAct();
                return;
            }
            // If a human has no neighbours, it dies.
            if (neighbours.size() == 0) {
                setNextState(false);
            }
            // It requires at least one tree neighbour to survive.
            if (!treeNeighbours.isEmpty()) {
                setNextState(true);
            }
            
            Random rand = Randomizer.getRandom();
            // After the human has turned 70, it has a 50% chance of dying.
            if (currentAge >= 70 && rand.nextDouble() <= OLD_AGE_DEATH) {
                setNextState(false);
            // After the human has turned 16, it has a 5% chance of dying by accident.
            } else if (currentAge >= 16 && rand.nextDouble() <= ACCIDENTAL_DEATH_PROB) {
                setNextState(false);
            } else {
                setNextState(true);
            }
        } else {
            // if they have two neighbours who are 18+, a new human cell is born.
            List<Cell> humanNeighbours = getSpecificNeighbours(neighbours, Human.class);
            Stream<Cell> adultNeighbours = humanNeighbours.stream().filter(h -> h.getAge() >= 18);
            // OR if the cell has 5 neighbours of any kind, it will be reborn.
            if (adultNeighbours.count() >= 2 || neighbours.size() == 4) {
                setColor(Color.PINK);
                resetAge();
                resetInfected();
                setNextState(true);
            }
        }
    }

}
