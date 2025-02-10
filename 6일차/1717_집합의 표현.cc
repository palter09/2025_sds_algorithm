#include <iostream>
#include <vector>
using namespace std;

vector<int> p;
int n, m;

int find(int a) {
	if (a == p[a])
		return a;
	
	p[a] = find(p[a]);
	return p[a];
}

void uni(int a, int b){
	int X = find(a);
	int Y = find(b);
	
	if (X != Y) {
		p[Y] = X;
	}
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	
	cin >> n >> m;
	p.resize(n+1);
	
	for (int i=0; i<=n; i++) {
		p[i] = i;
	}
	
	for (int i=0; i<m; i++) {
		int num, a, b;
		cin >> num >> a >> b;
		
		if (num == 0)
			uni(a, b);
		
		else if (num == 1) {
			if (find(a) == find(b))
				cout << "YES" << "\n";
			else
				cout << "NO" << "\n";
		}
	}
	
	return 0;
}
