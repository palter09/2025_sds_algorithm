#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int n, m, S, T;
vector<vector<int>> adj;
vector<vector<int>> adjR;
vector<int> fromS;
vector<int> fromT;
vector<int> toS;
vector<int> toT;

void dfs(int now, vector<vector<int>>& adj, vector<int>& visit);

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> n >> m;
	adj.resize(n + 1);
	adjR.resize(n + 1);

	for (int i = 0; i < m; i++) {
		int x, y;

		cin >> x >> y;
		adj[x].push_back(y);
		adjR[y].push_back(x);
	}

	cin >> S >> T;

	fromS.resize(n + 1);
	fromS[T] = 1; // 출근길에 T를 만나면 멈춤
	dfs(S, adj, fromS); // S에서 나가는 간선 확인

	fromT.resize(n + 1);
	fromT[S] = 1; // 퇴근길에 S를 만나면 멈춤
	dfs(T, adj, fromT); // T에서 나가는 간선 확인

	toS.resize(n + 1);
	dfs(S, adjR, toS); // S로 들어오는 간선 확인

	toT.resize(n + 1);
	dfs(T, adjR, toT); // T로 들어오는 간선 확인

	int count = 0;
	for (int i = 1; i < n + 1; i++) {
		if (fromS[i] && fromT[i] && toS[i] && toT[i]) {
			count += 1;
		}
	}
	cout << count-2; // S와 T를 포함해서 세기 때문에 -2

	return 0;
}

void dfs(int now, vector<vector<int>>& adj, vector<int>& visit) {
	if (visit[now] == 1) {
		return;
	}

	visit[now] = 1;
	for (auto neighbor : adj[now]) {
		dfs(neighbor, adj, visit);
	}
	return;
}
