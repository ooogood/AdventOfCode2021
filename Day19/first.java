import java.util.*;
import java.io.*;

public class first {
	public class Beacon {
		public int x;
		public int y;
		public int z;
		public Beacon( int x, int y, int z ) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		@Override
		public int hashCode() {
			return x;
		}
		@Override
		public boolean equals( Object o ) {
			if( !( o instanceof Beacon ) ) return false;
			Beacon obj = (Beacon)o;
			if( this.x == obj.x && this.y == obj.y && this.z == obj.z )
				return true;
			return false;
		}
	}
	private HashSet<Beacon> mp = new HashSet<Beacon>();
	private ArrayList<ArrayList<Beacon>> unconfirmed = new ArrayList<ArrayList<Beacon>>();
	public static void main( String args[] ) {
		first a = new first();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("expinput.txt");
			Scanner sr = new Scanner(fr);
			sr.nextLine();
			String str = sr.nextLine();
			while( str.length() > 0 ) {
				String[] val = str.split( "," );
				mp.add( new Beacon( Integer.valueOf( val [ 0 ] ), 
									Integer.valueOf( val [ 1 ] ),
									Integer.valueOf( val [ 2 ] ) ) );
				str = sr.nextLine();
			}
			while( sr.hasNextLine() ) {
				ArrayList<Beacon> thisScan = new ArrayList<Beacon>();
				sr.nextLine();
				str = sr.nextLine();
				while( str.length() > 0 ) {
					String[] val = str.split( "," );
					thisScan.add( new Beacon( Integer.valueOf( val [ 0 ] ), 
											Integer.valueOf( val [ 1 ] ),
											Integer.valueOf( val [ 2 ] ) ) );
					if( sr.hasNextLine() )
						str = sr.nextLine();
					else
						break;
				}
				unconfirmed.add( thisScan );
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		while( unconfirmed.size() > 0 ) {
			for( int i = 0; i < unconfirmed.size(); ++i ) {
				if( guessSensorPos( unconfirmed.get( i ) ) )
					break;
			}
		}
	}
	private boolean guessSensorPos( ArrayList<Beacon> sen ) {
		for( int i = 0; i < 8; ++i ) {
			for( int j = 0; j < 3; ++j ) {
				ArrayList<Beacon> arr = genDirectedSen( sen, i, j );
			}
		}
		return false;
	}
	// sign = 0 ~ 7
	// shift = 0 ~ 2
	private ArrayList<Beacon> genDirectedSen( ArrayList<Beacon> sen, int sign, int shift ) {
		ArrayList<Beacon> ret = new ArrayList<Beacon>();
		int xSign = ( sign & 1 ) == 0 ? 1 : -1;
		int ySign = ( sign & 2 ) == 0 ? 1 : -1;
		int zSign = ( sign & 4 ) == 0 ? 1 : -1;
		for( Beacon b : sen ) {
			switch( shift ) {
			case 0:
				ret.add( new Beacon( b.x * xSign, b.y * ySign, b.z * zSign ) );
				break;
			case 1:
				ret.add( new Beacon( b.z * zSign, b.x * xSign, b.y * ySign ) );
				break;
			case 2:
				ret.add( new Beacon( b.y * ySign, b.z * zSign, b.x * xSign ) );
				break;
			}
		}
		return ret;
	}
}