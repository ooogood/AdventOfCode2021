#include<bits\stdc++.h>
using namespace std;

int main() {
	fstream f( "Day3\\input.txt", fstream::in );
	if( !f ) {
		cout << "cant read file" << endl;
		return 0;
	}
	vector<vector<int>> v( 12, vector<int>( 2, 0 ) );
	while( !f.eof() ) {
		string str;
		getline( f, str );
		for( int i = 0; i < 12; ++i ) {
			if( str[ i ] == '0' ) {
				v[ i ][ 0 ]++;
			}
			else {
				v[ i ][ 1 ]++;
			}
		}
	}
	int base = 1;
	int gamma = 0;
	int epsilon = 0;
	for( int i = 0; i < 12; ++i ) {
		if( v[ 11 - i ][ 0 ] < v[ 11 - i ][ 1 ] ) {
			gamma += base;
		}
		else if ( v[ 11 - i ][ 0 ] > v[ 11 - i ][ 1 ] ) {
			epsilon += base;
		}
		else {
			int gg = 0;
		}
		base += base;
	}
	cout << gamma * epsilon << endl;
	f.close();
	return 0;
}