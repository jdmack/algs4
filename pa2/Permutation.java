import edu.princeton.cs.algs4.StdIn;

public class Permutation
{
    public static void main(String[] args)
    {
        if(args.length != 1) {
            System.out.println("ERROR: Invalid arguments");
            return;
        }
        
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> q = new RandomizedQueue<String>();
        
        while(!StdIn.isEmpty()) {
            String s = StdIn.readString();

            q.enqueue(s);

        }
        
        for(int i = 0; i < k; ++i) {
            System.out.println(q.dequeue());
        }
    }
}
