import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TextField;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

import javafx.stage.Popup;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeSet;
import javafx.animation.Timeline;
import javafx.animation.KeyFrame;


public class JavaFXTemplate extends Application {

   public static void main(String[] args) {
      launch(args);
   }

   @Override
   public void start(Stage primaryStage) throws Exception {

      primaryStage.setTitle("Welcome to Lottery Game - Keno");

      //set up player
      Player player = new Player();
      
      //create a menu on the top 
      menuGame menu = new menuGame();
      
    //create a logic game instance
      LogicGame logicGame = new LogicGame();
      
     //pop-up screen when click rules in the menu
      String rulesText = getRules();
      Popup rulesPopUp = PopUpRules(rulesText);
      menu.getRules().setOnAction(e -> {
         rulesPopUp.show(primaryStage);
      });


      //pop-up screen when click odds in the menu
      String oddsText= getOdds();
      Popup oddsPopUp = PopUpRules(oddsText);
      menu.getOdds().setOnAction(e -> {
         oddsPopUp.show(primaryStage);
         
      });

      menu.getQuit().setOnAction(e->System.exit(0));
           
      GameButton<Integer> start = new GameButton<Integer>(1, "start");
      start.getButton().setOnAction(e->{
    	  primaryStage.setScene(GamePlayScreen(primaryStage, player, logicGame));
    	  System.out.println("pressed");
      });

      BorderPane welcomeScreenLayout = new BorderPane();
      welcomeScreenLayout.setTop(menu.getMenuBar());
      welcomeScreenLayout.setCenter(start.getButton());

      Scene welcomeScene = new Scene(welcomeScreenLayout, 1000, 700);
      primaryStage.setScene(welcomeScene);
      primaryStage.show();
      
   }
   
   private String getRules() {
	   String rules = "Keno is a lottery-style game that involves selecting numbers from 1 to 80.\nThe player has to select 20 numbers.\nOnce the player has selected their numbers, they cannot change them until all of the drawings are completed.\nThe game involves multiple drawings, the player has the option to select 20 different random numbers if they choose not to select individually. No duplicates allowed.\nThe player wins a prize based on how many of their selected numbers match the winning numbers.\nThe payout amount depends on how many numbers the player selected and how many of those numbers match the winning numbers.\nThe more numbers match the winning numbers, the higher the potential payout if they win. However, the odds of winning decrease as the number of selected numbers increases.\n";
	   return rules;
   }
   
   private String getOdds() {
	   String odds = "Matching 1 number: approximately 1 in 4.25\nMatching 2 numbers: approximately 1 in 19.13\nMatching 5 numbers: approximately 1 in 1,883.18\nMatching 10 numbers: approximately 1 in 8,911,711\nMatching 15 numbers: approximately 1 in 428,010,965,870\nMatching 20 numbers: approximately 1 in 3,535,316,142,212,174,320\n";
	   return odds;
   }
   
   // pop-up screen with a text
   private Popup PopUpRules(String text) {
      Label rulesLabel = new Label(text);
      rulesLabel.setStyle("-fx-border-color: black; -fx-border-width: 1px; -fx-padding:3px");
      rulesLabel.setMaxWidth(Double.MAX_VALUE);
      rulesLabel.setWrapText(true);

      Popup popupScreen = new Popup();
      popupScreen.setOpacity(1.0);
      VBox popupVBox = new VBox();
      popupVBox.setPrefSize(400, 600);
      BackgroundFill fill = new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY);
      Background background = new Background(fill);
      popupVBox.setBackground(background);

      popupVBox.getChildren().add(rulesLabel);
      popupVBox.setAlignment(Pos.CENTER);
      popupScreen.getContent().addAll(rulesLabel);
      popupScreen.setAutoHide(true);
      return popupScreen;

   }

   private Scene GamePlayScreen(Stage primaryStage, Player player, LogicGame logicGame) {
      //create a menu on the top 
      menuGame menu = new menuGame();
      
      //pop-up screen when click rules in the menu
      String rulesText = getRules();
      Popup rulesPopUp = PopUpRules(rulesText);
      menu.getRules().setOnAction(e -> {
         rulesPopUp.show(primaryStage);
      });


      //pop-up screen when click odds in the menu
      String oddsText= getOdds();
      Popup oddsPopUp = PopUpRules(oddsText);
      menu.getOdds().setOnAction(e -> {
         oddsPopUp.show(primaryStage);
         
      });
      
      menu.getQuit().setOnAction(e->System.exit(0));
	      
      //create Start Drawing button and activate it when player has already chosen all the requirement
      Button resultBtn = new Button("Start Drawing");
      resultBtn.setPrefSize(200, 50);
      resultBtn.setOnAction(e -> {
    	  if (logicGame.startBetting(player)) {
    		  // set up some attributes of the logic game
    		  logicGame.setGameBetCards(logicGame.generateRandomNumbers());
    		  logicGame.setPlayerBetCards(player.getPickedCards());
    		  logicGame.printGameBetCards();
    		  logicGame.printPlayerBetCards();
    		  primaryStage.setScene(DisplayResultScreen(primaryStage, player, logicGame, 1));
    	  }
      });

      //SPOTS
      Label numSpotLabel = new Label("Select the spots to play");
      numSpotLabel.setFont(Font.font("Arial", 16));
      numSpotLabel.setTextFill(Color.BLACK);
      
      //create buttons for spot option
      ArrayList<Integer> spotValues = new ArrayList<Integer>(Arrays.asList(1, 4, 8 ,10));
      ArrayList<String> spotLabels = new ArrayList<String>(Arrays.asList("1 Spot", "4 Spots", "8 Spots", "10 Spots"));
      GameButtons<Integer> spots = new GameButtons<Integer>(4, spotValues, spotLabels);
      
      //setOnAction for each spot button
      ArrayList<GameButton<Integer>> spotObjButton = spots.getObjButtons();
      for (int i = 0; i < 4; i++) {
    	  final int index = i;
    	  spotObjButton.get(index).getButton().setOnAction(e->{
    		  spots.reset();  
    		  spotObjButton.get(index).setPressed(true);
    		  spotObjButton.get(index).getButton().setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
    		  player.setSpotNum(spotValues.get(index));
    		  logicGame.changeStartButton(player, resultBtn);
    	  });
      }

      //DRAWINGS
      Label numDrawingLabel = new Label("Select the drawings to play");
      numDrawingLabel.setFont(Font.font("Arial", 16));
      numDrawingLabel.setTextFill(Color.BLACK);
      
      //create buttons for spot option
      ArrayList<Integer> drawingValues = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4));
      ArrayList<String> drawingLabels = new ArrayList<String>(Arrays.asList("1 Drawing", "2 Drawings", "3 Drawings", "4 Drawings"));
      GameButtons<Integer> drawings = new GameButtons<Integer>(4, drawingValues, drawingLabels);

      //setOnAction for each drawing button
      ArrayList<GameButton<Integer>> drawingObjButton = drawings.getObjButtons();
      for (int i = 0; i < 4; i++) {
    	  final int index = i;
    	  drawingObjButton.get(index).getButton().setOnAction(e->{
    		  drawings.reset();
			  drawingObjButton.get(index).setPressed(true);
			  drawingObjButton.get(index).getButton().setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
			  player.setDrawingNum(drawingObjButton.get(index).getValue());
			  logicGame.changeStartButton(player, resultBtn);
    	  });
      }
      
      //create buttons for all the bet cards
      GridPane betCardsGrid = new GridPane();
      betCardsGrid.setPadding(new Insets(20));
      betCardsGrid.setHgap(10);
      betCardsGrid.setVgap(10);
      
      ArrayList<Integer> betCardValues = new ArrayList<Integer>();
      ArrayList<String> betCardLabels = new ArrayList<String>();
      for (int i = 1; i <= 80; i++) {
    	  betCardValues.add(i);
    	  betCardLabels.add(Integer.toString(i));
      }
      
      GameButtons<Integer> betCards = new GameButtons<Integer>(80, betCardValues, betCardLabels);
      ArrayList<GameButton<Integer>> betCardsObjButton = betCards.getObjButtons();
      int indexBetCards = 0;
      for (int x = 0; x < 10; x++) {
    	  for (int y = 0; y < 8; y++) {
    		  betCardsGrid.add(betCardsObjButton.get(indexBetCards).getButton(), x, y);
    		  indexBetCards += 1;
    	  }
      }
      
      //setOnAction for each bet card button
      for (int i = 0; i < 80; i++) {
    	  final int index = i;
    	  betCardsObjButton.get(index).getButton().setOnAction(e->{
    		  if (player.getDrawingNum() != 0 && player.getSpotNum() != 0) {
				  if (betCardsObjButton.get(index).getPressed()) {
					  //update bet card's outlook and status
					  betCardsObjButton.get(index).setPressed(false);
					  betCardsObjButton.get(index).getButton().setBackground(new Background(new BackgroundFill(Color.web("#cfcfcf"), null, null)));
					  //update user's bet cards set
					  player.getPickedCards().remove(betCardsObjButton.get(index).getValue());
					  player.print();
				  }
				  else {
					  if (!player.full()) {
					  betCardsObjButton.get(index).setPressed(true);
					  betCardsObjButton.get(index).getButton().setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
					  //update user's bet cards set
					  player.getPickedCards().add(betCardsObjButton.get(index).getValue());
					  player.print();
					  }
				  }
    		  }
    		  logicGame.changeStartButton(player, resultBtn);
    		  
    	  });
      }
      
      //FRAME
      BorderPane gamePlayScreen = new BorderPane();
      gamePlayScreen.setTop(menu.getMenuBar());
      
      //create random generate button
      Button randomGeneratorBtn = new Button("Random Generator");
      randomGeneratorBtn.setPrefSize(200, 50);

      //setOnAction for random generate button
      randomGeneratorBtn.setOnAction(e -> {
		betCards.reset();
		player.setBetCards(logicGame.generateRandomNumbers());
		for (int playerPickCard:player.getPickedCards()) {
			betCardsObjButton.get(playerPickCard-1).setPressed(true);
			betCardsObjButton.get(playerPickCard-1).getButton().setBackground(new Background(new BackgroundFill(Color.GREEN, null, null)));
		}
		logicGame.changeStartButton(player, resultBtn);
		player.print();
		System.out.println(player.full());
      });
      
      //create random generate Box
      VBox randomGenVBox = new VBox(20);
      randomGenVBox.getChildren().add(randomGeneratorBtn);
      randomGenVBox.setAlignment(Pos.CENTER);
      
      //create spot buttons Box
      HBox spotButtonBox = new HBox(10);
      spotButtonBox.getChildren().addAll(spots.getButtons());
      spotButtonBox.setAlignment(Pos.CENTER);

      //create drawing buttons Box
      HBox drawingButtonBox = new HBox(10);
      drawingButtonBox.getChildren().addAll(drawings.getButtons());
      drawingButtonBox.setAlignment(Pos.CENTER);

      //create bet card buttons Box
      HBox betCardBox = new HBox();
      betCardBox.getChildren().add(betCardsGrid);
      betCardBox.setAlignment(Pos.CENTER);

      //integrate spot, drawing Boxes into a Box, called spot-drawing Box
      VBox optionBox = new VBox(10);
      optionBox.getChildren().addAll(numSpotLabel, spotButtonBox, numDrawingLabel, drawingButtonBox);
      optionBox.setAlignment(Pos.CENTER);


      
      //integrate spot-drawing Box with bet card buttons Box
      VBox optionFinalBox = new VBox(30);
      optionFinalBox.getChildren().addAll(optionBox, randomGeneratorBtn, betCardBox, resultBtn);
      optionFinalBox.setAlignment(Pos.CENTER);
      
      gamePlayScreen.setCenter(optionFinalBox);


      return new Scene(gamePlayScreen, 1000, 700);
   }
   
   
   
   private Scene DisplayResultScreen(Stage primaryStage, Player player, LogicGame logicGame, int numDrawing) {

	      //create a menu on the top 
	      menuGame menu = new menuGame();
	      
	      //pop-up screen when click rules in the menu
	      String rulesText = getRules();
	      Popup rulesPopUp = PopUpRules(rulesText);
	      menu.getRules().setOnAction(e -> {
	         rulesPopUp.show(primaryStage);
	      });


	      //pop-up screen when click odds in the menu
	      String oddsText= getOdds();
	      Popup oddsPopUp = PopUpRules(oddsText);
	      menu.getOdds().setOnAction(e -> {
	         oddsPopUp.show(primaryStage);
	         
	      });
	      
	      menu.getQuit().setOnAction(e->System.exit(0));
	      
		Label drawingTitle = new Label("Drawing - " + numDrawing);
		drawingTitle.setFont(new Font(30));

		Label winningNumbersLabel = new Label("Winning Numbers: ");
		winningNumbersLabel.setFont(new Font(20));
		
		String winningNumbersText = "  ";
		for (int each:logicGame.getGameBetCards()) {
			winningNumbersText += Integer.toString(each) + "  ";
		}
		
		TextField winningNumbersTextField = new TextField();

		// Set up a timeline to show each number one by one with a pause in between


		winningNumbersTextField.setFont(new Font(18));
		winningNumbersTextField.setAlignment(Pos.CENTER);
		winningNumbersTextField.setEditable(false);

		String playerNumbersText = "  ";
		for (int each:logicGame.getPlayerBetCards()) {
			playerNumbersText += Integer.toString(each) + "  ";
		}
		Label playerNumbersLabel = new Label("Player Numbers: ");
		playerNumbersLabel.setFont(new Font(20));
		TextField playerNumbersTextField = new TextField(playerNumbersText);
		playerNumbersTextField.setFont(new Font(18));
		playerNumbersTextField.setAlignment(Pos.CENTER);
		playerNumbersTextField.setEditable(false);

		TreeSet<Integer> matchingNumbers = logicGame.numMatching();
		String matchingNumbersText = "  ";
		for(int each:matchingNumbers) {
			matchingNumbersText += Integer.toString(each) + "  ";
		}

		Label matchingNumbersLabel = new Label("Matching Numbers: ");
		matchingNumbersLabel.setFont(new Font(20));
		TextField matchingNumbersTextField = new TextField("");
		matchingNumbersTextField.setFont(new Font(18));
		matchingNumbersTextField.setAlignment(Pos.CENTER);
		matchingNumbersTextField.setEditable(false);

		int prizeThisDrawing = logicGame.prize(matchingNumbers.size(), player.getSpotNum());
		player.setMoney(player.getMoney() + prizeThisDrawing - logicGame.gameCost(player.getSpotNum(), player.getDrawingNum()));
		String drawingStatsText = "Won: " + Integer.toString(prizeThisDrawing) + "  Total money: " + Integer.toString(player.getMoney());
		
		Label drawingStatsLabel = new Label("Drawing Results: ");
		drawingStatsLabel.setFont(new Font(20));
		TextField drawingStatsTextField = new TextField();
		drawingStatsTextField.setFont(new Font(18));
		drawingStatsTextField.setAlignment(Pos.CENTER);
		drawingStatsTextField.setEditable(false);
		drawingStatsTextField.setPrefWidth(20);

		
		Timeline timeline = new Timeline();
		int i1  = 0;
		for (int each:logicGame.getGameBetCards()) {
		    final int index = each;
		    KeyFrame keyFrame = new KeyFrame(Duration.seconds(i1), event -> {
		        winningNumbersTextField.setText(winningNumbersTextField.getText() + "  " + each);
		    });
		    i1++;
		    timeline.getKeyFrames().add(keyFrame);
		}
		
		// Add an OnFinished event to the timeline
		final String matchingText = matchingNumbersText;
		final String finalText = drawingStatsText;
		timeline.setOnFinished(event -> {
		    // Add your code here that you want to execute after the timeline has finished
		    matchingNumbersTextField.setText(matchingText);
		    drawingStatsTextField.setText(finalText);
		});

		// Play the timeline
		timeline.play();

		Button continueBtn = new Button("Continue to next Drawing");
		continueBtn.setPrefSize(300, 50);
		continueBtn.setFont(new Font(15));

		continueBtn.setOnAction(e -> {
			if (numDrawing == player.getDrawingNum())
				primaryStage.setScene(EndScreen(primaryStage, player, logicGame));
			else {
				logicGame.setGameBetCards(logicGame.generateRandomNumbers());
				primaryStage.setScene(DisplayResultScreen(primaryStage, player, logicGame, numDrawing+1));
				}
			});


		VBox root = new VBox(20);
		root.setAlignment(Pos.CENTER);
		root.setPadding(new Insets(20));
		root.getChildren().addAll(drawingTitle, winningNumbersLabel,winningNumbersTextField,
								  playerNumbersLabel, playerNumbersTextField,
								  matchingNumbersLabel, matchingNumbersTextField,
								  drawingStatsLabel, drawingStatsTextField, continueBtn);


		BorderPane resultScreen = new BorderPane();
		resultScreen.setTop(menu.getMenuBar());
		resultScreen.setCenter(root);

		return new Scene(resultScreen, 1000, 700);
	}
   
   private Scene EndScreen(Stage primaryStage, Player player, LogicGame logicGame) {
	      //create a menu on the top 
	      menuGame menu = new menuGame();
	      
	      //pop-up screen when click rules in the menu
	      String rulesText = getRules();
	      Popup rulesPopUp = PopUpRules(rulesText);
	      menu.getRules().setOnAction(e -> {
	         rulesPopUp.show(primaryStage);
	      });


	      //pop-up screen when click odds in the menu
	      String oddsText= getOdds();
	      Popup oddsPopUp = PopUpRules(oddsText);
	      menu.getOdds().setOnAction(e -> {
	         oddsPopUp.show(primaryStage);
	         
	      });
	      
	      menu.getQuit().setOnAction(e->System.exit(0));

       Label winningMoneyLabel = new Label("Total money in your pocket:  $ " + Integer.toString(player.getMoney()));
       winningMoneyLabel.setFont(new Font(25));
       Button anotherGameBtn = new Button("Play again");
       anotherGameBtn.setPrefSize(150, 50);
       anotherGameBtn.setStyle("-fx-background-color: Grey ; -fx-text-fill: white; -fx-font-size: 20px;");
       anotherGameBtn.setOnAction(e->{
    	   logicGame.reset();
    	   player.newGame();
    	   primaryStage.setScene(GamePlayScreen(primaryStage, player, logicGame));
       });

       Button exitBtn = new Button("Exit");
       exitBtn.setPrefSize(150, 50);
       exitBtn.setStyle("-fx-background-color: #cfcfcf; -fx-text-fill: white; -fx-font-size: 20px;");
       exitBtn.setOnAction(e->System.exit(0));

       VBox root = new VBox(40);
       root.setAlignment(Pos.CENTER);
       root.setPadding(new Insets(20));
       root.setSpacing(20);
       root.getChildren().addAll(winningMoneyLabel, anotherGameBtn, exitBtn);

       BorderPane resultScreen = new BorderPane();
       resultScreen.setCenter(root);
       resultScreen.setTop(menu.getMenuBar());

       return new Scene(resultScreen, 1000, 700);
   }


}