#include <bits/stdc++.h>
using namespace std;
 
// ==============================
// 자료구조 및 구조체 정의
// ==============================

// MST 구성 시 사용할 간선 정보
struct Edge {
    int u, v, w;
    bool used; // MST에 포함되었으면 true
};
 
// 이진 상승(바이너리 리프트) 전처리 시 사용하는 구조체
struct Info {
    int parent;   // 해당 노드의 2^k번째 조상
    int minEdge;  // (필요할 경우 – 여기서는 초기화 용)
    int maxEdge;  // 해당 구간에서 최대 간선 가중치
};
 
// ==============================
// 전역 변수
// ==============================
 
int V, E;  // 정점 수, 간선 수
vector<Edge> edgeList;  // 전체 간선 리스트
vector<vector<pair<int,int>>> mstTree; // MST에 포함된 간선들로 구성한 트리 (각 노드: {인접 노드, 간선 가중치})
 
// ==============================
// 표준 DSU (Union-Find): MST 구성용
// ==============================
 
struct DSU {
    vector<int> parent;
    DSU(int n) : parent(n+1) {
        for (int i = 0; i <= n; i++) parent[i] = i;
    }
    int find(int a) {
        if(parent[a]==a) return a;
        return parent[a] = find(parent[a]);
    }
    bool merge(int a, int b) {
        a = find(a); b = find(b);
        if(a==b) return false;
        parent[b] = a;
        return true;
    }
};
 
// ==============================
// LCA 및 이진 상승 전처리 (MST 트리용)
// ==============================
 
int LOG; // 이진 상승 테이블의 레벨 수
vector<int> depth; // 각 노드의 깊이 (루트의 깊이는 0)
vector<vector<Info>> par; // par[k][v]: v의 2^k번째 조상 정보
 
// -- BFS를 이용하여 각 노드의 깊이와 바로 위 부모(레벨 0)를 계산
void bfs(int root) {
    queue<int> q;
    depth[root] = 0;
    q.push(root);
    // 루트의 부모는 자기 자신으로 처리하고 간선 가중치는 0으로 설정
    par[0][root].parent = root;
    par[0][root].maxEdge = 0;
    par[0][root].minEdge = INT_MAX;
    while(!q.empty()){
        int cur = q.front();
        q.pop();
        for(auto &p : mstTree[cur]){
            int nxt = p.first;
            int w = p.second;
            if(depth[nxt] == -1){
                depth[nxt] = depth[cur] + 1;
                par[0][nxt].parent = cur;
                par[0][nxt].maxEdge = w; // cur->nxt 간선 가중치
                par[0][nxt].minEdge = w;
                q.push(nxt);
            }
        }
    }
}
 
// -- 이진 상승 테이블을 완성 (par 배열 채우기)
void buildLCA() {
    for (int i = 1; i < LOG; i++){
        for (int v = 1; v <= V; v++){
            int mid = par[i-1][v].parent;
            par[i][v].parent = par[i-1][mid].parent;
            par[i][v].maxEdge = max(par[i-1][v].maxEdge, par[i-1][mid].maxEdge);
            par[i][v].minEdge = min(par[i-1][v].minEdge, par[i-1][mid].minEdge);
        }
    }
}
 
// -- 두 노드 u와 v 사이의 경로에서 최대 간선 가중치를 구하는 함수
int queryMax(int u, int v) {
    int ret = 0;
    if(depth[u] < depth[v]) swap(u, v);
    int diff = depth[u] - depth[v];
    for (int i = 0; diff; i++){
        if(diff & 1) {
            ret = max(ret, par[i][u].maxEdge);
            u = par[i][u].parent;
        }
        diff >>= 1;
    }
    if(u == v) return ret;
    for (int i = LOG - 1; i >= 0; i--){
        if(par[i][u].parent != par[i][v].parent){
            ret = max(ret, par[i][u].maxEdge);
            ret = max(ret, par[i][v].maxEdge);
            u = par[i][u].parent;
            v = par[i][v].parent;
        }
    }
    ret = max(ret, par[0][u].maxEdge);
    ret = max(ret, par[0][v].maxEdge);
    return ret;
}
 
// ====================================================================
// (참고) 아래 find, uni 함수는 “가중치 차이를 관리하는 DSU”용인데,
// 본 문제 MST 구성에는 표준 DSU를 사용하므로 여기서는 사용하지 않습니다.
/*
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
    p[rootB].first = rootA;
    p[rootB].second = p[a].second - p[b].second + w;
}
*/
// ====================================================================
 
// ==============================
// 메인 함수
// ==============================
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    cin >> V >> E;
    edgeList.resize(E);
    for (int i = 0; i < E; i++){
        int u, v, w;
        cin >> u >> v >> w;
        edgeList[i] = {u, v, w, false};
    }
 
    // --- 1. Kruskal 알고리즘으로 MST 구성
    sort(edgeList.begin(), edgeList.end(), [](const Edge &a, const Edge &b){
        return a.w < b.w;
    });
    DSU dsu(V);
    long long mstCost = 0;
    int cnt = 0;
    mstTree.assign(V+1, vector<pair<int,int>>());
    for(auto &edge : edgeList){
        if(dsu.merge(edge.u, edge.v)){
            edge.used = true;
            mstCost += edge.w;
            cnt++;
            // 양방향 MST 트리 구성
            mstTree[edge.u].push_back({edge.v, edge.w});
            mstTree[edge.v].push_back({edge.u, edge.w});
        }
    }
    // MST가 만들어지지 않으면 -1 출력
    if(cnt != V - 1){
        cout << -1;
        return 0;
    }
 
    // --- 2. MST 트리에 대해 LCA 전처리
    depth.assign(V+1, -1);
    LOG = ceil(log2(V)) + 1;
    par.assign(LOG, vector<Info>(V+1, {-1, INT_MAX, 0}));
    bfs(1);         // 임의의 루트(1)에서 BFS 실행
    buildLCA();
 
    // --- 3. MST에 포함되지 않은 간선을 후보로 고려하여
    //      “두 번째로 작은 스패닝 트리” 비용을 구한다.
    //      (간선을 추가하면 사이클이 생기므로, 그 사이클에서 최대 가중치 간선을 제거)
    long long ans = LLONG_MAX;
    for(auto &edge : edgeList){
        if(edge.used) continue; // 이미 MST에 쓰인 간선은 제외
        int u = edge.u, v = edge.v, w = edge.w;
        int maxEdgeOnPath = queryMax(u, v);
        // 새로운 스패닝 트리 비용
        long long candidate = mstCost - maxEdgeOnPath + w;
        // 두 번째 스패닝 트리는 MST와 다른 (비용이 같거나 큰) 트리여야 함
        if(candidate >= mstCost && candidate < ans)
            ans = candidate;
    }
 
    cout << (ans == LLONG_MAX ? -1 : ans);
    return 0;
}