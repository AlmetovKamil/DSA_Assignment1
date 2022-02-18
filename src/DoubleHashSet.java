/**
 * class for Double Hash Set.
 * Double hashing is used as a base of ISet implementation.
 *
 * @param <T> type of the elements
 * @author Kamil Almetov BS21-05
 * @see ISet
 */
class DoubleHashSet<T> implements ISet<T> {
    /**
     * Array of stored elements
     */
    private T[] elements;
    /**
     * Array of states. There can be 3 possible states:
     * states[i] == 0 - elements[i] is empty,
     * states[i] == 1 - elements[i] is occupied by some item,
     * states[i] == 2 - elements[i] was deleted
     */
    private byte[] states;
    /**
     * maximum size of elements. It's a prime number to avoid collisions as much as we can
     */
    private static final int capacity = 100003;
    /**
     * prime number that is used to calculate hashcode2
     */
    private static final int prime = 100019;
    /**
     * number of elements
     */
    private int size;

    /**
     * Calculates the hashcode1 using standard hashcode() function.
     * Hashcode1 must be non-negative and by module of capacity.
     * This function is needed only for internal logic, that's why it's private static
     * <br>
     * <b>Time complexity (worst case) - O(1)</b>
     * @param item for which hashcode1 is calculated
     */
    private static <T> int hashcode1(T item) {
        return Math.abs(item.hashCode()) % capacity;
    }

    /**
     * Calculates the hashcode2 using hashcode1 and prime number.
     * Hashcode2 must be non-negative and by module of capacity.
     * This function is needed only for internal logic, that's why it's private static
     * <br>
     * <b>Time complexity (worst case) - O(1)</b>
     * @param item for which hashcode2 is calculated
     */
    private static <T> int hashcode2(T item) {
        return (prime - hashcode1(item)) % capacity;
    }

    /**
     * Calculation of a key - index of an item.
     * This function is needed only for internal logic, that's why it's private static
     * <br>
     * <b>Time complexity (worst case) - O(1)</b>
     * @param h1 hashcode1
     * @param h2 hashcode2
     * @param j  number from 0 to capacity, is needed to calculate key in double hashing
     * @return key
     */
    private static int getKey(int h1, int h2, int j) {
        return (int) (((long) h1 + (long) j * h2) % capacity);
    }

    /**
     * Constructor with no parameters
     * <br>
     * <b>Time complexity (worst case) - O(n) (n - number of elements)</b>
     */
    @SuppressWarnings("unchecked")
    public DoubleHashSet() {
        //size is 0 at the beginning
        size = 0;
        //initialize the array of element and the array of states
        elements = (T[]) new Object[capacity];
        states = new byte[capacity];
    }

    /**
     * Constructor with one element. It's needed to create a copy of a set.
     * <br>
     * <b>Time complexity (worst case) - O(n) (n - number of elements)</b>
     * @param set that will be copied
     */
    public DoubleHashSet(DoubleHashSet<T> set) {
        //call default constructor to initialize all fields
        this();
        //copy all fields from the set
        this.size = set.size;
        this.states = set.states.clone();
        this.elements = set.elements.clone();
    }

    /**
     * <b>Time complexity (worst case) - O(n) (n - number of elements)</b>
     * @see ISet#add(T)
     * @param item that will be added
     */
    @Override
    public void add(T item) {
        int h1 = hashcode1(item);
        int h2 = hashcode2(item);
        //apply double hashing
        int key;
        for (int j = 0; j < capacity; ++j) {
            //calculate the key - double hashcode for the item
            key = getKey(h1, h2, j);
            //if nothing in this cell
            if (states[key] == 0) {
                //put the item here
                elements[key] = item;
                //now in this cell some data is stored
                states[key] = 1;
                //size has increased
                size++;
                return;
            }
            // if happened that the item has been already presented, do nothing and return
            else if (states[key] == 1 && elements[key].equals(item)) {
                return;
            }
        }

    }

    /**
     * <b>Time complexity (worst case) - O(n) (n - number of elements)</b>
     * @see ISet#remove(T)
     * @param item that will be removed
     */
    @Override
    public void remove(T item) {
        int h1 = hashcode1(item);
        int h2 = hashcode2(item);
        //apply double hashing to find the item
        int key;
        for (int j = 0; j < capacity; ++j) {
            //calculate the key - double hashcode for the item
            key = getKey(h1, h2, j);
            //if an empty cell is found, that means that there is no element with item value in the set,
            // so we should return
            if (states[key] == 0) {
                return;
            }
            //Otherwise, if we found the item, delete it, set the state to deleted (2), and decrease the size
            else if (states[key] == 1 && elements[key].equals(item)) {
                states[key] = 2;
                elements[key] = null;
                size--;
                return;
            }
        }
    }

    /**
     * <b>Time complexity (worst case) - O(n) (n - number of elements)</b>
     * @see ISet#contains(T)
     * @param item that is checked
     * @return true if the item is presented in the set
     */
    @Override
    public boolean contains(T item) {
        int h1 = hashcode1(item);
        int h2 = hashcode2(item);
        //apply double hashing to find the item
        int key;
        for (int j = 0; j < capacity; ++j) {
            //calculate the key - double hashcode for the item
            key = getKey(h1, h2, j);
            //if an empty cell is found, that means that there is no element with item value in the set,
            // so we should return false;
            if (states[key] == 0) {
                return false;
            }
            //Otherwise, if we found the item, return true;
            else if (states[key] == 1 && elements[key].equals(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * <b>Time complexity (worst case) - O(1)</b>
     * @see ISet#size()
     * @return number of elements of the set
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * <b>Time complexity (worst case) - O(1)</b>
     * @see ISet#isEmpty()
     * @return true if the set is empty
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Create string representation of the set - all elements are represented in a row with space delimiter
     * <br>
     * <b>Time complexity (worst case) - O(n) (n - number of elements)</b>
     * @return string representation of the set
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int k = 0;
        for (int i = 0; i < capacity; i++) {
            if (states[i] == 1) {
                stringBuilder.append(elements[i]).append(" ");
                k++;
            }
            if (k == size) break;
        }
        return stringBuilder.toString();
    }
}
