package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MatrixMultiplicationApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Layout and Controls
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
    
        // Matrix 1 Dimensions
        Label labelMatrix1 = new Label("Matrix 1 Dimensions:");
        HBox matrix1Dims = new HBox(5);
        TextField rows1Field = new TextField();
        rows1Field.setPromptText("Rows");
        TextField cols1Field = new TextField();
        cols1Field.setPromptText("Columns");
        matrix1Dims.getChildren().addAll(rows1Field, cols1Field);
    
        // Matrix 2 Dimensions
        Label labelMatrix2 = new Label("Matrix 2 Dimensions:");
        HBox matrix2Dims = new HBox(5);
        TextField rows2Field = new TextField();
        rows2Field.setPromptText("Rows");
        TextField cols2Field = new TextField();
        cols2Field.setPromptText("Columns");
        matrix2Dims.getChildren().addAll(rows2Field, cols2Field);
    
        // Operator Selection
        Label operatorLabel = new Label("Select Operation:");
        ComboBox<String> operatorComboBox = new ComboBox<>();
        operatorComboBox.getItems().addAll("Addition", "Subtraction", "Multiplication", "Division");
        operatorComboBox.setValue("Multiplication");
    
        // Button to generate matrix input grids
        Button generateButton = new Button("Generate Input Grids");
    
        // Containers for input grids
        VBox inputGrids = new VBox(10);
    
        // Button to calculate and show result
        Button calculateButton = new Button("Calculate");
        calculateButton.setDisable(true);
    
        // Clear Button
        Button clearButton = new Button("Clear");
    
        // Result Label
        Label resultLabel = new Label();
    
        // Button Action to Generate Input Grids
        generateButton.setOnAction(e -> {
            inputGrids.getChildren().clear();
            resultLabel.setText("");
    
            try {
                int rows1 = Integer.parseInt(rows1Field.getText());
                int cols1 = Integer.parseInt(cols1Field.getText());
                int rows2 = Integer.parseInt(rows2Field.getText());
                int cols2 = Integer.parseInt(cols2Field.getText());
    
                // Validation for matrices
                String operation = operatorComboBox.getValue();
                if (operation.equals("Multiplication") && cols1 != rows2) {
                    resultLabel.setText("Matrix multiplication not possible: Columns of Matrix 1 must equal Rows of Matrix 2.");
                    calculateButton.setDisable(true);
                    return;
                } else if (!operation.equals("Multiplication") && (rows1 != rows2 || cols1 != cols2)) {
                    resultLabel.setText("Matrix addition/subtraction/division requires matrices of the same dimensions.");
                    calculateButton.setDisable(true);
                    return;
                }
    
                // Matrix 1 Grid
                GridPane grid1 = createMatrixGrid(rows1, cols1, "Matrix 1");
                // Matrix 2 Grid
                GridPane grid2 = createMatrixGrid(rows2, cols2, "Matrix 2");
    
                inputGrids.getChildren().addAll(grid1, grid2);
                calculateButton.setDisable(false);
    
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please enter valid integers for matrix dimensions.");
            }
        });
    
        // Button Action to Calculate Result
        calculateButton.setOnAction(e -> {
            try {
                int rows1 = Integer.parseInt(rows1Field.getText());
                int cols1 = Integer.parseInt(cols1Field.getText());
                int rows2 = Integer.parseInt(rows2Field.getText());
                int cols2 = Integer.parseInt(cols2Field.getText());
    
                int[][] matrix1 = extractMatrixFromGrid(inputGrids.getChildren().get(0), rows1, cols1);
                int[][] matrix2 = extractMatrixFromGrid(inputGrids.getChildren().get(1), rows2, cols2);
    
                int[][] resultMatrix;
                String operation = operatorComboBox.getValue();
                switch (operation) {
                    case "Addition":
                        resultMatrix = addMatrices(matrix1, matrix2);
                        break;
                    case "Subtraction":
                        resultMatrix = subtractMatrices(matrix1, matrix2);
                        break;
                    case "Division":
                        resultMatrix = divideMatrices(matrix1, matrix2);
                        break;
                    case "Multiplication":
                    default:
                        resultMatrix = multiplyMatrices(matrix1, matrix2);
                }
    
                // Display Result
                GridPane resultGrid = createResultGrid(resultMatrix);
                inputGrids.getChildren().add(resultGrid);
    
            } catch (NumberFormatException ex) {
                resultLabel.setText("Please ensure all matrix elements are valid integers.");
            }
        });
    
        // Clear Button Action
        clearButton.setOnAction(e -> {
            rows1Field.clear();
            cols1Field.clear();
            rows2Field.clear();
            cols2Field.clear();
            operatorComboBox.setValue("Multiplication");
            inputGrids.getChildren().clear();
            resultLabel.setText("");
            calculateButton.setDisable(true);
        });
    
        // Wrap the entire layout in a ScrollPane
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(root); // Wrap the entire root VBox
    
        // Add Controls to the Root Layout
        root.getChildren().addAll(labelMatrix1, matrix1Dims, labelMatrix2, matrix2Dims, operatorLabel, operatorComboBox, generateButton, inputGrids, calculateButton, clearButton, resultLabel);
    
        // Scene and Stage
        Scene scene = new Scene(scrollPane, 600, 500); // Set the Scene to the ScrollPane
        primaryStage.setTitle("Matrix Operations");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Creates a GridPane object with the specified dimensions.
     * The GridPane will have a TextField in each cell for users to enter values for each index of the matrix.
     *
     * @param rows Specifies the number of rows that the resulting GridPane will have.
     * @param cols Specifies the number of columns that the resulting GridPane will have.
     * @param title Specifies the title, which is used in the input TextField to show the user which matrix they are inputting values for.
     * @return Returns the created GridPane object.
     */
    private GridPane createMatrixGrid(int rows, int cols, String title) {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        grid.setStyle("-fx-border-color: black; -fx-border-width: 1; -fx-padding: 10;");

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TextField field = new TextField();
                field.setPromptText(title + " [" + i + "][" + j + "]");
                grid.add(field, j, i);
            }
        }

        return grid;
    }

    /**
     *  Returns an int[][] containing the values of a GridPane.
     *
     * @param node A node that points to a GridPane.
     * @param rows The number of rows that the GridPane has.
     * @param cols The number of columns that the GridPane has.
     * @return An int[][] containing the values that have been entered into a GridPane.
     */
    private int[][] extractMatrixFromGrid(Node node, int rows, int cols) {
        GridPane grid = (GridPane) node;
        int[][] matrix = new int[rows][cols];

        for (Node child : grid.getChildren()) {
            if (child instanceof TextField) {
                TextField field = (TextField) child;
                Integer colIndex = GridPane.getColumnIndex(child);
                Integer rowIndex = GridPane.getRowIndex(child);
                matrix[rowIndex][colIndex] = Integer.parseInt(field.getText());
            }
        }

        return matrix;
    }

    /**
     * Returns the result of adding two matrices.
     *
     * @param matrix1 The first matrix to be added.
     * @param matrix2 The second matrix to be added.
     * @return The result of adding matrix1 and matrix2.
     */
    private int[][] addMatrices(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
        return result;
    }

    /**
     * Returns the result of subtracting matrix2 from matrix1.
     *
     * @param matrix1 The matrix to be subtracted from.
     * @param matrix2 The matrix to be subtracted.
     * @return The result of subtracting matrix2 from matrix1.
     */
    private int[][] subtractMatrices(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }
        return result;
    }

    /**
     * Returns the result of dividing matrix1 by matrix2.
     *
     * @param matrix1 The matrix to be divided.
     * @param matrix2 The matrix to be divided by.
     * @return The result of dividing matrix1 by matrix2.
     */
    private int[][] divideMatrices(int[][] matrix1, int[][] matrix2) {
        int rows = matrix1.length;
        int cols = matrix1[0].length;
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (matrix2[i][j] == 0) {
                    throw new ArithmeticException("Division by zero detected in matrix elements.");
                }
                result[i][j] = matrix1[i][j] / matrix2[i][j];
            }
        }
        return result;
    }

    /**
     * Returns the result of multiplying two matrices.
     *
     * @param matrix1 The first matrix to be multiplied.
     * @param matrix2 The second matrix to be multiplied.
     * @return The result of multiplying matrix1 and matrix2.
     */
    private int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;

        int[][] resultMatrix = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    resultMatrix[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return resultMatrix;
    }

    /**
     * Creates a GridPane from an int[][].
     *
     * @param matrix The matrix to be converted into a GridPane.
     * @return A GridPane containing the values from the input matrix.
     */
    private GridPane createResultGrid(int[][] matrix) {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setPadding(new Insets(10));
        grid.setStyle("-fx-border-color: green; -fx-border-width: 1; -fx-padding: 10;");

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                Label label = new Label(String.valueOf(matrix[i][j]));
                grid.add(label, j, i);
            }
        }

        return grid;
    }

    /**
     * Launches a javaFX window that allows the user to use the matrix calculator.
     *
     * @param args The arguments that are passed in from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
