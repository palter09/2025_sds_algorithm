#include <iostream>
using namespace std;

int gcd(int a, int b) {
	while(b!=0) {
		int r = a%b;
		a = b;
		b = r;
	}
	return a;
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	
	int a, b, c, d;
	cin >> a >> b >> c >> d;
	
	// cout << a << b << c << d;
	
	int dem, num;
	num = a * d + b * c;
	dem = b * d;
	
	int comm;
	comm = gcd(num, dem);
	
	num = num / comm;
	dem = dem / comm;
	
	 cout << num << " " << dem;
	
	return 0;
}
