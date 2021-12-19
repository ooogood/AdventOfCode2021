package Day8;
import java.util.*;
import java.io.*;

public class first {
	public ArrayList<String[]> pq = new ArrayList<String[]>();
	public static void main( String args[] ) {
		first a = new first();
		int ret = 0;
		try {
			FileReader fr = new FileReader( "Day8\\input.txt");
			Scanner sr = new Scanner( fr );
			while( sr.hasNextLine() ) {
				String str[] = new String[ 14 ];
				for( int i = 0; i < 10; ++i ) {
					str[ i ] = sr.next();
				}
				// '|'
				sr.next();
				for( int i = 10; i < 14; ++i ) {
					str[ i ] = sr.next();
					if( str[ i ].length() == 2 ||
						str[ i ].length() == 3 ||
						str[ i ].length() == 4 ||
						str[ i ].length() == 7 ) {
						ret++;
					}
				}
				a.pq.add( str );
			}
			System.out.println( ret );
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

}
