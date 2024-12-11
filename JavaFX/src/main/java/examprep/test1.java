package examprep;
import java.util.Arrays;
import java.util.Scanner;
public class test1 {
    public static void main(String[] args) {
            
        
        int[] intArray = new int[5];

        int[] array2 = Arrays.copyOf(intArray, 5);

        int regNum = 5;
        //++regNum++; --> error

        //boolean theResult = 5 + 3 * 2; --> error

        Scanner in = new Scanner(System.in);

        double gpa = 3.2;

        String validScholarship = (gpa > 3.0) ? "Qualifies" : "Does not qualify";

        System.out.print(validScholarship);
        System.out.println();

        /* 
        String targetWord = "Programming";
        char[][] matrix = new char[targetWord.length()][targetWord.length()];
        
        // Fill the matrix
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {  // Corrected condition
                if (i == j) {
                    matrix[i][j] = targetWord.charAt(i);
                } else {
                    matrix[i][j] = 'o';
                }
            }
        }

        // Print the matrix
        for (char[] row : matrix) {
            for (char c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }
        */

        /*int[][] matrix = new int[3][3];
        int starter = 1;

        int primDiag = 0;
        int secDiag = 0;

        for (int i = 0; i < matrix.length; i++){
            for (int j = 0; j < matrix[i].length; j++){
                matrix[i][j] = starter;

                if (i == j){
                    primDiag += starter;
                }

                if (i + j == matrix.length - 1) {
                    secDiag += starter;
                }

                starter++;
            }
        }

        for (int[] row : matrix) {
            for (int c : row) {
                System.out.print(c + " ");
            }
            System.out.println();
        }

        System.out.println(primDiag);
        System.out.println(secDiag);*/

        int[] arr = {1, 2, 3, 4, 5};
        System.out.println(arr[1]);

        /*int x = 3;
        switch (x) {
            case 1, 2 -> System.out.println("Low");
            case 3 -> System.out.println("Medium");
            default -> System.out.println("High");
        } */

        String p = "hannah";
        int left = 0;
        int right = p.length() - 1;

        boolean pal = true;

        while (left < right){
            if (p.charAt(left) != p.charAt(right)){
                pal = false; 
                break;
            }
            left++;
            right--;
        }   
        System.out.println(pal);

    }

    
}
