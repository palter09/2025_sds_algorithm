#include <iostream>
#include <vector>
#include <queue>
#include <limits>
using namespace std;

const int INF = 987654321;

struct Edge {
    int to;   // 도착 정점
    int cost; // 간선 가중치
    int idx;  // 간선의 고유 번호
};

int V, E, S, D;
vector<vector<Edge>> graph, reverseGraph;
vector<int> dist;
vector<int> used; // 간선 사용 여부 (최단 경로에 포함되면 1)

// 다익스트라 알고리즘: 시작점 S로부터 각 정점까지의 최단 거리 계산
void dijkstra(int start) {
    dist.assign(V, INF);
    // 우선순위 큐: {누적 가중치, 현재 정점}
    priority_queue<pair<int, int>, vector<pair<int, int>>, greater<pair<int, int>>> pq;
    
    dist[start] = 0;
    pq.push({0, start});
    
    while (!pq.empty()) {
        auto [curCost, cur] = pq.top();
        pq.pop();
        if (curCost > dist[cur])
            continue;
        for (const auto &edge : graph[cur]) {
            // 만약 이 간선이 최단 경로에서 제거된 간선이면 건너뜀
            if (used[edge.idx] == 1)
                continue;
            int next = edge.to;
            int newCost = curCost + edge.cost;
            if (newCost < dist[next]) {
                dist[next] = newCost;
                pq.push({newCost, next});
            }
        }
    }
}

// 역방향 그래프를 이용한 BFS
// 도착점 D부터 시작하여 최단 경로에 포함된 간선을 찾아 제거(mark)
void bfs(int dest) {
    queue<int> q;
    q.push(dest);
    
    while (!q.empty()) {
        int cur = q.front();
        q.pop();
        for (const auto &edge : reverseGraph[cur]) {
            int prev = edge.to;
            if (used[edge.idx] == 1)
                continue;
            // 만약 최단 경로라면 (dist[cur] == dist[prev] + edge.cost)
            if (dist[cur] == dist[prev] + edge.cost) {
                used[edge.idx] = 1; // 해당 간선을 제거(mark)
                q.push(prev);
            }
        }
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    while (true) {
        cin >> V >> E;
        if (V == 0 && E == 0)
            break;
        
        cin >> S >> D;
        
        // 그래프 초기화
        graph.assign(V, vector<Edge>());
        reverseGraph.assign(V, vector<Edge>());
        used.assign(E, 0);
        
        int a, b, c;
        for (int i = 0; i < E; i++) {
            cin >> a >> b >> c;
            // 정방향 그래프: a -> b
            graph[a].push_back({b, c, i});
            // 역방향 그래프: b -> a
            reverseGraph[b].push_back({a, c, i});
        }
        
        // 첫 번째 다익스트라: S로부터 각 정점까지의 최단 경로 계산
        dijkstra(S);
        // BFS를 통해 최단 경로에 포함된 간선 제거(mark)
        bfs(D);
        // 제거된 간선을 제외하고 다시 다익스트라 수행: "거의 최단 경로" 계산
        dijkstra(S);
        
        if (dist[D] == INF)
            cout << -1 << "\n";
        else
            cout << dist[D] << "\n";
    }
    
    return 0;
}
