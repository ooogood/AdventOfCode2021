import java.util.*;
import java.io.*;

public class second {
	public class Range3d {
		public long xmin;
		public long ymin;
		public long zmin;
		public long xmax;
		public long ymax;
		public long zmax;
		public Range3d( long xmin, long ymin, long zmin, long xmax, long ymax, long zmax ) {
			this.xmin = xmin;
			this.ymin = ymin;
			this.zmin = zmin;
			this.xmax = xmax;
			this.ymax = ymax;
			this.zmax = zmax;
		}
		public long getCnt() {
			return ( xmax - xmin + 1 ) * ( ymax - ymin + 1 ) * ( zmax - zmin + 1 );
		}
		public boolean isIntersect( Range3d o ) {
			return !( ( ( xmin < o.xmin ) && ( xmax < o.xmin ) ) ||
					( ( xmin > o.xmax ) && ( xmax > o.xmax ) ) ||
					( ( ymin < o.ymin ) && ( ymax < o.ymin ) ) ||
					( ( ymin > o.ymax ) && ( ymax > o.ymax ) ) ||
					( ( zmin < o.zmin ) && ( zmax < o.zmin ) ) ||
					( ( zmin > o.zmax ) && ( zmax > o.zmax ) ) );
		}
		// get intersect range between this and o
		public Range3d getIntersect( Range3d o ) {
			long newXmin = Math.max( xmin, o.xmin );
			long newXmax = Math.min( xmax, o.xmax );
			long newYmin = Math.max( ymin, o.ymin );
			long newYmax = Math.min( ymax, o.ymax );
			long newZmin = Math.max( zmin, o.zmin );
			long newZmax = Math.min( zmax, o.zmax );
			if( newXmin > newXmax || newYmin > newYmax || newZmin > newZmax ) 
				return null;
			else
				return new Range3d( newXmin, newYmin, newZmin, newXmax, newYmax, newZmax );
		}
		// split this cube if part of this is consumed by o
		// result in 0 ~ 26 new Range3d
		public ArrayList<Range3d> split( Range3d o ) {
			ArrayList<Range3d> ret = new ArrayList<Range3d>();
			long[][] xVal = { { Long.MIN_VALUE, o.xmin - 1 }, { o.xmin, o.xmax }, { o.xmax + 1, Long.MAX_VALUE } };
			long[][] yVal = { { Long.MIN_VALUE, o.ymin - 1 }, { o.ymin, o.ymax }, { o.ymax + 1, Long.MAX_VALUE } };
			long[][] zVal = { { Long.MIN_VALUE, o.zmin - 1 }, { o.zmin, o.zmax }, { o.zmax + 1, Long.MAX_VALUE } };
			Range3d tmp;
			for( int i = 0; i < xVal.length; ++i ) {
				for( int j = 0; j < yVal.length; ++j ) {
					for( int k = 0; k < zVal.length; ++k ) {
						// skip the middle cube
						if( i == 1 && j == 1 && k == 1 ) continue;
						tmp = getIntersect( new Range3d( xVal[ i ][ 0 ], yVal[ j ][ 0 ], zVal[ k ][ 0 ], xVal[ i ][ 1 ], yVal[ j ][ 1 ], zVal[ k ][ 1 ] ) );
						if( tmp != null ) ret.add( tmp );
					}
				}
			}
			return ret;
		}
	}
	ArrayList<Range3d> ons = new ArrayList<Range3d>();
	public static void main( String args[] ) {
		second a = new second();
		a.foo();
	}
	public void foo() {
		try {
			FileReader fr = new FileReader("input.txt");
			Scanner sr = new Scanner(fr);
			while( sr.hasNextLine() ) {
				String str = sr.nextLine();
				String val[] = str.split( "[=,.]+" );
				boolean bOn = ( val[ 0 ].charAt( 1 ) == 'n' );
				int xmin = Integer.valueOf( val[ 1 ] );
				int xmax = Integer.valueOf( val[ 2 ] );
				int ymin = Integer.valueOf( val[ 4 ] );
				int ymax = Integer.valueOf( val[ 5 ] );
				int zmin = Integer.valueOf( val[ 7 ] );
				int zmax = Integer.valueOf( val[ 8 ] );
				if( bOn ) {
					ArrayList<Range3d> tmp = new ArrayList<Range3d>();
					tmp.add( new Range3d(xmin, ymin, zmin, xmax, ymax, zmax));
					boolean checked;
					do {
						checked = true;
						for( Range3d t : tmp ) {
							for( Range3d r : ons ) {
								if( t.isIntersect( r ) ) {
									tmp.addAll( t.split( r ) );
									tmp.remove( t );
									checked = false;
									break;
								}
							}
							if( checked == false ) break;
						}
					} while( checked == false );
					ons.addAll( tmp );
				}
				else {
					Range3d off = new Range3d(xmin, ymin, zmin, xmax, ymax, zmax);
					boolean checked;
					do {
						checked = true;
						for( Range3d r : ons ) {
							if( r.isIntersect( off ) ) {
								ons.remove( r );
								ons.addAll( r.split( off ) );
								checked = false;
								break;
							}
						}
					} while( checked == false );
				}
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		long num = 0;
		for( Range3d r : ons ) {
			num += r.getCnt();
		}
		System.out.println( num );
	}
}