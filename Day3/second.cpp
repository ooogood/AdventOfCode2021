
#include<bits\stdc++.h>
#include"yuandebug.h"
using namespace std;

int main() {
	fstream f( "Day3\\input.txt", fstream::in );
	if( !f ) {
		cout << "cant read file" << endl;
		return 0;
	}
	vector<string> v;
	while( !f.eof() ) {
		string str;
		getline( f, str );
		v.push_back( str );
	}
	vector<string> most(v);
	for( int i = 0; i < 12 && most.size() > 1; ++i ) {
		vector<string> tmp(most);
		most.clear();
		int count = 0;
		char mostBit = '1';
		for( string s : tmp ) {
			if( s[ i ] == '1' ) {
				++count;
			}
		}
		if( count < ( tmp.size() / 2.0 ) ) {
			mostBit = '0';
		}
		for( string s : tmp ) {
			if( s[ i ] == mostBit ) {
				most.push_back( s );
			}
		}
	}
	vector<string> least(v);
	for( int i = 0; i < 12 && least.size() > 1; ++i ) {
		vector<string> tmp(least);
		least.clear();
		int count = 0;
		char leastBit = '0';
		for( string s : tmp ) {
			if( s[ i ] == '1' ) {
				++count;
			}
		}
		if( count < ( tmp.size() / 2.0 ) ) {
			leastBit = '1';
		}
		for( string s : tmp ) {
			if( s[ i ] == leastBit ) {
				least.push_back( s );
			}
		}
	}
	cout << most.size() << endl;
	cout << most[ 0 ] << endl;
	cout << least.size() << endl;
	cout << least[ 0 ] << endl;
	f.close();
	return 0;
}