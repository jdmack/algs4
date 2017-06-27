
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
            resize(arraySize_ * 2);
        }

        if(occupancy_ != 0) {
            if(++tail_ >= arraySize_) {
                tail_ = 0;
            }
        }
        array_[tail_] = item;

        ++occupancy_;
        //printArray();
 
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

        --occupancy_;

        if(occupancy_ <= (arraySize_ / 4)) {
            resize(arraySize_ / 2);
        }

        //printArray();
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

    private void resize(int newSize)
    {
        if(newSize < ARRAY_MIN_SIZE) return;
        System.out.println("occupancy: " + occupancy_);
        Item[] newArray = (Item[]) new Object[newSize];

        int current = head_;
        for(int i = 0; i < occupancy_; ++i) {
            newArray[i] = array_[current];
            array_[current] = null;
            if(++current >= arraySize_) current = 0;
        }
        head_ = 0;
        tail_ = occupancy_ - 1;
        array_ = newArray;
        System.out.println("resizing from " + arraySize_ + " to " + newSize);
        arraySize_ = newSize;
    }

    private void printArray()
    {
        System.out.println("head: " + head_ + ", tail: " + tail_ + " array: " + Arrays.toString(array_));
    }

    private class RandomizedQueueIterator implements Iterator<Item>
    {

        private int current_ = 0;
        private int[] indices_;

        public RandomizedQueueIterator()
        {
            indices_ = new int[occupancy_]; 
            
            int current = head_;
            for(int i = 0; i < occupancy_; ++i) {
                indices_[i] = current;
                if(++current >= arraySize_) current = 0;
            }
            StdRandom.shuffle(indices_);
            //System.err.println("(indices: " + Arrays.toString(indices_) + ")");

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
        RandomizedQueue<Integer> q = new RandomizedQueue<Integer>();        

        for(int j = 0; j < 3; ++j) {
            for(int i = 0; i < 100; ++i) {
                q.enqueue(i);
            }

            print(q);

            for(int i = 0; i < 75; ++i) {
                //System.out.println("Removing: " + q.dequeue());
                q.dequeue();
                print(q);
            }
        }

        print(q);
    }

    public static void print(RandomizedQueue<Integer> q)
    {   
        Iterator<Integer> it = q.iterator();

        System.out.print("size: " + q.size() + " [ ");
        for(Integer s: q) {
            System.out.print("[" + s + "]");
        }
        System.out.println(" ]");
    }

}
