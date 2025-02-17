#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int n, m, cnt = 0;
int grid[4][4];
bool visit[4][4];
vector<pair<int,int>> dest(16);

int dx[] = { 0, 0, 1, -1 };
int dy[] = { 1, -1, 0, 0 };

void dfs(pair<int, int> now, int destIdx);

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;

	for (int i = 0; i < n; i++) {
		for (int j = 0; j < n; j++) {
			cin >> grid[i][j];
			visit[i][j] = false;
		}
	}
	int x, y;
	for (int i = 0; i < m; i++) {
		cin >> x >> y;
		dest[i] = { x - 1, y - 1 };
	}

	dfs(dest[0], 1);
	cout << cnt;

	return 0;
}

void dfs(pair<int, int> now, int destIdx) {
	if (now == dest[destIdx]) { // 도착한 경우
		if (destIdx == m - 1) { // 맨 마지막이면 cnt++하고 return
			cnt++;
			return;
		}
		else {
			destIdx++;
		}
	}
	int x = now.first, y = now.second;
	visit[x][y] = true;
	for (int d = 0; d < 4; d++) {
		int nx = x + dx[d], ny = y + dy[d];

		if (nx < 0 || ny < 0 || n <= nx || n <= ny) continue; // nx, ny 범위 확인

		if (visit[nx][ny] || grid[nx][ny] != 0) continue; // 이미 방문한 곳이거나 벽인지 확인

		dfs({ nx, ny }, destIdx);
	}

	visit[x][y] = false;
	return;
}
