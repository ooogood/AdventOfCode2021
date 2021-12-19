package Day7;
import java.io.*;
import java.util.*;


public class first {
	/* <end, cost>*/
	private HashMap<Integer, Integer> rec = new HashMap<Integer, Integer>();
	public static void main( String args[] ) {
		first obj = new first();
		int ret;
		try {
			ArrayList<Integer> arr = new ArrayList<Integer>();
			FileReader fr = new FileReader( "Day7\\input.txt" );
			Scanner sr = new Scanner( fr ).useDelimiter( ",");
			Integer end = 0;
			while( sr.hasNext() ) {
				String str = sr.next();
				Integer tmp = Integer.parseInt( str );
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
	private Integer calcCost( ArrayList<Integer> arr, Integer end ) {
		Integer ret = rec.get(end);
		if( ret != null ) return ret;
		int cnt = 0;
		for( Integer i : arr ) {
			if( i > end )
				cnt += ( i - end );
			else
				cnt += ( end - i );
		}
		rec.put( end, cnt );
		return cnt;
	}
}