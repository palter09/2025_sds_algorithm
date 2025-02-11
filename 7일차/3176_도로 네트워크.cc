#include <iostream>
#include <vector>
#include <queue>
using namespace std;

const int MAX_K = 20; // 2^9 까지 사용

int N;
vector<vector<pair<int,int>>> parent;  // parent[k][i] : i의 2^k번째 부모
vector<int> depth;           // 각 노드의 깊이
vector<vector<pair<int,int>>> edges;   // 트리의 인접 리스트

// 두 노드의 LCA(최소 공통 조상)를 반환하는 함수
pair<int> lca(int a, int b) {
	// 항상 a의 depth가 b보다 작거나 같게 유지 (즉, b가 더 깊다고 가정)
	if (depth[a] > depth[b])
		swap(a, b);

	int min = 1000000;
	int max = 0;

	// 깊이 차이를 맞추기 위해 b를 올린다.
	for (int k = MAX_K - 1; k >= 0; k--) {
		if (depth[b] - depth[a] >= (1 << k)) {
			max = max(max, parent[k][b].second);
			min = min(min, parent[k][b].second)
			b = parent[k][b].first;
		}
	}
	if (a == b)
		return max;

	// 두 노드의 공통 조상이 될 때까지 동시에 올린다.
	for (int k = MAX_K - 1; k >= 0; k--) {
		if (parent[k][a].first != parent[k][b].first) {
			max = max(max, parent[k][a].second);
			max = max(max, parent[k][b].second);
			min = min(min, parent[k][a].second);
			min = min(min, parent[k][b].second);
			a = parent[k][a].first;
			b = parent[k][b].first;
		}
	}
	return { min, max };
}

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> N;
	// 인접 리스트, 깊이, parent 배열 초기화
	edges.resize(N + 1);
	depth.assign(N + 1, 0);
	parent.assign(MAX, vector<int>(N + 1, 0));

	// 트리의 간선 입력 (N-1개)
	for (int i = 0; i < N - 1; i++) {
		int a, b, len;
		cin >> a >> b >> len;
		edges[a].push_back({ b, len });
		edges[b].push_back({ a, len });
	}

	// BFS를 이용하여 각 노드의 깊이와 바로 위의 부모를 설정
	queue<int> q;
	q.push(1);
	depth[1] = 1;  // 루트의 깊이를 1로 설정
	while (!q.empty()) {
		int cur = q.front();
		q.pop();
		for (pair<int> nxt : edges[cur]) {
			if (depth[nxt.first] == 0) {
				depth[nxt.first] = depth[cur] + 1;
				parent[0][nxt.first].second = nxt.second;
				parent[0][nxt.first].first = cur;
				q.push(nxt);
			}
		}
	}

	// 이진 상승 기법(Binary Lifting)을 위한 전처리: 
	// 각 노드의 2^i번째 부모 정보를 저장
	for (int i = 1; i < MAX; i++) {
		for (int j = 1; j <= N; j++) {
			parent[i][j].first = parent[i - 1][parent[i - 1][j].first].first;
		}
	}

	// 쿼리 처리
	cin >> M;
	while (M--) {
		int a, b;
		cin >> a >> b;
		cout << lca(a, b) << "\n";
	}

	return 0;
}
