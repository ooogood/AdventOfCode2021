import java.util.*;
import java.io.*;

public class firstAndSecond {
	// line 18n + 5: div z ?
	private int[] const1 = { 1, 1, 1, 26, 1, 26, 1, 26, 1, 1, 26, 26, 26, 26 };
	// line 18n + 6: add x ?
	private int[] const2 = { 10, 13, 15, -12, 14, -2, 13, -12, 15, 11, -3, -13, -12, -13 };
	// line 18n + 16 add y ?
	private int[] const3 = { 10, 5, 12, 12, 6, 4, 15, 3, 7, 11, 2, 12, 4, 11 };
	private int blkCnt = 14;
	
	public static void main( String args[] ) {
		firstAndSecond a = new firstAndSecond();
		a.foo();
	}
	public int block( int c1, int c2, int c3, int z, int w ) {
		// x == 1 after line 8
		if( !( ( ( z % 26 ) + c2 ) == w ) ) {
			z /= c1;
			z *= 26;
			z += ( w + c3 );
		}
		// x == 0 after line 8
		else {
			z/= c1;
		}
		return z;
	}
	public boolean run( int z, int lvl ) {
		if( lvl == blkCnt ) return z == 0;
		// if abs(z) > 26 ^ 4, it's unlikely to get an answer from this combination
		// based on the observation of constants[ 10 ~ 13 ]
		if( ( z > 456976 || z < -456976 ) ) return false;
		// for( int i = 9; i >= 1; --i ) { // for part 1
		for( int i = 1; i < 10; ++i ) { // for part 2
			if( run( block( const1[ lvl ], const2[ lvl ], const3[ lvl ], z, i ), lvl + 1 ) ) {
				// the output digits are backward
				System.out.print( i );
				return true;
			}
		}
		return false;
	}
	public void foo() {
		run( 0, 0 );
	}
}