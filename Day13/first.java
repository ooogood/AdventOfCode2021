import java.util.*;
import java.io.*;

public class first {
	private class pair<K, T> {
		public K first;
		public T second;
		public pair( K f, T s ) {
			first = f;
			second = s;
		}
		public boolean equals( Object o ) {
			return ( ((pair)o).first.equals( first ) && ((pair)o).second.equals( second ) );
		}
		public int hashCode() {
			return ( (int)first << 10 ) + (int)second;
		}
	}
	private HashSet<pair<Integer, Integer>>mp = new HashSet<pair<Integer, Integer>>();
	private ArrayList<pair<Character, Integer>> op = new ArrayList<pair<Character, Integer>>();
	public static void main( String args[] ) {
		first a = new first();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("input.txt");
			Scanner sr = new Scanner(fr);
			while( sr.hasNextLine() ) {
				String str = sr.nextLine();
				if( str.equals("") ) break;
				String[] val = str.split( "," );
				mp.add( new pair<Integer, Integer>( Integer.valueOf( val[ 0 ] ),
													 Integer.valueOf( val[ 1 ] ) ) );
			}
			while( sr.hasNextLine() ) {
				String str = sr.nextLine();
				String val[] = str.split(" ");
				int splitPoint = Integer.valueOf( val[ 2 ].substring( 2, val[ 2 ].length() ) );
				op.add( new pair<Character, Integer>( val[ 2 ].charAt( 0 ), splitPoint ) );
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		for( pair<Character, Integer> p : op ) {
			HashSet<pair<Integer, Integer>> tmp = new HashSet<pair<Integer, Integer>>();
			for( pair<Integer, Integer> blk : mp ) {
				if( p.first.equals( 'x' ) ) {
					if( blk.first > p.second )
						tmp.add( new pair( p.second - ( blk.first - p.second ), blk.second ) );
					else
						tmp.add( blk );
				}
				else if( p.first.equals( 'y' ) ) {
					if( blk.second > p.second )
						tmp.add( new pair( blk.first, p.second - ( blk.second - p.second ) ) );
					else
						tmp.add( blk );
				}
			}
			mp = tmp;
// for( pair<Integer, Integer> blk : mp ) {
// 	System.out.print( blk.first );
// 	System.out.print( ' ' );
// 	System.out.println( blk.second );
// }
			System.out.println( mp.size() );
		}
	}
}