import java.io.*; 

public class BigInteger
{
	
	static class Bignum
	{
		int[] number = new int[201];
		int plusminus = 1;
		
		int setToArray(String input, int x)
		{
			// TODO Auto-generated method stub
			int i = 0;
			int j,k;			
			k=x;
				
			if(input.charAt(x)=='+')
			{
				this.plusminus=1;
				k++;
			}
			else if(input.charAt(x)=='-')
			{
				this.plusminus=-1;
				k++;
			}
			
			while(true)
			{
				if(input.charAt(i+k)=='0')
				{
					k++;
				}
				else
					break;
				
				if(i+k==input.length())
				{
					break;
				}
			}
			
			while(true)
			{
				if(i+k==input.length())
				{
					break;
				}
				
				if(input.charAt(i+k)==' ' || input.charAt(i+k)=='+' || input.charAt(i+k)=='-' || input.charAt(i+k)=='*')
				{
					break;
				}
					i++;
				
			}
			
			if(i==0)
			{
				k--;
				i++;
			}
			
			for(j=0;j<i;j++)
			{
				this.number[201-i+j]=(int)input.charAt(k+j) - '0';
			}
				
			return i+k;
		}
		
		void plus(Bignum x, Bignum y)
		{
			int i;
			
			if(x.plusminus * y.plusminus == 1) 
			{
				plusminus = x.plusminus;
				
				for(i=0;i<201;i++)
				{
					if(x.number[i]!=-1)
						number[i]+=x.number[i];
					if(y.number[i]!=-1)
						number[i]+=y.number[i];
				}
				
				for(i=200;i>0;i--)
				{
					if(number[i]>=10)
					{
						number[i]-=10;
						number[i-1]++;
					}
				}
				
				int j=0;
				
				for(i=200;i>=0;i--)
				{
					if(number[i]!=0)
						j=1;
				}
				
				if(j==0) plusminus = 1;
				
			}
			else 
			{
				if(this.absBigger(x, y)==0)
				{
					plusminus =x.plusminus;
					this.XminusY(x, y);
				}
				else if(this.absBigger(x, y)==1)
				{
					plusminus =y.plusminus;
					this.XminusY(y, x);
				}
				else
				{
					plusminus = 1;
					return;
				}
			}
		}
		
		void XminusY(Bignum x, Bignum y)
		{
			int i;
			
			for(i=0;i<201;i++)
			{
				if(x.number[i]!=-1)
					number[i]+=x.number[i];
				if(y.number[i]!=-1)
					number[i]-=y.number[i];
			}
			
			for(i=200;i>0;i--)
			{
				if(number[i]<0)
				{
					number[i]+=10;
					number[i-1]--;
				}
			}
		}
		
		int absBigger(Bignum x, Bignum y)   // 0 : x is big,   1 : y is big  2 : x,y is same
		{
			int i=0;
			while(true)
			{
				if(x.number[i]!=-1 && y.number[i]!=-1)
				{
					int j=i;
					while(true)
					{
						if(x.number[j]>y.number[j])
							return 0;
						else if(x.number[j]<y.number[j])
							return 1;
						else if(j==200)
							return 2;
						j++;
					}
				}
				else if(x.number[i]!=-1)
					return 0;
				else if(y.number[i]!=-1)
					return 1;
				i++;
			}
		}
		
		void minus(Bignum x, Bignum y)
		{
			y.plusminus*=-1;
			this.plus(x, y);
		}
		
		void multi(Bignum x, Bignum y)
		{
			int i,j,k;
			plusminus = x.plusminus * y.plusminus;
			
			for(i=200;i>=0;i--)
			{
				for(j=200;j>=0;j--)
				{
					if(i+j-200>=0 && x.number[i]!=-1 && y.number[j]!=-1)
					{
						number[i+j-200]+=x.number[i] * y.number[j];
					}
			
					for(k=i+j-200;k>=1;k--)
					{
						if(number[i]>=10)
						{
							number[i]-=10;
							number[i-1]++;
						}
					}
				}
			}
			
			k=0;
			
			for(i=200;i>=0;i--)
			{
				if(number[i]!=0)
					k=1;
			}
			
			if(k==0) plusminus = 1;
		}
		
		void print()
		{
			int i=1;
				
			if(plusminus==-1) System.out.print("-");
				
			for(i=0;i<201;i++)
			{
				if(number[i]!=-1)
					System.out.print(number[i]);
			}
			System.out.println();
		}
		
		void printResult()
		{
			int i=1;
			int flag=0;
			
			if(plusminus==-1) System.out.print("-");
				
			for(i=0;i<201;i++)
			{
				if(number[i]!=0) flag=1;
				
				if(flag==1)
					System.out.print(number[i]);
			}
			if(flag==0) System.out.print("0");
			System.out.println();
		}
		
		Bignum(int x)
		{
			int i;
			for(i=0;i<201;i++)
			{
				this.number[i] = x;
			}
		}
	}
	
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try 
			{
				String input = br.readLine();

				if (input.compareTo("quit") == 0)
				{
					break;
				}

				calculate(input);
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void calculate(String exinput)
	{
		
		int typeflag = -1;
		int i=0;
		String input = exinput.replaceAll(" ", "");
		
		Bignum a = new Bignum(-1);
		Bignum b = new Bignum(-1);
		Bignum c = new Bignum(0);
		
		i = a.setToArray(input,i);
		
		if(input.charAt(i)=='+')
		{
			typeflag=0;
		}
		else if(input.charAt(i)=='-')
		{
			typeflag=1;
		}
		else if(input.charAt(i)=='*')
		{
			typeflag=2;
		}
		
		i++;
		i = b.setToArray(input,i);
		
		if(typeflag == 0)
		{
			c.plus(a, b);
		}
		else if(typeflag == 1)
		{
			c.minus(a, b);
		}
		else
		{
			c.multi(a, b);
		}		
		c.printResult();
	}
}