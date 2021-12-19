#include<bits\stdc++.h>
using namespace std;

int main ( ) {
	fstream f;
	f.open( "input.txt", ios::in );
	string s;
	int last = INT32_MAX;
	int cur;
	int ret = 0;
	while( !f.eof() ) {
		getline( f, s );
		cur = stod( s );
		if( last < cur ) 
			ret++;
		last = cur;
	}
	cout << ret;
	return 0;
}