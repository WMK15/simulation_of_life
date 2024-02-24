import javafx.scene.control.Button;
import java.util.function.Function;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/**
 * Write a description of class WindowButton here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class WindowButton extends Button
{
    /**
     * Constructor for objects of class WindowButton
     */
    public WindowButton(String buttonName, String cssID, String cssClass)
    {
        super(buttonName);
        this.setId(cssID);
        this.getStyleClass().add(cssClass);
    }
}
