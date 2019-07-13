import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
public class Main {
	public static void main(String[] args)
	{
		Scanner scan=new Scanner(System.in);
		File output,input;
		if(args.length==0)//if no argument is passed
		{
			System.out.println("No arguments are passed,hence give the name of the output file in console window or terminal");
			System.out.println("Enter file name: ");
			output=new File(scan.next());
			if(!output.exists())
			{
				System.out.println("File Doesnot Excists,Creating file with that name...");
				try {
					output.createNewFile();
				} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("File Creation Failed!!");
				e.printStackTrace();
				System.exit(0);
				}
			}
	
		}
		else
		{
			System.out.println("Output file name is "+args[1]);
			output=new File(args[1]);
			if(!output.exists())
			{
				System.out.println("File Doesnot Exists,Creating file with that name...");
				try {
					output.createNewFile();
				} catch (IOException e) {
					System.out.println("File Creation Failed!!");
					e.printStackTrace();
					System.exit(0);
				}
			}
		}
		input=new File("graph.txt");
		try {
			scan=new Scanner(input);
		} catch (FileNotFoundException e) {
			System.out.println("File Not Found");
			System.exit(0);
			e.printStackTrace();
		}
		//Topological Sort
		try {
			Graph.writer=new FileWriter(output);
		} catch (IOException e) {
			System.out.println("Error with file writer");
			e.printStackTrace();
		}
		Graph g1=new Graph(7);
		g1.takeInputFromFile(scan);
		System.out.println("Adjacency list of Graph 1");
		g1.printadjlist();
		g1.topologicalSort();
		
		Graph g2=new Graph(7);
		g2.takeInputFromFile(scan);
		System.out.println("\nAdjacency list of Graph 2");
		g2.printadjlist();
		g2.dijistraSP();
		
		Graph g3=new Graph(7);
		g3.takeInputFromFile(scan);
		System.out.println("\nAdjacency list of Graph 3");
		g3.printadjlist();
		g3.kriskalMSP();
		
		Graph g4=new Graph(7);
		g4.takeInputFromFile(scan);
		int src=scan.nextInt();
		System.out.println("\nSource "+src);
		System.out.println("Adjacency list of Graph 4");
		g4.printadjlist();
		g4.findarticulationPoint(src,4);
		
		Graph g5=new Graph(7);
		g5.takeInputFromFile(scan);
		src=scan.nextInt();
		System.out.println("Source "+src);
		System.out.println("Adjacency list of Graph 5");
		g5.printadjlist();
		g5.findarticulationPoint(src, 5);
		
		Graph g6=new Graph(7);
		g6.takeInputFromFile(scan);
		System.out.println("Adjacency list of Graph 6");
		g6.printadjlist();
		g6.SCC(6);
		
		scan.close();
		
	}
}
