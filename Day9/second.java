import java.util.*;
import java.io.*;

public class second {
	private Boolean checked[][];
	private int map[][];
	public static void main( String args[] ) {
		second a = new second();
		a.foo();
	}
	public void foo() {
		ArrayList<ArrayList<Integer>> mp = new ArrayList<ArrayList<Integer>>();
		try {
			FileReader fr = new FileReader( "Day9\\input.txt" );
			Scanner sr = new Scanner( fr );
			while( sr.hasNextLine() ) {
				ArrayList<Integer> thisLine = new ArrayList<Integer>();
				String str = sr.nextLine();
				for( int i = 0; i < str.length(); ++i ) {
					int tmp = Integer.valueOf( str.substring( i, i+1 ) );
					thisLine.add( tmp );
				}
				mp.add( thisLine );
			}
			sr.close();
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		int ret = 1;
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>(Collections.reverseOrder());
		// initialize
 		checked = new Boolean[mp.size()][mp.get(0).size()];
		map = new int[mp.size()][mp.get(0).size()];
		for( int i = 0; i < mp.size(); ++i ) {
			for( int j = 0; j < mp.get( 0 ).size(); ++j ) {
				map[ i ][ j ] = mp.get( i ).get( j );
				checked[ i ][ j ] = false;
			}
		}
		// search
		for( int i = 0; i < mp.size(); ++i ) {
			for( int j = 0; j < mp.get( 0 ).size(); ++j ) {
				if( !checked[ i ][ j ] && map[ i ][ j ] != 9 ) {
					int tmp = getBasinSize( i, j );
					pq.add( tmp );
				}
			}
		}
		// multiply three most significant basin size
		for( int i = 1; i <= 3; ++i ) {
			ret *= pq.poll();
		}
		System.out.println( ret );
	}
	private int getBasinSize( int i, int j ) {
		if( i < 0 || i >= checked.length || j < 0 || j >= checked[0].length ) return 0;
		if( checked[ i ][ j ] ) return 0;
		checked[ i ][ j ] = true;
		if( map[ i ][ j ] == 9 ) return 0;
		// recursively check left, right, upper, lower adjoining blocks
		return 1 + getBasinSize( i, j - 1 ) + getBasinSize( i, j + 1 ) + getBasinSize( i + 1, j ) + getBasinSize( i - 1, j );
	}
}