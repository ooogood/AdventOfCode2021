import java.util.*;
import java.io.*;

public class first {
	Character[][] mp = new Character[26][26];
	ArrayList<Character> arr = new ArrayList<Character>();
	public static void main( String args[] ) {
		first a = new first();
		a.foo( 10 );
	}
	public void foo( int times ) {
		try {
			FileReader fr = new FileReader("expinput.txt");
			Scanner sr = new Scanner(fr);
			String str = sr.nextLine();
			for( int i = 0; i < str.length(); ++i ) {
				arr.add( str.charAt( i ) );
			}
			sr.nextLine();
			while( sr.hasNextLine() ) {
				String[] val = sr.nextLine().split( " -> " );
				int front = ( val[ 0 ].charAt( 0 ) - 'A' );
				int back = ( val[ 0 ].charAt( 1 ) - 'A' );
				mp[ front ][ back ] = Character.valueOf( val[ 1 ].charAt( 0 ) );
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		for( int i = 0; i < times; ++i ) {
			ArrayList<Character> temp = new ArrayList<Character>();
			ArrayList<Character> toInsert = new ArrayList<Character>();
			for( int j = 1; j < arr.size(); ++j ) {
				int front = ( arr.get( j - 1 ) - 'A' );
				int back = ( arr.get( j ) - 'A' );
				toInsert.add( mp[ front ][ back ] );
			}
			temp.add( arr.get( 0 ) );
			for( int j = 1; j < arr.size(); ++j ) {
				if( toInsert.get( j - 1 ) != null ) {
					temp.add( toInsert.get( j - 1 ) );
				}
				temp.add( arr.get( j ) );
			}
			arr = temp;
		}
		int[] count = new int[ 26 ];
		for( int i = 0; i < arr.size(); ++i ) {
			++count[ arr.get( i ) - 'A' ];
		}
		System.out.println( arr.size() );
	}
}