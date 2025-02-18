#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
using namespace std;

struct State {
    long long total; // 지금까지 누적 비용
    int node;        // 현재 노드
    int minCost;     // 현재까지의 최소 비용 (연료 가격 등)
};

struct CompareState {
    bool operator()(const State &a, const State &b) {
        return a.total > b.total; // 총 비용이 작은 상태 우선
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N, M;
    cin >> N >> M;

    // 각 노드의 비용 입력 (1번부터 N번까지)
    vector<int> c(N + 1);
    for (int i = 1; i <= N; i++) {
        cin >> c[i];
    }

    // 인접 리스트 구성 (무방향 그래프)
    vector<vector<pair<int,int>>> e(N + 1);
    for (int i = 0; i < M; i++) {
        int a, b, w;
        cin >> a >> b >> w;
        e[a].push_back({b, w});
        e[b].push_back({a, w});
    }

    // d[node][j]: node에 도달했을 때, 현재까지의 최소 비용이 j일 때의 총 비용
    const long long INF = 1e18;
    vector<vector<long long>> d(N + 1, vector<long long>(2501, INF));
    
    // 다익스트라를 위한 우선순위 큐
    priority_queue<State, vector<State>, CompareState> pq;
    pq.push({0, 1, c[1]});
    d[1][c[1]] = 0;
    
    long long ans = -1;
    
    while (!pq.empty()) {
        State cur = pq.top();
        pq.pop();
        long long total = cur.total;
        int node = cur.node;
        int minCost = cur.minCost;
        
        // 이미 더 좋은 비용으로 이 상태가 업데이트된 경우 건너뜁니다.
        if(total != d[node][minCost]) continue;
        
        if (node == N) {
            ans = total;
            break;
        }
        
        for(auto &edge: e[node]){
            int to = edge.first;
            int cost = edge.second;
            // 이동 후 총 비용 = 현재 비용 + (간선 가중치 * 현재까지의 최소 비용)
            long long newTotal = total + (long long)cost * minCost;
            // 다음 상태에서의 최소 비용은 현재 상태의 minCost와 도착 노드의 비용 c[to] 중 작은 값
            int newMin = min(minCost, c[to]);
            if(newTotal < d[to][newMin]){
                d[to][newMin] = newTotal;
                pq.push({newTotal, to, newMin});
            }
        }
    }
    
    cout << ans << "\n";
    return 0;
}
