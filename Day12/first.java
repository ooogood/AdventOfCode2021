import java.util.*;
import java.io.*;

public class first {
	private int pathCnt = 0;
	/* <node, nodes connected with this node> */
	HashMap<String, Vector<String>> nodes = new HashMap<String, Vector<String>>();
	/* only for small cases */
	HashMap<String, Boolean> used = new HashMap<String, Boolean>();
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
				String[] tok = str.split("-");
				Vector<String> tmp1 = nodes.get( tok[ 0 ] );
				if( tmp1 == null ) {
					tmp1 = new Vector<String>();
					nodes.put( tok[ 0 ], tmp1 );
					if( tok[ 0 ].charAt( 0 ) <= 'z' && tok[ 0 ].charAt( 0 ) >= 'a' )
						used.put( tok[ 0 ], false );
				}
				tmp1.add( tok[ 1 ] );
				Vector<String> tmp2 = nodes.get( tok[ 1 ] );
				if( tmp2 == null ) {
					tmp2 = new Vector<String>();
					nodes.put( tok[ 1 ], tmp2 );
					if( tok[ 1 ].charAt( 0 ) <= 'z' && tok[ 1 ].charAt( 0 ) >= 'a' )
						used.put( tok[ 1 ], false );
				}
				tmp2.add( tok[ 0 ] );
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		used.replace( "start", true );
		backTrack( "start" );
		System.out.println( pathCnt );
	}
	private void backTrack( String cur ) {
		if( cur.equals( "end" ) ) {
			++pathCnt;
			return;
		}
		Vector<String> tmp = nodes.get( cur );
		// assert: tmp != null
		for( String s : tmp ) {
			Boolean b = used.get( s );
			if( b == null ) {
				backTrack( s );
			}
			else if( !b ) {
				used.replace( s, true );
				backTrack( s );
				used.replace( s, false );
			}
		}
	}
}