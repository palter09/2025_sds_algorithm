
#include <iostream>
#include <vector>
#include <queue>
using namespace std;

typedef long long ll;

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int n, m, k;
    cin >> n >> m >> k;
    
    // 그래프 저장: graph[u]는 정점 u에서 나가는 간선들 (다음 정점, 가중치)
    vector<vector<pair<int, int>>> graph(n + 1);
    for (int i = 0; i < m; i++){
        int u, v, w;
        cin >> u >> v >> w;
        graph[u].push_back({v, w});
    }
    
    // 각 정점에 대해 지금까지 발견한 최단 경로 후보들을 저장합니다.
    // priority_queue<long long>는 기본적으로 최대 힙(max-heap)입니다.
    // 즉, top()은 지금까지 저장된 경로 중 가장 큰(= k번째 최단 경로 후보) 값을 반환합니다.
    vector<priority_queue<ll>> dist(n + 1);
    
    // 전역 우선순위 큐: (현재까지 경로의 총 거리, 현재 정점)
    // 여기서는 최소 힙(min-heap)을 사용하여, 거리가 짧은 경로부터 처리합니다.
    priority_queue<pair<ll, int>, vector<pair<ll, int>>, greater<pair<ll, int>>> pq;
    
    // 시작 정점 1번의 초기 거리는 0입니다.
    dist[1].push(0);
    pq.push({0, 1});
    
    while (!pq.empty()){
        auto [d, cur] = pq.top();
        pq.pop();
        
        // cur 정점에서 뻗어나가는 모든 간선에 대해
        for (auto &edge : graph[cur]){
            int nxt = edge.first;
            ll nd = d + edge.second;
            
            // 만약 nxt 정점까지 발견된 경로의 개수가 k개보다 작으면 새 경로를 추가합니다.
            if (dist[nxt].size() < (size_t)k){
                dist[nxt].push(nd);
                pq.push({nd, nxt});
            }
            // 이미 k개의 경로가 있다면, 그 중 가장 큰 값(현재 k번째 경로 후보)보다 작을 때 갱신합니다.
            else if (dist[nxt].top() > nd){
                dist[nxt].pop();
                dist[nxt].push(nd);
                pq.push({nd, nxt});
            }
        }
    }
    
    // 각 정점에 대해 k번째 최단경로의 길이를 출력합니다.
    // k번째 경로가 존재하지 않으면 -1을 출력합니다.
    for (int i = 1; i <= n; i++){
        if (dist[i].size() == (size_t)k)
            cout << dist[i].top() << "\n";
        else
            cout << -1 << "\n";
    }
    
    return 0;
}