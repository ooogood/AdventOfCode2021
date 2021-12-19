import java.util.*;
import java.io.*;

public class first {
	public static void main( String args[] ) {
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
		int ret = 0;
		ArrayList<Integer> lastLine = null;
		ArrayList<Integer> thisLine = mp.get( 0 );
		ArrayList<Integer> nextLine = mp.get( 1 );
		for( int i = 0; i < mp.size(); ++i ) {
			for( int j = 0; j < thisLine.size(); ++j ) {
				int thisBlock = thisLine.get( j );
				Boolean lowest = true;
				if( lastLine != null )
					lowest &= ( lastLine.get( j ) > thisBlock );
				if( j > 0 )
					lowest &= ( thisLine.get( j - 1 ) > thisBlock );
				if( j < thisLine.size() - 1 )
					lowest &= ( thisLine.get( j + 1 ) > thisBlock );
				if( nextLine != null ) 
					lowest &= ( nextLine.get( j ) > thisBlock );
				if( lowest )
					ret += ( 1 + thisBlock );
			}
			lastLine = thisLine;
			thisLine = nextLine;
			if( i < mp.size() - 2 )
				nextLine = mp.get( i + 2 );
			else 
				nextLine = null;
		}
		System.out.println( ret );
	}
}