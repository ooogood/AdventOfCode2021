package Day8;
import java.util.*;
import java.io.*;

public class second {
	public ArrayList<String[]> pq = new ArrayList<String[]>();
	public static void main( String args[] ) {
		second a = new second();
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
				}
				a.pq.add( str );
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		int ret = 0;
		for( String[] s : a.pq ) {
			String one = "", four = "", seven = "";
			for( String str : s ) {
				switch( str.length() ) {
				case 2:
					one = str;
					break;
				case 3:
					seven = str;
					break;
				case 4:
					four = str;
					break;
				default:
					break;
				}
			}
			int tmp = Integer.valueOf( renderDigit(s[10], one, four, seven) + 
									renderDigit(s[11], one, four, seven) + 
									renderDigit(s[12], one, four, seven) + 
									renderDigit(s[13], one, four, seven) ); 
			ret += tmp;
		}
		System.out.println( ret );
	}
	// give me the patterns of one, four, and seven
	// I can tell you what digit is tar
	public static String renderDigit( String tar, String one, String four, String seven ) {
		switch( tar.length() ) {
		case 2:
			return "1";
		case 3:
			return "7";
		case 4:
			return "4";
		case 7:
			return "8";
		case 5: {
				// '3'
				int i = 0;
				for(; i < one.length(); ++i ) {
					if( tar.indexOf( one.charAt( i ) ) == -1 ) break;
				}
				if( i == one.length() ) return "3";
				// '5'
				int idx1 = four.indexOf( one.charAt( 0 ) );
				int idx2 = four.indexOf( one.charAt( 1 ) );
				i = 0;
				for(; i < four.length(); ++i ) {
					if( i == idx1 || i == idx2 ) continue;
					if( tar.indexOf( four.charAt( i ) ) == -1 ) break;
				}
				if( i == four.length() ) return "5";
				else return "2";
			}
		default: { // tar.length() == 6
				for( int i = 0; i < seven.length(); ++i ) {
					if( tar.indexOf( seven.charAt( i ) ) == -1 ) return "6";
				}
				for( int i = 0; i < four.length(); ++i ) {
					if( tar.indexOf( four.charAt( i ) ) == -1 ) return "0";
				}
				return "9";
			}
		}
	}
}
