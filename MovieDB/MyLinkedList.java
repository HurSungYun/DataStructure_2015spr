import java.util.Iterator;
import java.util.NoSuchElementException;

class Node<T> {
	// FIXME implement this
	final T item;
	Node<T> next;

	public Node(T obj) {
		this.item = obj;
		this.next = null;
	}
}

public class MyLinkedList<T extends Comparable<T>> implements Iterable<T> {
	// FIXME implement this
	// Implement a linked list.
	// This linked list should maintain the items in a sorted order.
	// This linked list should discard a duplicate.

	Node<T> head = null;

	@Override
	public Iterator<T> iterator() {
		// This code does not have to be modified.
		// Implement MyLinkedListIterator instead.
		return new MyLinkedListIterator<T>(this);
	}

	public boolean add(T obj) {
		// FIXME implement this
		if(head==null)       
		{
			head = new Node<T>(obj);
		}
		else if(head.item.compareTo(obj)>0)  
		{
			Node<T> p = new Node<T>(obj);
			p.next = head;
			head = p;
		}
		else if(head.item.equals(obj))
		{
			return false;
		}
		else if(head.next!=null)      
		{
			Node<T> p = head.next;
			Node<T> prev = head;
			while(true)
			{
				if(p.item.compareTo(obj)>0)   
				{
					Node<T> t = new Node<T>(obj);
					t.next = p;
					prev.next = t;
					break;
				}
				else if(p.item.equals(obj))   
				{
					break;
				}
				else if(p.next==null)    
				{
					Node<T> t = new Node<T>(obj);
					p.next = t;
				}
				else
				{
					p = p.next;
					prev = prev.next;
				}
			}
		}
		else  
		{
			Node<T> t = new Node<T>(obj);
			t.next = null;
			head.next = t;
		}
		return true;
	}

	public boolean remove(T obj) {
		// FIXME implement this
		
		if(head==null)  
		{
			return true;
		}
		else if(head.item.compareTo(obj)>0) 
		{
			return false;
		}
		else if(head.item.equals(obj))  
		{
			if(head.next==null)
				return true;
			else
			{
				head=head.next;
				return false;
			}
		}
		else if(head.next!=null)  
		{
			Node<T> p = head.next;
			Node<T> prev = head;
			while(true)
			{
				if(p.item.equals(obj))
				{
					prev.next = p.next;
					return false;
				}
				else if(p.next!=null && p.item.compareTo(obj)<0)
				{
					p = p.next;
					prev = prev.next;
				}
				else
					break;
			}
			return false;
		}
		else
			return false;
		
//		throw new UnsupportedOperationException();
	}

	public int size() {
		// FIXME implement this
		Node<T> t=head;
		int i=1;
		
		while(t!=null)
		{
//			if(t.next==null) break;
			t = t.next;
			i++;
		}
		
		return head != null ? i : 0;
	}

	public T first() {
		// FIXME implement this
		// This is a helper method.
		// You do not necessarily have to implement this but still might be useful to do so.
		if (head != null)
			return head.item;
		else
			throw new NoSuchElementException();
	}

	public T last() {
		// FIXME implement this
		// This is a helper method.
		// You do not necessarily have to implement this but still might be useful to do so.
		throw new UnsupportedOperationException();
	}
}
