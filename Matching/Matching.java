import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

class HashTableManager{
	
	HashTable_AVL table;
	int k,length;
	ArrayList<String> text;
	
	HashTableManager(int l,int kk)
	{
		k=kk;
		length = l;
		table = new HashTable_AVL(length,kk);
	}
	
	void print(int idx)
	{
		table.print(idx);
	}
	
	void setString(String x,int doc)
	{
		for(int i=0;i+k<=x.length();i++)
		{
			table.doHashing(x.substring(i,i+k), doc, i);
		}
	}
	
	void findString(String x)
	{
		int i=0;
		LinkedList<Pair,String> t = table.find(x.substring(0,6));
		if(t == null) {
			System.out.println("(0, 0)");
		}else{
			LinkedListIterator<Pair,String> iter = new LinkedListIterator<Pair,String>(t);
			while(iter.hasNext())
			{
				Pair candi = iter.next();
				if(text.get(candi.doc).length()>= candi.idx + x.length() && text.get(candi.doc).substring(candi.idx, candi.idx + x.length()).equals(x))
				{
					if(i!=0) System.out.print(" ");
					System.out.print("(" + (candi.doc+1) + ", " + (candi.idx+1) + ")");
					i++;
				}
			}
			if(i==0){
				System.out.print("(0, 0)");
			}
			System.out.println();
		}
	}

	public void makefile(String params) throws Exception {
		
		//modification from https://wikidocs.net/227
		
		table = new HashTable_AVL(length,k);
		text = new ArrayList<String>();
		
		FileReader rd = new FileReader(params);
        BufferedReader reader = new BufferedReader(rd);
		
        String line = null;
		int i=0;
		
		while((line = reader.readLine()) != null){
			setString(line,i);
			text.add(line);
			i++;
		}
		reader.close();
	}
}

class HashTable_AVL{
	
	AVL_Tree[] array;
	int k;
	
	HashTable_AVL(int x,int kk)
	{
		array = new AVL_Tree[x];
		for(int i=0;i<x;i++)
		{
			array[i] = new AVL_Tree();
		}
		k=kk;
	}
	
	boolean doHashing(String x,int doc,int idx)
	{
		if(x.length()!=6) return false;
		array[getFunction(x)].add(new Pair(x,doc,idx));
		return true;
	}
	
	void print(int idx)
	{
		if(array[idx].head==null) System.out.println("EMPTY");
		else array[idx].print();
	}
	
	LinkedList<Pair, String> find(String x)
	{
		return array[getFunction(x)].find(x);
	}
	
	private int getFunction(String x)
	{
		int i,sum=0;
		for(i=0;i<k;i++)
			sum+=(int)x.charAt(i);
		return sum % 100;
	}
}

class AVL_Tree{
	AVLNode head;
	
	AVL_Tree()
	{
		head=null;
	}
	
	public LinkedList<Pair, String> find(String x) {
		// TODO Auto-generated method stub
		AVLNode t = head;
		while(t!=null){
			if(t.item.key.compareTo(x)==0){
				return t.item;
			}else if(t.item.key.compareTo(x)>0){
				t = t.getLeft();
			}else{
				t = t.getRight();
			}
		}
		return null;
	}

	public void print() {
		printInorder(head);
		System.out.println();
	}

	private void printInorder(AVLNode x) {
		// TODO Auto-generated method stub
		if(x!=null){
			if(x!=head) System.out.print(" ");
			System.out.print(x.item.key);
			printInorder(x.getLeft());
			printInorder(x.getRight());
		}
	}

	void add(Pair x)
	{
		if(head==null)
		{
			LinkedList<Pair,String> t = new LinkedList<Pair,String>(x, x.str);
			head = new AVLNode(t);
		}else
		{
			head = insert(head,x);
		}
	}

	private AVLNode insert(AVLNode node, Pair x) {
		AVLNode t;
		int balance;
		
		if(node.item.key.compareTo(x.str)==0){
			node.item.add(x);
		}else if(node.item.key.compareTo(x.str)>0){
			t = node.getLeft();
			if(t==null){
				t = new AVLNode(new LinkedList<Pair,String>(x, x.str));
				node.setLeft(t);
			}
			else{
				node.setLeft(insert(node.getLeft(),x));
			}
		}else{
			t = node.getRight();
			if(t==null){
				t = new AVLNode(new LinkedList<Pair,String>(x, x.str));
				node.setRight(t);
			}
			else{
				node.setRight(insert(node.getRight(),x));
			}
		}
		
		balance = node.getBalance();
		
	    if (balance > 1 && x.str.compareTo(node.getLeft().item.key) < 0){
	        return rotateLL(node);
	    }else if (balance < -1 && x.str.compareTo(node.getRight().item.key) > 0){
	        return rotateRR(node);
	    }else if (balance > 1 && x.str.compareTo(node.getLeft().item.key) > 0){
	        node.setLeft(rotateRR(node.getLeft()));
	        return rotateLL(node);
	    }else if (balance < -1 && x.str.compareTo(node.getRight().item.key) < 0){
	    	node.setRight(rotateLL(node.getRight()));
	        return rotateRR(node);
	    }else
	    	return node;
	}

	private AVLNode rotateLL(AVLNode node) {
		AVLNode t = node.getLeft();
		node.setLeft(t.getRight());
		t.setRight(node);
		return t;
	}

	private AVLNode rotateRR(AVLNode node) {
		AVLNode t = node.getRight();
		node.setRight(t.getLeft());
		t.setLeft(node);
		return t;
	}

	public void delete(Pair x){
		// @UnSupportedMethod
	}
}

class LinkedList<T,U>{
	
	Node<T> head,last;
	U key;
	
	LinkedList(){
		head=null;
	}
	
	LinkedList(T x,U k){
		head = new Node<T>(x);
		last = head;
		key = k;
	}
	
	void add(T x)
	{
		if(head==null)
		{
			head = new Node<T>(x);
			last = head;
		}
		else{
/*			Node<T> t = head;
			while(t.next!=null)
				t=t.next;
			t.next = new Node<T>(x);
*/
			last.next = new Node<T>(x);
			last = last.next;
		}
	}
	
	public void remove(int index){
		// @UnSupportedMethod
	}
	public boolean isEmpty(){
		// @UnSupportedMethod
		return false;
	}
	public int size(){
		// @UnSupportedMethod
		return 0;
	}
	public void removeAll( ){
		// @UnSupportedMethod
	}
	public Object get(int index){
		// @UnSupportedMethod
		return null;
	}

	
	U getString()
	{
		return key;
	}
}

class LinkedListIterator<T,U>{

	private final LinkedList<T,U> l;
	private Node<T> cursor;
	
	LinkedListIterator(LinkedList<T,U> myLinkedList) {
		this.l = myLinkedList;
		cursor = l.head;
	}
	
	public boolean hasNext() {
		if(cursor!=null)	return true;
		else return false;
	}

	public T next() {
		if(cursor!=null)
		{
			Node<T> t = cursor;
			cursor = cursor.next;
			return t.key;
		}
		else return null;
	}
	
}

class Node<T>{
	T key;
	Node<T> next;
	
	Node(T x)
	{
		key = x;
		next = null;
	}

}

class AVLNode{
	LinkedList<Pair,String> item;
	AVLNode left,right;
	int height;
	
	AVLNode(LinkedList<Pair,String> t)
	{
		item = t;
		left = null;
		right = null;
		height = 1;
	}

	public int getBalance() {
		// TODO Auto-generated method stub
		int l,r;
		if(this.left==null) l = 0;
		else l = this.left.height;
		
		if(this.right==null) r = 0;
		else r = this.right.height;
		
		return l - r;
	}

	void setLeft(AVLNode x){
		left = x;
		setHeight();
	}

	void setRight(AVLNode x){
		right = x;
		setHeight();
	}
	
	private void setHeight() {
		// TODO Auto-generated method stub
		int l,r;
		if(this.left==null) l = 0;
		else l = this.left.height;
		
		if(this.right==null) r = 0;
		else r = this.right.height;
		
		this.height = l>r ? l : r;
		this.height++;
	}
	
	AVLNode getRight(){
		return right;
	}
	
	AVLNode getLeft(){
		return left;
	}
}

class Pair{
	String str;
	int doc,idx;
	Pair(String z,int x,int y){
		str=z;
		doc=x;
		idx=y;
	}
}

public class Matching
{
	public static void main(String args[])
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		HashTableManager manager = new HashTableManager(100,6);
		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input,manager);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static void command(String input,HashTableManager manager)
	{
		char cmd = input.charAt(0);
		String params = input.substring(2);
		try{
			if(cmd=='<'){
				manager.makefile(params);
			}else if(cmd=='@'){
				manager.print(Integer.parseInt(params));
			}else if(cmd=='?'){
				manager.findString(params);
			}else{
			// UnSupportedCommandException;
			}
		}catch(Exception e){
			System.out.println(e);
		}
	}
}