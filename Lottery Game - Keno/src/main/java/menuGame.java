import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class menuGame {

	private Menu menu;
	private MenuBar menubar;
	private MenuItem rules;
	private MenuItem odds;
	private MenuItem quit;
	
	public menuGame(){
		rules = new MenuItem("Rules");
		odds = new MenuItem("Odds");
		quit = new MenuItem("Quit");
		menu = new Menu("Menu");
		menubar = new MenuBar();
		menu.getItems().addAll(rules, odds, quit);
		menubar.getMenus().add(menu);
		
	}
	public Menu getMenu() {
		return menu;
	}
	public MenuItem getRules() {
		return rules;
	}
	
	public MenuItem getOdds() {
		return odds;
	}
	
	public MenuItem getQuit() {
		return quit;
	}
	
	public MenuBar getMenuBar() {
		return menubar;
	}
	
}
