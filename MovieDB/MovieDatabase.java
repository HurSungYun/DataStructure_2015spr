import java.util.Iterator;

public class MovieDatabase {
	
	Genre head;
	
	public MovieDatabase() {
		// FIXME implement this
		// Maintain a linked list of Genre using MyLinkedList
		head = null;
	}

	public void insert(String genre, String title) {
		// FIXME implement this
		// Insert the given genre and title to the MovieDatabase.
		// Printing functionality is provided for the sake of debugging.
		// This code should be removed before submitting your work.
		if(head==null)                       
		{
			head = new Genre(genre);
			head.list.add(title);
		}
		else if(head.item.equals(genre))  
		{
			head.list.add(title);
		}
		else if(head.item.compareTo(genre)>0)     
		{
			Genre p = new Genre(genre);
			p.next = head;
			head = p;
			p.list.add(title);
		}
		else if(head.next!=null)    
		{
			Genre p = head.next;
			Genre prev = head;
			while(true)
			{
				if(p.item.equals(genre))  
				{
					p.list.add(title);
					break;
				}					
				if(p.item.compareTo(genre)>0)   
				{
					Genre t = new Genre(genre);
					t.next = p;
					prev.next = t;
					t.list.add(title);
					break;
				}
				else if(p.next==null)    
				{
					Genre t = new Genre(genre);
					p.next = t;
					t.next = null;
					t.list.add(title);
					break;
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
			Genre t = new Genre(genre);
			head.next = t;
			t.list.add(title);
		}
		
		
		
//		System.err.printf("[trace] INSERT [%s] [%s]\n", genre, title);
	}

	public void delete(String genre, String title) {
		// FIXME implement this
		// Remove the given genre and title from the MovieDatabase.
		// Printing functionality is provided for the sake of debugging.
		// This code should be removed before submitting your work.
		
		boolean alert;
		if(head==null || head.item.compareTo(genre)>0)    
		{
			return;
		}
		else if(head.item.equals(genre))  
		{
			alert = head.list.remove(title);
			if(alert == true)
			{
				if(head.next!=null)
					head = head.next;
				else
					head = null;
			}
		}
		else if(head.next!=null)
		{
			Genre p = head.next;
			Genre prev = head;
			while(true)
			{
				if(p.item.equals(genre))  
				{
					alert = p.list.remove(title);
						
					if(alert == true)
					{
						if(p.next==null)
							prev.next=null;
						else
						{
							prev.next=p.next;
							p.next=null;
						}
					}
					
					break;
				}
				if(p.next!=null && p.item.compareTo(genre)<0)
				{
					p = p.next;
					prev = prev.next;
				}
				else  
				{
					break;
				}
			}
		}
	}

	public MyLinkedList<QueryResult> search(String term) {
		// FIXME implement this
		// Search the given term from the MovieDatabase.
		// You should return a linked list of QueryResult.
		// The search command is handled at SearchCmd.java.
		// Printing functionality is provided for the sake of debugging.
		// This code should be removed before submitting your work.\
		
		if(term==null) term="";
		
//		System.err.printf("[trace] SEARCH [%s]\n", term);

		MyLinkedList<QueryResult> results = new MyLinkedList<QueryResult>();
		
		Genre t = head;
		
		while(t!=null)
		{
			MyLinkedList<String> tt = t.list;
			
			Iterator<String> it = tt.iterator();
			
			while(it.hasNext())
			{
				String x = it.next();
				if(x.indexOf(term)!=-1)
					results.add(new QueryResult(t.item,x));
			}
			t = t.next;
		}
		return results;
	}
}
