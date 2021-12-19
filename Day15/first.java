import java.util.*;
import java.io.*;

public class first {
	private class Node {
		public int row;
		public int col;
		public int cost;
	}
	private final int len = 10;
	private int cost[][];
	private PriorityQueue<
	public static void main( String args[] ) {
		first a = new first();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("expinput.txt");
			Scanner sr = new Scanner(fr);
			int ret = 0;
			while( sr.hasNextLine() ) {
				String str = sr.nextLine();
			}
			System.out.println( ret );
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
}