public abstract class AbstractCommand implements Command {
	@Override
	public void apply(MovieDatabase db, String args) throws DatabaseException {
		String[] arga = parse(args);
		queryDatabase(db, arga);
	}

	private String[] parse(String args) throws CommandParseException {
		if (args.isEmpty()) {
			return new String[] {};
		} else {
			// FIXME implement this
			// Parse the input appropriately.
			// You may need to change the return value.
			
			int i=0;
			int cnt=0;
			int[] ind = new int[4];
			while(true)
			{
				ind[cnt]=args.indexOf("%",i);
				if(ind[cnt]==-1) 
				{
					cnt--;
					break;
				}
				i = ind[cnt]+1;
				if(i>=args.length()) break;
				cnt++;
			}
			
			int l=1;
			
			if(cnt==1) l=1;
			else if(cnt==3) l=2; 
			
			String[] result = new String[l];
			
			for(i=0;i<l;i++)
			{
				result[i]=args.substring(ind[2*i]+1, ind[2*i+1]);
			}
			return result;

		}
	}

	protected abstract void queryDatabase(MovieDatabase db, String[] arga) throws DatabaseException;
}
