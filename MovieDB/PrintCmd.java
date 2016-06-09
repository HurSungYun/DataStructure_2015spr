import java.util.Arrays;

public class PrintCmd extends AbstractCommand {
	@Override
	protected void queryDatabase(MovieDatabase db, String[] arga) throws DatabaseException {
		checkArga(arga);
		MyLinkedList<QueryResult> result = db.search(null);

		//FIXED
		if(result.size() != 0) {
			for (QueryResult item : result) {
				System.out.printf("(%s, %s)\n", item.getGenre(), item.getTitle());
			}
		}
		else {
			System.out.printf("EMPTY\n");
		}
	}

	private void checkArga(String[] arga) throws DatabaseException {
		if (arga.length != 0)
			throw new CommandParseException("PRINT", Arrays.toString(arga), "unnecessary argument(s)");
	}
}