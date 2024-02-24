import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.Group; 
import javafx.scene.layout.BorderPane; 
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color; 
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/**
 * A graphical view of the simulation grid. The view displays a rectangle for
 * each location. Colors for each type of life form can be defined using the
 * setColor method.
 *
 * @author Izzie Yip, Waseef Khan
 * @version 2024.03.01
 */

public class SimulatorView extends Application {

    public static final int GRID_WIDTH = 100;
    public static final int GRID_HEIGHT = 80;    
    public static final int WIN_WIDTH = 650;
    public static final int WIN_HEIGHT = 650;
    
    private static final Color EMPTY_COLOR = Color.WHITE;

    private final String GENERATION_PREFIX = "Generation: ";
    private final String POPULATION_PREFIX = "Population: ";

    private Label genLabel, population, infoLabel;

    private FieldCanvas fieldCanvas;
    private FieldStats stats;
    private Simulator simulator;
    private boolean threadRunning;

    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    @Override
    public void start(Stage stage) {
        threadRunning = false;
        stats = new FieldStats();
        fieldCanvas = new FieldCanvas(WIN_WIDTH - 50, WIN_HEIGHT - 50);
        fieldCanvas.setScale(GRID_HEIGHT, GRID_WIDTH); 
        simulator = new Simulator();

        Group root = new Group();
        
        genLabel = new Label(GENERATION_PREFIX);
        genLabel.getStyleClass().add("label");
        infoLabel = new Label("  ");
        infoLabel.getStyleClass().add("label");
        population = new Label(POPULATION_PREFIX);

        BorderPane bPane = new BorderPane(); 
        HBox infoPane = new HBox();
        
        // Buttons
        Button playSimButton = new WindowButton("Play Simulation", "startButton", "button");
        playSimButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle (ActionEvent e) {
                simulate();
            }
        });
        
        Button pauseSimButton = new WindowButton("Pause Simulation", "stopButton", "button");
        pauseSimButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle (ActionEvent e) {
                pauseSim();
            }
        });
        
        Button resetSimButton = new WindowButton("Reset Simulation", "resetButton", "button");
        resetSimButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle (ActionEvent e) {
                resetSim();
            }
        });
        
        VBox bottomPane = new VBox();
        HBox buttonPane = new HBox();
        buttonPane.getStyleClass().add("horPane");
        HBox popPane = new HBox();
        popPane.getStyleClass().add("horPane");

        infoPane.setSpacing(10);
        infoPane.getChildren().addAll(genLabel, infoLabel);
        infoPane.getStyleClass().add("infoPane");
        popPane.getChildren().addAll(population);
        buttonPane.getChildren().addAll(playSimButton, pauseSimButton, resetSimButton);
        
        bottomPane.getChildren().addAll(popPane, buttonPane);
        
        bPane.setTop(infoPane);
        bPane.setCenter(fieldCanvas);
        bPane.setBottom(bottomPane);
        
        root.getChildren().add(bPane);
        Scene scene = new Scene(root, WIN_WIDTH - 40, WIN_HEIGHT + 40);
        scene.getStylesheets().add("./stylesheet.css");
        
        stage.setScene(scene);          
        stage.setTitle("Life Simulation");
        updateCanvas(simulator.getGeneration(), simulator.getField());
        
        stage.show();
    }
    
    /**
     * Reset the simulation by stopping the thread and resetting the field.
     */
    public void resetSim() {
        threadRunning = false;
        reset();
    }
    
    /**
     * Pause the simulation by stopping the thread.
     */
    public void pauseSim() {
        threadRunning = false;
    }

    /**
     * Display a short information label at the top of the window.
     */
    public void setInfoText(String text) {
        infoLabel.setText(text);
    }

    /**
     * Show the current status of the field.
     * @param generation The current generation.
     * @param field The field whose status is to be displayed.
     */
    public void updateCanvas(int generation, Field field) {
        genLabel.setText(GENERATION_PREFIX + generation);
        stats.reset();
        
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = field.getObjectAt(row, col);
        
                if (cell != null && cell.isAlive()) {
                    stats.incrementCount(cell.getClass());
                    fieldCanvas.drawMark(col, row, cell.getColor());
                }
                else {
                    fieldCanvas.drawMark(col, row, EMPTY_COLOR);
                }
            }
        }
        
        stats.countFinished();
        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field) {
        return stats.isViable(field);
    }

    /**
     * set threadRunning to true and start running the thread until
     * threadRunning is set to false.
     */
    public void simulate() {
        if (threadRunning) {
            return;
        }
        threadRunning = true;
        
        new Thread(() -> {
            while (threadRunning) {
                simulator.simOneGeneration(); 
                simulator.delay(500);
                Platform.runLater(() -> {
                    updateCanvas(simulator.getGeneration(), simulator.getField());
                });
            }
            
        }).start();
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        simulator.reset();
        updateCanvas(simulator.getGeneration(), simulator.getField());
    }
    
    public static void main(String args[]){           
        launch(args);      
   } 
}
