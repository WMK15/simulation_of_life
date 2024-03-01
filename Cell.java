import javafx.scene.paint.Color; 
import java.util.List;
import java.util.stream.Stream;

/**
 * A class representing the shared characteristics of all forms of life
 *
 * @author Izzie Yip, Waseef Khan
 * @version 2024.03.01
 */

public abstract class Cell {
    private boolean alive;    
    private boolean nextAlive; // The state of the cell in the next iteration
    private Field field;
    private Location location;
    private Color color = Color.WHITE;
    private int age;
    
    private boolean infected;
    private int infectedAge;
    
    private static final int MAX_INFECTED_AGE = 10;

    /**
     * Create a new cell at location in field.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Cell(Field field, Location location, Color col) {
        alive = true;
        nextAlive = false;
        this.field = field;
        setLocation(location);
        setColor(col);
        infectedAge = MAX_INFECTED_AGE;
        age = 0;
    }

    /**
     * Make this cell act - that is: the cell decides it's status in the
     * next generation.
     */
    abstract public void act();

    /**
     * Check whether the cell is alive or not.
     * @return true if the cell is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Indicate that the cell is no longer alive.
     */
    protected void setDead() {
        alive = false;
    }

    /**
     * Indicate that the cell will be alive or dead in the next generation.
     */
    public void setNextState(boolean value) {
        nextAlive = value;
    }

    /**
     * Changes the state of the cell
     */
    public void updateState() {
        alive = nextAlive;
    }

    /**
     * Changes the color of the cell
     */
    public void setColor(Color col) {
        color = col;
    }

    /**
     * Returns the cell's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Return the cell's location.
     * @return The cell's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Place the cell at the new location in the given field.
     * @param location The cell's location.
     */
    protected void setLocation(Location location) {
        this.location = location;
        field.place(this, location);
    }

    /**
     * Return the cell's field.
     * @return The cell's field.
     */
    protected Field getField() {
        return field;
    }
    
    /**
     * Return the age of the cell.
     * @return age of the cell.
     */
    protected int getAge() {
        return age;
    }
    
    /**
     * Increments the age of the cell by 1.
     */
    protected void incAge() {
        age++;
    }
    
    /**
     * Reset the age of the cell to zero.
     */
    protected void resetAge() {
        age = 0;
    }
    
    /**
     * Return the cell's infected status.
     * @return infected
     */
    protected boolean getInfected() {
        return infected;
    }
    
    /**
     * Set the the cell's infected attribute as true.
     */
    protected void setInfected() {
        infected = true;
    }
    
    /**
     * Resets the infectedAge of the cell and sets the infected value to false.
     */
    protected void resetInfected() {
        infectedAge = MAX_INFECTED_AGE;
        this.infected = false;
    }
    
    /**
     * An infected cell will 'infect' one of its non-infeceted neighbour.
     */
    protected void infectCells() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        for (Cell neighbour: neighbours) {
            if (!neighbour.getInfected()) {
                neighbour.setInfected();
                neighbour.setColor(Color.BLACK);
                break;
            }
        }
    }
    
    /**
     * When a cell is infected, it 'deteriorates' every generation, and
     * 'infects' other cells until it dies.
     */
    protected void infectedAct() {
        setNextState(false);
        
        if (infectedAge > 0) {
            infectedAge--;
        }
        
        // Infected change of behaviour.
        if (infectedAge > 0) {
            infectCells();
            setNextState(true);
        }
    }
    
    /**
     * Filters out a list of neighbours according to the provided cell type.
     * 
     * @param neighbours the List of neighbours to filter from.
     * @param cellType a class that this method will filter out.
     * @return a List of neighbours of the specific cell Type.
     */
    protected List<Cell> getSpecificNeighbours(List<Cell> neighbours, Class<?> cellType) {
        List<Cell> specificNeighbours = neighbours.stream().filter(cellType::isInstance).toList();
        return specificNeighbours;
    }
}
