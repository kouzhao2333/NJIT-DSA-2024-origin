
package oy.tol.tra;

public class QueueImplementation<E> implements QueueInterface<E> {

    private E[] itemArray;
    private int capacity;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private static final int DEFAULT_QUEUE_SIZE = 10;

    @SuppressWarnings("unchecked")
    public QueueImplementation() throws QueueAllocationException {
        this(DEFAULT_QUEUE_SIZE);
    }

    @SuppressWarnings("unchecked")
    public QueueImplementation(int capacity) throws QueueAllocationException {
        if (capacity < 2) {
            throw new QueueAllocationException("Capacity must be greater than 2");
        }
        try {
            this.capacity = capacity;
            itemArray = (E[]) new Object[capacity];
            head = 0;
            tail = 0;
            size = 0;
        } catch (OutOfMemoryError e) {
            throw new QueueAllocationException("Failed to allocate memory for the queue.");
        }
    }

    @Override
    public int capacity() {
        return capacity;
    }

    @Override
    public void enqueue(E element) throws QueueAllocationException, NullPointerException {
        if (element == null) {
            throw new NullPointerException("The element cannot be null");
        }
        if (size >= capacity) {
            resizeArray();
        }
        itemArray[tail] = element;
        tail = (tail + 1) % capacity;
        size++;
    }

    private void resizeArray() throws QueueAllocationException {
        try {
            int newCapacity = 2 * capacity;
            Object [] newArray = new Object[newCapacity];
            int i = 0;

            while (i<size){

                if (head+i<capacity){
                    newArray[i] = itemArray[head+i];
                }else {

                    newArray[i] = itemArray[i-(capacity-head)];
                }
                i++;
            }
            itemArray = (E[]) newArray;
            capacity = newCapacity;
            head = 0;
            tail = size;
        } catch (OutOfMemoryError e) {
            throw new QueueAllocationException("Failed to allocate more room for the queue.");
        }
    }

    @Override
    public E dequeue() throws QueueIsEmptyException {
        if (isEmpty()) {
            throw new QueueIsEmptyException(" The queue is null");
        }
        Object dequeueElement = itemArray[head];
        itemArray[head] = null;
        if (head == capacity-1){
            head = 0;
        }else {
            head = head+1;
        }
        size--;
        return (E) dequeueElement;
    }

    @Override
    public E element() throws QueueIsEmptyException {
        if (isEmpty()) {
            throw new QueueIsEmptyException("The queue is null");
        }
        return itemArray[head];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            itemArray[i] = null;
        }
        head = 0;
        tail = 0;
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            builder.append(itemArray[(head + i) % capacity]);
            if (i < size - 1) {
                builder.append(", ");
            }
        }
        builder.append("]");
        return builder.toString();
    }
}
