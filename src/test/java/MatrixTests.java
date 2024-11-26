import com.example.MatrixMultiplicationApp;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

public class MatrixTests {

    @Test
    public void testAddingMatrices() {
        int[][] m1 = {{1,2}, {3,4}};
        int[][] m2 = {{5,6}, {7,8}};
        int[][] expected = {{6,8}, {10,12}};
        int[][] result = MatrixMultiplicationApp.addMatrices(m1, m2);
        assertTrue(java.util.Arrays.deepEquals(expected, result), "Matrix addition returned a matrix that was not equal to the expected result.");
    }

    @Test
    public void testSubtractingMatrices() {
        int[][] m1 = {{1,2}, {3,4}};
        int[][] m2 = {{5,6}, {7,8}};
        int[][] expected = {{4,4}, {4,4}};
        int[][] result = MatrixMultiplicationApp.subtractMatrices(m2, m1);
        assertTrue(java.util.Arrays.deepEquals(expected, result), "Matrix subtraction returned a matrix that was not equal to the expected result.");
    }

    @Test
    public void testMultiplyingMatrices() {
        int[][] m1 = {{1,2}, {3,4}};
        int[][] m2 = {{5,6}, {7,8}};
        int[][] expected = {{19,22}, {43,50}};
        int[][] result = MatrixMultiplicationApp.multiplyMatrices(m1, m2);
        assertTrue(java.util.Arrays.deepEquals(expected, result), "Matrix multiplication returned a matrix that was not equal to the expected result.");
    }

    @Test
    public void testStrikeMatrix() {
        int[][] m = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] expected = {{5,6},{8,9}};
        int[][] result = MatrixMultiplicationApp.strikeMatrix(m, 0, 0);
        assertTrue(java.util.Arrays.deepEquals(expected,result), "Submatrix returned a matrix that was not equal to the expected result.");
    }

    @Test
    public void testDeterminant2x2() {
        int[][] m = {{1,2},{3,4}};
        assertEquals(-2, MatrixMultiplicationApp.calcDeterminant(m), "Calculating the determinant returned a value that was not equal to the expected value.");
    }

    @Test
    public void testDeterminant3x3() {
        int[][] m = {{1,2,3},{4,5,6},{7,8,9}};
        assertEquals(0, MatrixMultiplicationApp.calcDeterminant(m), "Calculating the determinant returned a value that was not equal to the expected value.");
    }

    @Test
    public void testTransposeMatrix() {
        int[][] m = {{1,2,3},{4,5,6},{7,8,9}};
        int[][] expected = {{1,4,7},{2,5,8},{3,6,9}};
        int[][] result = MatrixMultiplicationApp.transposeMatrix(m);
        assertTrue(java.util.Arrays.deepEquals(expected,result), "Transposed matrix is not equal to the expected result.");
    }
}
