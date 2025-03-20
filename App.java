import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("This tool is a matrix multiplier. Please input a matrix via file name in the program directory, or \"-1\" for a random matrix.");
        System.out.println("Matrices will be multiplied using linear algebra instead of (A,B) pairs.");

        System.out.println("First input: ");
        String file_path_1 = scanner.nextLine();
        int[][] matrix_1 = read_matrix(file_path_1);

        System.out.println("Second input: ");
        String file_path_2 = scanner.nextLine();
        int[][] matrix_2 = read_matrix(file_path_2);

        if (matrix_1[0].length != matrix_2.length) {
            System.out.println("Matrices cannot be multiplied. The number of columns in the first matrix must equal the number of rows in the second matrix.");
            return;
        }

        int[][] result = multiply_matrices(matrix_1, matrix_2);

        System.out.println("Matrices after multiplication:");
        print_matrix(result);

        scanner.close(); // Close the scanner to avoid resource leak
    }

    public static int[][] read_matrix(String file_path) {
        if (file_path.equals("-1")) {
            return generate_random_matrix();
        } else {
            try {
                Scanner file_scanner = new Scanner(new File(file_path));
                List<int[]> rows = new ArrayList<>();
                int cols = -1;

                while (file_scanner.hasNextLine()) {
                    String row_input = file_scanner.nextLine();
                    String[] numbers = row_input.split(" ");
                    int[] row = new int[numbers.length];
                    for (int i = 0; i < numbers.length; i++) {
                        row[i] = Integer.parseInt(numbers[i]);
                    }
                    if (cols == -1) {
                        cols = row.length;
                    } else if (cols != row.length) {
                        System.out.println("Inconsistent row length in the file.");
                        System.exit(1);
                    }
                    rows.add(row);
                }

                int[][] matrix = new int[rows.size()][cols];
                for (int i = 0; i < rows.size(); i++) {
                    matrix[i] = rows.get(i);
                }

                file_scanner.close();
                return matrix;
            } catch (FileNotFoundException e) {
                System.out.println("File not found: " + file_path);
                System.exit(1);
                return null;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format in the file.");
                System.exit(1);
                return null;
            }
        }
    }

    public static int[][] generate_random_matrix() {
        Scanner scanner = new Scanner(System.in);
        Random rand = new Random();

        System.out.println("Rows for random matrix: ");
        int rows = scanner.nextInt();

        System.out.println("Cols for random matrix: ");
        int cols = scanner.nextInt();

        int[][] matrix = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = rand.nextInt(10);
            }
        }

        scanner.close(); // Close the scanner to avoid resource leak
        return matrix;
    }

    public static int[][] multiply_matrices(int[][] matrix1, int[][] matrix2) {
        int rows1 = matrix1.length;
        int cols1 = matrix1[0].length;
        int cols2 = matrix2[0].length;
        int[][] result = new int[rows1][cols2];

        for (int i = 0; i < rows1; i++) {
            for (int j = 0; j < cols2; j++) {
                for (int k = 0; k < cols1; k++) {
                    result[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
        return result;
    }

    public static void print_matrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println();
        }
    }
}