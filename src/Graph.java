/*Data Structures Used*/
import java.util.ArrayList;
import java.util.Stack;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Queue;
import java.util.LinkedList;
import java.util.PriorityQueue;

/*For writing into Files*/
import java.io.FileWriter;
/*Each time writer is invoked a try catch block is used
 * for catching IO Exception*/
import java.io.IOException;
/*For Sorting*/
import java.util.Collections;
public class Graph {
	public int N;//Number of Vertices
	
	/*For writing to files*/
	public static FileWriter writer;
	
	/*Varibles Used for finding Articulation Point*/
	public int low[];
	public int count;//for dfsnum
	public int dfsnum[];
	public int vis[];
	public int parent[];
	public int ap[];
	
	/*Adjacency for GT to be in finding SCC*/
	public ArrayList<Pair>[] adjlistGT;
	/*Adjacency List used in Algorithms*/
	public ArrayList<Pair>[] adjlist;
	
	
	Graph(int N)
	{
		this.N=N;
		adjlist=new ArrayList[this.N+1];
		adjlistGT=new ArrayList[this.N+1];
		for(int i=1;i<=N;i++)
			adjlist[i]=new ArrayList<>();
		for(int i=1;i<=N;i++)
			adjlistGT[i]=new ArrayList<>();
	}
	public void addEdge(int u,int v,int w)
	{
		adjlist[u].add(new Pair(v,w));
		adjlistGT[v].add(new Pair(u,w));
	}
	public void printadjlist()
	{
		for(int u=1;u<=N;u++)
		{
			System.out.print(u+" -> ");
			for(int j=0;j<adjlist[u].size();j++)
			{
				int v=adjlist[u].get(j).key;
				int w=adjlist[u].get(j).value;
				System.out.print("("+v+","+w+")"+" ");
			}
			System.out.println();
		}
	}
	/*Scanner object is passed from the main so,it will continue where ever it has
	 * left over*/
	public void takeInputFromFile(Scanner scan)
	{
		for(int u=1;u<=7;u++)
		{
			int n=scan.nextInt();
			for(int j=0;j<n;j++)
			{
				int v=scan.nextInt();
				int w=scan.nextInt();
				this.addEdge(u, v, w);
			}
		}
	}
	
	/*1)Topological Sort() using Queue*/
	public void topologicalSort()
	{
		int indegree[]=new int[N+1];
		Queue<Integer>q=new LinkedList<>();
		int Ans[]=new int[N+1];
		int count=0;
		for(int i=1;i<=N;i++)
		{
			for(int j=0;j<adjlist[i].size();j++)
			{
				indegree[adjlist[i].get(j).key]++;
			}
		}
		for(int i=1;i<=N;i++)
		{
			if(indegree[i]==0)
				q.add(i);
		}
		while(!q.isEmpty())
		{
			int u=q.peek();
			Ans[u]=++count;
			q.poll();
			for(int i=0;i<adjlist[u].size();i++)
			{
				if(--indegree[adjlist[u].get(i).key]==0)
				{
					q.add(adjlist[u].get(i).key);
				}
			}
		}
		if(count!=N)
		{
			try {
				writer.write("Cycle Exists,hence not topological sorted order\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
		{
			try {
				System.out.println("The topological sort of the first graph is: \n");
				System.out.println("Vertex   Number");
				writer.write("The topological sort of the first graph is: \n");
				writer.write("Vertex    Number\n");
				for(int i=1;i<=7;i++)
				{
					System.out.println((char)(i+64) +"         "+Ans[i]);
					writer.write((char)(i+64) +"         "+Ans[i]+"\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
	}
	/*2)Dijistra's Algorithm*/
	public void dijistraSP()
	{
		PriorityQueue<Pair>pq=new PriorityQueue<>();
		int dis[]=new int[8];
		vis=new int[8];
		dis[1]=0;
		for(int i=2;i<=7;i++)
			dis[i]=Integer.MAX_VALUE;
		for(int i=1;i<=7;i++)
			vis[i]=0;
		pq.add(new Pair(1,0));
		while(!pq.isEmpty())
		{
			Pair u=pq.poll();
			vis[u.key]=1;
			for(int i=0;i<adjlist[u.key].size();i++)
			{
				int v=adjlist[u.key].get(i).key;
				int currentdist=dis[u.key]+adjlist[u.key].get(i).value;
				int previousdist=dis[v];
				if(currentdist < previousdist && vis[v]==0)
				{
					dis[v]=currentdist;
					pq.add(new Pair(v,currentdist));
				}
			}
		}
		try {
			System.out.println("\nFor the second graph");
			writer.write("\nFor the second graph\n");
			for(int i=1;i<=7;i++)
			{
				System.out.println("Shortest Path from "+(char)(65)+" to "+(char)(64+i)+" "+"(distance = "+dis[i]+" )");
				writer.write("Shortest Path from "+(char)(65)+" to "+(char)(64+i)+" "+"(distance = "+dis[i]+" )\n");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*3) Minimum Spanning Tree,Kriskal's Algorithm*/
	public void kriskalMSP()
	{
		ArrayList<Edge>A=new ArrayList<>();
		int s[]=new int[N+1];
		for(int i=1;i<=N;i++)
			s[i]=i;
		int added[][]=new int[N+1][N+1];
		ArrayList<Edge>Ans=new ArrayList<>();
		for(int i=1;i<=7;i++)
		{
			for(int j=0;j<adjlist[i].size();j++)
			{
				int v=adjlist[i].get(j).key;
				int w=adjlist[i].get(j).value;
				if(added[i][v]==0)
				{	
					added[i][v]=1;
					A.add(new Edge(i,v,w));
				}
			}
		}
		Collections.sort(A);
		int Cost=0;
		for(int i=0;i<A.size();i++)
		{
			int u=A.get(i).u;
			int v=A.get(i).v;
			int w=A.get(i).w;
			int u1=find(s,u);
			int u2=find(s,v);
			if(u1!=u2)
			{
				union(s,u,v);
				Cost+=w;
				Ans.add(A.get(i));
			}
			if(Ans.size()==N-1)
				break;
		}
		try {
			System.out.println("\nThe edges in MSP for graph 3 are ");
			writer.write("\nThe edges in MSP for graph 3 are \n");
			for(int i=0;i<Ans.size();i++)
			{
				System.out.print("("+(char)(Ans.get(i).u+64)+","+(char)(Ans.get(i).v+64)+")"+" ");
				writer.write("("+(char)(Ans.get(i).u+64)+","+(char)(Ans.get(i).v+64)+")"+" ");
			}
			System.out.println("\nIts cost is "+Cost);
			writer.write("\nIts cost is "+Cost);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	/*union-disjoint DS for kriskal's algorithm*/
	public void union(int S[],int r1,int r2)
	{
		int x=find(S,r1);
		int y=find(S,r2);
		S[y]=x;
	}
	public int find(int S[],int x)
	{
		while(S[x]!=x)
			x=S[x];
		return x;
	}
	/*4,5 Articulation Points*/
	public void dfsForArticulationPoints(int src)
	{
		vis[src]=1;
		count++;
		int childc=0;
		low[src]=count;
		dfsnum[src]=count;
		for(int i=0;i<adjlist[src].size();i++)
		{
			int v=adjlist[src].get(i).key;
			if(vis[v]==0)
			{
				parent[v]=src;
				childc++;
				dfsForArticulationPoints(v);
				if(parent[src]==-1 && childc>1)
					ap[src]=1;
				low[src]=Integer.min(low[src], low[v]);
				if(parent[src]!=-1 && low[v]>=dfsnum[src])
				{
					//System.out.println("Edge "+src+"->"+v+" "+low[v]+" "+dfsnum[src]);
					ap[src]=1;
				}
				
			}
			else if(parent[src]!=v)
			{
				low[src]=Integer.min(dfsnum[v], low[src]);
			}
		}
	}
	public void findarticulationPoint(int src,int gn)
	{
		low=new int[N+1];
		vis=new int[N+1];
		ap=new int[N+1];
		parent=new int[N+1];
		dfsnum=new int[N+1];
		for(int i=1;i<=N;i++)
		{
			parent[i]=-1;
		}
		this.dfsForArticulationPoints(src);
		try {
			writer.write("\n\nFor Graph,"+gn+" the articulation points are\n");
			System.out.println("\nFor Graph,"+gn+" the articulation points are");
			for(int i=1;i<=7;i++)
			{
				if(ap[i]==1) {
					System.out.println((char)(i+64));
					writer.write((char)(i+64)+"\n");
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}
	/*6)Kosaraju's algorithm for SCC*/
	public void dfs1ForSCC(Stack<Integer>S,int src)
	{
		vis[src]=1;
		for(int i=0;i<this.adjlist[src].size();i++)
		{
			int v=adjlist[src].get(i).key;
			if(vis[v]==0)
				dfs1ForSCC(S,v);
		}
		S.add(src);
		
	}
	public void dfs2ForSCC(ArrayList<Integer>A,int src)
	{
		vis[src]=1;
		A.add(src);
		for(int i=0;i<this.adjlistGT[src].size();i++)
		{
			int v=adjlistGT[src].get(i).key;
			if(vis[v]==0)
				dfs2ForSCC(A,v);
		}
	}
	public void SCC(int gn)
	{
		Stack<Integer>S=new Stack<>();
		vis=new int[N+1];
		for(int i=1;i<=N;i++)
		{
			if(vis[i]==0)
				dfs1ForSCC(S,i);
		}
		vis=new int[N+1];
		System.out.println("\nThe Strongly Connected Components of "+gn+" is");
		try {
			writer.write("\nThe Strongly Connected Components of "+gn+" is \n");
			while(!S.isEmpty())
			{
				int x=S.pop();
				if(vis[x]==0)
				{
					ArrayList<Integer>A=new ArrayList<>();
					dfs2ForSCC(A,x);
					writer.write("( ");
					System.out.print("( ");
					for(int i=0;i<A.size();i++)
					{
						System.out.print((char)(64+A.get(i))+" ");
						writer.write((char)(64+A.get(i))+" ");
					}
					System.out.print(") ");
					writer.write(") ");
				}
			}
			writer.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
