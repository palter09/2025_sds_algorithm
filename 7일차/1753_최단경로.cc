#include <iostream>
#include <vector>
#include <queue>
#include <functional>
using namespace std;

int V, E, start;
const int INF = 99999999;
vector<vector<pair<int,int>>> edges;
vector<int> dist;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    cin >> V >> E >> start;
    edges.resize(V + 1);
    dist.assign(V + 1, INF);

    for (int i = 0; i < E; i++) {
        int s, e, c;
        cin >> s >> e >> c;
        edges[s].push_back({ e, c });
    }

    // (거리, 정점) 순으로 저장하는 최소힙
    priority_queue<pair<int,int>, vector<pair<int,int>>, greater<pair<int,int>>> pq;
    dist[start] = 0;
    pq.push({ 0, start });

    while (!pq.empty()) {
        int curW = pq.top().first;
        int cur = pq.top().second;
        pq.pop();

        // 이미 더 짧은 경로가 발견된 경우 건너뛰기
        if (curW != dist[cur])
            continue;

        for (int i = 0; i < edges[cur].size(); i++) {
            int next = edges[cur][i].first;
            int nextW = edges[cur][i].second;
            if (dist[next] > curW + nextW) {
                dist[next] = curW + nextW;
                pq.push({ dist[next], next });
            }
        }
    }

    for (int i = 1; i <= V; i++) {
        if (dist[i] == INF)
            cout << "INF" << "\n";
        else
            cout << dist[i] << "\n";
    }

    return 0;
}