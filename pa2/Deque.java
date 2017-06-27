import java.util.*;

public class Deque<Item> implements Iterable<Item>
{

    private class Node<Item>
    {
        private Item item_;
        private Node<Item> prev_;
        private Node<Item> next_;

        public Item item() { return item_; }
        public Node<Item> prev() { return prev_; }
        public Node<Item> next() { return next_; }

        public void setItem(Item item) { item_ = item; }
        public void setPrev(Node<Item> prev) { prev_ = prev; }
        public void setNext(Node<Item> next) { next_ = next; }

        public Node(Item item)
        {
            item_ = item;
            prev_ = null;
            next_ = null;
        }

    }

    private int occupancy_;
    private Node<Item> first_;
    private Node<Item> last_;
    
    public Deque()
    {
        occupancy_ = 0;
    }

    public boolean isEmpty()
    {
        return occupancy_ == 0;
    }

    public int size()
    {
        return occupancy_;
    }

    public void addFirst(Item item)
    {
        if(item == null) {
            throw new NoSuchElementException();
        }

        Node<Item> node = new Node<Item>(item);

        if(first_ != null) {
            node.setNext(first_);
            first_.setPrev(node);
        }
        else {
            last_ = node;
        }
        first_ = node;
        ++occupancy_;
    }

    public void addLast(Item item)
    {
        if(item == null) {
            throw new NoSuchElementException();
        }

        Node<Item> node = new Node<Item>(item);

        if(last_ != null) {
            node.setPrev(last_);
            last_.setNext(node);
        }
        else {
            first_ = node;
        }
        last_ = node;
        ++occupancy_;
    }

    public Item removeFirst()
    {
        if(first_ == null) {
            throw new NoSuchElementException();    
        }

        Node<Item> node = first_;
        if(node.next() != null) {
            first_ = node.next();
            first_.setPrev(null);
        }
        else {
            first_ = null;
            last_ = null;
        }
        node.setNext(null);

        --occupancy_;

        return node.item();
    }

    public Item removeLast()
    {
        if(last_ == null) {
            throw new NoSuchElementException();    
        }

        Node<Item> node = last_;
        if(node.prev() != null) {
            last_ = node.prev();
            last_.setNext(null);
        }
        else {
            last_ = null;
            first_ = null;
        }
        node.setPrev(null);

        --occupancy_;

        return node.item();

    }

    public Iterator<Item> iterator()
    {
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item>
    {
        private Node<Item> current = first_;

        public boolean hasNext()
        {
            return current != null;
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public Item next()
        {
            if(current == null) {
                throw new NoSuchElementException();
            }
            Item item = current.item();
            current = current.next();
            return item;
        }
    }
    
    public static void main(String[] args)
    {
        Deque<String> d = new Deque<String>();        

        d.addFirst("C");
        print(d);
        d.addLast("D");
        print(d);
        d.addFirst("B");
        print(d);
        d.addLast("E");
        print(d);
        d.addFirst("A");
        print(d);
        d.addLast("F");
        print(d);

        d.removeFirst();
        print(d);
        d.removeLast();
        print(d);
        d.removeFirst();
        print(d);
        d.removeLast();
        print(d);
        d.removeFirst();
        print(d);
        d.removeLast();
        print(d);

        d.addFirst("C");
        print(d);
        d.addLast("D");
        print(d);
        d.addFirst("B");
        print(d);
        d.addLast("E");
        print(d);
        d.addFirst("A");
        print(d);
        d.addLast("F");
        print(d);

    }

    public static void print(Deque<String> d)
    {
        Iterator<String> it = d.iterator();

        System.out.print("size: " + d.size() + "[ ");
        for(String s: d) {
            System.out.print("[" + s + "]");
        }
        System.out.println(" ]");
    }

}
