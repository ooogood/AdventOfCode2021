#include <bits\stdc++.h>
#include "yuandebug.h"
#include "commaFacet.h"
using namespace std;

int main () {
	unsigned long long buf[ 9 ] ={ 0 };
	int tmp;
	fstream f( "input.txt", ios::in );
	f.imbue( locale( locale::classic(), new commaFacet ));
	while( !f.eof() ) {
		f >> tmp;
		++buf[ tmp ];
	}
	// days go by
	for( int i = 0; i < 256; ++i ) {
		unsigned long long birth = buf[ 0 ];
		for( int j = 0; j < 8; ++j ) {
			buf[ j ] = buf[ j + 1 ];
		}
		buf[ 8 ] = birth;
		buf[ 6 ] += birth;
	}
	// calculate total
	unsigned long long ret = 0;
	for( int i = 0; i < 9; ++i ) {
		ret += buf[ i ];
	}
	cout << ret << endl;
	return 0;
}