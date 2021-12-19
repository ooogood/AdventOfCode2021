
import java.util.*;
import java.io.*;

public class second {
	public static void main( String args[] ) {
		second a = new second();
		a.foo();
	}
	public void foo() {
		Stack<Character> st = new Stack<Character>();
		PriorityQueue<Long> pq = new PriorityQueue<Long>();
		try {
			FileReader fr = new FileReader( "Day10\\input.txt" );
			Scanner sr = new Scanner( fr );
			Long ret = Long.valueOf(0);
			while( sr.hasNextLine() ) {
				Boolean cont = true;
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
							System.out.println( str.charAt(i) );
							cont = false;
							break;
						}
					}
				}
				if( !cont ) continue;
				Long score = Long.valueOf(0);
				while( !st.empty() ) {
					score *= 5;
					if( st.peek() == ')' ) {
						score += 1;
					}
					else if( st.peek() == ']' ) {
						score += 2;
					}
					else if( st.peek() == '}' ) {
						score += 3;
					}
					else if( st.peek() == '>' ) {
						score += 4;
					}
					st.pop();
				}
				pq.add( score );
			}
			int popcnt = pq.size() / 2;
			// if( popcnt == 0 ) 
			for( int i = 0; i < popcnt; ++i ) {
				pq.remove();
			}
			ret = pq.poll();
			System.out.println( ret );
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
}