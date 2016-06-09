import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Stack;

class VertexFinder{
	HashMap<String,VertexGroup> group;
	HashMap<String,Vertex> element;
	
	VertexFinder()
	{
		group = new HashMap<String,VertexGroup>();
		element = new HashMap<String,Vertex>();
	}

	public void addVertex(String key, String id, String line) {
		// TODO Auto-generated method stub
		if(group.get(key)==null)
		{
			group.put(key, new VertexGroup(key));
		}
		
		VertexGroup t = group.get(key);
		Vertex tt = new Vertex(key,id,line);
		
		t.addVertex(tt);
		
		element.put(id, tt);
	}

	public void addEdge(String id, String targetid, int dist) {
		// TODO Auto-generated method stub
		Vertex tt = element.get(id);
		Vertex target = element.get(targetid);
		tt.addEdge(target,dist);
	}
	
	public void initAll(int i)
	{
		Iterator<String> ids = element.keySet().iterator();
        while( ids.hasNext() ){
            String idid = ids.next();
            element.get(idid).init(i);
        }
	}
	

	public void findShortestTransfer(String start, String end) {
		VertexGroup StGroup = group.get(start);

		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
		
		StGroup.setStart();
		
		for(int i = 0; i<StGroup.vertexes.size();i++){
			queue.add(StGroup.vertexes.get(i));
		}
		
		while(!queue.isEmpty()){
			Vertex tt = queue.remove();
			if(tt.visited==true) continue;
			if(tt.key.equals(end)){
				printStack(tt);
				break;
			}
			
			tt.visited=true;
			
			for(int i=0; i<tt.edges.size();i++){
				Vertex ttt = tt.edges.get(i).target;
				if(ttt.visited==false && 
						(ttt.transweight > tt.transweight + tt.edges.get(i).trans || 
								(ttt.transweight == tt.transweight + tt.edges.get(i).trans && ttt.weight > tt.weight + tt.edges.get(i).dist))){
					ttt.weight = tt.weight + tt.edges.get(i).dist;
					ttt.prev = tt;
					ttt.transweight = tt.transweight + tt.edges.get(i).trans;
					queue.remove(ttt);
					queue.add(ttt);
				}
			}
		}
	}


	private void printStack(Vertex tt) {
		Vertex pointer = tt;	
		Stack<String> tracker = new Stack<String>();
		
		while(pointer!=null)
		{
			tracker.push(pointer.key);
			pointer = pointer.prev;
		}
		
		String station,prevstation=null;
		int i=0;
		while(!tracker.isEmpty())
		{
			station = tracker.pop();
			if (prevstation == null){
				// do nothing
				prevstation = station;
			}
			else if(prevstation != null && !prevstation.equals(station)){
				if(i!=0) System.out.print(" ");
				System.out.print(prevstation);
				prevstation = station;
				i++;
			}
			else{
				if(i!=0) System.out.print(" ");
				System.out.print("[" + prevstation + "]");
				prevstation = null;
				i++;
			}
		}
		
		System.out.println(" " + prevstation);
		System.out.println(tt.weight);
	}

	public void findShortestPath(String start, String end) {
		VertexGroup StGroup = group.get(start);
		PriorityQueue<Vertex> queue = new PriorityQueue<Vertex>();
		
		StGroup.setStart();
		
		for(int i = 0; i<StGroup.vertexes.size();i++){
			queue.add(StGroup.vertexes.get(i));
		}
		
		while(!queue.isEmpty()){
			Vertex tt = queue.remove();
			
			if(tt.key.equals(end)){
				printStack(tt);
				break;
			}
			
			tt.visited=true;
			
			for(int i=0; i<tt.edges.size();i++){
				Vertex ttt = tt.edges.get(i).target;
				if(ttt.visited==false && ttt.weight > tt.weight + tt.edges.get(i).dist){
					ttt.weight = tt.weight + tt.edges.get(i).dist;
					ttt.prev = tt;
					queue.remove(ttt);
					queue.add(ttt);
				}
			}
		}
	}
}

class VertexGroup{
	static final int TRANSFER_VALUE = 5;
	String key;    // name of station
	ArrayList<Vertex> vertexes;
	
	VertexGroup(String k){
		key = k;
		vertexes = new ArrayList<Vertex>();
	}

	public void setStart() {
		for(int i = 0; i<vertexes.size();i++){
			vertexes.get(i).setStartPos();
		}
	}

	public void addVertex(Vertex vertex) {
		for(int i = 0; i<vertexes.size();i++){
			vertex.addTransEdge(vertexes.get(i), TRANSFER_VALUE);
			vertexes.get(i).addTransEdge(vertex, TRANSFER_VALUE);
		}
		vertexes.add(vertex);
	}
}

class Vertex implements Comparable<Vertex>{
	String key;
	String id;   // id of station
	Vertex prev;
	String line;
	boolean visited;
	int weight;
	int transweight;
	int istransfer;
	ArrayList<Edge> edges;
	
	public Vertex(String key2, String id2, String line2) {
		// TODO Auto-generated constructor stub
		key = key2;
		id = id2;
		line = line2;
		edges = new ArrayList<Edge>();
	}
	public void addTransEdge(Vertex target, int dist) {
		edges.add(new Edge(target,dist,1));
	}
	public void setStartPos() {
		weight = 0;
		transweight = 0;
	}
	public void init(int i) {
		prev = null;
		weight = 2147483647;
		transweight = 2147483647;
		visited = false;
		istransfer = i;
	}
	public void addEdge(Vertex target, int dist) {
		edges.add(new Edge(target,dist,0));
	}
	@Override
	public int compareTo(Vertex that) {	
		if(this.istransfer == 1 || that.istransfer ==1){
			if(this.transweight < that.transweight) return -1;
			else if(this.transweight > that.transweight) return 1;
			else{
				return this.weight > that.weight ? 1 : -1;
			}
		}else{
			if(this.weight < that.weight) return -1;
			else if(this.weight > that.weight) return 1;
			else return 0;
		}
	}
}

class Edge{
	public Edge(Vertex target2, int dist2, int trans2) {
		dist = dist2;
		target = target2;
		trans = trans2;
	}
	int dist;
	int trans;
	Vertex target;
}


public class Subway {
	public static void main(String args[]) throws Exception
	{
		VertexFinder finder = null;
		
		finder = getData(args[0]);
   	
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (true)
		{
			try
			{
				String input = br.readLine();
				if (input.compareTo("QUIT") == 0)
					break;

				command(input,finder);
			}
			catch (IOException e)
			{
				System.out.println("입력이 잘못되었습니다. 오류 : " + e.toString());
			}
		}
	}

	private static VertexFinder getData(String string) throws Exception {
		// TODO Auto-generated method stub
		VertexFinder t;
		
		t = new VertexFinder();
		
		FileReader reader = new FileReader(string);
        BufferedReader datainput = new BufferedReader(reader);
		
        String singleline = null;
		String key,id,line,targetid;
        int dist;
        
		while(true){
			singleline = datainput.readLine();
			if(singleline.length()==0) break;
			
			id = singleline.split("\\s+")[0];
			key = singleline.split("\\s+")[1];
			line = singleline.split("\\s+")[2];

			t.addVertex(key,id,line);
		}
	
		while((singleline = datainput.readLine()) !=null){	
			id = singleline.split("\\s+")[0];
			targetid = singleline.split("\\s+")[1];
			dist = Integer.parseInt(singleline.split("\\s+")[2]);

			t.addEdge(id,targetid,dist);
		}
		reader.close();
		
		return t;
	}

	private static void command(String input, VertexFinder finder)
	{
		String[] generator = input.split("\\s+");
		if(generator.length==2)
		{
			finder.initAll(0);
			finder.findShortestPath(generator[0],generator[1]);
		}
		else if(generator.length==3 && generator[2].equals("!"))
		{
			finder.initAll(1);
			finder.findShortestTransfer(generator[0],generator[1]);
		}
		else {}
	}
}
