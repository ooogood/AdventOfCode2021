#include<bits\stdc++.h>
using namespace std;

int main() {
	fstream f( "input.txt", ios::in );
	string str, num;
	int hor = 0, aim = 0, dep = 0;
	while( !f.eof() ) {
		f >> str;
		f >> num;
		if( str == "forward" ) {
			int tmp = stod( num );
			hor += tmp;
			dep += ( tmp * aim );
		}
		else if( str == "down" ) {
			aim += stod( num );
		}
		else if( str == "up" ) {
			aim -= stod( num );
		}
	}
	cout << hor * dep << endl;
	f.close();
}