import java.util.*;
import java.io.*;

public class firstAndSecond {
	// private final int initLen = 5;
	private final int initLen = 100;
	private boolean[] algo = new boolean[ 512 ];
	private boolean[][] pic = new boolean[ initLen + 2 ][];
	private boolean emptyFill = false;
	public static void main( String args[] ) {
		firstAndSecond a = new firstAndSecond();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("input.txt");
			Scanner sr = new Scanner(fr);
			// set algo pattern
			String str = sr.nextLine();
			for( int i = 0; i < 512; ++i ) {
				if( str.charAt( i ) == '#' ) 
					algo[ i ] = true;
			}
			sr.nextLine();
			// read in initial picture
			int rowCnt = 0;
			pic[ rowCnt++ ] =new boolean[ initLen + 2 ]; 
			while( sr.hasNextLine() ) {
				str = sr.nextLine();
				boolean[] row = new boolean[ str.length() + 2 ];
				for( int i = 0; i < str.length(); ++i ) {
					if( str.charAt( i ) == '#' )
						row[ i + 1 ] = true;
				}
				pic[ rowCnt++ ] = row;
			}
			pic[ rowCnt++ ] =new boolean[ initLen + 2 ]; 
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		// for part one
		pic = enhance( pic );
		pic = enhance( pic );
		int onCnt = 0;
		for( int i = 0; i < pic.length; ++i ) {
			for( int j = 0; j < pic[ i ].length; ++j ) {
				if( pic[ i ][ j ] ) 
					onCnt++;
			}
		}
		System.out.println( onCnt );
		// for part two
		for( int i = 0; i < 48; ++i ) {
			pic = enhance( pic );
		}
		onCnt = 0;
		for( int i = 0; i < pic.length; ++i ) {
			for( int j = 0; j < pic[ i ].length; ++j ) {
				if( pic[ i ][ j ] ) 
					onCnt++;
			}
		}
		System.out.println( onCnt );
	}
	private boolean[][] enhance( boolean[][] p ) {
		// every enhancement, the pixals outside the picture change as well
		boolean[][] space = new boolean[ 3 ][ 3 ];
		for( int i = 0; i < 3; ++i ) {
			Arrays.fill( space[ i ], emptyFill );
		}
		boolean emptyFillCache = emptyFill;
		emptyFill = calcPixal( space, 1, 1, emptyFill );
		// calculate inside picture
		boolean[][] ret = new boolean[ p.length + 2 ][];
		boolean[] emptyRow = new boolean[ p.length + 2 ];
		Arrays.fill( emptyRow, emptyFill );
		ret[ 0 ] = emptyRow;
		for( int i = 0; i < p.length; ++i ) {
			boolean[] row = new boolean[ p.length + 2 ];
			row[ 0 ] = emptyFill;
			for( int j = 0; j < p.length; ++j ) {
				row[ 1 + j ] = calcPixal( p, i, j, emptyFillCache );
			}
			row[ p.length + 1 ] = emptyFill;
			ret[ 1 + i ] = row;
		}
		emptyRow = new boolean[ p.length + 2 ];
		Arrays.fill( emptyRow, emptyFill );
		ret[ p.length + 1 ] = emptyRow;
		return ret;
	}
	private boolean calcPixal( boolean[][] p, int row, int col, boolean emptyFillCache ) {
		String str = "";
		for( int i = -1; i < 2; ++i ) {
			for( int j = -1; j < 2; ++j ) {
				if( row + i < 0 || row + i >= p.length || col + j < 0 || col + j >= p.length ) {
					if( emptyFillCache ) {
						str += '1';
					}
					else {
						str += '0';
					}
				}
				else {
					if( p[ row + i ][ col + j ] ) {
						str += '1';
					}
					else {
						str += '0';
					}
				}
			}
		}
		if( str.length() != 9 )
			throw new RuntimeException();
		return algo[ Integer.valueOf( str, 2 ) ];
	}
	private void drawPic( boolean[][] p ) {
		for( int i = 0; i < p.length; ++i ) {
			for( int j = 0; j < p.length; ++j ) {
				if( p[ i ][ j ] )
					System.out.print( "#" );
				else
					System.out.print( "." );
			}
			System.out.println();
		}
	}
}