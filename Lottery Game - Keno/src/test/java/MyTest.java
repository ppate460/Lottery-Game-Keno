import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;

class MyTest {
	Player player;
	LogicGame logicGame;
	
	@BeforeEach
	void setup() {
		player = new Player();
		logicGame = new LogicGame();
	}	

	@Test
	void PlayerConstructorTest() {
		assertEquals(player.getMoney(), 10000);
		assertEquals(player.getSpotNum(), 0);
		assertEquals(player.getPickedCards().size(), 0);
	}

	
	@Test
	void PlayerGetSpotTest() {
		player.setSpotNum(1);
		assertEquals(player.getSpotNum(), 1);
		
	}
	
	@Test 
	void PlayerSetSpotTest() {
		player.setSpotNum(3);
		assertEquals(player.getSpotNum(), 3);
	}
	
	@Test
	void PlayerGetDrawingNumTest() {
		player.setDrawingNum(1);
		assertEquals(player.getDrawingNum(),1);
	}
	
	
	@Test
	void PlayerSetDrawingNumTest() {
		player.setDrawingNum(3);
		assertEquals(player.getDrawingNum(), 3);
	}
	
	@Test
	void PlayerGetMoneytest() {
		player.setMoney(10000);
		assertEquals(player.getMoney(), 10000);
	}
	@Test
	void PlayerSetMoneyTest() {
		player.setMoney(100);
		assertEquals(player.getMoney(), 100);
	}
	
	@Test
	void PlayerFullTest() {
		for(int i = 0; i < 20; i++) {
			player.getPickedCards().add(i);
		}
		assertEquals(player.full(), true);
	}
	
	@Test
	void PlayerFullTest2() {
		for(int i = 0; i < 19; i++) {
			player.getPickedCards().add(i);
		}
		assertEquals(player.full(), false);
	
	}
	
	@Test
    void PlayerGetPickedCardsTest() {
        player.getPickedCards().add(3);
        player.getPickedCards().add(6);
        player.getPickedCards().add(9);
        int result = player.getPickedCards().size();
        assertEquals(result, 3);
    }
	
	@Test
	void PlayerGetPickedCardsTest2() {
		TreeSet<Integer> temp = new TreeSet<Integer>();
		for(int i = 0; i < 20; i++) {
			temp.add(i);
		}
		player.setBetCards(temp);
		assertEquals(player.getPickedCards(), temp);
	}
	
	@Test
	void PlayerNewGameTest() {
		player.getPickedCards().add(3);
		player.getPickedCards().add(2);
		player.setDrawingNum(3);
		player.setSpotNum(3);
		player.newGame();
		assertEquals(player.getPickedCards().size(), 0);
		assertEquals(player.getDrawingNum(), 0);
		assertEquals(player.getSpotNum(), 0);
	}
	
	@Test
	void PlayerSetBetCardsTest() {
		TreeSet<Integer> temp = new TreeSet<Integer>(Arrays.asList(1,2,3));
		player.setBetCards(temp);
		assertEquals(temp, player.getPickedCards());
	}
	
	@Test
	void LogicGameConstructorTest() {
		assertNotEquals(logicGame.getGameBetCards().size(), 0);
	}
	
	@Test
	void LogicGameGameCostTest() {
		player.setDrawingNum(2);
		player.setSpotNum(2);
		assertEquals(4,logicGame.gameCost(player.getSpotNum(),player.getDrawingNum()));
	}
	
	@Test
	void LogicGameSetPlayerCardsTest() {
		TreeSet<Integer> temp = new TreeSet<Integer>();
		for(int i = 1; i < 21; i++) {
			temp.add(i);
		}
		
		player.setBetCards(temp);
		logicGame.setPlayerBetCards(player.getPickedCards());
		assertEquals(logicGame.getPlayerBetCards(), temp);
		
	}
	
	@Test
	void LogicGameSetGameCardsTest() {
		TreeSet<Integer> temp = new TreeSet<Integer>();
		for(int i = 1; i < 21; i++) {
			temp.add(i);
		}
		
		logicGame.setGameBetCards(temp);
		assertEquals(logicGame.getGameBetCards(), temp);
		
	}
	
	@Test
	void LogicGameStartBettingTest() {
		assertEquals(logicGame.startBetting(player), false);
		player.setDrawingNum(1);
		player.setSpotNum(1);
		TreeSet<Integer> temp = new TreeSet<Integer>();
		for(int i = 1; i < 21; i++) {
			temp.add(i);
		}
		player.setBetCards(temp);
		assertEquals(logicGame.startBetting(player), true);
	}
	
	@Test
	void LogicGameStartBettingTest2() {
		assertEquals(logicGame.startBetting(player), false);
		player.setDrawingNum(0);
		player.setSpotNum(1);
		TreeSet<Integer> temp = new TreeSet<Integer>();
		for(int i = 1; i < 21; i++) {
			temp.add(i);
		}
		player.setBetCards(temp);
		assertEquals(logicGame.startBetting(player), false);
	}
	
	@Test
	void LogicGameNumMatchingTest() {
		TreeSet<Integer> temp = new TreeSet<Integer>();
		for(int i = 1; i < 21; i++) {
			temp.add(i);
		}
		logicGame.setGameBetCards(temp);
		logicGame.setPlayerBetCards(temp);
		assertEquals(logicGame.numMatching().size(), 20);
	}
	
	@Test
	void LogicGamePrizeTest() {
		assertEquals(logicGame.prize(1, 1), 2);
		assertEquals(logicGame.prize(0, 1), 0);
	}
	
	@Test
	void LogicGamePrizeTest2() {
		assertEquals(logicGame.prize(4, 4), 75);
		assertEquals(logicGame.prize(3, 4), 5);
		assertEquals(logicGame.prize(1, 4), 1);
	}
	
	@Test
	void LogicGamePrizeTest3() {
		assertEquals(logicGame.prize(8, 8), 10000);
		assertEquals(logicGame.prize(7, 8), 750);
		assertEquals(logicGame.prize(6, 8), 50);
		assertEquals(logicGame.prize(5, 8), 12);
		assertEquals(logicGame.prize(4, 8), 2);
	}
	
	@Test
	void LogicGamePrizeTest4() {
		assertEquals(logicGame.prize(10, 10), 100000);
		assertEquals(logicGame.prize(9, 10), 4250);
		assertEquals(logicGame.prize(8, 10), 450);
		assertEquals(logicGame.prize(7, 10), 40);
		assertEquals(logicGame.prize(6, 10), 15);
		assertEquals(logicGame.prize(5, 10), 2);
		assertEquals(logicGame.prize(0, 10), 5);
	}
	
	@Test
	void LogicGameReset() {
		TreeSet<Integer> temp = new TreeSet<Integer>();
		for(int i = 1; i < 21; i++) {
			temp.add(i);
		}
		TreeSet<Integer> temp1 = new TreeSet<Integer>();
		for(int i = 1; i < 21; i++) {
			temp.add(i);
		}
		logicGame.setGameBetCards(temp1);
		logicGame.setPlayerBetCards(temp);
		logicGame.reset();
		assertEquals(logicGame.getPlayerBetCards().size(), 0);
		assertEquals(logicGame.getGameBetCards().size(), 0);
	}
	
}
