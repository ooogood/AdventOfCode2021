package Day7;
import java.io.*;
import java.util.*;


public class second {
	/* <end, cost>*/
	private HashMap<Long, Long> rec = new HashMap<Long, Long>();
	/* <locDiff, cost>*/
	private HashMap<Long, Long> diffCost = new HashMap<Long, Long>();
	public static void main( String args[] ) {
		second obj = new second();
		long ret;
		try {
			ArrayList<Long> arr = new ArrayList<Long>();
			FileReader fr = new FileReader( "Day7\\input.txt" );
			Scanner sr = new Scanner( fr ).useDelimiter( ",");
			Long end = Long.valueOf( 0 );
			while( sr.hasNext() ) {
				String str = sr.next();
				Long tmp = Long.parseLong( str );
				arr.add( tmp );
				end += tmp;
			}
			end /= arr.size();
			ret = obj.calcCost( arr, end );
			while( obj.calcCost( arr, end + 1 ) < ret ||
					obj.calcCost( arr, end - 1 ) < ret ) {
				if( obj.calcCost( arr, end + 1 ) < ret ) {
					end += 1;
					ret = obj.calcCost( arr, end );
				}
				else {
					end -= 1;
					ret = obj.calcCost( arr, end );
				}
			}
			System.out.println( end );
			System.out.println( ret );
			sr.close();
		}
		catch (Exception e ) {
			e.printStackTrace();
		}
	}
	private Long calcCost( ArrayList<Long> arr, Long end ) {
		Long ret = rec.get(end);
		if( ret != null ) return ret;
		long cnt = 0;
		for( Long i : arr ) {
			if( i > end ) {
				cnt += calcDiffCost( i - end );
			}
			else {
				cnt += calcDiffCost( end - i );
			}
		}
		rec.put( end, cnt );
		return cnt;
	}
	private Long calcDiffCost( Long diff ) {
		Long ret = diffCost.get(diff);
		if( ret != null ) return ret;
		long cnt = ( diff ) * ( diff + 1 ) / 2;
		diffCost.put( diff, cnt );
		return cnt;
	}
}