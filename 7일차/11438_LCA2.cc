#include <iostream>
#include <vector>
#include <queue>
using namespace std;

const int MAX = 30;  // 최대 2^29 까지 계산 (즉, 30단계)
int N, M;
vector<vector<int>> adj;     // 인접 리스트
vector<int> depth;           // 각 노드의 깊이
vector<vector<int>> parent;  // parent[i][j] : j번 노드의 2^i번째 조상

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

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    cin >> N;
    // 인접 리스트, 깊이, parent 배열 초기화
    adj.resize(N + 1);
    depth.assign(N + 1, 0);
    parent.assign(MAX, vector<int>(N + 1, 0));
    
    // 트리의 간선 입력 (N-1개)
    for (int i = 0; i < N - 1; i++){
        int a, b;
        cin >> a >> b;
        adj[a].push_back(b);
        adj[b].push_back(a);
    }
    
    // BFS를 이용하여 각 노드의 깊이와 바로 위의 부모를 설정
    queue<int> q;
    q.push(1);
    depth[1] = 1;  // 루트의 깊이를 1로 설정
    while (!q.empty()){
        int cur = q.front();
        q.pop();
        for (int nxt : adj[cur]) {
            if (depth[nxt] == 0) {
                depth[nxt] = depth[cur] + 1;
                parent[0][nxt] = cur;
                q.push(nxt);
            }
        }
    }
    
    // 이진 상승 기법(Binary Lifting)을 위한 전처리: 
    // 각 노드의 2^i번째 부모 정보를 저장
    for (int i = 1; i < MAX; i++){
        for (int j = 1; j <= N; j++){
            parent[i][j] = parent[i - 1][parent[i - 1][j]];
        }
    }
    
    // 쿼리 처리
    cin >> M;
    while (M--){
        int a, b;
        cin >> a >> b;
        cout << lca(a, b) << "\n";
    }
    
    return 0;
}