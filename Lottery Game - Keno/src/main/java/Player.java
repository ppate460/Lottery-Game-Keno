import java.util.TreeSet;

public class Player {
	private int spotNum;
	private int drawingNum;
	private int money;
	private TreeSet<Integer> pickedCards;
	
	public Player() {
		money = 10000;
		spotNum = 0;
		drawingNum = 0;
		pickedCards = new TreeSet<Integer>();
	}
	
	public TreeSet<Integer> getPickedCards(){
		return this.pickedCards;
	}
	
	public int numPickedCards() {
		return this.pickedCards.size();
	}
	
	public void setSpotNum(int numOfSpots) {
		this.spotNum = numOfSpots;
	}
	
	public int getSpotNum() {
		return this.spotNum;
	}
	
	public void setDrawingNum(int numOfDrawing) {
		this.drawingNum = numOfDrawing;
	}
	
	public int getDrawingNum() {
		return this.drawingNum;
	}
	
	public void setMoney(int amountUpdate) {
		this.money = amountUpdate;
	}
	
	public int getMoney() {
		return this.money;
	}
	
	public boolean full() {
		return pickedCards.size() >= 20;
	}
	
	public void print() {
		for (int each:pickedCards) {
			System.out.print(each + " ");
		}
		System.out.println("");
	}
	  

	public void newGame() {
		this.drawingNum = 0;
		this.spotNum = 0;
		this.pickedCards.clear();
	}
	
	public void setBetCards(TreeSet<Integer> setCards) {
		this.pickedCards.clear();
		this.pickedCards = setCards;
	}
	
	
	
}
