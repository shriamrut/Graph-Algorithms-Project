/*For creating Edge list,for kriskal's algorithm,making it comparable for sorting*/
public class Edge implements Comparable<Edge> {
public int u;
public int v;
public int w;
Edge(int u,int v,int w)
{
	this.u=u;
	this.v=v;
	this.w=w;
}
public int compareTo(Edge E) {
	if(this.w > E.w)
		return 1;
	else if(this.w==E.w)
		return 0;
	else
	return -1;
}
public String toString()
{
	return "("+u+","+v+","+w+")";
}
}
