public interface Command {
	void apply(MovieDatabase db, String args) throws DatabaseException;
}
