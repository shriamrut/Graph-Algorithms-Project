import java.lang.Comparable;
/*Pair is used for vertex v(key), weight(v) for inserting into the adj list*/
/*Making it to interface Comparable for sorting and for Heap DS*/
public class Pair  implements Comparable<Pair>  {
public int key;
public int value;
Pair(int key,int value)
{
	this.key=key;
	this.value=value;
}
public int getValue()
{
	return this.value;
}
public int getKey()
{
	return this.key;
}
public int compareTo(Pair p)
{
	if(this.value < p.value)
		return -1;
	else if(this.value==p.value)
		return 0;
	else
		return 1;
}
}
