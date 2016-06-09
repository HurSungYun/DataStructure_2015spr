import java.util.Arrays;

public class InsertCmd extends AbstractCommand {
	@Override
	protected void queryDatabase(MovieDatabase db, String[] arga) throws DatabaseException {
		checkArga(arga);
		db.insert(arga[0], arga[1]);
	}

	private void checkArga(String[] arga) throws DatabaseException {
		if (arga.length != 2)
			throw new CommandParseException("INSERT", Arrays.toString(arga), "insufficient argument");
	}
}
