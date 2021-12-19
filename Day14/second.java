import java.util.*;
import java.io.*;

public class second {
	int[][] mp = new int[26][26];
	long[][] binds = new long[26][26];
	public static void main( String args[] ) {
		second a = new second();
		a.foo( 40 );
	}
	public second() {
		for( int i = 0; i < 26; ++i ) {
			Arrays.fill( mp[ i ], 0, 26, -1 );
		}
	}
	public void foo( int times ) {
		try {
			FileReader fr = new FileReader("input.txt");
			Scanner sr = new Scanner(fr);
			String str = sr.nextLine();
			int last = str.charAt( 0 ) - 'A';
			for( int i = 1; i < str.length(); ++i ) {
				int cur = str.charAt( i ) - 'A';
				++binds[ last ][ cur ];
				last = cur;
			}
			sr.nextLine();
			while( sr.hasNextLine() ) {
				String[] val = sr.nextLine().split( " -> " );
				int front = ( val[ 0 ].charAt( 0 ) - 'A' );
				int back = ( val[ 0 ].charAt( 1 ) - 'A' );
				mp[ front ][ back ] = val[ 1 ].charAt( 0 ) - 'A';
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		for( int i = 0; i < times; ++i ) {
			long[][] tmp = new long[ 26 ][ 26 ];
			for( int j = 0; j < 26; ++j ) {
				for( int k = 0; k < 26; ++k ) {
					if( binds[ j ][ k ] > 0 ) {
						// separate the bind into 2 new binds
						if( mp[ j ][ k ] != -1 ) {
							tmp[ j ][ mp[ j ][ k ] ] += binds[ j ][ k ];
							tmp[ mp[ j ][ k ] ][ k ] += binds[ j ][ k ];
						}
						// save the original bind
						else {
							tmp[ j ][ k ] = binds[ j ][ k ];
						}
					}
				}
			}
			binds = tmp;
		}
		long[] count = new long[ 26 ];
		for( int i = 0; i < 26; ++i ) {
			for( int j = 0; j < 26; ++j ) {
				count[ i ] += binds[ i ][ j ];
				count[ j ] += binds[ i ][ j ];
			}
		}
		// the above logic will count every element twice 
		//  except the first and the last element
		for( int i = 0; i < 26; ++i ) {
			count[ i ] = ( count[ i ] >> 1 ) + ( count[ i ] & 1 );
		}
		long max = 0;
		long min = Long.MAX_VALUE;
		for( int i = 0; i < 26; ++i ) {
			if( count[ i ] != 0 ) {
				if( max < count[ i ] ) {
					max = count[ i ];
				}
				if( min > count[ i ] ) {
					min = count[ i ];
				}
			}
		}
		System.out.println( max );
		System.out.println( min );
		System.out.println( max - min );
	}
}