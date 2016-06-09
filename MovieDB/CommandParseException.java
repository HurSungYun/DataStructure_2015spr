@SuppressWarnings("serial")
public class CommandParseException extends DatabaseException {
	private String command;
	private String input;

	public CommandParseException(String cmd, String input, String cause) {
		super(cause, null);
		this.command = cmd;
		this.input = input;
	}

	public String getCommand() {
		return command;
	}
	public String getInput() {
		return input;
	}

}
