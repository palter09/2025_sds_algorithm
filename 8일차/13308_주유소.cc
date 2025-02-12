#include <bits/stdc++.h>
using namespace std;
 
typedef long long ll;
 
// 상태: 현재까지의 비용, 현재 도시, 그리고 지금까지 만난 최저 기름 가격(effective fuel price)
struct State {
    ll cost;
    int node;
    ll eff; 
};
 
// 우선순위 큐에서 cost가 작은 순으로 처리하기 위한 comparator
struct StateComparator {
    bool operator()(const State &a, const State &b) const {
        return a.cost > b.cost; // min-heap
    }
};
 
// -----------------------
// 문제 해결 아이디어
// -----------------------
// 각 상태 (node, eff)는 지금까지 도달하는 데 드는 비용와 함께, 
// 지금까지 방문한 도시 중 최저 기름 가격(eff)을 기록합니다.
// 현재 상태가 (u, p)일 때, u의 인접 노드 v로 길이 d인 도로를 이용하면
// 새 비용은 cost + d * p가 되고, 새 상태의 effective fuel price는
// min(p, fuelPrice[v])가 됩니다.
// 
// 각 노드별로 "어떤 effective fuel price로 도착했을 때 최소 비용"인 상태들을 관리하여 
// 이미 도달했던 상태보다 좋지 않으면 넘어가도록 합니다.
// -----------------------
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    int N, M;
    cin >> N >> M;
    // 1-indexed로 노드를 관리합니다.
    vector<vector<pair<int, ll>>> graph(N+1);
    for (int i = 0; i < M; i++){
        int u, v;
        ll d;
        cin >> u >> v >> d;
        graph[u].push_back({v, d});
        graph[v].push_back({u, d});
    }
 
    // 각 도시의 기름 가격 입력
    vector<ll> fuelPrice(N+1);
    for (int i = 1; i <= N; i++){
        cin >> fuelPrice[i];
    }
 
    // best[node] : 도시 node에 도달했을 때, key = effective fuel price, value = 최소 비용
    // (만약 이미 더 낮은 effective price와 비용으로 도달한 상태가 있다면 새 상태는 쓸모없음)
    vector<map<ll, ll>> best(N+1);
 
    priority_queue<State, vector<State>, StateComparator> pq;
 
    // 시작 도시 1에서는 effective fuel price가 fuelPrice[1]이고 비용은 0입니다.
    best[1][fuelPrice[1]] = 0;
    pq.push({0, 1, fuelPrice[1]});
 
    while(!pq.empty()){
        State cur = pq.top();
        pq.pop();
 
        ll curCost = cur.cost;
        int curNode = cur.node;
        ll curEff = cur.eff;
 
        // 만약 현재 상태보다 더 좋은 상태가 이미 기록되어 있다면 무시합니다.
        if(best[curNode].find(curEff) == best[curNode].end() || best[curNode][curEff] < curCost)
            continue;
 
        // 목적지(N번 도시에 도달하면 답을 출력
        if(curNode == N){
            cout << curCost << "\n";
            return 0;
        }
 
        // 인접 도시로 relax 진행
        for(auto &edge : graph[curNode]){
            int nextNode = edge.first;
            ll distance = edge.second;
 
            // 현 상태로 다음 도로를 이용할 때 드는 비용 계산
            ll nextCost = curCost + distance * curEff;
            // 다음 도시에서의 effective fuel price : 지금까지의 값과 다음 도시의 기름 가격 중 최솟값
            ll nextEff = min(curEff, fuelPrice[nextNode]);
 
            // 이미 nextNode에 대해 효과적으로 도달한 상태 중,
            // (effective price가 nextEff 이하이면서 비용이 nextCost 이하인) 상태가 있다면
            // 이번 상태는 도태됩니다.
            bool skip = false;
            for(auto &entry : best[nextNode]){
                if(entry.first <= nextEff && entry.second <= nextCost) {
                    skip = true;
                    break;
                }
            }
            if(skip) continue;
 
            // 이번 상태로 인해 도태되는 기존 상태들을 제거합니다.
            vector<ll> toRemove;
            for(auto &entry : best[nextNode]){
                if(entry.first >= nextEff && entry.second >= nextCost)
                    toRemove.push_back(entry.first);
            }
            for(auto &key : toRemove)
                best[nextNode].erase(key);
 
            // 새 상태를 기록하고 우선순위 큐에 넣습니다.
            best[nextNode][nextEff] = nextCost;
            pq.push({nextCost, nextNode, nextEff});
        }
    }
 
    // 만약 도달할 수 없다면 (문제에 따라 -1 출력)
    cout << -1 << "\n";
    return 0;
}