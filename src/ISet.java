/**
 * An interface for Set.
 * All elements stored in the set are unique.
 *
 * @param <T> type of the stored elements
 * @author Kamil Almetov BS21-05
 */
interface ISet<T> {
    /**
     * Adds an item in the set (if the item is already in the set, do nothing)
     */
    void add(T item);

    /**
     * Removes an item from the set (if the item isn't presented in the set, do nothing)
     */
    void remove(T item);

    /**
     * Checks whether th item is presented in the set
     *
     * @return true if the item is in the set
     */
    boolean contains(T item);

    /**
     * @return the number of elements in the set
     */
    int size();

    /**
     * Checks whether the set is empty
     *
     * @return true if the set is empty
     */
    boolean isEmpty();
}