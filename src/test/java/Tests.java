import com.example.MatrixMultiplicationApp;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class Tests {

    @Test
    public void testAddingMatrices() {
        int[][] m1 = {{1,2}, {3,4}};
        int[][] m2 = {{5,6}, {7,8}};
        int[][] expected = {{6,8}, {10,12}};
        int[][] result = MatrixMultiplicationApp.addMatrices(m1, m2);
        assertTrue(java.util.Arrays.deepEquals(expected, result), "Matrix addition returned a matrix that was not equal to the expected result");
    }

    @Test
    public void testSubtractingMatrices() {
        int[][] m1 = {{1,2}, {3,4}};
        int[][] m2 = {{5,6}, {7,8}};
        int[][] expected = {{4,4}, {4,4}};
        int[][] result = MatrixMultiplicationApp.subtractMatrices(m2, m1);
        assertTrue(java.util.Arrays.deepEquals(expected, result), "Matrix subtraction returned a matrix that was not equal to the expected result");
    }

    @Test
    public void testMultiplyingMatrices() {
        int[][] m1 = {{1,2}, {3,4}};
        int[][] m2 = {{5,6}, {7,8}};
        int[][] expected = {{19,22}, {43,50}};
        int[][] result = MatrixMultiplicationApp.multiplyMatrices(m1, m2);
        assertTrue(java.util.Arrays.deepEquals(expected, result), "Matrix multiplication returned a matrix that was not equal to the expected result");
    }

    @Test
    public void testDividingMatrices() {}
}
