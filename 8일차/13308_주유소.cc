#include <iostream>
#include <vector>
#include <queue>
using namespace std;

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	int N, M;
	cin >> N >> M;

	vector<vector<pair<int,int>>> edges(N + 1);
	vector<priority_queue<int>> dist(N + 1);
	priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;

	vector<int> cost(N + 1);
	for (int i = 1; i <= N; i++) {
		cin >> cost[i];
	}

	for (int i = 0; i < M; i++) {
		int a, b, w;
		cin >> a >> b >> w;

		edges[a].push_back({ b, w });
	}

	dist[1].push(2501);
	pq.push({ 0, 1 });

	while (!pq.empty()) {
		int d = pq.top().first; // 현재 최단 거리
		int cur = pq.top().second; // 현재 위치
		pq.pop();

		for (auto &edge : edges[cur]) {
			int next = edge.first; // 다음 위치
			int nextW = d + edge.second * cost[cur]; // 다음 거리

			if (dist[next].top() > nextW) {
				dist[next].pop();
				dist[next].push(nextW);
				pq.push({ nextW, next });
			}
		}
	}

	cout << dist[N].top();

	return 0;
}
