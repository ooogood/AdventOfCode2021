import java.util.*;
import java.io.*;

public class first {
	public class DetDice {
		private int nextNum = 0;
		public int rollCnt = 0;
		public int roll() {
			rollCnt++;
			int ret = nextNum++;
			nextNum = nextNum % 100;
			return ret + 1;
		}
	}
	private int[] pos = new int[ 2 ];
	private int[] score = new int[ 2 ];
	private int nextTurnPlayer = 0;
	private DetDice dice = new DetDice();
	public static void main( String args[] ) {
		first a = new first();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("input.txt");
			Scanner sr = new Scanner(fr);
			String str = sr.nextLine();
			pos[ 0 ] = Integer.valueOf( str );
			str = sr.nextLine();
			pos[ 1 ] = Integer.valueOf( str );
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		while( !turn() ) {
		}
		System.out.println( score[ ( ( nextTurnPlayer + 1 ) & 1 ) ] * dice.rollCnt );
	}
	private boolean turn() {
		pos[ nextTurnPlayer ] += dice.roll();
		pos[ nextTurnPlayer ] += dice.roll();
		pos[ nextTurnPlayer ] += dice.roll();
		pos[ nextTurnPlayer ] = pos[ nextTurnPlayer ] % 10;
		if( pos[ nextTurnPlayer ] == 0 ) pos[ nextTurnPlayer ] = 10;
		score[ nextTurnPlayer ] += pos[ nextTurnPlayer ];
		if( score[ nextTurnPlayer ] >= 1000 ) {
			return true;
		}
		nextTurnPlayer = ( ( nextTurnPlayer + 1 ) & 1 );
		return false;
	}
}