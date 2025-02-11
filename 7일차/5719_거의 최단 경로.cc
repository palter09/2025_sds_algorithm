#include <iostream>
#include <vector>
#include <queue>
#include <functional>
using namespace std;
 
const int INF = 1e9;
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    while(true){
        int n, m;
        cin >> n >> m;
        if(n == 0 && m == 0) break;
        
        int s, d;
        cin >> s >> d;
        
        // 그래프: 각 정점에서 (다음 정점, 가중치) 정보 저장
        vector<vector<pair<int,int>>> graph(n);
        for (int i = 0; i < m; i++){
            int u, v, p;
            cin >> u >> v >> p;
            graph[u].push_back({v, p});
        }
        
        // 1. 첫 번째 다익스트라: S에서 각 정점까지의 최단 거리와 최단 경로 상의 부모 노드를 기록
        vector<int> dist(n, INF);
        dist[s] = 0;
        // (거리, 정점) 쌍을 저장하는 우선순위 큐 (최소 힙)
        priority_queue<pair<int,int>, vector<pair<int,int>>, greater<pair<int,int>>> pq;
        pq.push({0, s});
        
        // 각 정점에 대해 최단 경로상의 부모 노드들을 저장하는 배열
        vector<vector<int>> parent(n);
        
        while(!pq.empty()){
            auto [cost, cur] = pq.top();
            pq.pop();
            if(cost > dist[cur]) continue;
            for(auto [nxt, w] : graph[cur]){
                // (첫 번째 다익스트라에서는 간선 제거 표시는 없으므로 w == -1 조건은 필요없음)
                if(dist[nxt] > cost + w){
                    dist[nxt] = cost + w;
                    pq.push({dist[nxt], nxt});
                    parent[nxt].clear();
                    parent[nxt].push_back(cur);
                }
                else if(dist[nxt] == cost + w){
                    parent[nxt].push_back(cur);
                }
            }
        }
        
        // 2. 최단 경로 삭제: D에서 시작해서 부모 노드를 거슬러 올라가며 최단 경로 상의 간선을 모두 제거 
        //    (제거는 간선의 가중치를 -1로 표시하여 다익스트라에서 무시하도록 함)
        function<void(int)> removeShortestPath = [&](int u) {
            if(u == s) return;
            for (int prev : parent[u]) {
                for (auto &edge : graph[prev]) {
                    if(edge.first == u && edge.second != -1) { 
                        // 최단 경로에 포함되는 간선이면 제거(가중치를 -1로 설정)
                        edge.second = -1;
                    }
                }
                removeShortestPath(prev);
            }
        };
        removeShortestPath(d);
        
        // 3. 두 번째 다익스트라: 최단 경로 간선이 제거된 그래프에서 S에서 D까지의 최단 거리를 구함
        vector<int> dist2(n, INF);
        dist2[s] = 0;
        priority_queue<pair<int,int>, vector<pair<int,int>>, greater<pair<int,int>>> pq2;
        pq2.push({0, s});
        while(!pq2.empty()){
            auto [cost, cur] = pq2.top();
            pq2.pop();
            if(cost > dist2[cur]) continue;
            for(auto [nxt, w] : graph[cur]){
                if(w == -1) continue;  // 제거된 간선은 건너뜁니다.
                if(dist2[nxt] > cost + w){
                    dist2[nxt] = cost + w;
                    pq2.push({dist2[nxt], nxt});
                }
            }
        }
        
        // 결과 출력: 거의 최단 경로가 없으면 -1 출력
        cout << (dist2[d] == INF ? -1 : dist2[d]) << "\n";
    }
    return 0;
}