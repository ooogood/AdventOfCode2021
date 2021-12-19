#include<bits\stdc++.h>
using namespace std;

int main ( ) {
	fstream f;
	f.open( "input.txt", ios::in );
	string s;
	deque<int> vec;
	for( int i = 0; i < 3; ++i ) {
		getline( f, s );
		vec.push_back( stod( s ));
	}
	int ret = 0;
	while( !f.eof() ) {
		getline( f, s );
		vec.push_back( stod( s ) );
		if( vec.back() > vec.front() ) 
			ret++;
		vec.pop_front();
	}
	cout << ret;
	return 0;
}