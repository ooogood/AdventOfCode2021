import java.util.*;
import java.io.*;

public class first {
	public class Pos3d {
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
		public int hashCode() {
			return x + ( y << 4 ) + ( z << 8 );
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
	HashSet<Pos3d> ons = new HashSet<Pos3d>();
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
				String val[] = str.split( "[=,.]+" );
				boolean bOn = ( val[ 0 ].charAt( 1 ) == 'n' );
				int xmin = Integer.valueOf( val[ 1 ] );
				int xmax = Integer.valueOf( val[ 2 ] );
				int ymin = Integer.valueOf( val[ 4 ] );
				int ymax = Integer.valueOf( val[ 5 ] );
				int zmin = Integer.valueOf( val[ 7 ] );
				int zmax = Integer.valueOf( val[ 8 ] );
				// part one constraint
				if( ( ( xmin < -50 ) && ( xmax < -50 ) ) ||
					( ( xmin > 50 ) && ( xmax > 50 ) ) ||
					( ( ymin < -50 ) && ( ymax < -50 ) ) ||
					( ( ymin > 50 ) && ( ymax > 50 ) ) ||
					( ( zmin < -50 ) && ( zmax < -50 ) ) ||
					( ( zmin > 50 ) && ( zmax > 50 ) ) ) {
					continue;
				}
				if( bOn ) {
					for( int i = xmin; i <= xmax; ++i ) {
						for( int j = ymin; j <= ymax; ++j ) {
							for( int k = zmin; k <= zmax; ++k ) {
								ons.add( new Pos3d( i, j, k ) );
							}
						}
					}
				}
				else {
					for( int i = xmin; i <= xmax; ++i ) {
						for( int j = ymin; j <= ymax; ++j ) {
							for( int k = zmin; k <= zmax; ++k ) {
								ons.remove( new Pos3d( i, j, k ) );
							}
						}
					}
				}
			}
			sr.close();
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
		System.out.println( ons.size() );
	}
}