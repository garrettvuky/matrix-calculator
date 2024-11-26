package com.example;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class MatrixMultiplicationApp extends Application {

    /**
     * Launches the javaFx window and displays the GUI.
     *
     * @param primaryStage Used by JavaFx.
     */
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
        operatorComboBox.getItems().addAll("Addition", "Subtraction", "Multiplication", "Determinant", "Transpose");
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
            generateInputGrids(inputGrids, resultLabel, rows1Field, cols1Field, rows2Field, cols2Field, operatorComboBox, calculateButton);
        });
    
        // Button Action to Calculate Result
        calculateButton.setOnAction(e -> {
            calculateResult(rows1Field, cols1Field, rows2Field, cols2Field, inputGrids, operatorComboBox, resultLabel);
        });
    
        // Clear Button Action
        clearButton.setOnAction(e -> {
            clearValues(rows1Field, cols1Field, rows2Field, cols2Field, operatorComboBox, inputGrids, resultLabel, calculateButton);
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
     * Generates and displays grids that allow the user to input values.
     *
     * @param inputGrids A box containing all input grids.
     * @param resultLabel A label that displays information about the result, including errors.
     * @param rows1Field The value that was entered for the rows of matrix1.
     * @param cols1Field The value that was entered for the columns of matrix1.
     * @param rows2Field The value that was entered for the rows of matrix2.
     * @param cols2Field The value that was entered for the columns of matrix2.
     * @param operatorComboBox The ComboBox that is used to select the operation.
     * @param calculateButton The button that is used to calculate the result.
     */
    private void generateInputGrids(VBox inputGrids, Label resultLabel, TextField rows1Field, TextField cols1Field, TextField rows2Field, TextField cols2Field, ComboBox<String> operatorComboBox, Button calculateButton) {
        inputGrids.getChildren().clear();
        resultLabel.setText("");

        try {
            String operation = operatorComboBox.getValue();
            int rows1 = 0, cols1 = 0, rows2 = 0, cols2 = 0;
            rows1 = Integer.parseInt(rows1Field.getText());
            cols1 = Integer.parseInt(cols1Field.getText());
            if (!(operation.equals("Determinant")
                || operation.equals("Transpose"))) {
                rows2 = Integer.parseInt(rows2Field.getText());
                cols2 = Integer.parseInt(cols2Field.getText());
            }

            // Validation for matrices
            switch (operation) {
                case "Addition":
                    if (rows1 != rows2 || cols1 != cols2) {
                        resultLabel.setText("Matrix addition requires matrices of the same dimensions.");
                        calculateButton.setDisable(true);
                        return;
                    }
                    break;
                case "Subtraction":
                    if (rows1 != rows2 || cols1 != cols2) {
                        resultLabel.setText("Matrix subtraction requires matrices of the same dimensions.");
                        calculateButton.setDisable(true);
                        return;
                    }
                    break;
                case "Multiplication":
                    if (cols1 != rows2) {
                        resultLabel.setText("Matrix multiplication not possible: Columns of Matrix 1 must equal Rows of Matrix 2.");
                        calculateButton.setDisable(true);
                        return;
                    }
                    break;
                case "Determinant":
                    if (rows1 != cols1) {
                        resultLabel.setText("Matrix determinant requires square matrix.");
                        calculateButton.setDisable(true);
                        return;
                    }
                    break;
                case "Transpose":
                    if (rows1 != cols1) {
                        resultLabel.setText("Matrix transposition requires square matrix.");
                        calculateButton.setDisable(true);
                        return;
                    }
                    break;
            }

            // Matrix 1 Grid
            GridPane grid1 = createMatrixGrid(rows1, cols1, "Matrix 1");
            inputGrids.getChildren().addAll(grid1);
            // Matrix 2 Grid
            if (!(operation.equals("Determinant")
                || operation.equals("Transpose"))) {
                GridPane grid2 = createMatrixGrid(rows2, cols2, "Matrix 2");
                inputGrids.getChildren().addAll(grid2);
            }
            calculateButton.setDisable(false);

        } catch (NumberFormatException ex) {
            resultLabel.setText("Please enter valid integers for matrix dimensions.");
        }
    }

    /**
     * Calculates the result of the specified operation on the input values for matrix1 and matrix2.
     *
     * @param rows1Field The value that was entered for the rows of matrix1.
     * @param cols1Field The value that was entered for the columns of matrix1.
     * @param rows2Field The value that was entered for the rows of matrix2.
     * @param cols2Field The value that was entered for the columns of matrix2.
     * @param inputGrids A box containing all input grids.
     * @param operatorComboBox The ComboBox that is used to select the operation.
     * @param resultLabel A label that displays information about the result, including errors.
     */
    private void calculateResult(TextField rows1Field, TextField cols1Field, TextField rows2Field, TextField cols2Field, VBox inputGrids, ComboBox<String> operatorComboBox, Label resultLabel) {
        try {
            String operation = operatorComboBox.getValue();
            int rows1 = 0, cols1 = 0, rows2 = 0, cols2 = 0;
            int[][] matrix1, matrix2 = {{0}};
            rows1 = Integer.parseInt(rows1Field.getText());
            cols1 = Integer.parseInt(cols1Field.getText());
            matrix1 = extractMatrixFromGrid(inputGrids.getChildren().get(0), rows1, cols1);
            if (!(operation.equals("Determinant")
                || operation.equals("Transpose"))) {
                rows2 = Integer.parseInt(rows2Field.getText());
                cols2 = Integer.parseInt(cols2Field.getText());
                matrix2 = extractMatrixFromGrid(inputGrids.getChildren().get(1), rows2, cols2);
            }


            int[][] resultMatrix;
            switch (operation) {
                case "Addition":
                    resultMatrix = addMatrices(matrix1, matrix2);
                    break;
                case "Subtraction":
                    resultMatrix = subtractMatrices(matrix1, matrix2);
                    break;
                case "Multiplication":
                default:
                    resultMatrix = multiplyMatrices(matrix1, matrix2);
                    break;
                case "Determinant":
                    resultMatrix = new int[][]{{calcDeterminant(matrix1)}};
                    break;
                case "Transpose":
                    resultMatrix = transposeMatrix(matrix1);
                    break;
            }

            // Display Result
            GridPane resultGrid = createResultGrid(resultMatrix);
            inputGrids.getChildren().add(resultGrid);

        } catch (NumberFormatException ex) {
            resultLabel.setText("Please ensure all matrix elements are valid integers.");
        }
    }

    /**
     *Clears all the values that have been input.
     *
     * @param rows1Field The value that was entered for the rows of matrix1.
     * @param cols1Field The value that was entered for the columns of matrix1.
     * @param rows2Field The value that was entered for the rows of matrix2.
     * @param cols2Field The value that was entered for the columns of matrix2.
     * @param operatorComboBox The ComboBox that is used to select the operation.
     * @param inputGrids A box containing all input grids.
     * @param resultLabel A label that displays information about the result, including errors.
     * @param calculateButton The button that is used to calculate the result.
     */
    private static void clearValues(TextField rows1Field, TextField cols1Field, TextField rows2Field, TextField cols2Field, ComboBox<String> operatorComboBox, VBox inputGrids, Label resultLabel, Button calculateButton) {
        rows1Field.clear();
        cols1Field.clear();
        rows2Field.clear();
        cols2Field.clear();
        operatorComboBox.setValue("Multiplication");
        inputGrids.getChildren().clear();
        resultLabel.setText("");
        calculateButton.setDisable(true);
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
     * @param node A node that is a GridPane containing the results.
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
    public static int[][] addMatrices(int[][] matrix1, int[][] matrix2) {
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
    public static int[][] subtractMatrices(int[][] matrix1, int[][] matrix2) {
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
     * Returns the result of multiplying two matrices.
     *
     * @param matrix1 The first matrix to be multiplied.
     * @param matrix2 The second matrix to be multiplied.
     * @return The result of multiplying matrix1 and matrix2.
     */
    public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
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
     * Multiplies a matrix by a scalar.
     *
     * @param matrix The matrix to be multiplied.
     * @param a The scalar that the input matrix is to be multiplied by.
     * @return The result of multiplying matrix by a.
     */
    public static int[][] multiplyMatrixByScalar(int[][] matrix, int a) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] result = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                result[i][j] = matrix[i][j] * a;
            }
        }
        return result;
    }

    /**
     * Raises the input matrix to the input power via repeated multiplication.
     *
     * @param matrix The matrix that is to be raised to the given power.
     * @param power The power that the matrix is to be raised to.
     * @return The result of raising the input matrix to the input power.
     */
    public static int[][] raiseMatrixToPower(int[][] matrix, int power) {
        int[][] result = matrix;
        if (power == 0) {result = new int[][]{{1}};}
        for (int i = 1; i < power; i++) {
            result = multiplyMatrices(matrix, result);
        }
        return result;
    }

    /**
     * Calculates the determinant of a given matrix.
     *
     * @param matrix The matrix to calculate the determinant for.
     * @return An integer containing the determinant.
     */
    public static int calcDeterminant(int[][] matrix) {
        int n = matrix.length;
        int determinant = 0;
        if (n == 1) {return matrix[0][0];}
        if (n == 2) {return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];}
        else {
            for (int i = 0; i < n; i++) {
                int[][] strikeMatrix = strikeMatrix(matrix, 0, i);
                determinant += matrix[0][i] * calcDeterminant(strikeMatrix) * Math.pow(-1, i);
            }
        }
        return determinant;
    }

    /**
     * Generates the matrix that is formed by removing the row and column that a given element belongs to.
     * This is used to calculate the determinant of a matrix that is larger than 2x2.
     *
     * @param matrix The source matrix.
     * @param row The row that is being struck from the matrix.
     * @param column The column that is being struck from the matrix.
     * @return The matrix that is formed by striking the given row and column from the source matrix.
     */
    public static int[][] strikeMatrix(int[][] matrix, int row, int column) {
        int n = matrix.length - 1; //Length of the resulting matrix
        int a = 0, b = 0;
        int[][] result = new int[n][n];
        for (int i = 0; i <= n; i++) {
            if (i == row) {continue;}
            b = 0;
            for (int j = 0; j <= n; j++) {
                if (j == column) {continue;}
                result[a][b] = matrix[i][j];
                b++;
            }
            a++;
        }
        return result;
    }

    /**
     * Transposes the input matrix such that each element m,n of the result matrix is equal to element n,m of the source matrix.
     *
     * @param matrix The matrix to be transposed.
     * @return The result of transposing the source matrix.
     */
    public static int[][] transposeMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] result = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                result[j][i] = matrix[i][j];
            }
        }
        return result;
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
