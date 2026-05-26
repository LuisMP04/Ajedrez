package gui;

import Motor.Motor;
import Piezas.Pieza;
import Tablero.Movimiento;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.animation.TranslateTransition;
import javafx.animation.Interpolator;
import javafx.util.Duration;
import javafx.application.Platform;

public class Main extends Application 
{
    private static final int SIZE = 8;
    private static final int TILE_SIZE = 80;

    private StackPane[][] tiles = new StackPane[8][8];
    private Rectangle[][] squares = new Rectangle[8][8];
    
    // Matriz para guardar la referencia visual de los textos de las piezas
    private Text[][] pieceViews = new Text[8][8];

    // selección
    private int selectedRow = -1;
    private int selectedCol = -1;
    private boolean hasSelection = false;
    private boolean animating = false; // Bloqueo de clics durante la animación

    // motor y capas
    private Motor motor = new Motor();
    private GridPane board = new GridPane();
    private Pane pieceLayer = new Pane(); // Capa superior para que las piezas se muevan libremente
    private Stage stage;
    private int turno = 0;

    @Override
    public void start(Stage stage) 
    {
        this.stage = stage;

        createBoard();

        for (int i = 0; i < SIZE; i++) 
        {
            board.getRowConstraints().add(new RowConstraints(TILE_SIZE));
            board.getColumnConstraints().add(new ColumnConstraints(TILE_SIZE));
        }

        // Usamos un StackPane raíz para encimar la capa de piezas sobre el tablero
        StackPane root = new StackPane(board, pieceLayer);
        
        // Hacemos que toda la capa de piezas sea transparente al mouse
        pieceLayer.setMouseTransparent(true); 

        Scene scene = new Scene(root);

        stage.setTitle("Ajedrez - Lemposo");
        stage.setScene(scene);
        stage.show();

        updateBoard();
    }

    // =========================
    // TABLERO FIJO
    // =========================
    private void createBoard() 
    {
        for (int row = 0; row < SIZE; row++) 
        {
            for (int col = 0; col < SIZE; col++) 
            {

                StackPane tile = new StackPane();
                tiles[row][col] = tile;

                tile.setMinSize(TILE_SIZE, TILE_SIZE);
                tile.setMaxSize(TILE_SIZE, TILE_SIZE);
                tile.setPrefSize(TILE_SIZE, TILE_SIZE);

                Rectangle square = new Rectangle(TILE_SIZE, TILE_SIZE);
                squares[row][col] = square;

                square.setStroke(Color.WHITE);
                square.setStrokeWidth(1);

                boolean isLight = (row + col) % 2 == 0;
                square.setFill(isLight ? Color.web("#DBFFFC") : Color.web("#E4B3FF"));

                tile.getChildren().add(square);

                final int r = row;
                final int c = col;

                tile.setOnMouseClicked(e -> handleClick(r, c));

                board.add(tile, col, row);
            }
        }
    }

    // =========================
    // CLICK
    // =========================
    private void handleClick(int row, int col) 
    {
        if (animating) return; // Evita clics locos mientras se mueve una pieza

        Pieza p = motor.getPieza(row, col);

        // seleccionar pieza
        if (!hasSelection) 
        {
            if (p == null) return;

            // SOLO permitir seleccionar piezas del turno actual
            if(p.getBando() != turno)
            {
                return;
            }

            selectedRow = row;
            selectedCol = col;
            hasSelection = true;

            updateBoard();
            return;
        }

        // validar movimiento
        ArrayList<Movimiento> movimientos = motor.getMovimientos(selectedRow, selectedCol);
        boolean valido = false;

        for (Movimiento m : movimientos) 
        {
            if (m.i == row && m.j == col) 
            {
                valido = true;
                break;
            }
        }

        if (valido) 
        {
            animateMovement(selectedRow, selectedCol, row, col);
        } 
        else 
        {
            // Si hace clic en otra de sus propias piezas, cambiamos la selección directamente
            if (p != null) 
            {
                selectedRow = row;
                selectedCol = col;
                hasSelection = true;
            } 
            else 
            {
                hasSelection = false;
                selectedRow = -1;
                selectedCol = -1;
            }
            updateBoard();
        }
    }

    // =========================
    // ANIMACIÓN EASY IN-OUT
    // =========================
    private void animateMovement(int fromRow, int fromCol, int toRow, int toCol) 
    {
        Text movingPiece = pieceViews[fromRow][fromCol];

        if (movingPiece == null) 
        {
            ejecutarMovimientoLogico(fromRow, fromCol, toRow, toCol);
            return;
        }

        animating = true;
        movingPiece.toFront(); 

        // Calcular la distancia en píxeles
        double deltaX = (toCol - fromCol) * TILE_SIZE;
        double deltaY = (toRow - fromRow) * TILE_SIZE;

        TranslateTransition transition = new TranslateTransition(Duration.millis(300), movingPiece);
        transition.setByX(deltaX);
        transition.setByY(deltaY);
        transition.setInterpolator(Interpolator.EASE_BOTH); 

        transition.setOnFinished(e -> {
            movingPiece.setTranslateX(0);
            movingPiece.setTranslateY(0);
            
            ejecutarMovimientoLogico(fromRow, fromCol, toRow, toCol);
            animating = false;
        });

        transition.play();
    }

    private void ejecutarMovimientoLogico(int fromRow, int fromCol, int toRow, int toCol) 
    {
        motor.mover(fromRow, fromCol, toRow, toCol);

        // cambiar turno
        turno = (turno == 0) ? 1 : 0;

        motor.turno();
        
        hasSelection = false;
        selectedRow = -1;
        selectedCol = -1;
        
        updateBoard();

        //Verifica si la partida ha terminado
        if(motor.terminarPartida())
        {
            Platform.runLater(() -> {

                Alert alert = new Alert(AlertType.INFORMATION);

                alert.setTitle("Fin de la partida");
                alert.setHeaderText(null);
                //alert.setContentText("Jaque mate");

                alert.showAndWait();

                stage.close();
            });
        }
    }

    // =========================
    // RENDER
    // =========================
    private void updateBoard() 
    {
        pieceLayer.getChildren().clear();

        for (int row = 0; row < SIZE; row++) 
        {
            for (int col = 0; col < SIZE; col++) 
            {

                Rectangle square = squares[row][col];
                pieceViews[row][col] = null;

                // reset base color
                boolean isLight = (row + col) % 2 == 0;
                square.setFill(isLight ? Color.web("#DBFFFC") : Color.web("#E4B3FF"));
                square.setStroke(Color.WHITE);
                square.setStrokeWidth(1);

                Pieza p = motor.getPieza(row, col);

                if (p != null) 
                {
                    Text t = new Text(p.toString());
                    t.setStyle("""
                        -fx-font-size: 42px;
                        -fx-font-family: 'Segoe UI Symbol';
                    """);
                    
                    // IMPORTANTE: Hace que el texto ignore los clics por completo
                    t.setMouseTransparent(true); 
                    
                    // Posicionamiento centrado
                    t.setLayoutX((col * TILE_SIZE) + (TILE_SIZE / 2.0) - 16); 
                    t.setLayoutY((row * TILE_SIZE) + (TILE_SIZE / 2.0) + 12); 
                    
                    pieceViews[row][col] = t;
                    pieceLayer.getChildren().add(t);
                }

                // pintar movimientos SOLO si hay selección
                if (hasSelection) 
                {
                    ArrayList<Movimiento> movimientos = motor.getMovimientos(selectedRow, selectedCol);

                    for (Movimiento m : movimientos) 
                    {
                        if (m.i == row && m.j == col) 
                        {
                            switch (m.tipoMov) 
                            {
                                case NORMAL -> square.setFill(Color.web("#9effd7"));
                                case CAPTURA -> square.setFill(Color.web("#ffa8a8"));
                                case ENROQUE -> square.setFill(Color.web("#ffefb0"));
                                case PROMOCION -> square.setFill(Color.web("#ddd8ff"));
                            }
                            square.setStroke(Color.web("#ae5db9"));
                            square.setStrokeWidth(2);
                        }
                    }
                }
            }
        }
        repaintSelection();
    }

    // =========================
    // SELECCIÓN
    // =========================
    private void repaintSelection() 
    {
        if (hasSelection && selectedRow != -1 && selectedCol != -1) 
        {
            Rectangle square = squares[selectedRow][selectedCol];
            square.setStroke(Color.web("#ae5db9"));
            square.setStrokeWidth(3);
        }
    }

    public static void main(String[] args) 
    {
        launch(args);
    }
}