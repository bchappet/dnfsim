package draft;

import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

public class RoutingTest {
	
	public static class Point implements Comparable<Point>{
		
		public int x;
		public int y;
		
		public Point(int x,int y){
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Point o) {
			if (y<o.y) return(-1);
			if (y>o.y) return(1);
			if (x<o.x) return(-1);
			if (x>o.x) return(1);
			return 0;
			}

		
		@Override
		public boolean equals(Object obj) {
			Point other = (Point) obj;
			return ((x==other.x)&&(y==other.y));
		}

		
		@Override
		public String toString(){
			return "("+x+","+y+")";
		}
		
	}


	public static class RoutingTable{
		/**Routing table first index is the transmitter number **/

		/**Routing table second index is the targeted neuron data (variable size)**/

		/**Routing table last table index **/
		public static final int  X = 0; //X offset for destination neuron
		public static final int  Y = 1; //Y offset
		public static final int  TRANS = 2; //transmitter of destination neuron
		public static final int COUNT = 3; //does it count to add the point to the list

		protected int[][][] table;


		public RoutingTable(int[][][] table){
			this.table = table;
		}

		public static RoutingTable getCurrent(){
			return new RoutingTable(new int[][][]{
					{{0,-1,2,1},{0,1,1,1},{1,0,4,1},{-1,0,3,1}},
					{{0,1,1,1},{1,0,4,1},{-1,0,3,1}},
					{{0,-1,2,1},{1,0,4,1},{-1,0,3,1}},
					{{-1,0,3,1}},
					{{1,0,4,1}}
			});
		}
		

		public void navigate(Point current,int currentTrans,Collection<Point> visitedPoint,int count,int limit) throws Exception{
			if(Math.abs(current.x) <= limit && Math.abs(current.y) <= limit){
				if(count == 1)
					if(visitedPoint.add(current)){

						for(int[] dest : table[currentTrans]){
							this.navigate(new Point(current.x +dest[X],current.y + dest[Y] ),dest[TRANS],visitedPoint,dest[COUNT], limit);
						}
					}else{
						throw new Exception("Point already counted " + current);
					}
			}else{
				//nothing end of recursion
			}
		}
	}

	public static void main(String[] args){

		RoutingTable table = RoutingTable.getCurrent();

		Set<Point> points = new TreeSet<Point>();
		try {
			table.navigate(new Point(0,0),0, points, 1,3);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(points);
		System.out.println(points.size());


	}




}
