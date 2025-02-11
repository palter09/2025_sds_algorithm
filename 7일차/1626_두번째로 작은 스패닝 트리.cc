#include <iostream>
#include <vector>
#include <array>
#include <queue>
#include <algorithm>
#include <functional>
#include <climits>
using namespace std;

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int V, E;
    cin >> V >> E;

    // MST를 구성할 때 사용할 인접 리스트 (1-indexed)
    vector<vector<pair<int,int>>> tree(V+1);
    // 모든 간선을 저장 (각 간선: {a, b, c})
    vector<array<int,3>> edges;
    edges.reserve(E);
    for (int i = 0; i < E; i++){
        int a, b, c;
        cin >> a >> b >> c;
        edges.push_back({a, b, c});
    }
    // 가중치 c 기준 오름차순 정렬
    sort(edges.begin(), edges.end(), [](const array<int,3>& e1, const array<int,3>& e2){
        return e1[2] < e2[2];
    });
    
    // DSU (Union-Find) 준비
    vector<int> dsu(V+1);
    for (int i = 0; i <= V; i++){
        dsu[i] = i;
    }
    function<int(int)> findDSU = [&](int a) -> int {
        if (dsu[a] == a)
            return a;
        return dsu[a] = findDSU(dsu[a]);
    };
    auto unionDSU = [&](int a, int b){
        a = findDSU(a); b = findDSU(b);
        if(a != b)
            dsu[b] = a;
    };

    int vcnt = 0;
    long long mstCost = 0;
    // MST에 사용하지 못한 간선들 (remains)
    vector<array<int,3>> remains;
    for (auto &ed : edges){
        int a = ed[0], b = ed[1], c = ed[2];
        if (findDSU(a) != findDSU(b)){
            vcnt++;
            mstCost += c;
            unionDSU(a, b);
            tree[a].push_back({b, c});
            tree[b].push_back({a, c});
        } else {
            remains.push_back({a, b, c});
        }
    }
    
    // MST가 완성되지 못한 경우
    if (vcnt != V-1){
        cout << -1 << "\n";
        return 0;
    }

    // LCA 및 최대 간선 정보를 위한 배열 준비
    vector<int> depth(V+1, 0);
    const int LOG = 17;
    // parent[k][v]: 정점 v의 2^k번째 조상
    vector<vector<int>> parent(LOG, vector<int>(V+1, 0));
    // maxEdge[k][v]: 정점 v에서 2^k번째 조상으로 올라갈 때 경로 상의 최대 간선 가중치
    vector<vector<int>> maxEdge(LOG, vector<int>(V+1, 0));
    // secondEdge[k][v]: 최대 간선이 maxEdge[k][v]일 때, 두번째로 큰 간선 가중치 (없으면 -1)
    vector<vector<int>> secondEdge(LOG, vector<int>(V+1, -1));
    
    // BFS로 depth 및 parent[0] 설정 (1번 정점을 루트로)
    queue<int> q;
    depth[1] = 1;
    q.push(1);
    while(!q.empty()){
        int cur = q.front();
        q.pop();
        for(auto &pr : tree[cur]){
            int nxt = pr.first, cost = pr.second;
            if(depth[nxt] == 0){
                depth[nxt] = depth[cur] + 1;
                parent[0][nxt] = cur;
                maxEdge[0][nxt] = cost;
                secondEdge[0][nxt] = -1;
                q.push(nxt);
            }
        }
    }
    
    // largestNeq 함수: a, b 중에서 maxVal과 같지 않은 값 중 큰 값을 반환 (두 값 모두 maxVal이면 -1)
    auto largestNeq = [&](int a, int b, int maxVal) -> int {
        if(a == maxVal && b == maxVal)
            return -1;
        if(a == maxVal)
            return b;
        if(b == maxVal)
            return a;
        return max(a, b);
    };
    
    // 이진 리프팅 테이블 작성
    for (int i = 1; i < LOG; i++){
        for (int j = 1; j <= V; j++){
            parent[i][j] = parent[i-1][ parent[i-1][j] ];
            maxEdge[i][j] = max(maxEdge[i-1][j], maxEdge[i-1][ parent[i-1][j] ]);
            int l1 = largestNeq(maxEdge[i-1][j], maxEdge[i-1][ parent[i-1][j] ], maxEdge[i][j]);
            int l2 = largestNeq(secondEdge[i-1][j], secondEdge[i-1][ parent[i-1][j] ], maxEdge[i][j]);
            secondEdge[i][j] = max(l1, l2);
        }
    }
    
    // lca 함수: 두 정점 x, y 사이 경로 상에서, 간선 가중치가 c와 같지 않은 간선 중 가장 큰 값을 반환.
    auto lca = [&](int x, int y, int c) -> int {
        int ret = -1;
        if(depth[x] > depth[y])
            swap(x, y);
        // y를 x와 같은 깊이로 올림
        for (int i = LOG-1; i >= 0; i--){
            if(depth[y] - depth[x] >= (1 << i)){
                int candidate = (maxEdge[i][y] != c ? maxEdge[i][y] : secondEdge[i][y]);
                ret = max(ret, candidate);
                y = parent[i][y];
            }
        }
        if(x == y)
            return ret;
        for (int i = LOG-1; i >= 0; i--){
            if(parent[i][x] != parent[i][y]){
                int candidateX = (maxEdge[i][x] != c ? maxEdge[i][x] : secondEdge[i][x]);
                int candidateY = (maxEdge[i][y] != c ? maxEdge[i][y] : secondEdge[i][y]);
                ret = max(ret, candidateX);
                ret = max(ret, candidateY);
                x = parent[i][x];
                y = parent[i][y];
            }
        }
        // 최종 단계: x와 y는 LCA의 자식들임
        ret = max(ret, (maxEdge[0][x] != c ? maxEdge[0][x] : secondEdge[0][x]));
        ret = max(ret, (maxEdge[0][y] != c ? maxEdge[0][y] : secondEdge[0][y]));
        return ret;
    };
    
    long long ans = LLONG_MAX;
    // remains에 있는 간선에 대해 MST에서 제거하고 대체했을 때의 비용을 계산
    for (auto &ed : remains){
        int a = ed[0], b = ed[1], c = ed[2];
        int maxCycleEdge = lca(a, b, c);
        if(maxCycleEdge < 0)
            continue;
        long long candidate = mstCost - maxCycleEdge + c;
        // second MST는 MST보다 비용이 커야 함.
        if(candidate > mstCost && candidate < ans)
            ans = candidate;
    }
    
    cout << (ans == LLONG_MAX ? -1 : ans) << "\n";
    return 0;
}