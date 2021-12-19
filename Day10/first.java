
import java.util.*;
import java.io.*;

public class first {
	public static void main( String args[] ) {
		first a = new first();
		a.foo();
	}
	public void foo() {
		Stack<Character> st = new Stack<Character>();
		try {
			FileReader fr = new FileReader( "Day10\\input.txt" );
			Scanner sr = new Scanner( fr );
			int ret = 0;
			Boolean cont = true;
			while( sr.hasNextLine() && cont ) {
				st.clear();
				String str = sr.nextLine();
				for( int i = 0; i < str.length(); ++i ) {
					if( str.charAt( i ) == '[' ) {
						st.add( ']' );
					}
					else if( str.charAt( i ) == '(' ) {
						st.add( ')' );
					}
					else if( str.charAt( i ) == '{' ) {
						st.add( '}' );
					}
					else if( str.charAt( i ) == '<' ) {
						st.add( '>' );
					}
					else {
						if( str.charAt( i ) == st.peek() ) {
							st.pop();
						}
						else {
							if( str.charAt( i ) == ')' ) {
								ret += 3;
							}
							else if( str.charAt( i ) == ']' ) {
								ret += 57;
							}
							else if( str.charAt( i ) == '}' ) {
								ret += 1197;
							}
							else if( str.charAt( i ) == '>' ) {
								ret += 25137;
							}
							break;
						}
					}
				}
			}
			System.out.println( ret );
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
}
