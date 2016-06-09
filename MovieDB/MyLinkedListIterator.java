import java.util.Iterator;

public class MyLinkedListIterator<T extends Comparable<T>> implements Iterator<T> {
	// FIXME implement this
	// Implement the iterator for MyLinkedList.
	// You have to maintain the current position of the iterator.

	// Remove below annotation(@SuppressWarnings("unused")) after you implement this iterator.
	// This annotation tells compiler that "Do not warn me about this variable not being used".
//	@SuppressWarnings("unused")
	private final MyLinkedList<T> l;
	private Node<T> cursor;

	public MyLinkedListIterator(MyLinkedList<T> myLinkedList) {
		this.l = myLinkedList;
		cursor = l.head;
	}

	@Override
	public boolean hasNext() {
		if(cursor!=null)	return true;
		else return false;
	}

	@Override
	public T next() {
		if(cursor!=null)
		{
			Node<T> t = cursor;
			cursor = cursor.next;
			return t.item;
		}
		else return null;
	}

	@Override
	public void remove() {
		// This code does not have to be modified.
		throw new UnsupportedOperationException();
	}
}
