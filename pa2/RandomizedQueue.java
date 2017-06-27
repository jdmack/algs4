
import java.util.*;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item>
{
    private final int ARRAY_MIN_SIZE = 8;

    private int occupancy_;    
    private int arraySize_;
    private int head_;
    private int tail_;
    private Item[] array_;

    public RandomizedQueue()
    {
        occupancy_ = 0;
        head_ = 0;
        tail_ = 0;
        arraySize_ = ARRAY_MIN_SIZE;
        array_ = (Item[]) new Object[ARRAY_MIN_SIZE];
    }

    public boolean isEmpty()
    {
        return occupancy_ == 0;
    }

    public int size()
    {
        return occupancy_;
    }

    public void enqueue(Item item)
    {
        if(item == null) {
            throw new NoSuchElementException();
        }

        if(occupancy_ == arraySize_) {
            resize();
        }

        if(occupancy_ != 0) {
            if(++tail_ >= arraySize_) {
                tail_ = 0;
            }
        }
        array_[tail_] = item;

        ++occupancy_;
 
    }

    public Item dequeue()
    {
        if(occupancy_ == 0) {

        }


        Item item = array_[head_];
        array_[head_] = null;
            
        if(++head_ >= arraySize_) {
            head_ = 0;
        }

        if(occupancy_ <= (arraySize_ / 4)) {
            resize();
        }

        --occupancy_;

        return item;
    }

    public Item sample()
    {
        return array_[0];
    }

    public Iterator<Item> iterator()
    {
        return new RandomizedQueueIterator();
    }

    private void resize()
    {

    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {

        private int current_ = 0;
        private int[] indices_;

        public RandomizedQueueIterator()
        {
            indices_ = new int[occupancy_]; 
            
            int current = head_;
            for(int i = 0; i < indices_.length; ++i) {
                indices_[i] = current;
                if(++current >= occupancy_) {
                    current = 0;
                }
            }
            //StdRandom.shuffle(indices_);

        }

        public boolean hasNext()
        {   
            return current_ < indices_.length;
        }

        public void remove()
        {   
            throw new UnsupportedOperationException();
        }

        public Item next()
        {   
            return array_[indices_[current_++]];
        }
    }

    public static void main(String[] args)
    {
        RandomizedQueue<String> q = new RandomizedQueue<String>();        

        print(q);
        q.enqueue("A");
        print(q);
        q.enqueue("B");
        print(q);
        q.enqueue("C");
        print(q);
        q.enqueue("D");
        print(q);
        q.enqueue("E");
        print(q);
        q.enqueue("F");
        print(q);
        q.enqueue("G");
        print(q);
        System.out.println("Removing: " + q.dequeue());
        print(q);
        q.enqueue("H");
        print(q);
    }

    public static void print(RandomizedQueue<String> q)
    {   
        Iterator<String> it = q.iterator();

        System.out.print("size: " + q.size() + " [ ");
        for(String s: q) {
            System.out.print("[" + s + "]");
        }
        System.out.println(" ]");
    }

}
