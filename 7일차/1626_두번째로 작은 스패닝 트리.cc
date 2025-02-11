#include <bits/stdc++.h>
using namespace std;
 
// 간선 정보를 저장하는 구조체
struct Edge {
    int u, v, w;
    bool used;  // MST에 포함되었는지 여부
};
 
// DSU (Union-Find) 자료구조
struct DSU {
    vector<int> par;
    DSU(int n) : par(n+1) {
        for (int i = 0; i <= n; i++) par[i] = i;
    }
    int find(int a) {
        return par[a] == a ? a : par[a] = find(par[a]);
    }
    bool unite(int a, int b) {
        a = find(a); b = find(b);
        if(a == b) return false;
        par[b] = a;
        return true;
    }
};
 
// 메인 함수
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int n, m;
    cin >> n >> m;
    vector<Edge> edges(m);
    for (int i = 0; i < m; i++){
        cin >> edges[i].u >> edges[i].v >> edges[i].w;
        edges[i].used = false;
    }
    
    // 간선의 개수가 n-1 미만이면 spanning tree 구성 불가
    if(m < n-1){
        cout << -1;
        return 0;
    }
    
    // 가중치 오름차순 정렬 (Kruskal을 위해)
    sort(edges.begin(), edges.end(), [](const Edge &a, const Edge &b){
        return a.w < b.w;
    });
    
    DSU dsu(n);
    long long mstWeight = 0;
    int countUsed = 0;
    // MST에 포함된 간선으로 구성한 트리의 인접 리스트
    vector<vector<pair<int,int>>> tree(n+1);
    
    // Kruskal 알고리즘으로 MST 구성
    for (int i = 0; i < m; i++){
        if(dsu.unite(edges[i].u, edges[i].v)){
            edges[i].used = true;
            mstWeight += edges[i].w;
            countUsed++;
            tree[edges[i].u].push_back({edges[i].v, edges[i].w});
            tree[edges[i].v].push_back({edges[i].u, edges[i].w});
        }
    }
    if(countUsed != n-1){
        cout << -1;
        return 0;
    }
    
    // ----- Binary Lifting 을 위한 전처리 -----
    int LOG = 0;
    while((1 << LOG) <= n) LOG++;
    vector<int> depth(n+1, 0);
    // up[v][i] : v의 2^i 번째 조상, maxEdge[v][i] : v에서 2^i 조상까지 경로 상의 최대 간선 가중치
    vector<vector<int>> up(n+1, vector<int>(LOG, -1));
    vector<vector<int>> maxEdge(n+1, vector<int>(LOG, 0));
    
    // DFS를 통해 각 노드의 깊이와 바로 위의 부모, 그리고 부모로 가는 간선 가중치를 저장
    function<void(int,int)> dfs = [&](int v, int p){
        up[v][0] = p;
        for(auto &pInfo : tree[v]){
            int nv = pInfo.first, w = pInfo.second;
            if(nv == p) continue;
            depth[nv] = depth[v] + 1;
            maxEdge[nv][0] = w;
            dfs(nv, v);
        }
    };
    dfs(1, -1);
    
    // Binary lifting 표 채우기
    for (int j = 1; j < LOG; j++){
        for (int i = 1; i <= n; i++){
            if(up[i][j-1] != -1){
                up[i][j] = up[ up[i][j-1] ][j-1];
                maxEdge[i][j] = max(maxEdge[i][j-1], maxEdge[ up[i][j-1] ][j-1]);
            }
        }
    }
    
    // 두 노드 a, b 사이의 경로 상에서 최대 간선 가중치를 구하는 함수
    auto getMaxOnPath = [&](int a, int b) -> int {
        int res = 0;
        if(depth[a] < depth[b]) swap(a, b);
        int diff = depth[a] - depth[b];
        for (int i = 0; i < LOG; i++){
            if(diff & (1 << i)){
                res = max(res, maxEdge[a][i]);
                a = up[a][i];
            }
        }
        if(a == b) return res;
        for (int i = LOG - 1; i >= 0; i--){
            if(up[a][i] != up[b][i]){
                res = max(res, maxEdge[a][i]);
                res = max(res, maxEdge[b][i]);
                a = up[a][i];
                b = up[b][i];
            }
        }
        res = max(res, maxEdge[a][0]);
        res = max(res, maxEdge[b][0]);
        return res;
    };
    // ---------------------------------------
 
    // MST에 포함되지 않은 간선을 하나씩 보며, 해당 간선을 추가하면 생기는 사이클 내
    // 최대 간선 가중치를