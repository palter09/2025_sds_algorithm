#include <iostream>
#include <vector>
#include <queue>
using namespace std;

int V, E, start;
int INF = 99999999;
vector<vector<pair<int,int>>> edges;
vector<int> dist;

int main() {
	cin >> V >> E;
	cin >> start;
	edges.resize(V + 1);
	dist.assign(V + 1, INF);

	for (int i = 0; i < E; i++) {
		int s, e, c;
		cin >> s >> e >> c;
		edges[s].push_back({ e, c });
	}

	priority_queue<pair<int,int>> pq;
	pq.push({ start, 0 });
	dist[start] = 0;
	int cur, curW, next, nextW;

	while (!pq.empty()) {
		cur = pq.top().first;
		curW = pq.top().second;
		pq.pop();

		for (int i = 0; i < edges[cur].size(); i++) {
			next = edges[cur][i].first;
			nextW = edges[cur][i].second;

			if (dist[next] > curW + nextW) {
				dist[next] = curW + nextW;
				pq.push({ next, curW + nextW });
			}
		}
	}

	for (int i = 1; i <= V; i++) {
		if (dist[i] == INF)
			cout << "INF" << "\n";
		else
			cout << dist[i] << "\n";
	}

	return 0;
}
