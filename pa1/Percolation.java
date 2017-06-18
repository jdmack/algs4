import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation
{
    boolean DEBUG = false;

    private int n_;
    private int numOpenSites_;
    private boolean[] open_;
    public WeightedQuickUnionUF uf_;

    private int TOP;
    private int BOTTOM;

    ////////////////////////////////////////////////////////////////////////////
    // 
    // Percolation
    //
    // create n-by-n grid, with all sites blocked
    ////////////////////////////////////////////////////////////////////////////
    public Percolation(int n)
    {
        if(n <= 0) {
            throw new IllegalArgumentException();
        }
        n_ = n;

        numOpenSites_ = 0;
        int size = n_ * n_;

        uf_ = new WeightedQuickUnionUF(size + 2);

        open_ = new boolean[size];

        for(int i = 0; i < open_.length; ++i) {
            open_[i] = false;
        }

        TOP = size;
        BOTTOM = size + 1;

        // Connect TOP with the first row
        for(int i = convertIndex(1, 1); i < convertIndex(1, n_); ++i) {
            uf_.union(i, TOP);
        }

        // Connect BOTTOM with the last row
        for(int i = convertIndex(n_, 1); i < convertIndex(n_, n_); ++i) {
            uf_.union(i, BOTTOM);
        }
    }

    ////////////////////////////////////////////////////////////////////////////
    // 
    // open
    //
    // open site (row, col) if it is not open already
    ////////////////////////////////////////////////////////////////////////////
    public void open(int row, int col)
    {
        if(!indiciesValid(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        
        if(DEBUG) System.out.println("opening (" + row + ", " + col + ")");
        int index = convertIndex(row, col);
        open_[index] = true;
        ++numOpenSites_;

        int newIndex;

        // Connect to left if open
        if((col - 1) >= 1) {
            newIndex = convertIndex(row, col - 1);
            if(open_[newIndex]) {
                uf_.union(index, newIndex);
                if(DEBUG) System.out.println("union(" + index + ", " + newIndex + ") - left");
            }
        }
        
        // Connect to right if open
        if((col + 1) <= n_) {
            newIndex = convertIndex(row, col + 1);
            if(open_[newIndex]) {
                uf_.union(index, newIndex);
                if(DEBUG) System.out.println("union(" + index + ", " + newIndex + ") - right");
            }
        }
        
        // Connect to up if open
        if((row - 1) >= 1) {
            newIndex = convertIndex(row - 1, col);
            if(open_[newIndex]) {
                uf_.union(index, newIndex);
                if(DEBUG) System.out.println("union(" + index + ", " + newIndex + ") - up");
            }
        }
        
        // Connect to down if open
        if((row + 1) <= n_) {
            newIndex = convertIndex(row + 1, col);
            if(open_[newIndex]) {
                uf_.union(index, newIndex);
                if(DEBUG) System.out.println("union(" + index + ", " + newIndex + ") - down");
            }
        }
        

    }

    ////////////////////////////////////////////////////////////////////////////
    // 
    // isOpen
    //
    // is site (row, col) open
    ////////////////////////////////////////////////////////////////////////////
    public boolean isOpen(int row, int col)
    {
        if(!indiciesValid(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        
        return open_[convertIndex(row, col)];
    }

    ////////////////////////////////////////////////////////////////////////////
    // 
    // isFull
    //
    // is site (row, col) full?
    ////////////////////////////////////////////////////////////////////////////
    public boolean isFull(int row, int col)
    {
        if(!indiciesValid(row, col)) {
            throw new IndexOutOfBoundsException();
        }
        
        return uf_.connected(convertIndex(row, col), TOP);
    }

    ////////////////////////////////////////////////////////////////////////////
    // 
    // numberOfOpenSites
    //
    // number of open sites
    ////////////////////////////////////////////////////////////////////////////
    public int numberOfOpenSites()
    {
        return numOpenSites_;
    }

    ////////////////////////////////////////////////////////////////////////////
    // 
    // percolates
    //
    // does the system percolate
    ////////////////////////////////////////////////////////////////////////////
    public boolean percolates()
    {
        return uf_.connected(TOP, BOTTOM);
    }

    ////////////////////////////////////////////////////////////////////////////
    // 
    // convertIndex()
    //
    // Convert index from row,col format to linear array format
    ////////////////////////////////////////////////////////////////////////////
    private int convertIndex(int row, int col)
    {
        int newIndex = ((row - 1) * n_) + col - 1;
        if(DEBUG) System.out.println("(" + row + ", " + col + ") : " + newIndex);
        return newIndex;
    }

    ////////////////////////////////////////////////////////////////////////////
    // 
    // indiciesValid
    //
    // Validate given indices are within range for the grid
    ////////////////////////////////////////////////////////////////////////////
    private boolean indiciesValid(int row, int col)
    {
        if((row > n_) || (row < 1) || (col > n_) || (col < 1)) {
            return false;
        }
        return true;
    }

    ////////////////////////////////////////////////////////////////////////////
    // 
    // main
    //
    // test client
    ////////////////////////////////////////////////////////////////////////////
    public static void main(String[] args)
    {
        System.out.println("Creating Percolation object");
        Percolation perc = new Percolation(10);
        
        for(int i = 1; i <= 10; ++i) {
            System.out.println("Opening (" + i + ", 1)");
            perc.open(i, 1);
            if(perc.uf_.connected(perc.TOP, perc.BOTTOM)) {
                System.out.println("Connected");
            }
            else {
                System.out.println(" Not Connected");
            }
        }
        
    }
}
