#include <iostream>
#include <vector>
using namespace std;

vector<long long> d(90);

// top down : 기존 상태를 알수있다 
long long fi(int N) {
	if (N==0) {
		return 0;
	}
	
	if (N==1) {
		return 1;
	}
	
	if (d[N] != 0) {
		return d[N];
	}
	d[N] = fi(N-1) + fi(N-2);
	return d[N];
}

int main() {
	int n;
	cin >> n;
	
	// bottom up : 시간이 빠르다 
	//d[0] = 0;
	//d[1] = 1;
	//for (int i=2; i<=n; i++) {
	//	d[i] = d[i-2] + d[i-1];
	//}
	
	cout << fi(n);
	return 0;
}
