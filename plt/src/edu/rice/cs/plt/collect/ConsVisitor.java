package edu.rice.cs.plt.collect;

import edu.rice.cs.plt.lambda.Lambda;
import edu.rice.cs.plt.lambda.Predicate;

/**
 * A visitor for {@link ConsList}s.  Implementations handle the two list variants: 
 * {@link ConsList.Empty} and {@link ConsList.Nonempty}.  For convenience, visitors may
 * also be treated as {@code Lambda}s -- the {@code value} method is implemented to
 * apply the visitor.  A number of standard list visitors are also provided, either as static
 * fields or static methods.
 */
public abstract class ConsVisitor<T, Ret> implements Lambda<ConsList<? extends T>, Ret> {
  
  /** Handle an empty list */
  public abstract Ret forEmpty(ConsList.Empty<? extends T> list);
  
  /** Handle a nonempty list */
  public abstract Ret forNonempty(ConsList.Nonempty<? extends T> list);
  
  /** Invoke {@code list.apply(this)} */
  public Ret value(ConsList<? extends T> list) { return list.apply(this); }
  
  
  /** Determines if a list is empty */
  public static final ConsVisitor<Object, Boolean> IS_EMPTY = new ConsVisitor<Object, Boolean>() {
    public Boolean forEmpty(ConsList.Empty<?> list) { return true; }
    public Boolean forNonempty(ConsList.Nonempty<?> list) { return false; }
  };
  
  /** Reverses the order of the elements in a list */
  public static <T> ConsVisitor<T, ConsList<? extends T>> reverse() {
    /** Reverses the list and appends {@code toAppend} to the end of it */
    class ReverseHelper extends ConsVisitor<T, ConsList<? extends T>> {
      private ConsList<? extends T> _toAppend;
      
      public ReverseHelper(ConsList<? extends T> toAppend) { _toAppend = toAppend; }

      public ConsList<? extends T> forEmpty(ConsList.Empty<? extends T> list) { return _toAppend; }

      public ConsList<? extends T> forNonempty(ConsList.Nonempty<? extends T> list) {
        return list.rest().apply(new ReverseHelper(ConsList.cons(list.first(), _toAppend)));
      }
    }
    
    return new ReverseHelper(ConsList.<T>empty());
  }
  
  /** Appends the given list to the end of another list */
  public static <T> ConsVisitor<T, ConsList<? extends T>> append(final ConsList<? extends T> rest) {
    return new ConsVisitor<T, ConsList<? extends T>>() {
      public ConsList<? extends T> forEmpty(ConsList.Empty<? extends T> list) { return rest; }
      public ConsList<? extends T> forNonempty(ConsList.Nonempty<? extends T> list) {
        return ConsList.cons(list.first(), list.rest().apply(this));
      }
    };
  }
  
  /** Filters a list to contain only those elements accepted by the given predicate */
  public static <T> ConsVisitor<T, ConsList<? extends T>> filter(final Predicate<? super T> pred) {
    return new ConsVisitor<T, ConsList<? extends T>>() {
      public ConsList<? extends T> forEmpty(ConsList.Empty<? extends T> list) { return list; }

      public ConsList<? extends T> forNonempty(ConsList.Nonempty<? extends T> list) {
        if (pred.value(list.first())) { return ConsList.cons(list.first(), list.rest().apply(this)); }
        else { return list.rest().apply(this); }
      }
    };
  }
  
  /** Produces a new list by applying the given lambda to each of a list's elements */
  public static <S, T> ConsVisitor<S, ConsList<? extends T>> map(final Lambda<? super S, ? extends T> lambda) {
    return new ConsVisitor<S, ConsList<? extends T>>() {
      public ConsList<? extends T> forEmpty(ConsList.Empty<? extends S> list) { return ConsList.empty(); }
      public ConsList<? extends T> forNonempty(ConsList.Nonempty<? extends S> list) {
        return ConsList.cons(lambda.value(list.first()), list.rest().apply(this));
      }
    };
  }
  
}
