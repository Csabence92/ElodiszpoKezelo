package berwin.StockHandler.Others;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by Röhberg Péter on 2018. 10. 31..
 */

public class Reversed<T> implements Iterable<T> {
    private final List<T> original;

    public Reversed(List<T> original) {
        this.original = original;
    }

    public Iterator<T> iterator() {
        final ListIterator<T> i = original.listIterator(original.size());

        return new Iterator<T>() {
            public boolean hasNext() { return i.hasPrevious(); }
            public T next() { return i.previous(); }
            public void remove() { i.remove(); }
        };
    }

    public static <T> Reversed<T> reversed(List<T> original) {
        return new Reversed<T>(original);
    }
}