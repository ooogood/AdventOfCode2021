import java.util.*;

public class firstAndSecond {
	long minCost = Long.MAX_VALUE;
	public class Instance {
		// -1 means empty space
		// -2 means cannot stand
		public int[] hall; // hall[ 11 ]
		// hall[ 2 ] is in front of room0
		// hall[ 4 ] is in front of room1
		// hall[ 6 ] is in front of room2
		// hall[ 8 ] is in front of room3
		public int[][] rooms = new int[ 4 ][];
		public long cost;
		public final int iterLimit = 25;
		public int iteration;
		public Instance( int[] hall, int[] room0, int[] room1, int[] room2, int[] room3, long cost, int iteration ) {
			this.hall = hall;
			this.rooms[ 0 ] = room0;
			this.rooms[ 1 ] = room1;
			this.rooms[ 2 ] = room2;
			this.rooms[ 3 ] = room3;
			this.cost = cost;
			this.iteration = iteration;
		}
		public Instance( Instance o ) {
			this.hall = Arrays.copyOf( o.hall, o.hall.length );
			this.rooms[ 0 ] = Arrays.copyOf( o.rooms[ 0 ], o.rooms[ 0 ].length );
			this.rooms[ 1 ] = Arrays.copyOf( o.rooms[ 1 ], o.rooms[ 1 ].length );
			this.rooms[ 2 ] = Arrays.copyOf( o.rooms[ 2 ], o.rooms[ 2 ].length );
			this.rooms[ 3 ] = Arrays.copyOf( o.rooms[ 3 ], o.rooms[ 3 ].length );
			this.cost = o.cost;
			this.iteration = o.iteration;
		}
		public boolean isFinished() {
			for( int i = 0; i < rooms.length; ++i ) {
				for( int j : rooms[ i ] ) {
					if( j != i ) return false;
				}
			}
			return true;
		}
		// the amphipods can only enter the room if the room welcome
		public boolean roomWelcome( int o ) {
			for( int i : rooms[ o ] ) {
				if( i != -1 && i != o ) return false;
			}
			return true;
		}
		// get door position
		public int door( int room ) {
			switch( room ) {
			case 0:
				return 2;
			case 1:
				return 4;
			case 2:
				return 6;
			case 3:
				return 8;
			default:
				return 0;
			}
		}
		// calculate the steps needed from hallway to room's door
		// if return -1, it means impossible to move
		public int moveInHall( int h1, int h2 ) {
			if( h1 > h2 )
				return moveInHallAbs(h2, h1);
			else if( h1 < h2 )
				return moveInHallAbs(h1, h2);
			else return 0;
		}
		// h2 must be > h1
		public int moveInHallAbs( int h1, int h2 ) {
			// check all slot between h1 and h2
			// ( doesn't check h1 and h2 )
			for( int i = h1 + 1; i < h2; ++i ) {
				if( hall[ i ] >= 0 && hall[ i ] <= 3 ) return -1;
			}
			return h2 - h1;
		}
		public long getCost( int i ) {
			switch( i ) {
			case 0:
				return 1;
			case 1:
				return 10;
			case 2:
				return 100;
			case 3:
				return 1000;
			default:
				return 0;
			}
		}
		public int getOutermostElemSlot( int room ) {
			int i = rooms[ room ].length - 1;
			for(; i >= 0; --i ) {
				if( rooms[ room ][ i ] != -1 ) break;
			}
			return i;
		}
		// return every possible move
		public void advance() {
			/* key here: if we don't have this line, we will be out of memory */
			if( this.cost >= minCost ) return;
			// check if completed
			if( this.isFinished() ) {
				minCost = Math.min( minCost, this.cost );
				return;
			}
			// if over iteration limit, give up this instance
			if( this.iteration > iterLimit ) return;
			boolean someOneMoved = false;
			// if an amphipod in hallway is welcome to a room
			for( int i = 0; i < hall.length; ++i ) {
				if( hall[ i ] < 0 || hall[ i ] > 3 ) continue;
				if( roomWelcome( hall[ i ] ) ) {
					Instance tmp = new Instance( this );
					int steps = 0;
					// cost from current position to room door
					int h = moveInHall( i, door( hall[ i ] ) );
					if( h == -1 ) continue;
					steps += h;
					// cost from room door to slot inside the room
					int j = getOutermostElemSlot( hall[ i ] ) + 1;
					steps += ( rooms[ hall[ i ] ].length - j );
					// calculate the cost and swap position
					tmp.cost += ( steps * getCost( hall[ i ] ) );
					tmp.rooms[ hall[ i ] ][ j ] = hall[ i ];
					tmp.hall[ i ] = -1;
					tmp.iteration++;
					tmp.advance();
					someOneMoved = true;
				}
			}
			// if an amphipod in room can exit freely and is welcome to a room
			for( int i = 0; i < rooms.length; ++i ) {
				int slot = getOutermostElemSlot( i );
				// if it is already in destination
				if( slot < 0 || rooms[ i ][ slot ] == i ) continue;
				if( roomWelcome( rooms[ i ][ slot ] ) ) {
					Instance tmp = new Instance( this );
					int destination = rooms[ i ][ slot ];
					int steps = 0;
					// cost to get out of the current room
					steps += ( rooms[ i ].length - slot );
					// cost from current door to destination door
					int h = moveInHall( door( i ), door( destination ) );
					if( h == -1 ) continue;
					steps += h;
					// cost from room door to slot inside the room
					int j = getOutermostElemSlot( destination ) + 1;
					steps += ( rooms[ destination ].length - j );
					// calculate the cost and swap position
					tmp.cost += ( steps * getCost( destination ) );
					tmp.rooms[ destination ][ j ] = destination;
					tmp.rooms[ i ][ slot ] = -1;
					tmp.iteration++;
					tmp.advance();
					someOneMoved = true;
				}
			}
			// we assume moving an amphipod to its destination is the optimal move
			if( someOneMoved ) {
				return;
			}
			// try every possible move 
			// move an amphipod in the room to every possible hallway position
			for( int i = 0; i < rooms.length; ++i ) {
				// all elements in this room are already in its destination, skip
				if( roomWelcome( i ) ) continue;
				int slot = getOutermostElemSlot( i );
				// empty room, skip
				if( slot < 0 ) continue;
				for( int j = 0; j < hall.length; ++j ) {
					if( hall[ j ] == -1 ) {
						int h = moveInHall( j, door( i ) );
						if( h == -1 ) continue;
						Instance tmp = new Instance( this );
						tmp.cost += ( rooms[ i ].length - slot + h ) * getCost( rooms[ i ][ slot ] );
						tmp.hall[ j ] = tmp.rooms[ i ][ slot ];
						tmp.rooms[ i ][ slot ] = -1;
						tmp.iteration++;
						tmp.advance();
					}
				}
			}
		}
		public void drawInstance() {
			String out = "";
			for( int i : hall ) {
				out += String.format( "%2d ", i );
			}
			System.out.println( out );
			for( int i = rooms[ 0 ].length - 1; i >= 0; --i )
				System.out.println( String.format( "      %2d    %2d    %2d    %2d       ", rooms[ 0 ][ i ], rooms[ 1 ][ i ], rooms[ 2 ][ i ], rooms[ 3 ][ i ] ) );
		}
	}
	public static void main( String args[] ) {
		firstAndSecond a = new firstAndSecond();
		a.foo();
	}
	public void foo() {
		int[] hall = { -1, -1, -2, -1, -2, -1, -2, -1, -2, -1, -1 };
		// example input for part one
		// int[] room0 = { 0, 1 };
		// int[] room1 = { 3, 2 };
		// int[] room2 = { 2, 1 };
		// int[] room3 = { 0, 3 };
		// // real input for part one
		// int[] room0 = { 2, 1 };
		// int[] room1 = { 3, 0 };
		// int[] room2 = { 3, 1 };
		// int[] room3 = { 0, 2 };
		// // example input for part two
		// int[] room0 = { 0, 3, 3, 1 };
		// int[] room1 = { 3, 1, 2, 2 };
		// int[] room2 = { 2, 0, 1, 1 };
		// int[] room3 = { 0, 2, 0, 3 };
		// real input for part one
		int[] room0 = { 2, 3, 3, 1 };
		int[] room1 = { 3, 1, 2, 0 };
		int[] room2 = { 3, 0, 1, 1 };
		int[] room3 = { 0, 2, 0, 2 };
		Instance a = new Instance( hall, room0, room1, room2, room3, 0, 0 );
		// use BFS to prevent memory shortage
		a.advance();
		System.out.println( this.minCost );
	}
}