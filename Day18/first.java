import java.util.*;
import java.io.*;

public class first {
	public class Node{
		public int number;
		public int level;
		public Node( int n, int l ) {
			this.number = n;
			this.level = l;
		}
	}
	private ArrayList<Node> result = new ArrayList<Node>();
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
				ArrayList<Node> thisLine = buildNodeArr(str);
				addLineToResult( thisLine );
				reduceResult();
			}
printResult();
System.out.println( calcMagnitude( result ) );
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
	private void addLineToResult( ArrayList<Node> line ) {
		if( result.size() > 0 && line.size() > 0 ) {
			for( Node n : result ) {
				++n.level;
			}
			for( Node n : line ) {
				++n.level;
			}
		}
		result.addAll( line );
	}
	private void reduceResult() {
		boolean clear;
		do {
			clear = true;
// printResult();
			for( int i = 0; i < result.size(); ++i ) {
				// check explode
				if( i < result.size() - 1 && result.get( i ).level > 4 && result.get( i + 1 ).level > 4 
					&& result.get( i ).level == result.get( i + 1 ).level ) {
					// if this pair is not at the end of the list
					if( i + 1 < result.size() - 1 ) {
						result.get( i + 2 ).number += result.get( i + 1 ).number;
					}
					// if this pair is not at the begin of the list
					if( i > 0 ) {
						result.get( i - 1 ).number += result.get( i ).number;
					}
					result.remove( i + 1 );
					result.get( i ).number = 0;
					result.get( i ).level--;
					clear = false;
					break;
				}
			}
			if( clear == false ) continue;
			for( int i = 0; i < result.size(); ++i ) {
				// check split
				if( result.get( i ).number > 9 ) {
					int num = result.get( i ).number;
					result.get( i ).number = num / 2;
					result.get( i ).level++;
					result.add( i + 1, new Node( num / 2 + ( num & 1 ), result.get( i ).level ) );
					clear = false;
					break;
				}
			}
		} while( clear == false );
	}
	private static int calcMagnitude( ArrayList<Node> result ) {
		ArrayList<Node> arr = new ArrayList<Node>( result );
		while( arr.size() > 1 ) {
			for( int i = 0; i < arr.size() - 1; ++i ) {
				if( arr.get( i ).level == arr.get( i + 1 ).level ) {
					arr.get( i ).number = 3 * arr.get( i ).number + 2 * arr.get( i + 1 ).number;
					arr.get( i ).level--;
					arr.remove( i + 1 );
					break;
				}
			}
		}
		return arr.get( 0 ).number;
	}
	// input example: [[[0,[5,8]],[[1,7],[9,6]]],[[4,[1,2]],[[1,4],2]]]
	private ArrayList<Node> buildNodeArr( String str ) {
		ArrayList<Node> ret = new ArrayList<Node>();
		int level = 0;
		for( int i = 0; i < str.length(); ++i ) {
			char c = str.charAt( i );
			if( c == '[' ) 
				level++;
			else if( c == ']' )
				level--;
			else if( c == ',' )
				continue;
			else {
				Node n = new Node( c - '0', level );
				ret.add( n );
			}
		}
		return ret;
	}
	private void printResult() {
		for( Node n : result ) {
			System.out.print( String.format( "%3d", n.number ) );
		}
		System.out.println();
		for( Node n : result ) {
			System.out.print( String.format( "%3d", n.level ) );
		}
		System.out.println();
	}
}