#include <iostream>
#include <vector>
#include <queue>
using namespace std;

// 정점(V)와 간선(E)의 개수를 저장할 변수
int V, E;

// 각 정점의 진입 차수(indegree)를 저장하는 벡터
vector<int> ind;

// 진입 차수가 0인 정점을 저장할 큐 (탐색 순서를 위한 자료구조)
queue<int> q;

// 각 정점에서 나가는 간선을 인접 리스트 형식으로 저장하는 벡터
vector<vector<int>> edges;

int main() {
    // C++ 입출력 속도 향상을 위해 C와의 동기화를 비활성화하고,
    // cin과 cout의 묶음을 해제합니다.
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    // 정점과 간선의 개수를 입력받음
    cin >> V >> E;
    
    // 정점 번호가 1부터 시작한다고 가정하여, 
    // 크기를 V+1로 맞춰 인접 리스트와 진입 차수 벡터를 초기화합니다.
    edges.resize(V+1);
    ind.resize(V+1);
    
    int s, e;
    // 모든 간선 정보를 입력받음
    for (int i = 0; i < E; i++) {
        // 간선의 시작 정점 s와 도착 정점 e를 입력받음
        cin >> s >> e;
        // 시작 정점 s에서 도착 정점 e로 가는 간선을 추가
        edges[s].push_back(e);
        // 도착 정점 e의 진입 차수를 1 증가시킴
        ind[e]++;
    }
    
    // 진입 차수가 0인 정점을 큐에 넣음 (탐색 시작점)
    for (int i = 1; i <= V; i++) {
        if (ind[i] == 0)
            q.push(i);
    }
    
    // 큐가 빌 때까지 반복 (모든 정점을 처리할 때까지)
    while (!q.empty()) {
        // 큐의 front에서 현재 정점을 가져옴
        int cur = q.front();
        q.pop(); // 큐에서 현재 정점을 제거
        cout << cur << " "; // 현재 정점을 출력 (topological sort 순서대로 출력)
        
        // 현재 정점에서 나가는 모든 간선을 확인
        for (int next : edges[cur]) {
            // 해당 인접 정점의 진입 차수를 감소시키고,
            // 만약 진입 차수가 0이 되면 큐에 추가하여 처리합니다.
            if (--ind[next] == 0) {
                q.push(next);
            }
        }
    }
    
    return 0; // 프로그램 종료
}
