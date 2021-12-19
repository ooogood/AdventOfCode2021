#include<bits\stdc++.h>
#include"yuandebug.h"
#include"commaFacet.h"
using namespace std;
struct pair_hash {
    template <class T1, class T2>
    std::size_t operator () (const std::pair<T1,T2> &p) const {
        auto h1 = std::hash<T1>{}(p.first);
        auto h2 = std::hash<T2>{}(p.second);
        return h1 ^ h2;  
    }
};

int main () {
	int ret = 0;
	/* ( ( x, y ), fogCnt )*/
	unordered_map<pair<int, int>, int, pair_hash> mp;
	fstream f( "input.txt", ios::in );
	f.imbue( locale( locale::classic(), new commaFacet ));
	while( !f.eof() ) {
		int x1, y1, x2, y2;
		string dummy;
		f >> x1 >> y1 >> dummy >> x2 >> y2;
		// ignore slash line ( disabled in second part )
		// if( x1 != x2 && y1 != y2 ) continue;
		// put upper right position in 2
		if( x1 > x2 || y1 > y2 ) {
			swap( x1, x2 );
			swap( y1, y2 );
		}
		int xAdv = x2 - x1;
		int yAdv = y2 - y1;
		int totalAdv = max( xAdv, yAdv );
		xAdv /= totalAdv;
		yAdv /= totalAdv;
		for( int i = 0; i <= totalAdv; ++i ) {
			if( mp.count( pair<int, int>( x1 + ( xAdv * i ), y1 + ( yAdv * i ) ) ) != 0 ) {
				if( ++mp[ pair<int, int>( x1 + ( xAdv * i ), y1 + ( yAdv * i ) ) ] == 2 ) {
					++ret;
					// std::cout << x1 + ( xAdv * i ) << ' ' << y1 + ( yAdv * i ) << endl;
				}
			}
			else {
				mp[ pair<int, int>( x1 + ( xAdv * i ), y1 + ( yAdv * i ) ) ] = 1;
			}
		}
	}
	std::cout << ret << endl;
	return 0;
}