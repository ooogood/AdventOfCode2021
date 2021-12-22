import java.util.*;
import java.io.*;

public class firstAndSecond {
	public class Pos3d implements Comparable<Pos3d> {
		public int x;
		public int y;
		public int z;
		public Pos3d( int x, int y, int z ) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		public Pos3d( Pos3d o ) {
			this.x = o.x;
			this.y = o.y;
			this.z = o.z;
		}
		public void plus( Pos3d o ) {
			x += o.x;
			y += o.y;
			z += o.z;
		}
		public void minus( Pos3d o ) {
			x -= o.x;
			y -= o.y;
			z -= o.z;
		}
		@Override
		public int compareTo( Pos3d o ) {
			if( x > o.x ) {
				return 1;
			}
			else return -1;
		}
		@Override
		public int hashCode() {
			return x;
		}
		@Override
		public boolean equals( Object o ) {
			if( !( o instanceof Pos3d ) ) return false;
			Pos3d obj = (Pos3d)o;
			if( this.x == obj.x && this.y == obj.y && this.z == obj.z )
				return true;
			return false;
		}
	}
	private HashSet<Pos3d> bcMp = new HashSet<Pos3d>();
	private ArrayList<ArrayList<Pos3d>> unconfirmedSen = new ArrayList<ArrayList<Pos3d>>();
	private ArrayList<Pos3d> confirmedSenPos = new ArrayList<Pos3d>();
	public static void main( String args[] ) {
		firstAndSecond a = new firstAndSecond();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("input.txt");
			Scanner sr = new Scanner(fr);
			// add first sensor
			sr.nextLine();
			String str = sr.nextLine();
			while( str.length() > 0 ) {
				String[] val = str.split( "," );
				bcMp.add( new Pos3d( Integer.valueOf( val [ 0 ] ), 
									Integer.valueOf( val [ 1 ] ),
									Integer.valueOf( val [ 2 ] ) ) );
				str = sr.nextLine();
			}
			confirmedSenPos.add( new Pos3d( 0, 0, 0 ) );
			// add other sensors
			while( sr.hasNextLine() ) {
				ArrayList<Pos3d> thisScan = new ArrayList<Pos3d>();
				sr.nextLine();
				str = sr.nextLine();
				while( str.length() > 0 ) {
					String[] val = str.split( "," );
					thisScan.add( new Pos3d( Integer.valueOf( val [ 0 ] ), 
											Integer.valueOf( val [ 1 ] ),
											Integer.valueOf( val [ 2 ] ) ) );
					if( sr.hasNextLine() )
						str = sr.nextLine();
					else
						break;
				}
				unconfirmedSen.add( thisScan );
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		while( unconfirmedSen.size() > 0 ) {
			for( int i = 0; i < unconfirmedSen.size(); ++i ) {
				if( guessSensorPos( unconfirmedSen.get( i ) ) ) {
					unconfirmedSen.remove( i );
					break;
				}
			}
		}
		// for part one
		System.out.println( bcMp.size() );
		// for part two
		int maxDist = Integer.MIN_VALUE;
		for( int i = 0; i < confirmedSenPos.size() - 1; ++i ) {
			for( int j = i + 1; j < confirmedSenPos.size(); ++j ) {
				int dist = Math.abs( confirmedSenPos.get( i ).x - confirmedSenPos.get( j ).x ) +
							Math.abs( confirmedSenPos.get( i ).y - confirmedSenPos.get( j ).y ) +
							Math.abs( confirmedSenPos.get( i ).z - confirmedSenPos.get( j ).z );
				if( dist > maxDist ) maxDist = dist;
			}
		}
		System.out.println( maxDist );
	}
	private boolean guessSensorPos( ArrayList<Pos3d> sen ) {
		ArrayList<Pos3d> arr;
		for( int i = 0; i < 8; ++i ) {
			for( int j = 0; j < 3; ++j ) {
				arr = genDirectedSen( sen, i, j );
				if( guessDirectedSensorPos( arr ) ) 
					return true;
			}
		}
		return false;
	}
	private boolean guessDirectedSensorPos( ArrayList<Pos3d> dsen ) {
		for( Pos3d b : bcMp ) {
			for( Pos3d ub : dsen ) {
				Pos3d senPos = new Pos3d( b );
				senPos.minus( ub );
				int matchCnt = 0;
				int misMatchCnt = 0;
				int misMatchCntLimit = dsen.size() - 12;
				for( Pos3d rb : dsen ) {
					Pos3d bcPos = new Pos3d( senPos.x + rb.x, senPos.y + rb.y, senPos.z + rb.z );
					if( bcMp.contains( bcPos ) ) {
						if( ++matchCnt >= 12 ) break;
					}
					else {
						if( ++misMatchCnt > misMatchCntLimit ) break;
					}
				}
				// successfully match more than 12 beacon position
				// the sensor position and direction was found
				// add all beacon position into the map
				if( matchCnt >= 12 ) {
					confirmedSenPos.add( senPos );
					for( Pos3d rb : dsen ) {
						bcMp.add( new Pos3d( senPos.x + rb.x, senPos.y + rb.y, senPos.z + rb.z ) );
					}
					return true;
				}
			}
		}
		return false;
	}
	// sign = 0 ~ 7
	// shift = 0 ~ 2
	// simplified rotation matrix
	private ArrayList<Pos3d> genDirectedSen( ArrayList<Pos3d> sen, int sign, int shift ) {
		ArrayList<Pos3d> ret = new ArrayList<Pos3d>();
		int xSign = ( sign & 1 ) == 0 ? 1 : -1;
		int ySign = ( sign & 2 ) == 0 ? 1 : -1;
		int zSign = ( sign & 4 ) == 0 ? 1 : -1;
		if( xSign * ySign * zSign > 0 ) {
			for( Pos3d b : sen ) {
				switch( shift ) {
				case 0:
					ret.add( new Pos3d( b.x * xSign, b.y * ySign, b.z * zSign ) );
					break;
				case 1:
					ret.add( new Pos3d( b.z * xSign, b.x * ySign, b.y * zSign ) );
					break;
				case 2:
					ret.add( new Pos3d( b.y * xSign, b.z * ySign, b.x * zSign ) );
					break;
				}
			}
		}
		else {
			for( Pos3d b : sen ) {
				switch( shift ) {
				case 0:
					ret.add( new Pos3d( b.x * xSign, b.z * ySign, b.y * zSign ) );
					break;
				case 1:
					ret.add( new Pos3d( b.z * xSign, b.y * ySign, b.x * zSign ) );
					break;
				case 2:
					ret.add( new Pos3d( b.y * xSign, b.x * ySign, b.z * zSign ) );
					break;
				}
			}
		}
		return ret;
	}
}