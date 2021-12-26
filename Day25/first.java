import java.util.*;
import java.io.*;

public class first {
	// private final int row = 9, col = 10;
	private final int row = 137, col = 139;
	private SeaCum[][] mp = new SeaCum[ row ][ col ];
	private ArrayList<SeaCum> set = new ArrayList<SeaCum>();
	public class SeaCum {
		// 0: east-facing, 1: south-facing
		public boolean type;
		public int x, y;
		public int nextX, nextY;
		public boolean canMoveNext;
		public SeaCum( boolean t, int x, int y ) {
			this.type = t;
			this.x = x;
			this.y = y;
			this.nextX = x;
			this.nextY = y;
			predict();
		}
		public void predict() {
			if( type ) {
				nextY = ( nextY + 1 ) % row;
			}
			else {
				nextX = ( nextX + 1 ) % col;
			}
		}
		public boolean checkCanMove() {
			canMoveNext = ( mp[ nextY ][ nextX ] == null );
			return canMoveNext;
		}
		public void advance() {
			mp[ nextY ][ nextX ] = mp[ y ][ x ];
			mp[ y ][ x ] = null;
			x = nextX;
			y = nextY;
			predict();
		}
	}
	public static void main( String args[] ) {
		first a = new first();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("input.txt");
			Scanner sr = new Scanner(fr);
			int rowCnt = 0;
			while( sr.hasNextLine() ) {
				String str = sr.nextLine();
				for( int i = 0; i < col; ++i ) {
					if( str.charAt( i ) == '>' ) {
						mp[ rowCnt ][ i ] = new SeaCum( false, i, rowCnt );
						set.add( mp[ rowCnt ][ i ] );
					}
					else if( str.charAt( i ) == 'v' ) {
						mp[ rowCnt ][ i ] = new SeaCum( true, i, rowCnt );
						set.add( mp[ rowCnt ][ i ] );
					}
				}
				rowCnt++;
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}

		int turnCnt = 0;
		int moveCnt = set.size();
		while( moveCnt > 0 ) {
// draw();
			moveCnt = 0;
			// for east-facing
			for( SeaCum s : set ) {
				if( !s.type ) {
					if( s.checkCanMove() ) {
						moveCnt++;
					}
				}
			}
			for( SeaCum s : set ) {
				if( !s.type && s.canMoveNext ) {
					s.advance();
				}
			}
// draw();
			// for south-facing
			for( SeaCum s : set ) {
				if( s.type ) {
					if( s.checkCanMove() ) {
						moveCnt++;
					}
				}
			}
			for( SeaCum s : set ) {
				if( s.type && s.canMoveNext ) {
					s.advance();
				}
			}
			turnCnt++;
		}
		System.out.println( turnCnt );
	}
	public void draw() {
		for( int i = 0; i < row; ++i ) {
			for( int j = 0; j < col; ++j ) {
				if( mp[ i ][ j ] == null )
					System.out.print( "." );
				else if ( mp[ i ][ j ].type ) {
					System.out.print( "v" );
				}
				else {
					System.out.print( ">" );
				}
			}
			System.out.println(  );
		}
		System.out.println(  );
	}
}