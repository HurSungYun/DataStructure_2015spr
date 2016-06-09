public class CommandNotFoundException extends Exception {
	private String command;

	public CommandNotFoundException(String command) {
		super(String.format("input command: %s", command));
		this.command = command;
	}

	private static final long serialVersionUID = 1L;

	public String getCommand() {
		return command;
	}
}
