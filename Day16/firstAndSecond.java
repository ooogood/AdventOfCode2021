import java.util.*;
import java.io.*;

public class firstAndSecond {
	private int cursor = 0;
	public int verSum = 0;
	public static void main( String args[] ) {
		// firstAndSecond a = new firstAndSecond();
		// a.foo();
		// // for first part
		// System.out.println( a.verSum );
		Integer a = Integer.valueOf( 127 );
		Integer b = Integer.valueOf( 127 );
		boolean c = ( a == b );	// c = false
		boolean d = a.equals( b ); // d = true
		System.out.println( c );
		System.out.println( d );
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("input.txt");
			Scanner sr = new Scanner(fr);
			String str = sr.nextLine();
			String bits = "";
			for( int i = 0; i < str.length(); ++i ) {
				bits += hexToBits( str.substring( i, i + 1 ) );
			}
			Long ret = analyzeSubpacket( bits );
			// for second part
			System.out.println( ret );
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
	private Long analyzeSubpacket( String bits ) {
		if( bits.length() - cursor < 11 ) 
			return -1L;
		int version = Integer.valueOf( bits.substring( cursor, cursor + 3 ), 2 );
		verSum += version;
		int type = Integer.valueOf( bits.substring( cursor + 3, cursor + 6 ), 2 );
		switch( type ) {
		// only literal
		case 4: {
			Long num = analyzeLiteral( bits );
			return num;
		}
		// sum
		case 0: {
			Long ret = 0L;
			char lenType = bits.charAt( cursor + 6 );
			// indicate bits length
			if( lenType == '0' ) {
				int lenBits = Integer.valueOf( bits.substring( cursor + 7, cursor + 22 ), 2 );
				cursor += 22;
				int base = cursor;
				while( cursor - base < lenBits ) {
					ret += analyzeSubpacket( bits );
				}
			}
			// indicate subpacket count
			else {
				int lenSubPkt = Integer.valueOf( bits.substring( cursor + 7, cursor + 18 ), 2 );
				cursor += 18;
				while( lenSubPkt-- > 0 ) {
					ret += analyzeSubpacket( bits );
				}
			}
			return ret;
		}
		// product
		case 1: {
			Long ret = 1L;
			char lenType = bits.charAt( cursor + 6 );
			// indicate bits length
			if( lenType == '0' ) {
				int lenBits = Integer.valueOf( bits.substring( cursor + 7, cursor + 22 ), 2 );
				cursor += 22;
				int base = cursor;
				while( cursor - base < lenBits ) {
					ret *= analyzeSubpacket( bits );
				}
			}
			// indicate subpacket count
			else {
				int lenSubPkt = Integer.valueOf( bits.substring( cursor + 7, cursor + 18 ), 2 );
				cursor += 18;
				while( lenSubPkt-- > 0 ) {
					ret *= analyzeSubpacket( bits );
				}
			}
			return ret;
		}
		// min
		case 2: {
			Long ret = Long.MAX_VALUE;
			char lenType = bits.charAt( cursor + 6 );
			// indicate bits length
			if( lenType == '0' ) {
				int lenBits = Integer.valueOf( bits.substring( cursor + 7, cursor + 22 ), 2 );
				cursor += 22;
				int base = cursor;
				while( cursor - base < lenBits ) {
					Long num = analyzeSubpacket( bits );
					if( num < ret ) ret = num;
				}
			}
			// indicate subpacket count
			else {
				int lenSubPkt = Integer.valueOf( bits.substring( cursor + 7, cursor + 18 ), 2 );
				cursor += 18;
				while( lenSubPkt-- > 0 ) {
					Long num = analyzeSubpacket( bits );
					if( num < ret ) ret = num;
				}
			}
			return ret;
		}
		// max 
		case 3: {
			Long ret = Long.MIN_VALUE;
			char lenType = bits.charAt( cursor + 6 );
			// indicate bits length
			if( lenType == '0' ) {
				int lenBits = Integer.valueOf( bits.substring( cursor + 7, cursor + 22 ), 2 );
				cursor += 22;
				int base = cursor;
				while( cursor - base < lenBits ) {
					Long num = analyzeSubpacket( bits );
					if( num > ret ) ret = num;
				}
			}
			// indicate subpacket count
			else {
				int lenSubPkt = Integer.valueOf( bits.substring( cursor + 7, cursor + 18 ), 2 );
				cursor += 18;
				while( lenSubPkt-- > 0 ) {
					Long num = analyzeSubpacket( bits );
					if( num > ret ) ret = num;
				}
			}
			return ret;
		}
		// gt 
		case 5: {
			char lenType = bits.charAt( cursor + 6 );
			// indicate bits length
			if( lenType == '0' ) {
				cursor += 22;
				Long num1 = analyzeSubpacket( bits );
				Long num2 = analyzeSubpacket( bits );
				return num1 > num2 ? 1L : 0L;
			}
			// indicate subpacket count
			else {
				cursor += 18;
				Long num1 = analyzeSubpacket( bits );
				Long num2 = analyzeSubpacket( bits );
				return num1 > num2 ? 1L : 0L;
			}
		}
		// lt 
		case 6: {
			char lenType = bits.charAt( cursor + 6 );
			// indicate bits length
			if( lenType == '0' ) {
				cursor += 22;
				Long num1 = analyzeSubpacket( bits );
				Long num2 = analyzeSubpacket( bits );
				return num1 < num2 ? 1L : 0L;
			}
			// indicate subpacket count
			else {
				cursor += 18;
				Long num1 = analyzeSubpacket( bits );
				Long num2 = analyzeSubpacket( bits );
				return num1 < num2 ? 1L : 0L;
			}
		}
		// eq 
		case 7: {
			char lenType = bits.charAt( cursor + 6 );
			// indicate bits length
			if( lenType == '0' ) {
				cursor += 22;
				Long num1 = analyzeSubpacket( bits );
				Long num2 = analyzeSubpacket( bits );
				return num1.equals( num2 ) ? 1L : 0L;
			}
			// indicate subpacket count
			else {
				cursor += 18;
				Long num1 = analyzeSubpacket( bits );
				Long num2 = analyzeSubpacket( bits );
				return num1.equals( num2 ) ? 1L : 0L;
			}
		}
		default:
			throw new RuntimeException();
		}
	}
	// get [ VVVTTTAAAAABBBBBCCCCC... ] and return long
	private Long analyzeLiteral( String lit ) {
		try {
			cursor += 6;
			String ret = "";
			while( true ) {
				ret += lit.substring( cursor + 1, cursor + 5 );
				cursor += 5;
				if( lit.charAt( cursor - 5 ) == '0' ) break;
			}
			return Long.valueOf( ret, 2 );
		}
		catch( Exception e ) {
			return 0L;
		}
	}
	private static String hexToBits( String hex ) {
		return String.format( "%4s", Integer.toBinaryString( Integer.valueOf( hex, 16 ) ) ).replace( ' ', '0' );
	}
}