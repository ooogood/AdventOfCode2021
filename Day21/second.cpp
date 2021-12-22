#include<bits\stdc++.h>
using namespace std;

long long wins[ 2 ] = { 0 };

void turn( short p1Score, short p2Score, short p1Pos, short p2Pos, short RemainRollCnt, bool p1Turn, long long universeCnt ) {
	// finish rolling three times, change player
	// the positions will be moduloed when roll count == 0
	if( RemainRollCnt == 0 ) {
		if( p1Turn ) {
			p1Pos = p1Pos % 10;
			if( p1Pos == 0 ) p1Pos = 10;
			p1Score += p1Pos;
			if( p1Score >= 21 ) {
				wins[ 0 ] += universeCnt;
				return;
			}
		}
		else {
			p2Pos = p2Pos % 10;
			if( p2Pos == 0 ) p2Pos = 10;
			p2Score += p2Pos;
			if( p2Score >= 21 ) {
				wins[ 1 ] += universeCnt;
				return;
			}
		}
		turn( p1Score, p2Score, p1Pos, p2Pos, 3, !p1Turn, universeCnt );
	}
	else {
		RemainRollCnt = 0;
		if( p1Turn ) {
			turn( p1Score, p2Score, p1Pos + 3, p2Pos, RemainRollCnt, p1Turn, universeCnt );
			turn( p1Score, p2Score, p1Pos + 4, p2Pos, RemainRollCnt, p1Turn, universeCnt * 3 );
			turn( p1Score, p2Score, p1Pos + 5, p2Pos, RemainRollCnt, p1Turn, universeCnt * 6 );
			turn( p1Score, p2Score, p1Pos + 6, p2Pos, RemainRollCnt, p1Turn, universeCnt * 7 );
			turn( p1Score, p2Score, p1Pos + 7, p2Pos, RemainRollCnt, p1Turn, universeCnt * 6 );
			turn( p1Score, p2Score, p1Pos + 8, p2Pos, RemainRollCnt, p1Turn, universeCnt * 3 );
			turn( p1Score, p2Score, p1Pos + 9, p2Pos, RemainRollCnt, p1Turn, universeCnt );
		}
		else {
			turn( p1Score, p2Score, p1Pos, p2Pos + 3, RemainRollCnt, p1Turn, universeCnt );
			turn( p1Score, p2Score, p1Pos, p2Pos + 4, RemainRollCnt, p1Turn, universeCnt * 3 );
			turn( p1Score, p2Score, p1Pos, p2Pos + 5, RemainRollCnt, p1Turn, universeCnt * 6 );
			turn( p1Score, p2Score, p1Pos, p2Pos + 6, RemainRollCnt, p1Turn, universeCnt * 7 );
			turn( p1Score, p2Score, p1Pos, p2Pos + 7, RemainRollCnt, p1Turn, universeCnt * 6 );
			turn( p1Score, p2Score, p1Pos, p2Pos + 8, RemainRollCnt, p1Turn, universeCnt * 3 );
			turn( p1Score, p2Score, p1Pos, p2Pos + 9, RemainRollCnt, p1Turn, universeCnt );
		}
	}
}
int main() {
	turn( 0, 0, 8, 6, 3, true, 1 );
	cout << wins[ 0 ] << endl;
	cout << wins[ 1 ] << endl;
}