import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[][] sites;
    private boolean[][] openState;
    private int count = 0;
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Invalid argument!");
        }

        sites = new int[n][n];
        openState = new boolean[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                sites[i][j] = n * i + j + 1;
                openState[i][j] = false;
            }
        }

        uf = new WeightedQuickUnionUF(n*n+2);

        for (int i = 0; i < n; i++) {
            uf.union(0, sites[0][i]);
            uf.union(n*n+1, sites[n-1][i]);
        }
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (isOpen(row, col)) return;

        // set open state
        openState[row-1][col-1] = true;
        // increase open sites count
        count++;

        int site = sites[row-1][col-1];

        // top
        if (row > 1 && isOpen(row-1, col)) {
            uf.union(site, sites[row - 2][col - 1]);
        }
        // right
        if (col < sites.length && isOpen(row,col+1)) {
            uf.union(site, sites[row - 1][col]);
        }
        // bottom
        if (row < sites.length && isOpen(row+1, col)) {
            uf.union(site, sites[row][col - 1]);
        }
        // left
        if (col > 1 && isOpen(row, col-1)) {
            uf.union(site, sites[row - 1][col - 2]);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > sites.length) {
            throw new IllegalArgumentException("Invalid row!");
        }

        if (col < 1 || col > sites.length) {
            throw new IllegalArgumentException("Invalid column!");
        }

        return openState[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isOpen(row, col)) return false;

        int foo = uf.find(0);
        int rootOfRow = uf.find(sites[row-1][col-1]);

        return foo == rootOfRow;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        int virtualTopRoot = uf.find(0);
        int virtualBottomRoot = uf.find(sites.length * sites.length + 1);

        return virtualTopRoot == virtualBottomRoot;
    }

    // test client (optional)
    public static void main(String[] args) {}
}
