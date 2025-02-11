#include <iostream>
#include <vector>
#include <queue>
using namespace std;

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);
	
	int INF = 9999;
	
	vector<vector<pair<int, int>>> edges;
	vector<int> dist;
	
	while (true) {
		int N, M;
		cin >> N >> M;
		
		if (N == 0 && M == 0)
			break;
		
		edges.resize(N+1);
		dist.assign(N+1, INF);
		
		int S, D;
		cin >> S >> D;
		
		for (int i=0; i<M; i++) {
			int U, V, P;
			cin >> U >> V >> P;
			
			edges[U].push_back({V, P});
		}
		
		priority_queue<pair<int,int>, vector<pair<int,int>>, greater<pair<int,int>>> pq;
		
		// 최단 거리 찾기 
		dist[S] = 0;
		pq.push({0, S});
		while (!pq.empty()) {
			int curW = pq.top().first;
			int cur = pq.top().second;
			pq.pop();
			
			if (curW != dist[cur])
				continue;
			
			for (int i=0; i<edges[cur].size(); i++) {
				int next = edges[cur][i].first;
				int nextW = edges[cur][i].second;
				if (dist[next] > curW + nextW) {
					dist[next] = curW + nextW;
					pq.push({dist[next], next});
				}
			}
		}
		
		// 최단 거리 지우기 
		pq.push({dist[N], D});
		while (!pq.empty()) {
			int curW = pq.top().first;
			int cur = pq.top().second;
			pq.pop();
			
			if (curW != dist[cur])
				continue;
				
			for (int i=0; i<edges[cur].size(); i++) {
				int next = edges[cur][i].first;
				int nextW = edges[cur][i].second;
				if (curW == dist[next] + nextW) {
					dist[next] = curW - nextW;
					pq.push({dist[next], next});
				}
			}
		}
		
		// 거의 최단 경로 출력 
		cout << dist[D] << "\n";
	}
	
	return 0;
}



