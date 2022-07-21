//Mustafa Said Çanak	150120020
//MOHAMAD NAEL AYOUBI	150120997
package application;

import java.io.File;

import java.io.FileNotFoundException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

public class UserInterface extends Application {
	private int levelCounter = 1; // will be used to count level number
	private double firstX, firstY, draggedX, draggedY; // will be used to keep mouse locations
	private int row, col; // will be used to store tiles location while moving
	private final double size = 97.5; // is the size of tile edges
	private static Tile[][] tiles; // will be used to store tiles
	private Tile pressedTile;// will be used to make motion
	private static GridPane grid;// will be used to locate tiles
	private int moves = 0;// will be used to store number of moves
	private Label numOfMoves;// will be used to show number of moves
	private BorderPane gameBoard;// will be used to create gameboard
	private HBox bottomBox; // will be used to create bottom side of the interface
	private Button nxtLvl; // will be used to let the user change level
	private Label lvl;// will be used to show level

	@Override
	public void start(Stage primaryStage) {

		lvl = new Label("Level " + levelCounter);
		lvl.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));

		numOfMoves = new Label("Number of Moves: " + moves);
		numOfMoves.setFont(Font.font("Verdana", FontWeight.BOLD, FontPosture.REGULAR, 13));

		bottomBox = new HBox();
		grid = new GridPane();
		gameBoard = new BorderPane();

		// setting grid pane
		grid.setMaxSize(400, 400);
		grid.setMinSize(400, 400);
		grid.setVgap(2);
		grid.setHgap(2);
		grid.setStyle("-fx-border-color: black; -fx-background-color: gray");

		// setting bottom box under grid
		bottomBox.setPadding(new Insets(10, 10, 10, 10));
		bottomBox.getChildren().addAll(numOfMoves);
		numOfMoves.setPadding(new Insets(5, 191, 5, 5));

		// setting boarder pane as the whole interface
		gameBoard.setPadding(new Insets(15, 20, 15, 20));
		gameBoard.setBottom(bottomBox);
		gameBoard.setTop(lvl);
		gameBoard.setCenter(grid);
		gameBoard.setStyle("-fx-border-color: black; -fx-background-color: orange");
		BorderPane.setAlignment(lvl, Pos.CENTER);

		// constructing tiles based on the input file
		tiles = constructLevel();
		// set height and width values of the tiles one by one
		for (Tile[] tiles : tiles) {
			for (Tile tile : tiles) {
				tile.getTileImg().setFitHeight(size);
				tile.getTileImg().setFitWidth(size);
			}
		}
		// show the gameboard
		showGameBoard();

		// take the tile that user pressed
		gameBoard.setOnMousePressed(e -> {

			// store the coordinates of the cursor
			firstX = e.getX();
			firstY = e.getY();

			// calculate the row and column
			row = (int) ((firstY - 50) / size);
			col = (int) ((firstX - 50) / size);

			// take the tile that user pressed
			if (row < 4 && row >= 0 && col < 4 && col >= 0) {
				pressedTile = tiles[row][col];
			}
		});

		// move the tile that user drags
		gameBoard.setOnMouseDragged(e -> {

			// keep storing the coordinates of the cursor on the motion
			draggedX = e.getX();
			draggedY = e.getY();
			// if the pipe can move and the user drag till half of the next tile then move
			// it
			if (pressedTile.getType().equalsIgnoreCase("Pipe") || (pressedTile.getType().equalsIgnoreCase("Empty"))) {
				if (firstX - draggedX > size / 2 && col > 0) {
					moveLeft(pressedTile, row, col);
				} else if (draggedX - firstX > size / 2 && col < 3) {
					moveRight(pressedTile, row, col);
				} else if (firstY - draggedY > size / 2 && row > 0) {
					moveUp(pressedTile, row, col);
				} else if (draggedY - firstY > size / 2 && row < 3) {
					moveDown(pressedTile, row, col);
				}
			}
		});

		// setting the pane on top of scene then the scene on top of stage
		Scene scene = new Scene(gameBoard, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Pipe Tiles");
		primaryStage.getIcons().add(new Image("EndV.png"));
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	// constructing tiles based on the input file, returns an array of tiles
	public Tile[][] constructLevel() {
		// keep the tiles in an array
		Tile[][] tiles = new Tile[4][4];
		// take the file
		File input = new File("level" + levelCounter + ".txt");
		int i = 0, j = 0;// will be used to read file and create tile objects
		try {
			// read file
			Scanner scan = new Scanner(input);
			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				if (!line.isEmpty()) {
					String[] attributes = line.split(",");
					tiles[i][j] = inputReader(Integer.parseInt(attributes[0]), attributes[1], attributes[2]);
					j++;
					if (j == 4) {
						j = 0;
						i++;
					}
				}
			}
			scan.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return tiles;
	}

	// returns a subtype object of type Tile
	public Tile inputReader(int id, String type, String property) {
		Tile newObject = null;
		switch (type) {
		case "Starter":
			newObject = new Starter(id, type, property);
			return newObject;
		case "End":
			newObject = new End(id, type, property);
			return newObject;
		case "PipeStatic":
			newObject = new PipeStatic(id, type, property);
			return newObject;
		case "Empty":
			newObject = new Empty(id, type, property);
			return newObject;
		case "Pipe":
			newObject = new Pipe(id, type, property);
			return newObject;
		default:
			return newObject;
		}
	}

	// add all the tiles into the GridPane
	public void showGameBoard() {
		grid.add(tiles[0][0].getTileImg(), 0, 0);
		grid.add(tiles[0][1].getTileImg(), 1, 0);
		grid.add(tiles[0][2].getTileImg(), 2, 0);
		grid.add(tiles[0][3].getTileImg(), 3, 0);
		grid.add(tiles[1][0].getTileImg(), 0, 1);
		grid.add(tiles[1][1].getTileImg(), 1, 1);
		grid.add(tiles[1][2].getTileImg(), 2, 1);
		grid.add(tiles[1][3].getTileImg(), 3, 1);
		grid.add(tiles[2][0].getTileImg(), 0, 2);
		grid.add(tiles[2][1].getTileImg(), 1, 2);
		grid.add(tiles[2][2].getTileImg(), 2, 2);
		grid.add(tiles[2][3].getTileImg(), 3, 2);
		grid.add(tiles[3][0].getTileImg(), 0, 3);
		grid.add(tiles[3][1].getTileImg(), 1, 3);
		grid.add(tiles[3][2].getTileImg(), 2, 3);
		grid.add(tiles[3][3].getTileImg(), 3, 3);
	}

	// set the positions of the tiles according to movement
	public void setGameBoard() {
		GridPane.setConstraints(tiles[0][0].getTileImg(), 0, 0);
		GridPane.setConstraints(tiles[0][1].getTileImg(), 1, 0);
		GridPane.setConstraints(tiles[0][2].getTileImg(), 2, 0);
		GridPane.setConstraints(tiles[0][3].getTileImg(), 3, 0);
		GridPane.setConstraints(tiles[1][0].getTileImg(), 0, 1);
		GridPane.setConstraints(tiles[1][1].getTileImg(), 1, 1);
		GridPane.setConstraints(tiles[1][2].getTileImg(), 2, 1);
		GridPane.setConstraints(tiles[1][3].getTileImg(), 3, 1);
		GridPane.setConstraints(tiles[2][0].getTileImg(), 0, 2);
		GridPane.setConstraints(tiles[2][1].getTileImg(), 1, 2);
		GridPane.setConstraints(tiles[2][2].getTileImg(), 2, 2);
		GridPane.setConstraints(tiles[2][3].getTileImg(), 3, 2);
		GridPane.setConstraints(tiles[3][0].getTileImg(), 0, 3);
		GridPane.setConstraints(tiles[3][1].getTileImg(), 1, 3);
		GridPane.setConstraints(tiles[3][2].getTileImg(), 2, 3);
		GridPane.setConstraints(tiles[3][3].getTileImg(), 3, 3);
	}

	// move left if the tile that is on the left side of the pressedTile is an Free
	// tile and update moves value
	public void moveLeft(Tile pressedTile, int row, int col) {
		if (tiles[row][col - 1].getProperty().equalsIgnoreCase("Free")) {
			Tile tempTile = tiles[row][col - 1];
			tiles[row][col - 1] = pressedTile;
			tiles[row][col] = tempTile;
			moves++;
			numOfMoves.setText("Number of Moves: " + moves);
			setGameBoard();
			// when mouse released check the level whether it is completed or not
			gameBoard.setOnMouseReleased((MouseEvent e) -> {
				if (isCompleted(levelCounter)) {
					// create and show the next level button if the level is completed
					nxtLvl = new Button("Next Level");
					nxtLvl.setStyle("-fx-board-color: black");
					bottomBox.getChildren().add(nxtLvl); // add the button to the gameboard
					// go to next level if the user press the next level button
					nxtLvl.setOnAction(a -> {
						bottomBox.getChildren().remove(nxtLvl);
						moves = 0;
						numOfMoves.setText("Number of Moves: " + moves);
						levelCounter++;
						if (levelCounter > 6)
							System.exit(0);
						lvl.setText("Level " + levelCounter);
						tiles = constructLevel();
						for (Tile[] tiles : tiles) {
							for (Tile tile : tiles) {
								tile.getTileImg().setFitHeight(97.5);
								tile.getTileImg().setFitWidth(97.5);
							}
						}
						showGameBoard();
					});
				}
			});
		}
	}

	// move right if the tile that is on the right side of the pressedTile is an
	// Free tile and update moves value
	public void moveRight(Tile pressedTile, int row, int col) {
		if (tiles[row][col + 1].getProperty().equalsIgnoreCase("Free")) {
			Tile tempTile = tiles[row][col + 1];
			tiles[row][col + 1] = pressedTile;
			tiles[row][col] = tempTile;
			moves++;
			numOfMoves.setText("Number of Moves: " + moves);
			setGameBoard();
			// when mouse released check the level whether it is completed or not
			gameBoard.setOnMouseReleased((MouseEvent e) -> {
				if (isCompleted(levelCounter)) {
					// create and show the next level button if the level is completed
					nxtLvl = new Button("Next Level");
					nxtLvl.setStyle("-fx-board-color: black");
					bottomBox.getChildren().add(nxtLvl); // add the button to the gameboard
					// go to next level if the user press the next level button
					nxtLvl.setOnAction(a -> {
						bottomBox.getChildren().remove(nxtLvl);
						moves = 0;
						numOfMoves.setText("Number of Moves: " + moves);
						levelCounter++;
						if (levelCounter > 6)
							System.exit(0);
						lvl.setText("Level " + levelCounter);
						tiles = constructLevel();
						// set height and width values of the tiles one by one
						for (Tile[] tiles : tiles) {
							for (Tile tile : tiles) {
								tile.getTileImg().setFitHeight(97.5);
								tile.getTileImg().setFitWidth(97.5);
							}
						}
						showGameBoard();
					});
				}
			});
		}
	}

	// move up if the tile that is on the up side of the pressedTile is an Free tile
	// and update moves value
	public void moveUp(Tile pressedTile, int row, int col) {
		if (tiles[row - 1][col].getProperty().equalsIgnoreCase("Free")) {
			Tile tempTile = tiles[row - 1][col];
			tiles[row - 1][col] = pressedTile;
			tiles[row][col] = tempTile;
			moves++;
			numOfMoves.setText("Number of Moves: " + moves);
			setGameBoard();
			// when mouse released check the level whether it is completed or not
			gameBoard.setOnMouseReleased((MouseEvent e) -> {
				if (isCompleted(levelCounter)) {
					// create and show the next level button if the level is completed
					nxtLvl = new Button("Next Level");
					nxtLvl.setStyle("-fx-board-color: black");
					bottomBox.getChildren().add(nxtLvl); // add the button to the gameboard
					// go to next level if the user press the next level button
					nxtLvl.setOnAction(a -> {
						bottomBox.getChildren().remove(nxtLvl);
						moves = 0;
						numOfMoves.setText("Number of Moves: " + moves);
						levelCounter++;
						if (levelCounter > 6)
							System.exit(0);
						lvl.setText("Level " + levelCounter);
						tiles = constructLevel();
						// set height and width values of the tiles one by one
						for (Tile[] tiles : tiles) {
							for (Tile tile : tiles) {
								tile.getTileImg().setFitHeight(97.5);
								tile.getTileImg().setFitWidth(97.5);
							}
						}
						showGameBoard();
					});
				}
			});
		}
	}

	// move down if the tile that is on the down side of the pressedTile is an Free
	// tile and update moves value
	public void moveDown(Tile pressedTile, int row, int col) {
		if (tiles[row + 1][col].getProperty().equalsIgnoreCase("Free")) {
			Tile tempTile = tiles[row + 1][col];
			tiles[row + 1][col] = pressedTile;
			tiles[row][col] = tempTile;
			moves++;
			numOfMoves.setText("Number of Moves: " + moves);
			setGameBoard();
			// when mouse released check the level whether it is completed or not
			gameBoard.setOnMouseReleased(e -> {
				if (isCompleted(levelCounter)) {
					// create and show the next level button if the level is completed
					nxtLvl = new Button("Next Level");
					nxtLvl.setStyle("-fx-board-color: black");
					bottomBox.getChildren().add(nxtLvl); // add the button to the gameboard
					// go to next level if the user press the next level button
					nxtLvl.setOnAction(a -> {
						bottomBox.getChildren().remove(nxtLvl);
						moves = 0;
						numOfMoves.setText("Number of Moves: " + moves);
						levelCounter++;
						if (levelCounter > 6)
							System.exit(0);
						lvl.setText("Level " + levelCounter);
						tiles = constructLevel();
						// set height and width values of the tiles one by one
						for (Tile[] tiles : tiles) {
							for (Tile tile : tiles) {
								tile.getTileImg().setFitHeight(97.5);
								tile.getTileImg().setFitWidth(97.5);
							}
						}
						showGameBoard();
					});
				}
			});
		}
	}
	
	// check whether level is completed or not by checking each level one by one
	public boolean isCompleted(int level) {
		switch (level) {
		case 1:
		case 2:
		case 3:
			if ((tiles[1][0].getType().equalsIgnoreCase("Pipe")
					&& tiles[1][0].getProperty().equalsIgnoreCase("Vertical"))
					&& (tiles[2][0].getType().equalsIgnoreCase("Pipe")
							&& tiles[2][0].getProperty().equalsIgnoreCase("Vertical"))
					&& (tiles[3][0].getType().equalsIgnoreCase("Pipe")
							&& tiles[3][0].getProperty().equalsIgnoreCase("01"))
					&& (tiles[3][1].getType().equalsIgnoreCase("Pipe")
							&& tiles[3][1].getProperty().equalsIgnoreCase("Horizontal")))
				return true;
		case 4:
			if ((tiles[2][0].getType().equalsIgnoreCase("Pipe") && tiles[2][0].getProperty().equalsIgnoreCase("01"))
					&& (tiles[2][1].getType().equalsIgnoreCase("Pipe")
							&& tiles[2][1].getProperty().equalsIgnoreCase("Horizontal"))
					&& (tiles[2][2].getType().equalsIgnoreCase("Pipe")
							&& tiles[2][2].getProperty().equalsIgnoreCase("Horizontal"))
					&& (tiles[2][3].getType().equalsIgnoreCase("Pipe")
							&& tiles[2][3].getProperty().equalsIgnoreCase("00")))
				return true;
		case 5:
			if ((tiles[1][0].getType().equalsIgnoreCase("Pipe")
					&& tiles[1][0].getProperty().equalsIgnoreCase("Vertical"))
					&& (tiles[2][1].getType().equalsIgnoreCase("Pipe")
							&& tiles[2][1].getProperty().equalsIgnoreCase("Horizontal"))
					&& (tiles[2][2].getType().equalsIgnoreCase("Pipe")
							&& tiles[2][2].getProperty().equalsIgnoreCase("Horizontal"))
					&& (tiles[2][3].getType().equalsIgnoreCase("Pipe")
							&& tiles[2][3].getProperty().equalsIgnoreCase("00")))
				return true;
		case 6:
			if (tiles[3][1].getType().equalsIgnoreCase("Pipe") && tiles[3][1].getProperty().equalsIgnoreCase("01"))
				return true;
		default:
			return false;
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}