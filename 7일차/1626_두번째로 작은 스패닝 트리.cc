#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
using namespace std;
 
// 상수
const int MAX_K = 20;
const int INF = 1000000000;
 
// LCA (binary lifting)에서 각 노드에 대한 정보를 저장할 구조체
struct Info {
    int parent;   // 2^k번째 조상
    int minEdge;  // 현재 구간에서의 최소 간선 가중치
    int maxEdge;  // 현재 구간에서의 최대 간선 가중치
};
 
// 전역 변수
int V, E, Q;
 
// weighted union‐find (연결 여부 확인용)
// uf[i] = { parent, diff } : i에서 부모까지의 누적 차이 (여기서는 연결 여부만 사용)
vector<pair<int,int>> uf;
 
// 입력 그래프 (양방향)
vector<vector<pair<int,int>>> edges;
 
// 각 노드의 깊이 (BFS에서 결정)
vector<int> depth;
 
// binary lifting 테이블 : par[k][i]는 i의 2^k번째 조상에 관한 정보
vector<vector<Info>> par;
 
// weighted union‐find – find 함수
int findUF(int a) {
    if(a == uf[a].first) return a;
    int p = uf[a].first;
    uf[a].first = findUF(p);
    uf[a].second += uf[p].second;
    return uf[a].first;
}
 
// weighted union‐find – union 함수 (두 정점을 같은 집합으로 합침)
void unionUF(int a, int b, int w) {
    int ra = findUF(a), rb = findUF(b);
    if(ra == rb) return;
    // rb를 ra의 아래에 붙인다.
    uf[rb].first = ra;
    uf[rb].second = uf[a].second - uf[b].second + w;
}
 
// BFS를 통해 각 연결 컴포넌트마다 스패닝 트리를 구성하고,
// 각 노드의 깊이와 par[0] (직계 부모와 해당 간선 가중치)를 채운다.
void bfs(int start) {
    queue<int> q;
    depth[start] = 0;
    // 루트는 자기 자신을 부모로 하고, 경로상 간선이 없으므로 min은 INF, max는 -1로 설정
    par[0][start].parent = start;
    par[0][start].minEdge = INF;
    par[0][start].maxEdge = -1;
    q.push(start);
    while(!q.empty()){
        int cur = q.front();
        q.pop();
        for(auto &edge : edges[cur]){
            int nxt = edge.first;
            int w = edge.second;
            if(depth[nxt] == -1){
                depth[nxt] = depth[cur] + 1;
                par[0][nxt].parent = cur;
                par[0][nxt].minEdge = w;
                par[0][nxt].maxEdge = w;
                q.push(nxt);
            }
        }
    }
}
 
// binary lifting 테이블 구성 (LCA 전처리)
void buildLCA() {
    for (int k = 1; k < MAX_K; k++) {
        for (int i = 1; i <= V; i++) {
            int mid = par[k-1][i].parent;
            par[k][i].parent = par[k-1][mid].parent;
            par[k][i].minEdge = min(par[k-1][i].minEdge, par[k-1][mid].minEdge);
            par[k][i].maxEdge = max(par[k-1][i].maxEdge, par[k-1][mid].maxEdge);
        }
    }
}
 
// u와 v 사이의 경로 상에서 최소/최대 간선 가중치를 구하는 함수
pair<int,int> queryPath(int u, int v) {
    // 만약 같은 정점이면 경로에 간선이 없으므로 0, 0 출력 (문제에 따라 처리)
    if(u == v) return {0, 0};
    
    int minVal = INF, maxVal = -1;
    // u가 더 깊은 노드가 되도록 swap
    if(depth[u] < depth[v]) swap(u, v);
 
    // 깊이 차이만큼 u를 위로 올린다.
    int diff = depth[u] - depth[v];
    for (int k = 0; diff; k++) {
        if(diff & 1) {
            minVal = min(minVal, par[k][u].minEdge);
            maxVal = max(maxVal, par[k][u].maxEdge);
            u = par[k][u].parent;
        }
        diff >>= 1;
    }
    if(u == v)
        return {minVal, maxVal};
 
    // u와 v가 같아지기 전까지 동시에 위로 올린다.
    for (int k = MAX_K - 1; k >= 0; k--) {
        if(par[k][u].parent != par[k][v].parent) {
            minVal = min(minVal, min(par[k][u].minEdge, par[k][v].minEdge));
            maxVal = max(maxVal, max(par[k][u].maxEdge, par[k][v].maxEdge));
            u = par[k][u].parent;
            v = par[k][v].parent;
        }
    }
    // 마지막으로 한 단계 더 올려 LCA에 도달
    minVal = min(minVal, min(par[0][u].minEdge, par[0][v].minEdge));
    maxVal = max(maxVal, max(par[0][u].maxEdge, par[0][v].maxEdge));
    return {minVal, maxVal};
}
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    cin >> V >> E;
 
    // 1번부터 V번까지 union‐find 초기화
    uf.resize(V+1);
    for (int i = 1; i <= V; i++){
        uf[i] = {i, 0}; // 부모는 자기 자신, 누적 차이는 0
    }
 
    edges.resize(V+1);
    depth.assign(V+1, -1);
    // par 테이블 초기화 – 기본값: parent=0, minEdge=INF, maxEdge=-1
    par.assign(MAX_K, vector<Info>(V+1, {0, INF, -1}));
 
    // 간선 입력 (양방향)
    for (int i = 0; i < E; i++){
        int a, b, w;
        cin >> a >> b >> w;
        edges[a].push_back({b, w});
        edges[b].push_back({a, w});
        // union‐find로 두 정점을 같은 집합에 합침
        unionUF(a, b, w);
    }
 
    // 각 연결 컴포넌트마다 BFS를 돌려 스패닝 트리 구성
    for (int i = 1; i <= V; i++){
        if(depth[i] == -1){
            bfs(i);
        }
    }
 
    // binary lifting (LCA) 테이블 전처리
    buildLCA();
 
    // 쿼리 처리
    cin >> Q;
    for (int i = 0; i < Q; i++){
        int u, v;
        cin >> u >> v;
        // union‐find로 두 정점이 같은 컴포넌트에 있는지 확인
        if(findUF(u) != findUF(v)){
            cout << -1 << "\n";
        } else {
            pair<int,int> ans = queryPath(u, v);
            cout << ans.first << " " << ans.second << "\n";
        }
    }
 
    return 0;
}