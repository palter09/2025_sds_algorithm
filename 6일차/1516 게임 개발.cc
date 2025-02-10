#include <iostream>
#include <vector>
#include <queue>
#include <algorithm>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N;
    cin >> N;

    // 인접 리스트, 진입차수, 각 건물의 건설시간, 최소 완료 시간 배열 선언
    vector<vector<int>> adj(N + 1);
    vector<int> indegree(N + 1, 0);
    vector<int> buildTime(N + 1, 0);
    vector<int> ret(N + 1, 0);

    // 각 건물에 대해 건설시간과 선행 건물 정보를 입력받음.
    // 입력 형식: 각 줄에 건설시간과 선행 건물 번호들이 주어지고, -1이 나오면 종료
    for (int i = 1; i <= N; i++) {
        cin >> buildTime[i];
        while (true) {
            int prerequisite;
            cin >> prerequisite;
            if (prerequisite == -1)
                break;
            // prerequisite 건물이 i번 건물의 선행 건물임.
            // 따라서 prerequisite 에서 i로 향하는 간선 추가
            adj[prerequisite].push_back(i);
            indegree[i]++;
        }
    }

    // 진입차수가 0인 건물(선행 건물이 없는 건물)을 큐에 넣음.
    queue<int> q;
    for (int i = 1; i <= N; i++) {
        if (indegree[i] == 0) {
            q.push(i);
            ret[i] = buildTime[i];
        }
    }

    // 위상 정렬을 진행하며 각 건물의 최소 완료 시간을 계산.
    while (!q.empty()) {
        int cur = q.front();
        q.pop();
        for (int next : adj[cur]) {
            indegree[next]--;
            ret[next] = max(ret[next], ret[cur] + buildTime[next]);
            if (indegree[next] == 0) {
                q.push(next);
            }
        }
    }

    // 각 건물의 최소 완료 시간 출력
    for (int i = 1; i <= N; i++) {
        cout << ret[i] << "\n";
    }
    
    return 0;
}