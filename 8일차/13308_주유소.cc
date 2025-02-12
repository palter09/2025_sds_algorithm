#include <iostream>
#include <vector>
#include <queue>
#include <limits>
#include <algorithm>
using namespace std;
 
// 상태 구조체: cost = 지금까지 누적 비용, node = 현재 노드, minCost = 지금까지 만난 연료비 중 최솟값
struct State {
    long long cost;
    int node;
    int minCost;
};
 
// 우선순위 큐에서 비용이 낮은 순으로 정렬 (min-heap)
struct Compare {
    bool operator()(const State &a, const State &b) {
        return a.cost > b.cost;
    }
};
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int N, M;
    cin >> N >> M;
    
    // 1번 노드부터 N번 노드까지 사용하므로 크기는 N+1 (인덱스 0은 사용하지 않음)
    vector<int> c(N+1);
    // 인접 리스트: 각 노드에 대해 (인접 노드, 간선 가중치)를 저장
    vector<vector<pair<int,int>>> adj(N+1);
    
    // 노드별 연료 비용 읽기
    for (int i = 1; i <= N; i++){
        cin >> c[i];
    }
    
    // 간선 입력 (무방향 그래프)
    for (int i = 0; i < M; i++){
        int a, b, w;
        cin >> a >> b >> w;
        adj[a].push_back({b, w});
        adj[b].push_back({a, w});
    }
    
    // d[node][k] : 노드 node에 도달했을 때, 상태에 기록된 최소 연료비가 k일 때의 최소 총 비용
    // 자바 코드에서는 2500까지의 인덱스를 사용하므로 크기를 2501로 만듭니다.
    const int MAXC = 2500;
    vector<vector<long long>> d(N+1, vector<long long>(MAXC+1, numeric_limits<long long>::max()));
    
    // 우선순위 큐 선언 (비용이 낮은 순으로 꺼내도록 설정)
    priority_queue<State, vector<State>, Compare> pq;
    
    // 시작 상태: 노드 1, 총 비용 0, 현재 연료비는 c[1]
    pq.push({0, 1, c[1]});
    
    long long ans = 0;
    while (!pq.empty()){
        State cur = pq.top();
        pq.pop();
        long long total = cur.cost;
        int node = cur.node;
        int currMin = cur.minCost;
        
        // 도착 노드에 도달하면 종료 (자바 코드와 동일하게 최초 도착 시 정답 처리)
        if (node == N) {
            ans = total;
            break;
        }
        
        // 현재 노드의 모든 인접 간선에 대해
        for (const auto &edge : adj[node]) {
            int nextNode = edge.first;
            int w = edge.second;
            // 현재 상태의 최소 연료비로 간선을 지날 때 비용 계산
            long long newTotal = total + (long long)w * currMin;
            // 다음 상태의 최소 연료비는 현재까지의 연료비와 다음 노드의 연료비 중 작은 값
            int nextMin = min(currMin, c[nextNode]);
            
            // 자바 코드에서는 d[nextNode][currMin] 을 사용하여 갱신 여부를 판단합니다.
            if(d[nextNode][currMin] > newTotal) {
                d[nextNode][currMin] = newTotal;
                pq.push({newTotal, nextNode, nextMin});
            }
        }
    }
    
    cout << ans << "\n";
    return 0;
}