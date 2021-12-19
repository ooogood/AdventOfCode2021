#include <bits\stdc++.h>
#include "yuandebug.h"
#include "commaFacet.h"
using namespace std;
/*
first thought:
	If the marked index is the input, how to define a line:
		1. horizontal: for i = 0 : 5 : 20, [i ~ i+4] indices are marked
		2. vertical: for i = 0 : 4, check if [i, i+5, i+10, i+15, i+20 ] are marked
*/
class board {
private:
	/* (value, index) */
	unordered_map<int, int> idx;
	bool marked[ 25 ];
public:
	board() {
		memset( marked, 0, sizeof( bool ) * 25 );
	}
	void add( int value, int index) {
		idx[ value ] = index;
	}
	void mark( int value ) {
		if( idx.count( value ) != 0 ) {
			marked[ idx[ value ] ] = true;
		}
	}
	bool isLineExist() {
		if( checkHorizontal() || checkVertical() )
			return true;
		return false;
	}
	bool showMark() {
		for( int i = 0; i < 5; ++i ) {
			for( int j = 0; j < 5; ++j ) {
				if( marked[ i * 5 + j ] )
					cout << "1 ";
				else 
					cout << "0 ";
			}
			cout << endl;
		}
	}
private:
	bool checkSlash() {
		if( ( marked[ 0 ] && marked[ 6 ] && marked[ 12 ] && marked[ 18 ] && marked[ 24 ] ) ||
			( marked[ 4 ] && marked[ 8 ] && marked[ 12 ] && marked[ 16 ] && marked[ 20 ] ) )
			return true;
		return false;
	}
	bool checkHorizontal() {
		for( int i = 0; i <= 20; i += 5 ) {
			if( marked[ i ] && marked[ i + 1 ] && marked[ i + 2 ] && marked[ i + 3 ] && marked[ i + 4 ] )
				return true;
		}
		return false;
	}
	bool checkVertical() {
		for( int i = 0; i <= 4; ++i ) {
			if( marked[ i ] && marked[ i + 5 ] && marked[ i + 10 ] && marked[ i + 15 ] && marked[ i + 20 ] )
				return true;
		}
		return false;
	}
};

int main () {
	vector<board> boards;
	fstream f( "boards.txt", ios::in );
	while( !f.eof() ) {
		board b;
		int tmp;
		for( int i = 0; i < 25; ++i ) {
			f >> tmp;
			b.add( tmp, i );
		}
		boards.push_back( b );
	}
	f.close();
	fstream input( "input.txt", ios::in );
	input.imbue( locale( locale::classic(), new commaFacet ));
	int tmp;
	int winner = 0;
	for( int i = 0; i < 4; ++i ) {
		input >> tmp;
		for( int j = 0; j < boards.size(); ++j ) {
			boards[ j ].mark( tmp );
		}
	}
	vector<bool> disabled( boards.size(), false );
	int ActiveCnt = boards.size();
	int lastone = 0;
	while( !input.eof() && ActiveCnt > 0 ) {
		input >> tmp;
		for( winner = 0; winner < boards.size(); winner++ ) {
			if( disabled[ winner ] ) continue;
			boards[ winner ].mark( tmp );
			if( boards[ winner ].isLineExist() ) {
				disabled[ winner ] = true;
				--ActiveCnt;
			}
		}
		if( ActiveCnt == 1 ) {
			for(; lastone < disabled.size(); ++lastone ) {
				if( !disabled[ lastone ] ) break;
			}
		}
	}
	cout << lastone << endl;
	boards[ lastone ].showMark();
	return 0;
}