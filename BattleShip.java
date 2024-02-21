
 
import javafx.application.*;
import javafx.event.*;
import javafx.scene.control.*;
import javafx.scene.control.Labeled;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.image.*;
import javafx.scene.input.*;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;


public class BattleShip extends Application
{
   //Declare Menu fields.
   private Menu gameMenu;
   private Menu helpMenu;
   private MenuItem menuNew;
   private MenuItem menuExit;
   private MenuItem menuHelp;
   
   private RadioMenuItem menuEasy;
   private RadioMenuItem menuMedium;
   private RadioMenuItem menuHard;
   
   private Menu menuDifficulty;
   
   private MenuBar menuBar;
   
   //Declare variables for gameplay.  
   private int hits = 0;
   private int misses = 0;
   private int shipsRemaining;
   
   //Declare the game object.
   private BattleshipsGame game;
   //Declare array for the buttons.
   private Button[] btnArray;
   
   //Create image objects. 
   private Image space = new Image("images/space.png");
   private Image hit = new Image("images/HIT.png");
   private Image miss = new Image("images/Miss.png");
   private Image life = new Image("images/batteryLife.png");
   private Image notHit = new Image("images/notHit.png");
   
   //Create information labels.
   private Label lbl1;
   private Label lifeLbl;
   private Label lifeLbl2;
   private Label hitsLbl;
   private Label missesLbl;
   private Label shipsLbl;
   
   //Declare vbox for the lives remaining display and declare the stage.
   private VBox lifeBox;
   private Stage stage;
    
    
   public void start(Stage primaryStage)
   {
      stage = primaryStage;
     
      game = new BattleshipsGame();
      shipsRemaining = 6;
           
      Scene scene = new Scene(createBorderPane() );
      scene.getStylesheets().add("BattleShipCSS.css"); 
   
      primaryStage.setScene(scene); 
      primaryStage.setTitle("BattleSpaceShip");
      primaryStage.show();
   }
   
   //Method to create a borderpane.
   //This method contains other methods for creating the parts of the borderpane.
   private BorderPane createBorderPane()
   {
      BorderPane bPane = new BorderPane();
      bPane.setCenter(createPane() );      
      bPane.setBottom(createResultPane() ); 
      bPane.setLeft(createLivesPane() );
      bPane.setRight(createProgressPane() );
      
      VBox menuPane = new VBox();      
      menuPane.getChildren().add(createMenu() );
      bPane.setTop(menuPane);
      bPane.setId("bPane");
      
      return bPane;
   }
   
   //Method to create a VBox showing remaining lives on the left side of the BorderPane.
   private VBox createLivesPane()
   {
      lifeBox = new VBox();
      lifeBox.setAlignment(Pos.TOP_CENTER);
      lifeBox.setId("lifeBox"); 
      
      lifeLbl = new Label("   Shots  \nRemaining");
      lifeLbl.setTextFill(Color.RED);
      
      lifeBox.getChildren().addAll(lifeLbl);
      
      for(int i=0; i<game.getLives(); i++)
         lifeBox.getChildren().add(new ImageView(life) );
               
      return lifeBox;
   }
   
   //Method to update the lives if a shot misses.
   private void updateLives()
   {
      lifeBox.getChildren().clear();
      lifeBox.getChildren().addAll(lifeLbl);
      for(int i=0; i<game.getLives(); i++)
         lifeBox.getChildren().add(new ImageView(life) );
   }
   
   //Method to create a VBox showing hits, misses & ships remaining on the right side of the BorderPane.
   private VBox createProgressPane()
   {
      VBox progressBox = new VBox();
      progressBox.setAlignment(Pos.TOP_LEFT);
      progressBox.setId("lifeBox");
      hitsLbl = new Label("Hits: \n" + hits);
      hitsLbl.setTextFill(Color.RED);
      missesLbl = new Label("Misses: \n" + misses);
      missesLbl.setTextFill(Color.RED);
      shipsLbl = new Label("Remaining \nSpaceships: \n" + shipsRemaining);
      shipsLbl.setTextFill(Color.RED);
      progressBox.getChildren().addAll(hitsLbl, missesLbl, shipsLbl);
      
      return progressBox;
   }
   
   //Method to update the hits, misses & ships remaining.
   private void updateProgress()
   {
      hitsLbl.setText("Hits: \n" + hits);
      missesLbl.setText("Misses: \n" + misses);
      shipsLbl.setText("Remaining \nSpaceships: \n" + shipsRemaining);
   }
   
   /*Method to create a HBox informing the player of the outcome of their last move 
   at the bottom of the BorderPane.*/
   private HBox createResultPane()
   {
      HBox resultBox = new HBox(10);
      resultBox.setAlignment(Pos.CENTER);
      resultBox.setId("lifeBox");
      lbl1 = new Label("Outcome: ");
      lbl1.setTextFill(Color.RED);
      resultBox.getChildren().add(lbl1);
      return resultBox;
   }
   
   /*This method crates the 16 buttons used in the game. It also creates an array of the 16 button
   objects. The method creates a 4 column by 4 row TilePane and inserts the buttons into it. The 
   TilePane is then inserted into a Pane to keep the 4x4 layout. The Pane is then returned to the
   center of the BorderPane.*/
   private Pane createPane()
   {
      //Create array to hold buttons and a TilePane to act as a grid.
      btnArray = new  Button[16];
      TilePane tPane = new TilePane();
      tPane.setPrefColumns(4);
      tPane.setPrefRows(4);
      tPane.setPrefTileHeight(110);
      tPane.setPrefTileWidth(110);
      
      //Create 16 buttons, set size and action.
      for(int i=0; i<16; i++)
      {
         Button b = new Button ("" + i);
         btnArray[i] = b;
         b.setOnAction(e -> shootBtn(e) );
         b.setMinHeight(100);
         b.setPrefHeight(100);
         b.setMinWidth(100);
         b.setPrefWidth(100);
         b.setPadding(new Insets(3) );
        
         tPane.getChildren().add(b);//Add buttons to the TilePane.  
      }
      //
      Pane pane = new Pane();
      pane.getChildren().add(tPane);
      return pane;       
   }
   
   //Method creates the MenuBar at the top.
   private MenuBar createMenu()
   {
      //Create menu items for the game Menu.
      gameMenu = new Menu("_Game");
      menuNew = new MenuItem("_New");
      menuExit = new MenuItem("E_xit");
      
      //Set Actions for above MenuItems.
      menuNew.setOnAction(e -> newGame() );
      menuExit.setOnAction(e -> exit() );
      
      //Create RadioMenuItems for the Difficulty sub-menu.
      menuEasy = new RadioMenuItem("Easy");
      menuMedium = new RadioMenuItem("Medium");
      menuHard = new RadioMenuItem("Hard");
      menuMedium.setSelected(true); //Set medium as default difficulty.
      
      //Create and set toggleGroup for RadioMenuItems.
      ToggleGroup difficultyGroup = new ToggleGroup();
      menuEasy.setToggleGroup(difficultyGroup);
      menuMedium.setToggleGroup(difficultyGroup);
      menuHard.setToggleGroup(difficultyGroup);
   
      //Create the new submenu and add difficulty items to it.
      menuDifficulty = new Menu("_Difficulty");
      menuDifficulty.getItems().add(menuEasy);
      menuDifficulty.getItems().add(menuMedium);
      menuDifficulty.getItems().add(menuHard);
      
      helpMenu = new Menu("_Help");
      menuHelp = new MenuItem("Help");
      menuHelp.setOnAction(e -> help() );
      
      /*Add all MenuItems to the gameMenu with a SeparatorMenuItem. Then add the gameMenu to 
      a menuBar and return the menuBar.*/
      gameMenu.getItems().addAll(menuNew, new SeparatorMenuItem(), menuDifficulty, menuExit);
      helpMenu.getItems().add(menuHelp);
      menuBar = new MenuBar(gameMenu, helpMenu);
      return menuBar;
   }
   
   //Method which defines what happens when a button is clicked.
   private void shootBtn(ActionEvent e)
   {
      
      if((game.getLives() != 0) && (shipsRemaining >0) )
      {
         //get the button source
         Button b = (Button)e.getSource();
         //disable the button
         b.setDisable(true); 
         //Convert the text from the buttons from String to Integer.
         int btnVal = Integer.parseInt(b.getText());
         
         //Calculate the row and column based on the number of the button in the GUI.
         int r = btnVal/4;
         int c = btnVal %4;
         //Shoot the corresponding cell in battleshipgame using the shoot() method from the game.
         String output = game.shoot(r,c);
         
         //Display outcome of the shot to the player, at the bottom of the BorderPane.
         lbl1.setText("Outcome: " + output);
         
         //
         if (output.equals("Miss!"))
         {
            b.setGraphic(new ImageView(miss) );
            misses++;
            updateLives();
            updateProgress();
         }
         else 
         {
            b.setGraphic(new ImageView(hit) ); 
            hits++;
            shipsRemaining--;
            updateProgress();
         }
      }
      
      if(shipsRemaining == 0)
      {
         hits =0;
         misses =0;
         lbl1.setText("!!!Winner!!!");
         for(Button btn: btnArray) 
         {
            btn.setDisable(true);
         }
         Alert alert = new Alert(AlertType.INFORMATION, "!!!WINNER!!!");
         alert.showAndWait();
      } 
      
      if(game.getLives() == 0)
      {
         hits =0;
         misses =0;
         lbl1.setText("GAME OVER \n Use the game menu to start again.");
         for(Button btn: btnArray) 
         {
            int btnVal = Integer.parseInt(btn.getText() );
            int r = btnVal/4;
            int c = btnVal%4;
            if (game.checkIfShip(r,c) && (!btn.isDisabled()) )
               btn.setGraphic(new ImageView(notHit) ); 
               
            btn.setDisable(true);                
         }   
      }             
   }//shootBtn()
   

   

   
   private void exit()
   {
      stage.close();
   }
   
   private BattleshipsGame newGame() //Alertbox if restart mid-game
   {
      if(game.getLives() > 0)
      {  
         //CONFIRMATION Dialog
         Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to abandon this game?");
              
         //alert.showAndWait();
         if (alert.showAndWait().get() == ButtonType.OK)
         {
            for (Button b: btnArray)
            {
               b.setGraphic(null);
               b.setDisable(false);
            }
            if(menuEasy.isSelected() )
            {
               game = new BattleshipsGame(10, 6);
               shipsRemaining = 6;
               updateLives();
               updateProgress();
               return game;
            }
            else if(menuHard.isSelected() )
            {
               game = new BattleshipsGame(5, 6);
               shipsRemaining = 6;
               updateLives();
               updateProgress();
               return game;
            }
            else
            {
               game = new BattleshipsGame();
               shipsRemaining = 6;
               updateLives();
               updateProgress();
               return game;
            }
         } 
      }
      else
      {
         for (Button b: btnArray)
         {
            b.setGraphic(null);
            b.setDisable(false);
         }
         if(menuEasy.isSelected() )
         {
            game = new BattleshipsGame(10, 6);
            updateLives();
            updateProgress();
            return game;
         }
         else if(menuHard.isSelected() )
         {
            game = new BattleshipsGame(5, 6);
            updateLives();
            updateProgress();
            return game;
         }
         else
         {
            game = new BattleshipsGame();
            updateLives();
            updateProgress();
            return game;
         }
      }
      return game;   
   }
   
    private void help()          
   {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("Game Instructions");
      //alert.setHeaderText("This is an Information Dialog");
      //alert.setHeaderText("this is the header text"); //without header text
      alert.setHeaderText(null); 
      alert.setContentText("1. You are flying a spaceship. \n2. Enemy ships are hiding behind some of the panels on the grid. "
                              + "\n3. Click a number to shoot your lazer. \n4. When you hit a ship you absorb their power and don't lose lazer battery charge. "
                              + "\n5. When you miss you lose battery charge. \n6. Charges are shown on the left. When you have no more charges you are dead in the proverbial water.");
      alert.showAndWait();
   }
}