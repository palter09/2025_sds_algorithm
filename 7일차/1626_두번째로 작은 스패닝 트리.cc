#include <bits/stdc++.h>
using namespace std;

const int LOGN = 17;

int V, E;
// MST 트리: 각 정점마다 (인접 정점, 간선 가중치)를 저장 (1-indexed)
vector<vector<array<int, 2>>> adj;
// 입력받은 전체 간선 리스트 (u, v, w)
vector<array<int, 3>> edgesList;
// MST에 포함되지 않은 간선들
vector<array<int, 3>> nonTreeEdges;

// Union-Find 관련 변수
vector<int> uf;
int findUF(int a) {
    if (uf[a] == a) return a;
    return uf[a] = findUF(uf[a]);
}
void unionUF(int a, int b) {
    int pa = findUF(a), pb = findUF(b);
    if (pa != pb)
        uf[pb] = pa;
}

// Binary Lifting 테이블
// parentTable[k][v]: v의 2^k번째 조상
vector<vector<int>> parentTable;
// maxEdgeTable[k][v]: v에서 2^k번째 조상까지 경로에서의 최대 간선 가중치
vector<vector<int>> maxEdgeTable;
// secondEdgeTable[k][v]: 최대 간선과 같지 않은 후보 중 두번째로 큰 값
vector<vector<int>> secondEdgeTable;

// 각 정점의 깊이 (1-indexed)
vector<int> depth;

// 최대값과 같지 않은 값 중 큰 값을 반환 (둘 다 같다면 -1 반환)
int largestNeq(int a, int b, int maxVal) {
    if (a == maxVal && b == maxVal) return -1;
    if (a == maxVal) return b;
    if (b == maxVal) return a;
    return max(a, b);
}

// LCA 쿼리: 정점 x와 y의 경로 상에서, 
// 간선의 가중치가 c와 같다면 두번째 후보값을 고려하여 최대 간선 가중치를 반환
int lcaQuery(int x, int y, int c) {
    int ret = -1;
    if (depth[x] > depth[y]) swap(x, y);
    // y의 깊이를 x와 맞춤
    for (int i = LOGN - 1; i >= 0; i--) {
        if (depth[y] - depth[x] >= (1 << i)) {
            int candidate = (maxEdgeTable[i][y] != c ? maxEdgeTable[i][y] : secondEdgeTable[i][y]);
            ret = max(ret, candidate);
            y = parentTable[i][y];
        }
    }
    if (x == y)
        return ret;
    for (int i = LOGN - 1; i >= 0; i--) {
        if (parentTable[i][x] != parentTable[i][y]) {
            int candidate1 = (maxEdgeTable[i][x] != c ? maxEdgeTable[i][x] : secondEdgeTable[i][x]);
            int candidate2 = (maxEdgeTable[i][y] != c ? maxEdgeTable[i][y] : secondEdgeTable[i][y]);
            ret = max(ret, candidate1);
            ret = max(ret, candidate2);
            x = parentTable[i][x];
            y = parentTable[i][y];
        }
    }
    int candidate1 = (maxEdgeTable[0][x] != c ? maxEdgeTable[0][x] : secondEdgeTable[0][x]);
    int candidate2 = (maxEdgeTable[0][y] != c ? maxEdgeTable[0][y] : secondEdgeTable[0][y]);
    ret = max(ret, candidate1);
    ret = max(ret, candidate2);
    return ret;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> V >> E;

    // 초기화 (1-indexed 사용)
    adj.resize(V + 1);
    uf.resize(V + 1);
    depth.assign(V + 1, 0);
    for (int i = 0; i <= V; i++) {
        uf[i] = i;
    }

    edgesList.resize(E);
    for (int i = 0; i < E; i++) {
        int u, v, w;
        cin >> u >> v >> w;
        edgesList[i] = { u, v, w };
    }

    // 가중치 기준 오름차순 정렬
    sort(edgesList.begin(), edgesList.end(), [](const array<int, 3>& a, const array<int, 3>& b) {
        return a[2] < b[2];
        });

    int cnt = 0;
    long long mstCost = 0;
    // 크루스칼 알고리즘으로 MST 구성
    for (auto& edge : edgesList) {
        int u = edge[0], v = edge[1], w = edge[2];
        if (findUF(u) != findUF(v)) {
            cnt++;
            mstCost += w;
            unionUF(u, v);
            // 양방향으로 추가
            adj[u].push_back({ v, w });
            adj[v].push_back({ u, w });
        }
        else {
            nonTreeEdges.push_back({ u, v, w });
        }
    }
    if (cnt != V - 1) {
        cout << -1 << "\n";
        return 0;
    }

    // Binary Lifting을 위한 테이블 초기화
    parentTable.assign(LOGN, vector<int>(V + 1, 0));
    maxEdgeTable.assign(LOGN, vector<int>(V + 1, 0));
    secondEdgeTable.assign(LOGN, vector<int>(V + 1, -1));

    // BFS를 통해 각 정점의 깊이와 바로 위의 부모, 해당 간선 가중치 초기화
    queue<int> q;
    depth[1] = 1;
    q.push(1);
    while (!q.empty()) {
        int cur = q.front();
        q.pop();
        for (auto& p : adj[cur]) {
            int nxt = p[0], w = p[1];
            if (depth[nxt] == 0) {
                depth[nxt] = depth[cur] + 1;
                parentTable[0][nxt] = cur;
                maxEdgeTable[0][nxt] = w;
                secondEdgeTable[0][nxt] = -1;
                q.push(nxt);
            }
        }
    }

    // Binary Lifting 테이블 채우기
    for (int i = 1; i < LOGN; i++) {
        for (int v = 1; v <= V; v++) {
            parentTable[i][v] = parentTable[i - 1][parentTable[i - 1][v]];
            maxEdgeTable[i][v] = max(maxEdgeTable[i - 1][v], maxEdgeTable[i - 1][parentTable[i - 1][v]]);
            int l1 = largestNeq(maxEdgeTable[i - 1][v], maxEdgeTable[i - 1][parentTable[i - 1][v]], maxEdgeTable[i][v]);
            int l2 = largestNeq(secondEdgeTable[i - 1][v], secondEdgeTable[i - 1][parentTable[i - 1][v]], maxEdgeTable[i][v]);
            secondEdgeTable[i][v] = max(l1, l2);
        }
    }

    long long ans = LLONG_MAX;
    // MST에 포함되지 않은 각 간선에 대해 MST 대체 가능성 탐색
    for (auto& edge : nonTreeEdges) {
        int u = edge[0], v = edge[1], w = edge[2];
        int maxCycleEdge = lcaQuery(u, v, w);
        if (maxCycleEdge < 0) continue;
        long long candidate = mstCost - maxCycleEdge + w;
        if (candidate > mstCost && candidate < ans)
            ans = candidate;
    }

    cout << (ans == LLONG_MAX ? -1 : ans) << "\n";
    return 0;
}
