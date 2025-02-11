#include <bits/stdc++.h>
using namespace std;
 
// 간선 정보 구조체
struct Edge {
    int u, v, w;
    bool used; // MST에 포함되었으면 true
};
 
// 이진 상승(바이너리 리프트) 정보 구조체
struct Info {
    int parent;   // 2^k번째 조상
    int minEdge;  // (여기서는 초기화 용)
    int maxEdge;  // 해당 구간의 최대 간선 가중치
};
 
int V, E;
vector<Edge> edgeList;
vector<vector<pair<int,int>>> mstTree;
 
// 표준 DSU
struct DSU {
    vector<int> parent;
    DSU(int n) : parent(n+1) {
        for (int i = 0; i <= n; i++) parent[i] = i;
    }
    int find(int a) {
        if(parent[a] == a) return a;
        return parent[a] = find(parent[a]);
    }
    bool merge(int a, int b) {
        a = find(a); b = find(b);
        if(a == b) return false;
        parent[b] = a;
        return true;
    }
};
 
// LCA 관련 전역 변수
int LOG;
vector<int> depth;
vector<vector<Info>> par;
 
// BFS로 MST 트리의 깊이와 바로 위 부모(레벨 0) 채우기
void bfs(int root) {
    queue<int> q;
    depth[root] = 0;
    q.push(root);
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
                par[0][nxt].maxEdge = w;
                par[0][nxt].minEdge = w;
                q.push(nxt);
            }
        }
    }
}
 
// 이진 상승 테이블 구성
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
 
// u와 v 사이 경로 상의 최대 간선 가중치 (수정된 queryMax)
int queryMax(int u, int v) {
    int ret = 0;
    if(depth[u] < depth[v]) swap(u, v);
    int diff = depth[u] - depth[v];
    for (int i = 0; i < LOG; i++) {
        if(diff & (1 << i)){
            ret = max(ret, par[i][u].maxEdge);
            u = par[i][u].parent;
        }
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
 
    // 1. Kruskal을 사용하여 MST 구성
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
            mstTree[edge.u].push_back({edge.v, edge.w});
            mstTree[edge.v].push_back({edge.u, edge.w});
        }
    }
    if(cnt != V - 1){
        cout << -1;
        return 0;
    }
 
    // 2. MST 트리에 대해 LCA 전처리
    depth.assign(V+1, -1);
    LOG = ceil(log2(V)) + 1;
    par.assign(LOG, vector<Info>(V+1, {-1, INT_MAX, 0}));
    bfs(1);
    buildLCA();
 
    // 3. MST에 속하지 않은 간선을 후보로 두 번째 스패닝 트리 비용 계산
    long long ans = LLONG_MAX;
    for(auto &edge : edgeList){
        if(edge.used) continue;
        int u = edge.u, v = edge.v, w = edge.w;
        int maxEdgeOnPath = queryMax(u, v);
        long long candidate = mstCost - maxEdgeOnPath + w;
        if(candidate >= mstCost && candidate < ans)
            ans = candidate;
    }
 
    cout << (ans == LLONG_MAX ? -1 : ans);
    return 0;
}