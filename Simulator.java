import javafx.scene.paint.Color; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2024.02.03
 */

public class Simulator {

    private static final double MYCOPLASMA_ALIVE_PROB = 0.25;
    private static final double CHAM_ALIVE_PROB = 0.15;
    private static final double HUMAN_ALIVE_PROB = 0.10;
    private List<Cell> cells;
    private Field field;
    private int generation;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(SimulatorView.GRID_HEIGHT, SimulatorView.GRID_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        cells = new ArrayList<>();
        field = new Field(depth, width);
        reset();
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        for (Iterator<Cell> it = cells.iterator(); it.hasNext(); ) {
            Cell cell = it.next();
            cell.act();
        }

        for (Cell cell : cells) {
          cell.updateState();
        }
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        populate();
    }

    /**
     * Randomly populate the field live/dead life forms
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                int cellType = rand.nextInt(3);
          
                if (cellType == 0) {
                    Mycoplasma myco = new Mycoplasma(field, location, Color.ORANGE);
                    if (rand.nextDouble() <= MYCOPLASMA_ALIVE_PROB){
                        cells.add(myco);
                    } else {
                        myco.setDead();
                        cells.add(myco);
                    }
                } else if (cellType == 1) {
                    Chameleon cham = new Chameleon(field, location, Color.GREEN);
                    if (rand.nextDouble() <= CHAM_ALIVE_PROB) {
                        cells.add(cham);
                    } else {
                        cham.setDead();
                        cells.add(cham);
                    }
                } else {
                    Human human = new Human(field, location, Color.PINK);
                    handleCellSpawn(human, HUMAN_ALIVE_PROB);
                }
            }
        }
    }
    
    private void handleCellSpawn(Cell cell, double prob) {
        Random rand = Randomizer.getRandom();
        double spawnProb = rand.nextDouble();
        if (spawnProb <= prob) {
            cells.add(cell);
        } else {
            cell.setDead();
            cells.add(cell);
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    public void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
    
    public Field getField() {
        return field;
    }

    public int getGeneration() {
        return generation;
    }
}
