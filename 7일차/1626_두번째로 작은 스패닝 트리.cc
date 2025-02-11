/*
입력
첫째 줄에 그래프의 정점의 수 V(1 ≤ V ≤ 50,000)와 간선의 수 E(1 ≤ E ≤ 200,000)가 들어온다. 둘째 줄부터 E+1번째 줄까지 한 간선으로 연결된 두 정점과 그 간선의 가중치가 주어진다. 가중치는 100,000보다 작거나 같은 자연수 또는 0이고, 답은 231-1을 넘지 않는다.

정점 번호는 1보다 크거나 같고, V보다 작거나 같은 자연수이다.
*/

/*
출력
두 번째로 작은 스패닝 트리의 값을 출력한다. 만약 스패닝 트리나 두 번째로 작은 스패닝 트리가 존재하지 않는다면 -1을 출력한다.
*/


#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
using namespace std;

// 이진 상승을 위한 정보를 저장할 구조체
struct Info {
	int parent;   // 해당 노드의 2^k번째 부모
	int weight;
};

int N;
vector<vector<pair<int, int>>> edges;  // 각 노드의 인접 리스트: {인접 노드, 간선 길이}
vector<int> depth;                      // 각 노드의 깊이 (루트는 0)
vector<vector<Info>> par;               // par[k][v]: 노드 v의 2^k번째 부모 및 구간 정보

// BFS를 이용하여 각 노드의 깊이와 바로 위 부모(레벨 0)를 계산합니다.
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

// 두 노드의 최소 공통 조상(LCA)를 찾는 함수
int lca(int x, int y) {
	// x가 더 얕은 노드가 되도록 swap
	if (depth[x] > depth[y])
		swap(x, y);

	// y의 깊이를 x와 동일하게 맞춤
	for (int i = MAX - 1; i >= 0; i--) {
		if (depth[y] - depth[x] >= (1 << i))
			y = parent[i][y];
	}

	if (x == y)
		return x;

	// 두 노드가 같아질 때까지 조상을 올려보냄
	for (int i = MAX - 1; i >= 0; i--) {
		if (parent[i][x] != parent[i][y]) {
			x = parent[i][x];
			y = parent[i][y];
		}
	}

	return parent[0][x];
}

// find 함수: a의 루트를 찾으면서, 경로 상의 차이(누적 가중치)를 갱신합니다.
int find(int a) {
	if (a == p[a].first) return a;
	int par = p[a].first;
	p[a].first = find(par);
	p[a].second += p[par].second;
	return p[a].first;
}

// uni 함수: 두 노드 a, b가 주어지고, "weight[b] - weight[a] = w" 관계가 주어질 때 두 집합을 합칩니다.
void uni(int a, int b, int w) {
	int rootA = find(a);
	int rootB = find(b);

	if (rootA == rootB) return;
	// a와 b가 다른 집합에 있다면, b의 루트를 a의 루트에 연결합니다.
	// 이때, b의 루트와 a의 루트 사이의 누적 차이를 맞춰줍니다.
	p[rootB].first = rootA;
	p[rootB].second = p[a].second - p[b].second + w;
}
