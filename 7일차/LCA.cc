#include <iostream>
#include <vector>
#include <queue>
using namespace std;

const int MAX_K = 10; // 2^9 까지 사용

int N;
vector<vector<int>> parent;  // parent[k][i] : i의 2^k번째 부모
vector<int> depth;           // 각 노드의 깊이
vector<vector<int>> edges;   // 트리의 인접 리스트

// 두 노드의 LCA(최소 공통 조상)를 반환하는 함수
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

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> N;
    parent.assign(MAX_K, vector<int>(N + 1, 0));
    depth.assign(N + 1, 0);
    edges.assign(N + 1, vector<int>());

    // 각 노드의 부모를 입력받고, 자식 리스트를 구성한다.
    // 입력 예시:
    // 13
    // 0
    // 1
    // 1
    // 1
    // 2
    // 2
    // 4
    // 4
    // 4
    // 5
    // 5
    // 8
    // 8
    for (int i = 1; i <= N; i++) {
        int p;
        cin >> p;
        parent[0][i] = p;
        // p가 0인 경우는 루트(또는 dummy)로 가정하고, 여기서는 1번 노드를 루트로 사용
        // (예제에서는 1번 노드가 루트이므로, 1번 노드의 부모는 0이다.)
        edges[p].push_back(i);
    }

    // BFS를 이용해 모든 노드의 depth를 계산한다.
    queue<int> q;
    q.push(1);  // 1번 노드를 루트로 가정
    while (!q.empty()){
        int cur = q.front();
        q.pop();
        for (int next : edges[cur]) {
            depth[next] = depth[cur] + 1;
            q.push(next);
        }
    }

    // 2^k번째 부모를 계산하는 DP 테이블을 만든다.
    for (int k = 1; k < MAX_K; k++) {
        for (int i = 1; i <= N; i++) {
            // parent[k][i]는 parent[k-1]의 부모
            parent[k][i] = parent[k - 1][ parent[k - 1][i] ];
        }
    }

    cout << lca(6, 3) << "\n";
    cout << lca(7, 13) << "\n";

    return 0;
}