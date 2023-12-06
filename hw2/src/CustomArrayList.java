
import java.util.Arrays;
import java.util.Comparator;

public class CustomArrayList<E> {

    private Object[] elements;
    private final static int INITIAL_CAPACITY = 10;
    private int size;

    public CustomArrayList() {
        this.elements = new Object[INITIAL_CAPACITY];
    }

    public CustomArrayList(int collSize) {
        if (collSize < 0) {
            throw new IllegalArgumentException("Illegal collection size " + collSize);
        }
        this.elements = new Object[collSize];
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public E get(int index) {
        rangeCheck(index);
        E element = (E) this.elements[index];
        return element;
    }

    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is " + index);
        }
        E oldValue = (E) this.elements[index];
        fastRemove(index);
        return oldValue;
    }

    public boolean remove(Object element) {
        for (int i = 0; i < this.size; i++) {
            if (element.equals(this.elements[i])) {
                fastRemove(i);
                return true;
            }
        }
        return false;
    }

    private void fastRemove(int index) {
        int indexToMove = this.size - index - 1;
        if (indexToMove > 0) {
            System.arraycopy(this.elements, index + 1, this.elements, index, indexToMove);
        }
        this.size--;
        this.elements[this.size] = null;
    }

    public void add(E element) {
        if (size == elements.length)
            elements = increaseCapacity();
        elements[size++] = element;
    }

    public void add(int index, E element) {
        rangeCheck(index);
        if (size == elements.length)
            elements = increaseCapacity();
        System.arraycopy(this.elements, index, this.elements, index + 1, size - index);
        elements[index] = element;
        this.size++;
    }

    private Object[] increaseCapacity() {
        int newCapacity = this.elements.length * 2;
        return elements = Arrays.copyOf(this.elements, newCapacity);
    }

    private void rangeCheck(int index) {
        if (index >= (this.size + 1) || index < 0) {
            throw new IllegalArgumentException("Illegal index:" + index);
        }
    }

    public String toString() {
        return "{ " +
                "elements :" + Arrays.toString(this.elements) +
                ", size : " + this.size +
                " }";
    }

    public void sort(Comparator<E> comparator) {
        E[] temp = (E[]) this.elements;
        int low = 0;
        int high = this.size - 1;
        quickSort(comparator, low, high);
    }

    private void quickSort(Comparator<E> comparator, int low, int high) {
        if (this.size == 0 || low >= high) return;

        int middle = low + (high - low) / 2;
        E border = (E) this.elements[middle];

        int i = low;
        int j = high;
        while (i <= j) {

            while (comparator.compare(border, (E) this.elements[i]) > 0) i++;
            while (comparator.compare(border, (E) this.elements[j]) < 0) j--;

            if (i <= j) {
                E swap = (E) this.elements[i];
                this.elements[i] = this.elements[j];
                this.elements[j] = swap;
                i++;
                j--;
            }
        }
        if (low < j) quickSort(comparator, low, j);
        if (high > i) quickSort(comparator, i, high);
    }
}




