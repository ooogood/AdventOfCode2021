import java.util.*;
import java.io.*;

public class first {
	private int xMin;
	private int xMax;
	private int yMin;
	private int yMax;
	public static void main( String args[] ) {
		first a = new first();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("expinput.txt");
			Scanner sr = new Scanner(fr);
			String[] str = sr.nextLine().split(",");
			xMin = Integer.valueOf( str[ 0 ] );
			xMax = Integer.valueOf( str[ 1 ] );
			yMin = Integer.valueOf( str[ 2 ] );
			yMax = Integer.valueOf( str[ 3 ] );
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		// calc x init vel range
		int xRangeMin = 0;
		int xRangeMax = 0;
		int xDisp = 0;
		for( int i = 1; xDisp < xMax; i++ ) {
			xDisp
		}
	}
	private boolean isPossible( int Vx, int Vy ) {

	}
}