import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Calculator extends Stack<Long>
{
	public Calculator()
	{
		top = null;
	}

	void printCalculatedResult()
	{
		System.out.println(top.key);
	}
	
	void push(String x) throws Exception
	{	
		long a,b,t;
		
		if(x.equals("^"))
		{
			a=super.pop();
			b=super.pop();
			if(b==0 && a<0) throw new Exception("PowerZeroException");
			t=(long)Math.pow(b, a);
			
			super.push(t);
		}
		else if(x.equals("~"))
		{
			a=super.pop();
			t=a*(long)(-1);
			
			super.push(t);
		}
		else if(x.equals("*"))
		{
			a=super.pop();
			b=super.pop();
			t=a*b;
			super.push(t);
		}
		else if(x.equals("/"))
		{
			a=super.pop();
			b=super.pop();
			t=b/a;
			super.push(t);
		}
		else if(x.equals("%"))
		{
			a=super.pop();
			b=super.pop();
			t=b%a;
			super.push(t);
		}
		else if(x.equals("+"))
		{
			a=super.pop();
			b=super.pop();
			t=a+b;
			super.push(t);
		}
		else if(x.equals("-"))
		{
			a=super.pop();
			b=super.pop();
			t=b-a;
			super.push(t);
		}
		else
		{
			super.push(Long.parseLong(x));
		}
	}
	
	int chkUnderflow()
	{
		Node<Long> t = top;
		if(t==null) return 0;
		else if(t.next==null) return 1;
		else return 2;
	}
}

class PostOrderString
{
	String post;
	
	public PostOrderString()
	{
		post = null;
	}
	
	void printPostOrder()
	{
		System.out.println(post);
	}
	
	void putPostString(String x)
	{
		if(post==null)
		{
			post = x;
		}
		else
		{
			post = post.concat(" " + x);
		}
	}
}

class Stack<T>
{
	Node<T> top;
	int chkerror;
	
	public Stack()
	{
		top = null;
		chkerror = 0;
	}

	void push(T x)
	{
		if(top == null)
		{
			top = new Node<T>(x);
			top.next = null;
		}
		else
		{
			Node<T> t = new Node<T>(x);
			t.next = top;
			top = t;
		}
	}

	T pop() throws Exception
	{
		if(top==null)
		{
			throw new Exception("UnderflowException");
		}
		Node<T> t = top;
		if(top.next!=null)
		{
			top = top.next;
		}
		else
			top=null;
		
		return t.key;
	}
	
	T atTop()
	{
		if(top!=null)
			return top.key;
		else
			return null;
	}
	
	int isEmpty()
	{
		if(top==null)
			return 1;
		else
			return 0;
	}
}

class Node<T>
{
	T key;
	Node<T> next;
	
	Node(T x)
	{
		key = x;
		next = null;
	}
}

class Tokenizer
{
	Matcher matcher;
	int l;
	int t = -1;
	Tokenizer(String t)
	{
		l = t.length();
		Pattern pattern = Pattern.compile("(\\+|-|\\*|/|%|\\^|\\(|\\)|[0-9]+)\\s*");
		matcher = pattern.matcher(t);
	}
	
	boolean isEmpty()
	{
		if(matcher.find())
		{
			t = matcher.end();
			return true;
		}
		else {

			return false;
		}
	}
	
	boolean isOver()
	{
		return l==t;
	}
	
	String nextToken()
	{
		return matcher.group().trim();
	}
}

public class CalculatorTest
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("q") == 0)
					break;

				command(input);
			}
			catch (Exception e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input)
	{
		Stack<String> opSt = new Stack<String>();             // operator stack
		Calculator Cal = new Calculator();                    // postfix calculator 
		PostOrderString postorder = new PostOrderString();    // post order string for print result
		Tokenizer tokenizer = new Tokenizer(input.trim());    // input string and parsing
		
		int k;
		int prev_kind = 0;
		String popped,t;
		
		try{
			while(tokenizer.isEmpty())                   // make infix to post fix
			{
				t = tokenizer.nextToken();               
				
				if(t.equals("("))
				{
					if(prev_kind == 1) throw new Exception("WrongEquationException");
					opSt.push(t);
					prev_kind=0;
				}
				else if(t.equals("^"))
				{
					while(opSt.isEmpty()!=1)
					{
						if(getPriority(opSt.atTop()) < getPriority(t))
						{
							popped = opSt.pop();
							postorder.putPostString(popped);
							Cal.push(popped);
						}
						else break;
					}
					opSt.push(t);
					prev_kind=0;
				}
				else if(t.equals("-"))
				{
					if(prev_kind==0)
					{
						while(opSt.isEmpty()!=1)
						{
							if(getPriority(opSt.atTop()) < getPriority("~"))
							{
								popped = opSt.pop();
								postorder.putPostString(popped);
								Cal.push(popped);
							}
							else break;
						}
						opSt.push("~");
					}
					else
					{
						while(opSt.isEmpty()!=1)
						{
							if(getPriority(opSt.atTop()) <= getPriority("-"))
							{
								popped = opSt.pop();
								postorder.putPostString(popped);
								Cal.push(popped);
							}
							else break;
						}
						opSt.push("-");
					}
					
					prev_kind=0;
				}
				else if(t.equals("+") || t.equals("*") || t.equals("/") || t.equals("%"))
				{
					while(opSt.isEmpty()!=1)
					{
						if(getPriority(opSt.atTop()) <= getPriority(t))
						{
							popped = opSt.pop();
							postorder.putPostString(popped);
							Cal.push(popped);
						}
						else break;
					}
					opSt.push(t);
					
					prev_kind=0;
				}
				else if(t.equals(")"))
				{
					k=0;
					while(true)
					{
						if(opSt.atTop().equals("(")==false)
						{
							popped = opSt.pop();
							postorder.putPostString(popped);
							Cal.push(popped);
							k++;
						}
						else
						{
							if(k==0  && prev_kind!=1) throw new Exception("EmptyParenthesisException");
							popped = opSt.pop();
							break;
						}
					}
					
					prev_kind=1;
				}
				else 
				{
					if(prev_kind == 1) throw new Exception("WrongEquationException");
					Long.parseLong(t);
					postorder.putPostString(t);
					Cal.push(t);
					prev_kind=1;
				}
			}	
			
			if(!tokenizer.isOver()) throw new Exception("ParsingException");
			
			while(opSt.isEmpty()!=1)
			{
				popped = opSt.pop();
				postorder.putPostString(popped);
				Cal.push(popped);
			}
		
			if(Cal.chkUnderflow()!=1) throw new Exception("RemainNumException");

			postorder.printPostOrder();                 // print result
			Cal.printCalculatedResult();                // print result
			
		}catch(Exception e)
		{
			System.out.println("ERROR");
		}
	}

	static int getPriority(String x) {
		if(x.equals("(") || x.equals(")"))
			return 999;
		else if(x.equals("^"))
			return 1;
		else if(x.equals("~"))
			return 2;
		else if(x.equals("*") || x.equals("/") || x.equals("%"))
			return 3;
		else if(x.equals("+") || x.equals("-"))
			return 4;
		else
			return -1;
	}

}