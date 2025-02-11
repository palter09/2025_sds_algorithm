#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

vector<vector<int>> d;


int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	int N, K, M;
	cin >> N >> K >> M;

	vector<int> start(N);
	d.resize(4, vector<int>(K+1, 0));

	for (int i = 0; i < N; i++) {
		cin >> start[i];
	}

	for (int i = 1; i <= K; i++) {
		d[0][i] = i;
	}

	for (int i = 1; i <= K; i++) {
		cin >> d[1][i];
	}

	for (int i = 2; i <= M; i++) {
		for (int j = 1; j <= K; j++) {
			d[2][j] = d[1][d[0][j]];
		}
	}

	for (int i = 0; i < N; i++) {
		cout << d[2][start[i]] << " ";
	}

	return 0;
}
