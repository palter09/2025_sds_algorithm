#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
using namespace std;
 
// 상수들
const int MAX_K = 20;           // N이 10^5 정도까지면 2^20이 충분합니다.
const int INF = 1000000000;       // 충분히 큰 값
 
// 이진 상승을 위한 정보를 저장할 구조체
struct Info {
    int parent;   // 해당 노드의 2^k번째 부모
    int minEdge;  // 노드에서 2^k번째 부모로 가는 경로 상의 최소 간선 길이
    int maxEdge;  // 노드에서 2^k번째 부모로 가는 경로 상의 최대 간선 길이
};
 
int N;
vector<vector<pair<int,int>>> edges;  // 각 노드의 인접 리스트: {인접 노드, 간선 길이}
vector<int> depth;                      // 각 노드의 깊이 (루트는 0)
vector<vector<Info>> par;               // par[k][v]: 노드 v의 2^k번째 부모 및 구간 정보
 
// BFS를 이용하여 각 노드의 깊이와 바로 위 부모(레벨 0)를 계산합니다.
void bfs(int root) {
    queue<int> q;
    depth[root] = 0;
    q.push(root);
    while(!q.empty()){
        int cur = q.front();
        q.pop();
        for(auto &edge : edges[cur]){
            int nxt = edge.first;
            int w = edge.second;
            if(depth[nxt] == -1){  // 아직 방문하지 않았다면
                depth[nxt] = depth[cur] + 1;
                par[0][nxt].parent = cur;
                par[0][nxt].minEdge = w;
                par[0][nxt].maxEdge = w;
                q.push(nxt);
            }
        }
    }
}
 
// 두 노드 a와 b 사이의 경로 상의 최소/최대 간선 길이를 구하는 함수
pair<int,int> query(int a, int b) {
    // 만약 같은 노드라면 경로에 간선이 없으므로 (0, 0) 출력
    if(a == b) return {0, 0};
 
    int minAns = INF, maxAns = 0;
    // 항상 a가 더 깊은 노드가 되도록 swap
    if(depth[a] < depth[b]) swap(a, b);
 
    // 먼저 a를 b와 같은 깊이로 올립니다.
    int diff = depth[a] - depth[b];
    for (int i = 0; i < MAX_K; i++) {
        if(diff & (1 << i)) {
            minAns = min(minAns, par[i][a].minEdge);
            maxAns = max(maxAns, par[i][a].maxEdge);
            a = par[i][a].parent;
        }
    }
 
    // 두 노드가 같아졌다면 끝.
    if(a == b) return {minAns, maxAns};
 
    // a와 b의 부모가 달라질 때까지 동시에 위로 올립니다.
    for (int i = MAX_K - 1; i >= 0; i--) {
        if(par[i][a].parent != par[i][b].parent) {
            minAns = min(minAns, min(par[i][a].minEdge, par[i][b].minEdge));
            maxAns = max(maxAns, max(par[i][a].maxEdge, par[i][b].maxEdge));
            a = par[i][a].parent;
            b = par[i][b].parent;
        }
    }
    // 마지막 한 번 더 올려서 LCA에 도달 (각각 a와 b에서 LCA로 가는 간선 포함)
    minAns = min(minAns, min(par[0][a].minEdge, par[0][b].minEdge));
    maxAns = max(maxAns, max(par[0][a].maxEdge, par[0][b].maxEdge));
 
    return {minAns, maxAns};
}
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    cin >> N;
    edges.resize(N+1);
    depth.assign(N+1, -1);
    // par 배열을 초기화합니다.
    par.assign(MAX_K, vector<Info>(N+1, {0, INF, 0}));
 
    // 트리 간선 입력 (노드 번호는 1부터 시작)
    for (int i = 1; i < N; i++){
        int a, b, w;
        cin >> a >> b >> w;
        edges[a].push_back({b, w});
        edges[b].push_back({a, w});
    }
 
    // BFS로 깊이 및 1단계(부모) 정보 채우기 (1번 노드를 루트로 사용)
    bfs(1);
 
    // 이진 상승 테이블 전처리:
    // par[i][v] = par[i-1][v] 와 par[i-1][ par[i-1][v].parent ] 를 합친 결과
    for (int i = 1; i < MAX_K; i++){
        for (int v = 1; v <= N; v++){
            int mid = par[i-1][v].parent;
            if(mid == 0){
                par[i][v].parent = 0;
                par[i][v].minEdge = par[i-1][v].minEdge;
                par[i][v].maxEdge = par[i-1][v].maxEdge;
            } else {
                par[i][v].parent = par[i-1][mid].parent;
                par[i][v].minEdge = min(par[i-1][v].minEdge, par[i-1][mid].minEdge);
                par[i][v].maxEdge = max(par[i-1][v].maxEdge, par[i-1][mid].maxEdge);
            }
        }
    }
 
    int M;
    cin >> M;
    while(M--){
        int a, b;
        cin >> a >> b;
        pair<int,int> ans = query(a, b);
        cout << ans.first << " " << ans.second << "\n";
    }
 
    return 0;
}