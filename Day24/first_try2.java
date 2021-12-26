import java.util.*;
import java.io.*;

public class first_try2 {
	// line 18n + 5: div z ?
	private int[] const2 = { 1, 1, 1 };
	// line 18n + 6: add x ?
	private int[] const3 = { 10, 13, 15 };
	// line 18n + 16 add y ?
	private int[] const4 = { 10, 5, 12 };
	public static void main( String args[] ) {
		first_try2 a = new first_try2();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("input.txt");
			Scanner sr = new Scanner(fr);
			while( sr.hasNextLine() ) {
				String str = sr.nextLine();
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
}