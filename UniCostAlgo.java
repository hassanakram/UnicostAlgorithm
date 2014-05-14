
import java.io.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Collections;

public class UniCostAlgo
{



	
	   static private int[][] graph = { {0,0,0,0,0,0,0,0,0,0,0,0,0,0},  
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0},  
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0},  
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0},  
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0},	
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0},
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0},
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0},
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0},
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0},
										{0,0,0,0,0,0,0,0,0,0,0,0,0,0}    
									  };
	
	static private	String[] cities={"TownMakamSakoon","Matli", "Digri","KotGhulamMohammad", "TanduGhulamALi", "MirwahGorchani", "ShaikhBhirkio", "TanduAllahYar", "TanduMohammadKhan", "Hyderabad", "YarMohammadKhaskheli", "Sherosherani", "MirpurKhas", "TanduJam"};
	
	static private int unicost_spaceComplexity=0;


		public static void main(String[] args)
		{

			boolean error=false;

			String start=args[0];
			String goal=args[1];

			int startIndex=indexOf(start);
			int goalIndex=indexOf(goal);

			

			if(startIndex>=0 && goalIndex >=0)
			{
				if(!(startIndex <= goalIndex))
				{
					int temp=goalIndex;
					goalIndex=startIndex;
					startIndex=temp;
				}
			}
			else
			{

				error=true;
			}

			try
			{
				FileReader fr=new FileReader("input.txt");

				BufferedReader br=new BufferedReader(fr);
				String line=null;
				while((line = br.readLine()) != null)
				{
					

					String[] parts=line.split(" ");
					int i=indexOf(parts[0]);
					
					int j=indexOf(parts[1]);
					
					int distance=Integer.parseInt(parts[2]);
					
					if(i>=0&&j>=0&&distance>=0)
					{
						graph[i][j]=distance;
						graph[j][i]=distance;
					}
					else
					{
						error=true;
					}
				}


				fr.close();
				
			}
			catch(IOException ioe)
			{
				System.out.println(ioe);
				error=true;
			}

			

			if(!error)
			{
				ArrayList<Integer> Start = new ArrayList();
				
				Start.add(startIndex);    // The start node
				
				ArrayList<ArrayList> Queue = new ArrayList();
				
				Queue.add(Start); // inserted in the initial queue of paths as Start
				
				ArrayList Path = uni_cost(Queue,goalIndex); // Uniform cost search
				System.out.println("");
				System.out.println("**************** Graph of cities ************************");

				printGraph();

				System.out.println("**************** Graph of cities ************************");				
				System.out.println("");
				Collections.reverse(Path);
				
				ArrayList<String> cityPath=new ArrayList();

				for(int i=0;i<Path.size();i++)
					cityPath.add(cityOnIndex(((int)Path.get(i))));
				
				System.out.println("Route of Path  :"+cityPath);

				System.out.println("Total distance :"+path_cost(Path));
			}
			else
			{
				System.out.println("Error Encountered");
			}
		}



		public static int indexOf(String s)
		{
			
			for(int i=0;i<cities.length;i++)
				if(cities[i].equals(s))
					return i;


			return -1;
		}

		public static String cityOnIndex(int index)
		{
			
			if(index>=0 && index <=9)
				return cities[index];
			else
				return "";
		}

		public static void printGraph()
		{
			for(int i=0;i<10;i++)
			{
				for(int j=0;j<10;j++)
				{
					System.out.print(graph[i][j]);
					System.out.print("  ");	
				}

				System.out.println("\n");
			}
		}		


		public static ArrayList<ArrayList> extend(ArrayList<Integer> Path)
		{
			ArrayList<ArrayList> NewPaths = new ArrayList();
			for (int i=0;i<graph.length;i++)
				if (graph[Path.get(0)][i]>0 && !Path.contains(i))
				{
					ArrayList Path1 = (ArrayList)Path.clone();
					Path1.add(0,i);
					NewPaths.add(Path1);
				}
			return NewPaths;
		}


		

		public static ArrayList appendChildren(ArrayList x, ArrayList y) 
		{
			ArrayList z = (ArrayList)x.clone();
			for (int i=0;i<y.size();i++) z.add(y.get(i));
			return z;
		}
		
		public static int path_cost(ArrayList<Integer> Path)
		{
			int cost = 0;
			for (int i=0;i<Path.size()-1;i++)
				cost = cost + graph[Path.get(i+1)][Path.get(i)];
			return cost;
		}
		
		
		public static ArrayList<ArrayList> uni_cost(ArrayList<ArrayList> Queue, int Goal) 
		{
			if(Queue.size()==0) return Queue;          	
			if ((Integer)Queue.get(0).get(0) == Goal)  	
				return Queue.get(0);                    
			else 										
			{ 
				
				ArrayList<ArrayList> NewPaths = extend(Queue.get(0));
				
				Queue.remove(0);
				
				ArrayList<ArrayList> Queue1 = appendChildren(Queue,NewPaths);
				
				sort(Queue1);   						

				return uni_cost(Queue1,Goal);
			}
		}
	

		// priority queue can also be used but this method is simpler one :)

		public static void sort(ArrayList<ArrayList> Queue)
		{
			int out, in;
			for(out=Queue.size()-1; out>=1; out--)
			 for(in=0; in<out; in++)
				if( path_cost(Queue.get(in)) > path_cost(Queue.get(in+1)) )
				   swap(Queue, in, in+1);
		}

	

		private static void swap(ArrayList<ArrayList> a, int one, int two)
		{
			ArrayList<Integer> temp = a.get(one);
			a.set(one,a.get(two));
			a.set(two,temp);
		}






}