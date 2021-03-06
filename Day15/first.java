import java.util.*;
import java.io.*;

public class first {
	private class Node implements Comparable<Node>{
		public final int row;
		public final int col;
		public final int cost;
		public int dist = Integer.MAX_VALUE;
		public Node prev = null;
		public Node( int r, int c, int cost ) {
			row = r;
			col = c;
			this.cost = cost;
		}
		public int compareTo( Node o ) {
			if( this.dist == o.dist ) return 0;
			if( this.dist < o.dist ) return -1;
			else return 1;
		}
		
	}
	private final int len = 100;
	private Node mp[][];
	private Set<Node> unsettled = new HashSet<Node>();
	public static void main( String args[] ) {
		first a = new first();
		a.foo();
	}
	public first() {
		mp = new Node[ len ][ len ];
	}
	public void foo() {
		// read in map
		try {
			FileReader fr = new FileReader( "input.txt" );
			Scanner sr = new Scanner(fr);
			int rowNum = 0;
			while( sr.hasNextLine() ) {
				String str = sr.nextLine();
				for( int i = 0; i < len; ++i ) {
					Node tmp = new Node( rowNum, i, str.charAt( i ) - '0' );
					mp[ rowNum ][ i ] = tmp;
					unsettled.add( tmp );
				}
				rowNum++;
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		// dijkstra's
		mp[ 0 ][ 0 ].dist = 0;
		while( !unsettled.isEmpty() ) {
			Node cur = getSmallestUnsettled();
			unsettled.remove( cur );
			updateNeighbor(cur, cur.row - 1, cur.col);
			updateNeighbor(cur, cur.row + 1, cur.col);
			updateNeighbor(cur, cur.row, cur.col - 1);
			updateNeighbor(cur, cur.row, cur.col + 1);
		}
		// for( int i = 0; i < len; ++i ) {
		// 	for( int j = 0; j < len; ++j ) {
		// 		System.out.print( String.format( "%3d", mp[ i ][ j ].dist ) );
		// 	}
		// 	System.out.println();
		// }
		
		System.out.println( mp[ len - 1 ][ len - 1 ].dist );
	}
	private Node getSmallestUnsettled() {
		Node ret = null;
		int smallest = Integer.MAX_VALUE;
		for( Node n : unsettled ) {
			if( smallest > n.dist ) {
				smallest = n.dist;
				ret = n;
			}
		}
		return ret;
	}
	private void updateNeighbor( Node cur, int row, int col ) {
		if( row < 0 || col < 0 || row >= len || col >= len ) return;
		Node neighbor = mp[ row ][ col ];
		if( neighbor.dist > cur.dist + neighbor.cost ) {
			neighbor.dist = cur.dist + neighbor.cost;
			neighbor.prev = cur;
		}
	}
}