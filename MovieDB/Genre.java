class Genre implements Comparable<Genre> {
	// FIXME implement this
	// Implement a Genre class.
	// This class should hold the name of the genre.
	// This class should maintain a linked list of movie titles for this genre.
	
	MyLinkedList<String> list;
	String item;
	Genre next;
	
	public Genre(String name) {
		list = new MyLinkedList<String>();
		this.item = name;
		next = null;
	}

	@Override
	public int compareTo(Genre other) {
		// TODO implement this
		return this.item.compareTo(other.item);
//		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Genre other = (Genre) obj;
		if (item == null) {
			if (other.item != null)
				return false;
		} else if (!item.equals(other.item))
			return false;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((item == null) ? 0 : item.hashCode());
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return this.item;
	}
}
