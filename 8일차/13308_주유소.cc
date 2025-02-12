#include <bits/stdc++.h>
using namespace std;
 
// 상태: 현재까지 누적 비용, 현재 도시에 도달했을 때까지 만난 연료가격 중 최저값(curMin)
struct State {
    long long cost;
    int city;
    int curMin; // 지금까지 만난 연료가격의 최솟값
    bool operator>(const State &other) const {
        return cost > other.cost;
    }
};
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int N, M;
    cin >> N >> M;
    
    // 각 도시의 주유소 연료가격 (1번 도시부터)
    vector<int> fuel(N+1);
    for (int i = 1; i <= N; i++){
        cin >> fuel[i];
    }
    
    // 단방향 도로: 각 도로는 (u → v, 거리 w)
    vector<vector<pair<int,int>>> adj(N+1);
    for (int i = 0; i < M; i++){
        int u, v, w;
        cin >> u >> v >> w;
        adj[u].push_back({v, w});
    }
    
    // dp[도시][현재까지 최저 연료가격] = 해당 상태로 도시에 도달했을 때 누적 비용의 최솟값
    // 문제 조건에 따라 연료가격은 최대 2500이므로 (1 ~ 2500) 크기를 사용합니다.
    const int MAXFUEL = 2500;
    const long long INF = 1LL << 60;
    vector<vector<long long>> dp(N+1, vector<long long>(MAXFUEL+1, INF));
    
    // 초기 상태: 도시 1에서 시작. 이때 지금까지의 최저 연료가격은 fuel[1]이며 비용은 0.
    dp[1][ fuel[1] ] = 0;
    priority_queue<State, vector<State>, greater<State>> pq;
    pq.push({0, 1, fuel[1]});
    
    while(!pq.empty()){
        State cur = pq.top();
        pq.pop();
        int city = cur.city, curMin = cur.curMin;
        long long costSoFar = cur.cost;
        
        // 이미 더 좋은 상태로 갱신되어 있다면 건너뜁니다.
        if(costSoFar != dp[city][curMin]) continue;
        
        // 목적지에 도달하면 Dijkstra 방식이므로 최적해입니다.
        if(city == N){
            cout << costSoFar;
            return 0;
        }
        
        // 현재 도시에서 갈 수 있는 모든 도로에 대해 relax 수행
        for(auto &edge : adj[city]){
            int nxt = edge.first;
            int d = edge.second;
            // 현재까지의 최저 연료가격(curMin)으로 이번 도로의 비용 계산
            long long nCost = costSoFar + (long long)d * curMin;
            // 다음 도시에 도착하면, 새로운 상태의 최저 연료가격은 
            // 지금까지의 curMin과 다음 도시의 연료가격 중 더 작은 값입니다.
            int nCurMin = min(curMin, fuel[nxt]);
            if(nCost < dp[nxt][nCurMin]){
                dp[nxt][nCurMin] = nCost;
                pq.push({nCost, nxt, nCurMin});
            }
        }
    }
    
    // (문제 조건상 N에 도달하는 경로가 항상 존재하므로 여기까지 오지 않습니다.)
    long long ans = INF;
    for (int p = 1; p <= MAXFUEL; p++){
        ans = min(ans, dp[N][p]);
    }
    cout << ans;
    
    return 0;
}