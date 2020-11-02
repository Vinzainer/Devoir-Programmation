//CODINGAME PSEUDO : Vinzainer

import java.util.*;

class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);

        //String array representation of the two dimensional array
        ArrayList<String> grid = new ArrayList<String>();

        //String array containing all the possible paths
        ArrayList<String> allPaths = new ArrayList<String>();

        //Getting the dimension of the array
        int M = in.nextInt();
        int N = in.nextInt();

        //Initializing the grid
        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < M; i++) {
            grid.add(in.nextLine());
        }

        //Generate a naive path
        String str = generateNaivePath(M, N);

        //Generate all paths
        generateAllPAths(allPaths, str, "");

        //Test all paths and count the number of valid one
        int nbPossiblePaths = testAllPaths(allPaths, grid);

        //Print the result
        System.out.println(nbPossiblePaths);

        in.close();
    }

    /*Method to generate a naive path.
      A path between two oppisite point (0,0) and (N-1, M-1) in an array where the only
      moves permitted is "right" (or the x axis) and "down" (or the y axis) will contains
      N-1 right move and M-1 down move. So we can assimilate a path to a String containing
      the move in the right order.
      For a 2x2 array a naive path would be : "yx" or down and left.
    */
    static String generateNaivePath(int M, int N){
        String res = "";

        //Add M-1 "y" to the path
        for(int i = 0; i < M-1; i++){
            res += "y";
        }

        //Add M-1 "x" to the path
        for(int i = 0; i < N-1; i++){
            res += "x";
        }

        return res;
    }

    /*Method to generate all paths from the naive path.
      Using the naive path we can generate all the paths possible by finding
      all the permutation possible of the naive one.
      This is a recursive function using a boolean array to prevent double and so useless
      recursive calls to reduce the processing time.
    */
    static void generateAllPAths(ArrayList<String> res, String original,  String permuted) {
        //Exit condition
        if (original.length() == 0) {
            res.add(permuted);
            return;
        }

        //Boolean array to store if the character as already being used
        boolean direction[] = new boolean[2];
        direction[0] = false;
        direction[1] = false;

        /*Loop to iter the string.
          Permutes the character one by one by removing them from the original string
          and adding them to a new one and placing the other one in a new position.
        */
        for (int i = 0; i < original.length(); i++) {
            char c = original.charAt(i);

            String sub = original.substring(0, i) +
                         original.substring(i + 1);

            //If the character has already been used we skip the recursive call
            if (direction[c - 'x'] == false){
                generateAllPAths(res, sub, c + permuted);
            }

            direction[c - 'x'] = true;
        }
    }

    /*Method to test all paths
      We iter the allPaths array and test them one by one by iterating through them.
      Testing each step if we are not on a non reachable cell, if so then the path is invalid.
    */
    public static int testAllPaths(ArrayList<String> res, ArrayList<String> grid){
        boolean success;
        int nb = 0;

        for(String e : res){
            //Boolean indicating the validity of a path
            success = true;

            //Current position on the grid
            int r = 0;
            int c = 0;

            for(int i = 0; i < e.length(); i++){
                //If the character is an "x" then we move right
                if(e.charAt(i) == 'x'){
                    c +=1;
                }

                //If the character is an "y" then we move down
                else if(e.charAt(i) == 'y'){
                    r += 1;
                }

                //Test if we are on a non reachable cell
                if(grid.get(r).charAt(c) == '1'){
                    success = false;
                }
            }

            //Increment the number of valid path each time we validate one
            if(success){
                nb += 1;
            }
        }

        return nb;
    }
}
