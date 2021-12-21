import java.util.*;
import java.io.*;

public class second {
	public class Node{
		public int number;
		public int level;
		public Node( int n, int l ) {
			this.number = n;
			this.level = l;
		}
		public Node( Node n ) {
			this.number = n.number;
			this.level = n.level;
		}
	}
	private ArrayList<ArrayList<Node>> result = new ArrayList<ArrayList<Node>>();
	private int maxMag = 0;
	public static void main( String args[] ) {
		second a = new second();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("input.txt");
			Scanner sr = new Scanner(fr);
			while( sr.hasNextLine() ) {
				String str = sr.nextLine();
				ArrayList<Node> thisLine = buildNodeArr(str);
				result.add( thisLine );
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		for( int i = 0; i < result.size(); ++i ) {
			for( int j = 0; j < result.size(); ++j ) {
				if( i == j ) continue;
				ArrayList<Node> arr = addLines( result.get( i ), result.get( j ) );
				reduceResult( arr );
				int resMag = calcMagnitude( arr );
				if( resMag > this.maxMag ) 
					this.maxMag = resMag;
			}
		}
		System.out.println( this.maxMag );
	}
	private ArrayList<Node> addLines( ArrayList<Node> line1, ArrayList<Node> line2 ) {
		ArrayList<Node> ret = new ArrayList<Node>();
		for( Node n : line1 ) {
			ret.add( new Node( n ) );
		}
		for( Node n : line2 ) {
			ret.add( new Node( n ) );
		}
		if( line1.size() > 0 && line2.size() > 0 ) {
			for( Node n : ret ) {
				++n.level;
			}
		}
		return ret;
	}
	private void reduceResult( ArrayList<Node> line ) {
		boolean clear;
		do {
			clear = true;
			for( int i = 0; i < line.size(); ++i ) {
				// check explode
				if( i < line.size() - 1 && line.get( i ).level > 4 && line.get( i + 1 ).level > 4 
					&& line.get( i ).level == line.get( i + 1 ).level ) {
					// if this pair is not at the end of the list
					if( i + 1 < line.size() - 1 ) {
						line.get( i + 2 ).number += line.get( i + 1 ).number;
					}
					// if this pair is not at the begin of the list
					if( i > 0 ) {
						line.get( i - 1 ).number += line.get( i ).number;
					}
					line.remove( i + 1 );
					line.get( i ).number = 0;
					line.get( i ).level--;
					clear = false;
					break;
				}
			}
			if( clear == false ) continue;
			for( int i = 0; i < line.size(); ++i ) {
				// check split
				if( line.get( i ).number > 9 ) {
					int num = line.get( i ).number;
					line.get( i ).number = num / 2;
					line.get( i ).level++;
					line.add( i + 1, new Node( num / 2 + ( num & 1 ), line.get( i ).level ) );
					clear = false;
					break;
				}
			}
		} while( clear == false );
	}
	private int calcMagnitude( ArrayList<Node> arr ) {
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
	private static void printResult( ArrayList<Node> result ) {
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