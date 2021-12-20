import java.util.*;
import java.io.*;

public class firstAndSecond {
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	private int maxYAchieved;
	private int count = 0;
	public static void main( String args[] ) {
		firstAndSecond a = new firstAndSecond();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("input.txt");
			Scanner sr = new Scanner(fr);
			String[] str = sr.nextLine().split(",");
			xMin = Integer.valueOf( str[ 0 ] );
			xMax = Integer.valueOf( str[ 1 ] );
			yMin = Integer.valueOf( str[ 2 ] );
			yMax = Integer.valueOf( str[ 3 ] );
			maxYAchieved = yMin;
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		// calculate minimum initial Vx
		int minVx, xDisp = xMin;
		for( minVx = 1; xDisp > 0; minVx++ ) {
			xDisp -= minVx;
		}
		minVx--;
		for( int i = minVx; i < 1000; ++i ) {
			for( int j = -1000; j < 1000; ++j ) {
				if( isPossible( i, j ) ) {
					++count;
				}
			}
		}
		System.out.println( this.maxYAchieved );
		System.out.println( this.count );
	}
	private boolean isPossible( int Vx, int Vy ) {
		int x = 0, y = 0;
		int maxY = yMin;
		while( y > yMin ) {
			x += Vx;
			y += Vy;
			if( y > maxY )  maxY = y;
			if( x >= xMin && x <= xMax && y >= yMin && y <= yMax )  {
				if( maxYAchieved < maxY ) maxYAchieved = maxY;
				return true;
			}
			if( Vx > 0 ) Vx--;
			else if( Vx < 0 ) Vx++;
			Vy--;
		}
		return false;
	}
}