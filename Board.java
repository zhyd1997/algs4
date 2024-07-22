import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {
    private final int[][] board;
    private final int dimension;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        dimension = tiles.length;
        board = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                board[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        String string = "" + dimension;

        for (int i = 0; i < dimension; i++) {
            String temp = "";
            for (int j = 0; j < dimension; j++) {
                temp += board[i][j];
                if (j != dimension - 1) {
                    temp += " ";
                }
            }
            string += "\n " + temp;
        }

        return string;
    }

    // board dimension n
    public int dimension() {
        return dimension;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;

        int[] original = new int[dimension*dimension];
        int[] arr = new int[dimension*dimension];

        int idx = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                original[idx] = board[i][j];
                arr[idx] = board[i][j];
                idx++;
            }
        }

        Arrays.sort(arr);

        for (int i = 1; i < original.length; i++) {
            if (original[i-1] != arr[i]) {
                count++;
            }
        }

        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;

        int[] original = new int[dimension*dimension];
        int[] arr = new int[dimension*dimension];

        int idx = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                original[idx] = board[i][j];
                arr[idx] = board[i][j];
                idx++;
            }
        }

        Arrays.sort(arr);

        for (int i = 1; i < arr.length; i++) {
            if (original[i-1] == arr[i]) continue;
            for (int j = 0; j < original.length; j++) {
                if (original[j] == arr[i]) {
                    int xDistance;
                    int yDistance;

//                    StdOut.println("arr[" + i + "] is " + arr[i]);
//                    StdOut.println("j is " + j);
                    int x1 = (i - 1) / dimension;
                    int y1 = (i - 1) % dimension;
                    int x2 = j / dimension;
                    int y2 = j % dimension;
//                    StdOut.println("(" + x1 + ", " + y1 + ")");
//                    StdOut.println("(" + x2 + ", " + y2 + ")");

                    if (y1 > y2) {
                        yDistance = y1 - y2;
                    } else {
                        yDistance = y2 - y1;
                    }

                    if (x1 > x2) {
                        xDistance = x1 - x2;
                    } else {
                        xDistance = x2 - x1;
                    }

                    sum += xDistance + yDistance;
                }
            }
        }

        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int hamming = hamming();

        return hamming == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        // Two boards are equal if they are have the same size and their corresponding tiles are in the same positions.
        // The equals() method is inherited from java.lang.Object, so it must obey all of Java’s requirements.
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) {
            return false;
        }
        Board that = (Board) y;
        return this.equals(that);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int[][] tiles1 = new int[dimension][dimension];
        int[][] tiles2 = new int[dimension][dimension];
        int[][] tiles3 = new int[dimension][dimension];
        int[][] tiles4 = new int[dimension][dimension];

        int count = 0;

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                tiles1[i][j] = board[i][j];
                tiles2[i][j] = board[i][j];
                tiles3[i][j] = board[i][j];
                tiles4[i][j] = board[i][j];
            }
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (board[i][j] == 0) {
                    if (i == 0 && j == 0) {
                        // 2
                        count = 2;

                        tiles1[i][j] = tiles1[i+1][j];
                        tiles1[i+1][j] = 0;

                        tiles2[i][j] = tiles2[i][j+1];
                        tiles2[i][j+1] = 0;
                    } else if (i == 0) {
                        // 2
                        if (j == dimension - 1) {
                            count = 2;

                            tiles1[i][j] = tiles1[i+1][j];
                            tiles1[i+1][j] = 0;

                            tiles2[i][j] = tiles2[i][j-1];
                            tiles2[i][j-1] = 0;
                        } else {
                            // 3
                            count = 3;

                            tiles1[i][j] = tiles1[i+1][j];
                            tiles1[i+1][j] = 0;

                            tiles2[i][j] = tiles2[i][j-1];
                            tiles2[i][j-1] = 0;

                            tiles3[i][j] = tiles3[i][j+1];
                            tiles3[i][j+1] = 0;
                        }
                    } else if (j == 0) {
                        // 2
                        if (i == dimension - 1) {
                            count = 2;

                            // i-1
                            tiles1[i][j] = tiles1[i-1][j];
                            tiles1[i-1][j] = 0;
                            // j+1
                            tiles2[i][j] = tiles2[i][j+1];
                            tiles2[i][j+1] = 0;
                        } else {
                            // 3
                            count = 3;

                            // i-1
                            tiles1[i][j] = tiles1[i-1][j];
                            tiles1[i-1][j] = 0;
                            // j+1
                            tiles2[i][j] = tiles2[i][j+1];
                            tiles2[i][j+1] = 0;
                            // i+1
                            tiles3[i][j] = tiles3[i+1][j];
                            tiles3[i+1][j] = 0;
                        }
                    } else {
                        if (i == dimension - 1 && j == dimension - 1) {
                            // 2
                            count = 2;

                            // i-1
                            tiles1[i][j] = tiles1[i-1][j];
                            tiles1[i-1][j] = 0;
                            // j-1
                            tiles2[i][j] = tiles2[i][j-1];
                            tiles2[i][j-1] = 0;
                        } else if (i == dimension - 1) {
                            // 3
                            count = 3;

                            // i-1
                            tiles1[i][j] = tiles1[i-1][j];
                            tiles1[i-1][j] = 0;
                            // j-1
                            tiles2[i][j] = tiles2[i][j-1];
                            tiles2[i][j-1] = 0;
                            // j+1
                            tiles3[i][j] = tiles3[i][j+1];
                            tiles3[i][j+1] = 0;
                        } else if (j == dimension - 1) {
                            // 3
                            count = 3;

                            // j-1
                            tiles1[i][j] = tiles1[i][j-1];
                            tiles1[i][j-1] = 0;
                            // i-1
                            tiles2[i][j] = tiles2[i-1][j];
                            tiles2[i-1][j] = 0;
                            // i+1
                            tiles3[i][j] = tiles3[i+1][j];
                            tiles3[i+1][j] = 0;
                        } else {
                            count = 4;

                            // 4
                            tiles1[i][j] = tiles1[i][j+1];
                            tiles1[i][j+1] = 0;

                            tiles2[i][j] = tiles2[i+1][j];
                            tiles2[i+1][j] = 0;

                            tiles3[i][j] = tiles3[i][j-1];
                            tiles3[i][j-1] = 0;

                            tiles4[i][j] = tiles4[i-1][j];
                            tiles4[i-1][j] = 0;
                        }
                    }
                    break;
                }
            }
        }

        ArrayList<Board> boards = new ArrayList<Board>(count);

        if (count == 2) {
            boards.add(new Board(tiles1));
            boards.add(new Board(tiles2));
        } else if (count == 3) {
            boards.add(new Board(tiles1));
            boards.add(new Board(tiles2));
            boards.add(new Board(tiles3));
        } else if (count == 4) {
            boards.add(new Board(tiles1));
            boards.add(new Board(tiles2));
            boards.add(new Board(tiles3));
            boards.add(new Board(tiles4));
        }

        return boards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int swap = 0;

        int[][] tiles = new int[dimension][dimension];

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (swap == 0 && board[i][j] != 0) {
                    swap = board[i][j];
                }
                tiles[i][j] = board[i][j];
            }
        }

        boolean isSwapped = false;

        for (int i = 0; i < dimension; i++) {
            if (isSwapped) break;
            for (int j = 0; j < dimension; j++) {
                if (tiles[i][j] == swap) {
                    if (i+1 < dimension) {
                        tiles[i][j] = tiles[i+1][j];
                        tiles[i+1][j] = swap;
                        isSwapped = true;
                        break;
                    } else if (i-1 >= 0) {
                        tiles[i][j] = tiles[i-1][j];
                        tiles[i-1][j] = swap;
                        isSwapped = true;
                        break;
                    } else if (j+1 < dimension) {
                        tiles[i][j] = tiles[i][j+1];
                        tiles[i][j+1] = swap;
                        isSwapped = true;
                        break;
                    } else if (j-1 >= 0) {
                        tiles[i][j] = tiles[i][j-1];
                        tiles[i][j-1] = swap;
                        isSwapped = true;
                        break;
                    }
                }
            }
        }

        return new Board(tiles);
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] tiles = {
//                {8, 1, 3},
//                {4, 0, 2},
//                {7, 6, 5},
//
//                {0, 1, 3},
//                {4, 2, 5},
//                {7, 8, 6},
//
//                {1, 2, 3},
//                {0, 7, 6},
//                {5, 4, 8},
//
//                {1, 0},
//                {3, 2},
//
//                {7, 5, 3, 1},
//                {10, 15, 9, 14},
//                {0, 8, 6, 2},
//                {11, 12, 13, 4},
//
//                {1, 0},
//                {2, 3},
//
                {0, 1},
                {3, 2},
        };

        Board board = new Board(tiles);

        StdOut.println("neighbors: ");

        for (Board neighbor: board.neighbors()) {
            if (neighbor == null) {
                StdOut.println("neighbor is " + null);
            } else {
                StdOut.println(neighbor.toString());
            }
        }

        StdOut.println("board.toString()");
        StdOut.println(board.toString());
        StdOut.println("board.dimension()");
        StdOut.println(board.dimension());
        StdOut.println("twins: ");
        StdOut.println(board.twin());
        StdOut.println("board.hamming()");
        StdOut.println(board.hamming());
        StdOut.println("board.manhattan()");
        StdOut.println(board.manhattan());
        StdOut.println("is goal?");
        StdOut.println(board.isGoal());
        StdOut.println("board.toString()");
        StdOut.println(board.toString());
    }
}
