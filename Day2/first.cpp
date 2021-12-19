#include<bits\stdc++.h>
using namespace std;

int main() {
	fstream f( "input.txt", ios::in );
	string str, num;
	int hor = 0, ver = 0;
	while( !f.eof() ) {
		f >> str;
		f >> num;
		if( str == "forward" ) {
			hor += stod( num );
		}
		else if( str == "down" ) {
			ver += stod( num );
		}
		else if( str == "up" ) {
			ver -= stod( num );
		}
		else {
			int gg = 0;
		}
		// if( ver < 0 ) ver = 0;
	}
	cout << hor * ver << endl;
	f.close();
}