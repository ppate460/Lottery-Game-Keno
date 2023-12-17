import java.util.ArrayList;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class GameButtons<T> {
	private final ArrayList<GameButton<T>> buttons;
	private final ArrayList<String> labels;
	private final ArrayList<T> values;
	
	public GameButtons(int amount, ArrayList<T> value, ArrayList<String> text) {
		buttons = new ArrayList<GameButton<T>>();
		for (int i = 0; i < amount; i++) {
			GameButton<T> btn = new GameButton<T>(value.get(i), text.get(i));
			buttons.add(btn);
		}
		
		labels = new ArrayList<String>();
		values = new ArrayList<T>();
		for (int i = 0; i < amount; i++) {
			labels.add(text.get(i));
			values.add(value.get(i));
		}
		
	}
	
	public ArrayList<GameButton<T>> getObjButtons(){
		return this.buttons;
	}
	
	public ArrayList<Button> getButtons() {
		ArrayList<Button> btns = new ArrayList<Button>();
		for (GameButton each:buttons) {
			btns.add(each.getButton());
		}
		return btns;
	}
	
	public ArrayList<String> getLabels(){
		return this.labels;
	}
	
	public ArrayList<T> getValues(){
		return this.values;
	}
	
	//reset the button back to default setting
	public void reset() {
		for (GameButton each:buttons) {
			if (each.getPressed()) {
				each.setPressed(false);
				each.getButton().setBackground(new Background(new BackgroundFill(Color.web("#cfcfcf"),null, null)));
			}
		}
	}
	
}
