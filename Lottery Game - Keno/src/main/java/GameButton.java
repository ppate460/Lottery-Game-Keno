import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameButton<T> {
	private final Button btn;
	private T value;
	private String label;
	private boolean pressed;
	
	public GameButton(T btnValue, String text) {
		this.value = btnValue;
		this.label = text;
		this.pressed = false;
		btn = new Button(text);
        btn.setPrefSize(150, 80);
        btn.setFont(new Font(15));
		btn.setBackground(new Background(new BackgroundFill(Color.web("#cfcfcf"), null, null)));
		
	}
	
	public void setValue(T btnValue) {
		this.value = btnValue;
	}
	
	public void setLabel(String text) {
		this.label = text;
		btn.setText(label);
	}
	
	public Button getButton() {
		return this.btn;
	}
	
	public T getValue() {
		return this.value;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public boolean getPressed() {
		return this.pressed;
	}
	
	public void setPressed(boolean status) {
		this.pressed = status;
	}

}
