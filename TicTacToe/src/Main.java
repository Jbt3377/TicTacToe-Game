import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class Main extends Application{

    // Defining Scenes and Stage
    private Stage window;
    private Scene scene1, scene2, scene3;
    private String username1, username2;
    private boolean whosTurn = true;

    @Override
    public void start(Stage primaryStage){
        // Main Window
        window = primaryStage;
        primaryStage.setTitle("TicTacToe Game");



        // == MENU SCENE ==

        // Menu - Scene Layout
        GridPane menuGrid = new GridPane();
        menuGrid.setPadding(new Insets(10,10,10,10));
        menuGrid.setVgap(8);
        menuGrid.setHgap(8);


        // Menu - Scene Title
        Label gameTitle = new Label("Tic Tac Toe");
        gameTitle.setStyle("-fx-font-size: 16pt; -fx-font-weight: bold;");
        GridPane.setConstraints(gameTitle, 0, 0);


        // Menu - Start Button
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> window.setScene(scene2));
        GridPane.setConstraints(startButton, 0, 1);


        // Menu - Quit Button
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(e -> window.close());
        GridPane.setConstraints(quitButton, 0, 2);
        // GameOutcomeAlert.display(title, message)


        // Menu - Layout and Widget Addition
        menuGrid.getChildren().addAll(gameTitle, startButton, quitButton);
        scene1 = new Scene(menuGrid, 300, 200);





        // == PLAYERS SCENE ==

        // Players - Scene Layout
        GridPane playersGrid = new GridPane();
        playersGrid.setPadding(new Insets(10,10,10,10));
        playersGrid.setVgap(8);
        playersGrid.setHgap(8);


        // Players - Scene Title
        Label playerTitle = new Label("Player Options");
        playerTitle.setStyle("-fx-font-size: 16pt; -fx-font-weight: bold;");
        GridPane.setConstraints(playerTitle, 0, 0);


        // Players - Player 1 Label and Text Field
        Label player1Label = new Label("Player 1 Name:");
        GridPane.setConstraints(player1Label, 0, 1);
        TextField name1Input = new TextField();
        GridPane.setConstraints(name1Input, 1, 1);


        // Players - Player 2 Label and Text Field
        Label player2Label = new Label("Player 2 Name:");
        GridPane.setConstraints(player2Label, 0, 2);
        TextField name2Input = new TextField();
        GridPane.setConstraints(name2Input, 1, 2);


        // Players - Back Button
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> window.setScene(scene1));
        GridPane.setConstraints(backButton, 1, 3);


        // Players - Start Game Button
        Button startGame = new Button("Start Game");
        startGame.setOnAction(e -> window.setScene(scene3));
        GridPane.setConstraints(startGame, 0, 3);
        startGame.setDisable(true);


        // Players - TextField Listeners
        // If the contents in either Textfield 1 or Textfield 2 change,
        // they will run the validateNames method and enable/disable the start game button accordingly


        // TextField 1 Listener
        name1Input.textProperty().addListener(((observable, oldValue, newValue) -> {
            // Function checks if usernames are valid
            boolean enableButton = validateName(name1Input, name2Input, name1Input.getText(), name2Input.getText());

            // If both usernames are valid, start game button enabled
            startGame.setDisable(!enableButton);

            // If the usernames are valid, they are stored in variables username1 and username2
            if(enableButton){
                username1 = name1Input.getText();
                username2 = name2Input.getText();
            }
        }));


        // TextField 2 Listener
        name2Input.textProperty().addListener(((observable, oldValue, newValue) -> {
            // Function checks if usernames are valid
            boolean enableButton = validateName(name1Input, name2Input, name1Input.getText(), name2Input.getText());

            // If both usernames are valid, start game button enabled
            startGame.setDisable(!enableButton);

            // If the usernames are valid, they are stored in variables username1 and username2
            if(enableButton){
                username1 = name1Input.getText();
                username2 = name2Input.getText();
            }
        }));


        // Players - Layout and Widget Addition
        playersGrid.getChildren().addAll(playerTitle, player1Label, name1Input, player2Label, name2Input, startGame,
                backButton);
        scene2 = new Scene(playersGrid, 400, 200);





        // == Tic Tac Toe Game Scene ==

        // Game - Scene Layout
        GridPane gameGrid = new GridPane();
        gameGrid.setPadding(new Insets(10,10,10,10));
        gameGrid.setVgap(8);
        gameGrid.setHgap(8);


        // Game - Scene Title
        Label tttTitle = new Label("TicTacToe");
        tttTitle.setStyle("-fx-font-size: 16pt; -fx-font-weight: bold;");
        GridPane.setConstraints(tttTitle, 3, 0);


        // Game - Player 1 Label and Side Text Field
        Label tttPlayer1Name = new Label(username1);
        GridPane.setConstraints(tttPlayer1Name, 0, 1);
        Label tttPlayer1Faction = new Label("X");
        GridPane.setConstraints(tttPlayer1Faction, 1, 1);


        // Game - Player 2 Label and Side Text Field
        Label tttPlayer2Name = new Label(username2);
        GridPane.setConstraints(tttPlayer2Name, 5, 1);
        Label tttPlayer2Faction = new Label("O");
        GridPane.setConstraints(tttPlayer2Faction, 6, 1);

        // Game - TicTacToe Grid of Buttons
        // > Top Left
        Button topLeft = new Button(" ");
        topLeft.textProperty().addListener(((observable, oldValue, newValue) -> {
            buttonClick(topLeft, whosTurn);
        }));
        topLeft.setMinSize(150,150);
        GridPane.setConstraints(topLeft, 2, 1);

        // > Top Middle
        Button topMiddle = new Button(" ");
        topMiddle.textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean enableButton = validateName(name1Input, name2Input, name1Input.getText(), name2Input.getText());
            startGame.setDisable(!enableButton);
        }));
        topMiddle.setMinSize(150,150);
        GridPane.setConstraints(topMiddle, 3, 1);

        // > Top Right
        Button topRight = new Button(" ");
        topRight.textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean enableButton = validateName(name1Input, name2Input, name1Input.getText(), name2Input.getText());
            startGame.setDisable(!enableButton);
        }));
        topRight.setMinSize(150,150);
        GridPane.setConstraints(topRight, 4, 1);

        // > Middle Left
        Button middleLeft = new Button(" ");
        middleLeft.textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean enableButton = validateName(name1Input, name2Input, name1Input.getText(), name2Input.getText());
            startGame.setDisable(!enableButton);
        }));
        middleLeft.setMinSize(150,150);
        GridPane.setConstraints(middleLeft, 2, 2);

        // > Middle Middle
        Button middleMiddle = new Button(" ");
        middleMiddle.textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean enableButton = validateName(name1Input, name2Input, name1Input.getText(), name2Input.getText());
            startGame.setDisable(!enableButton);
        }));
        middleMiddle.setMinSize(150,150);
        GridPane.setConstraints(middleMiddle, 3, 2);

        // > Middle Right
        Button middleRight = new Button(" ");
        middleRight.textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean enableButton = validateName(name1Input, name2Input, name1Input.getText(), name2Input.getText());
            startGame.setDisable(!enableButton);
        }));
        middleRight.setMinSize(150,150);
        GridPane.setConstraints(middleRight, 4, 2);

        // > Bottom Left
        Button bottomLeft = new Button(" ");
        bottomLeft.textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean enableButton = validateName(name1Input, name2Input, name1Input.getText(), name2Input.getText());
            startGame.setDisable(!enableButton);
        }));
        bottomLeft.setMinSize(150,150);
        GridPane.setConstraints(bottomLeft, 2, 3);

        // > Bottom Middle
        Button bottomMiddle = new Button(" ");
        bottomMiddle.textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean enableButton = validateName(name1Input, name2Input, name1Input.getText(), name2Input.getText());
            startGame.setDisable(!enableButton);
        }));
        bottomMiddle.setMinSize(150,150);
        GridPane.setConstraints(bottomMiddle, 3, 3);

        // > Bottom Right
        Button bottomRight = new Button(" ");
        bottomRight.textProperty().addListener(((observable, oldValue, newValue) -> {
            boolean enableButton = validateName(name1Input, name2Input, name1Input.getText(), name2Input.getText());
            startGame.setDisable(!enableButton);
        }));
        bottomRight.setMinSize(150,150);
        GridPane.setConstraints(bottomRight, 4, 3);




        // Game - Layout and Widget Addition
        gameGrid.getChildren().addAll(tttTitle, tttPlayer1Name, tttPlayer1Faction, tttPlayer2Name, tttPlayer2Faction,
                topLeft,topMiddle, topRight, middleLeft, middleMiddle, middleRight, bottomLeft, bottomMiddle,
                bottomRight);
        scene3 = new Scene(gameGrid, 600, 600);


        // Launch Game
        scene1.getStylesheets().add("Blackout.css");
        scene2.getStylesheets().add("Blackout.css");
        scene3.getStylesheets().add("Blackout.css");
        window.setScene(scene1);
        window.setTitle("TicTacToe");
        window.show();


    }

    /**
     * Method is used to check if both usernames are valid.
     * @param textfield1 takes in the first textfield
     * @param textfield2 takes in the second textfield
     * @param text1 takes in the contents of text field 1
     * @param text2 takes in the contents of text field 2
     * @return returns a boolean true or false value. If true, start button can be enabled and both
     * usernames are valid for the game to start.
     */
    private boolean validateName(TextField textfield1, TextField textfield2, String text1, String text2){
        // Defines local variables
        boolean username1Valid = false;
        boolean username2Valid = false;

        // Decides if current contents in text field 1 are a valid Username
        if(text1.length()>=2 && text1.length()<=18){
            // Username1 valid - Username1 set to green text
            textfield1.setStyle("-fx-text-fill: green");
            username1Valid = true;

        }else{
            // Username1 not valid - Username2 set to red text
            textfield1.setStyle("-fx-text-fill: red");
        }


        // Decides if current contents in text field 2 are a valid Username
        if(text2.length()>=2 && text2.length()<=18){
            // Username2 valid - Username2 set to green text
            textfield2.setStyle("-fx-text-fill: green");
            username2Valid = true;
        }else{
            // Username2 not valid - Username2 set to red text
            textfield2.setStyle("-fx-text-fill: red");
        }


        // Checks to see if the start game button should be enabled
        return(username1Valid && username2Valid);
    }

    /**
     * Method will run each time a Button is clicked on the Tic Tac Toe Grid.
     * The method will;
     *      1) Check whos turn it is,
     *      2) Change the button text to X or O (depending on whos turn it is),
     *      3) Disable the button so it cannot be clicked until the game has reset,
     *      4) Check if the game has been won,
     *      5) a)If the game has been won/draw, the grid is reset and the winning players score is increased if appropriate
     *         b)Popup window will inform users of outcome
     *      6) If the game hasn't been won yet, the turn is inverted
     *      7) Turn variable is returned
     * @param buttonClicked Takes in the button object that has been clicked
     * @param playerTurn    Takes in the boolean variable keeping track of which player's turn it is
     * @return              Returns an inverted boolean value if game has won, and the same boolean state if the game
     *                      has been won.
     */
    private boolean buttonClick(Button buttonClicked, boolean playerTurn){
        // Variables
        String player;
        boolean gameWon = false;

        // Check which players turn
        if(playerTurn){
            player = "X";
        }else{
            player = "O";
        }

        // Change button text
        buttonClicked.setText(player);

        // disable clicked button
        buttonClicked.setDisable(true);

        // Win Check


        // If won, output popup window
        // end game
        // reset widgets

        // else
        // invert turn
        return false;
    }


    public static void main(String[] args) {
        launch(args);       // Method inside application class - Sets program up as javafx application
    }
}
