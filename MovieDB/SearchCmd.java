import java.util.Arrays;

public class SearchCmd extends AbstractCommand {
	@Override
	protected void queryDatabase(MovieDatabase db, String[] arga) throws DatabaseException {
		checkArga(arga);
		MyLinkedList<QueryResult> result = db.search(arga[0]);

		//FIXED
		if(result.size() != 0) {
			for (QueryResult item: result) {
				System.out.printf("(%s, %s)\n", item.getGenre(), item.getTitle());
			}
		}
		else {
			System.out.printf("EMPTY\n");
		}
	}

	private void checkArga(String[] arga) throws DatabaseException {
		if (arga.length != 1)
			throw new CommandParseException("SEARCH", Arrays.toString(arga), "insufficient argument");
	}
}
