import java.util.*;
import java.io.*;

public class first {
	private static final int len = 10;
	private int[][] mp = new int[len][len];
	public static void main( String args[] ) {
		first a = new first();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("input.txt");
			Scanner sr = new Scanner(fr);
			int ret = 0;
			int i = 0;
			while( sr.hasNextLine() ) {
				String str = sr.nextLine();
				for( int j = 0; j < str.length(); ++j ) {
					mp[ i ][ j ] = Integer.valueOf( str.substring(j, j+1) );
				}
				i++;
			}
			for( int n = 0; n < 100; ++n ) {
				ret += activate();
			}
			System.out.println( ret );
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
	public int activate() {
		int ret = 0;
		for( int i = 0; i < len; ++i ) {
			for( int j = 0; j < len; ++j ) {
				++mp[ i ][ j ];
			}
		}
		for( int i = 0; i < len; ++i ) {
			for( int j = 0; j < len; ++j ) {
				if( mp[ i ][ j ] > 9 ) {
					ret += ignite( i, j );
				}
			}
		}
		return ret;
	}
	public int ignite( int i, int j ) {
		if( mp[ i ][ j ] == 0 ) return 0;
		int ret = 0;
		if( ++mp[ i ][ j ] > 9 ) {
			ret++;
			mp[ i ][ j ] = 0;
			if( i + 1 < len )
				ret += ignite( i + 1, j );
			if( i - 1 >= 0 )
				ret += ignite( i - 1, j );
			if( j + 1 < len )
				ret += ignite( i, j + 1 );
			if( j - 1 >= 0 )
				ret += ignite( i, j - 1 );
			if( i + 1 < len && j + 1 < len )
				ret += ignite( i + 1, j + 1 );
			if( i + 1 < len && j - 1 >= 0 )
				ret += ignite( i + 1, j - 1 );
			if( i - 1 >= 0 && j + 1 < len )
				ret += ignite( i - 1, j + 1 );
			if( i - 1 >= 0 && j - 1 >= 0 )
				ret += ignite( i - 1, j - 1 );
		}
		return ret;
	}
}