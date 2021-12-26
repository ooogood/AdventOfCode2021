import java.util.*;
import java.io.*;

public class first {
	public class ALU{
		// input
		public long input = 0;
		// x, y, z, w
		public long[] var = new long[ 4 ];
		public ALU() {}
		public ALU( ALU o ) {
			input = o.input;
			var = Arrays.copyOf( o.var, 4 );
		}
		public void inp( int w, int val ) {
			input = input * 10 + val;
			var[ w ] = val;
		}
		public boolean op( int opcode, int a, int b ) {
			switch( opcode ) {
			case 1: // add
				var[ a ] += var[ b ];
				break;
			case 2: // mul
				var[ a ] *= var[ b ];
				break;
			case 3: // div
				var[ a ] /= var[ b ];
				break;
			case 4: // mod
				var[ a ] = var[ a ] % var[ b ];
				break;
			case 5: // eql
				var[ a ] = ( var[ a ] == var[ b ] ) ? 1 : 0;
				break;
			case 6: // addi
				var[ a ] += b;
				break;
			case 7: // muli
				var[ a ] *= b;
				break;
			case 8: // divi
				var[ a ] /= b;
				break;
			case 9: // modi
				var[ a ] = var[ a ] % b;
				break;
			case 10: // eqli
				var[ a ] = ( var[ a ] == b ) ? 1 : 0;
				break;
			default:
				return false;
			}
			return true;
		}
		public boolean isValid() {
			return var[ 2 ] == 0;
		}
	}
	private int[] opcode = new int[ 300 ];
	private int[] varA = new int[ 300 ];
	private int[] varB = new int[ 300 ];
	private int instCnt = 0;
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
				this.read( str );
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		ALU u = new ALU();
		u = run( u, 0 );
		System.out.println( u.input );
	}
	// if invalid, return null
	public ALU run( ALU u, int pc ) {
		while( pc < instCnt ) {
			if( opcode[ pc ] > 0 ) {
				u.op( opcode[ pc ], varA[ pc ], varB[ pc ] );
			}
			else {
				for( int i = 9; i >= 0; --i ) {
					ALU tmp = new ALU( u );
					tmp.inp( opcode[ pc ], i );
					tmp = run( tmp, pc + 1 );
					if( tmp != null ) return tmp;
				}
			}
			++pc;
		}
		if( u.isValid() ) return u;
		else return null;
	}
	public boolean isImme( String tok ) {
		if( tok.equals( "x" ) || tok.equals( "y" ) || tok.equals( "z" ) || tok.equals( "w" ) ) 
			return false;
		return true;
	}
	public int getVar( String tok ) {
		if( tok.equals( "x" ) ) return 0;
		else if( tok.equals( "y" ) ) return 1;
		else if( tok.equals( "z" ) ) return 2;
		else if( tok.equals( "w" ) ) return 3;
		else return Integer.valueOf( tok );
	}
	public void read( String line ) {
		String val[] = line.split( " " );
		varA[ instCnt ] = getVar( val[ 1 ] );
		if( val.length == 2 ) {
			opcode[ instCnt ] = 0;
		}
		else {
			varB[ instCnt ] = getVar( val[ 2 ] );
			if( val[ 0 ].equals( "add" ) ) 
				opcode[ instCnt ] = 1;
			else if( val[ 0 ].equals( "mul" ) )
				opcode[ instCnt ] = 2;
			else if( val[ 0 ].equals( "div" ) )
				opcode[ instCnt ] = 3;
			else if( val[ 0 ].equals( "mod" ) )
				opcode[ instCnt ] = 4;
			else if( val[ 0 ].equals( "eql" ) )
				opcode[ instCnt ] = 5;
			if( isImme( val[ 2 ] ) )
				opcode[ instCnt ] += 5;
		}
		instCnt++;
	}
	// then I found out every block of instructions is a function( w, some immediates... ) that output z
}