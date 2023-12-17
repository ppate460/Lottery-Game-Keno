import java.util.ArrayList;
import java.util.TreeSet;

import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;

public class LogicGame {
	private TreeSet<Integer> playerBetCards;
	private TreeSet<Integer> gameBetCards;
	
	public LogicGame() {
		gameBetCards = generateRandomNumbers();
	}
	

	public TreeSet<Integer> generateRandomNumbers(){
		TreeSet<Integer> temp = new TreeSet<Integer>();
		while(temp.size() < 20) {
			 int randomNumber = (int) (Math.random() * 80) + 1;
			 temp.add(randomNumber);
		}
		return temp;
	}
	
	public int gameCost(int numSpot, int numDrawing) {
		return numSpot*numDrawing;
	}
	
	public TreeSet<Integer> getPlayerBetCards(){
		return this.playerBetCards;
	}
	
	public void setPlayerBetCards(TreeSet<Integer> playerPickedBetCards) {
		this.playerBetCards = playerPickedBetCards;
	}
	
	public TreeSet<Integer> getGameBetCards(){
		return this.gameBetCards;
	}
	
	public void setGameBetCards(TreeSet<Integer> gamePickedCards) {
		this.gameBetCards = gamePickedCards;
	}
	
	public boolean startBetting(Player player) {
		if (player.getDrawingNum() != 0 && player.getSpotNum() != 0 && player.full())
			return true;
		return false;
	}
	
	public void printPlayerBetCards() {
		for (int each:playerBetCards) {
			System.out.print(each + " ");
		}
		System.out.println("");
	}
	
	public void printGameBetCards() {
		for (int each:gameBetCards) {
			System.out.print(each + " ");
		}
		System.out.println("");
	}
	  
	public TreeSet<Integer> numMatching() {
		TreeSet<Integer> matchSet = new TreeSet<Integer>();
		for(int each:playerBetCards) {
			matchSet.add(each);
		}
		matchSet.retainAll(gameBetCards);
		return matchSet;
	}
	
	
	public int prize(int numMatch, int numSpot) {
		int totalPrize = 0;
		switch (numSpot){
		case 1:
			if (numMatch == 1)
				totalPrize = 2;
			break;
		case 4:
			if (numMatch == 4)
				totalPrize = 75;
			else if (numMatch == 3)
				totalPrize = 5;
			else if (numMatch == 1)
				totalPrize = 1;
			break;
		case 8:
			if (numMatch == 8)
				totalPrize = 10000;
			else if (numMatch == 7)
				totalPrize = 750;
			else if (numMatch == 6)
				totalPrize = 50;
			else if (numMatch == 5)
				totalPrize = 12;
			else if (numMatch == 4)
				totalPrize = 2;
			break;
		case 10:
			if (numMatch == 10)
				totalPrize = 100000;
			else if (numMatch == 9)
				totalPrize = 4250;
			else if (numMatch == 8)
				totalPrize = 450;
			else if (numMatch == 7)
				totalPrize = 40;
			else if (numMatch == 6)
				totalPrize = 15;
			else if (numMatch == 5)
				totalPrize = 2;
			else if (numMatch == 0)
				totalPrize = 5;
			break;	
		}
		return totalPrize;
	}
	
	public void changeStartButton(Player player, Button startBtn) {
		if (this.startBetting(player))
			startBtn.setBackground(new Background(new BackgroundFill(Color.web("#f7ff82"), null, null)));
		else
			startBtn.setBackground(new Background(new BackgroundFill(Color.web("#cfcfcf"), null, null)));		
	}
	
	public void reset() {
		playerBetCards.clear();
		gameBetCards.clear();
	}
	
	
	
}
