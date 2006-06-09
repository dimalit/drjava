package edu.rice.cs.plt.iter;

import java.util.Iterator;

/**
 * Wraps an iterator in an immutable interface, preventing modifications to underlying data  
 * structures via {@link #remove()}.
 */
public class ImmutableIterator<T> implements Iterator<T> {
  
  private final Iterator<? extends T> _i;
  
  public ImmutableIterator(Iterator<? extends T> i) { _i = i; }
  public boolean hasNext() { return _i.hasNext(); }
  public T next() { return _i.next(); }
  public void remove() { throw new UnsupportedOperationException(); }  
  
  /** Call the constructor (allows {@code T} to be inferred) */
  public static <T> ImmutableIterator<T> make(ImmutableIterator<? extends T> i) {
    return new ImmutableIterator<T>(i);
  }
}
