#include <iostream>
#include <vector>
#include <queue>
using namespace std;

const int MAX_K = 20;

struct Info {
	int parent;
	int weight;
};

int V, E;
vector<pair<int, int>> p;
vector<vector<pair<int, int>>> edges;
vector<int> depth;
vector<vector<Info>> par;

int find(int a) {
	if (a == p[a].first) return a;
	int par = p[a].first;
	p[a].first = find(par);
	p[a].second += p[par].second;
	return p[a].first;
}

void uni(int a, int b, int w) {
	int rootA = find(a);
	int rootB = find(b);

	if (rootA == rootB) return;
	// a와 b가 다른 집합에 있다면, b의 루트를 a의 루트에 연결
	// 이때, b의 루트와 a의 루트 사이의 누적 차이를 맞춤
	p[rootB].first = rootA;
	p[rootB].second = p[a].second - p[b].second + w;
}

int lca(int a, int b) {
	// 항상 a의 depth가 b보다 작거나 같게 유지 (즉, b가 더 깊다고 가정)
	if (depth[a] > depth[b])
		swap(a, b);

	// 깊이 차이를 맞추기 위해 b를 올린다.
	for (int k = MAX_K - 1; k >= 0; k--) {
		if (depth[b] - depth[a] >= (1 << k)) {
			b = parent[k][b];
		}
	}
	if (a == b)
		return a;

	// 두 노드의 공통 조상이 될 때까지 동시에 올린다.
	for (int k = MAX_K - 1; k >= 0; k--) {
		if (parent[k][a] != parent[k][b]) {
			a = parent[k][a];
			b = parent[k][b];
		}
	}
	return parent[0][a];
}

void bfs(int root) {
	queue<int> q;
	depth[root] = 0;
	q.push(root);
	while (!q.empty()) {
		int cur = q.front();
		q.pop();
		for (auto &edge : edges[cur]) {
			int nxt = edge.first;
			int w = edge.second;
			if (depth[nxt] == -1) {  // 아직 방문하지 않았다면
				depth[nxt] = depth[cur] + 1;
				par[0][nxt].parent = cur;
				par[0][nxt].minEdge = w;
				par[0][nxt].maxEdge = w;
				q.push(nxt);
			}
		}
	}
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> V >> E;
	p.assign(V + 1, { 0, 0 });
	edges.resize(N + 1);
	depth.assign(N + 1, -1);
	par.assign(MAX_K, vector<Info>(V + 1, { 0, 0 }));

	for (int i = 1; i <= V; i++) {
		p[i].first = i;
		p[i].second = 0;
	}

	for (int i = 0; i < E; i++) {
		int a, b, w;
		cin >> a >> b >> w;

		edges[a].push_back({ b, w });
		edges[b].push_back({ a, w });

		uni(a, b, w);
	}



	return 0;
}
