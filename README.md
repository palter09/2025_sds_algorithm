# 2025_sds_algorithm
2025 sds 알고리즘 특강
</br>
</br>

# 1순위 : bottom-up DP, 다익스트라, 인덱스 트리 </br>
</br>
</br>


# 1일차 </br>
### 3425번 고스택 : 코딩 기초 훈련 </br>

### 3055번 탈출 : BFS </br>
물 bfs와 고슴도치 bfs를 따로 계산
```c++
vector<vector<int>> waterTime(R, vector<int>(C, -1)); // (i, j)칸에 물이 도달하는 시간
queue<pair<int,int>> wq; // 물의 위치

vector<vector<int>> hedgehogTime(R, vector<int>(C, -1)); // (i, j)칸에 고슴도치가 도달하는 시간
queue<pair<int,int>> hq; // 고슴도치 위치
```

물 bfs를 먼저 계산하여 각 칸에 물이 도달하는 시간을 계산하고, 고슴도치를 이동시키며 물이 있는지 확인
```c++
int nextTime = hedgehogTime[x][y] + 1;
// 만약 해당 칸에 물이 도착하는 시간이 있다면, 고슴도치가 먼저 도착해야 안전
if(waterTime[nx][ny] != -1 && nextTime >= waterTime[nx][ny])
  continue;
```

</br>

### 1103번 게임 : DFS </br>
최대 이동 횟수를 저장할 dp와 현재 dfs 경로상에서 방문 여부를 확인하는 visit 배열 사용
```c++
vector<vector<int>> dp;         // (x,y)에서 출발할 때 최대 이동 횟수
vector<vector<bool>> visited;   // 현재 DFS 경로상에서 방문한 여부
```

다음 위치가 범위 밖이거나 구멍이면, 이동할 수 없으므로 1칸 이동한 것으로 처리
```c++
if(nx < 0 || nx >= n || ny < 0 || ny >= m || board[nx][ny] == 'H')
  dp[x][y] = max(dp[x][y], 1);
```

만약 현재 경로상에서 이미 방문한 칸이라면 사이클이 발생한 것
```c++
if(visited[nx][ny]){
  cout << -1 << "\n";
  exit(0);
}
```

</br>
</br>

# 2일차 </br>
### 1062번 가르침 : DFS </br>
각 알파벳을 배웠는지 여부를 저장하는 배열 learned
```c++
bool learned[26] = { false }; // (false: 배우지 않음, true: 배움)
```

dfs 함수 정의
```c++
// idx: 알파벳 중에서 선택을 고려할 시작 인덱스 (0 ~ 25)
// cnt: 현재까지 배운 문자 개수
void dfs(int idx, int cnt);
```

알파벳 a부터 z까지 순서대로 선택하며 dfs문 돌기
```c++
for (int i = idx; i < 26; i++) {
  if (!learned[i]) {
    learned[i] = true;
    dfs(i + 1, cnt + 1);
    learned[i] = false;
  }
}
```

k개의 문자를 모두 선택했다면, 각 단어를 읽을 수 있는지 확인
```c++
if (cnt == K) {
        int countReadable = 0;
        // 모든 단어에 대해 검사
        for (const string &word : words) {
            bool canRead = true;
            // 단어에 포함된 각 문자가 배운 문자에 포함되어 있는지 확인
            for (char c : word) {
                if (!learned[c - 'a']) { 
                    canRead = false;
                    break;
                }
            }
            if (canRead) countReadable++;
        }
        ans = max(ans, countReadable);
        return;
    }
```

필수 문자들은 learned 배열에 미리 표시
```c++
learned['a' - 'a'] = true;
learned['n' - 'a'] = true;
learned['t' - 'a'] = true;
learned['i' - 'a'] = true;
learned['c' - 'a'] = true;
```

s의 길이에서 앞의 4글자("anta")와 뒤의 4글자("tica")를 제외
```c++
string middle = s.substr(4, s.size() - 8);
words.push_back(middle);
```

</br>

### 7453번 합이 0인 네 정수 : two pointer algorithm </br>


### 3020번 개똥벌레 : 이진 탐색 </br>


</br>
</br>

# 3일차 </br>
### 2842번 집배원 한상덕 : 파라메트릭 서치(47p) </br>
### 1725번 히스토그램 : 자료구조(스택), pre fix sum </br>
### 11003번 최솟값 찾기 : 자료구조(덱) </br>
### 2696번 중앙값 구하기 : 자료구조(min heap, max heap) </br>
### 2517번 달리기 : 인덱스 트리 </br>
</br>
</br>

# 4일차 </br>
### 2042번 구간 합 구하기 : 인덱스 트리 </br>
### 1275번 커피숍2 : 인덱스 트리 </br>
### 2243번 사탕상자 : 인덱스 트리 </br>
### 1321번 군인 : 인덱스 트리 </br>
### 10999번 구간 합 구하기2 : 인덱스 트리 </br>
### 1395번 스위치 : 인덱스 트리 </br>
### 1202번 보석 도둑 : 그리디 알고리즘 </br>
### 1735번 분수합 : 정수론 </br>
### 14476번 최대공약수 하나빼기 </br>
</br>
</br>

# 5일차 </br>
### 6588번 골드바흐의 추측 : 에라토스테네스의 체 </br>
### 1644번 소수의 연속합 : 에라토스테네스의 체, 투포인터</br>
### 2725번 보이는 점의 개수 : 오일러 </br>
### 2748번 피보나치 수2 : DP </br>
### 1932번 정수삼각형 : DP </br>
### 12865번 평범한 배낭 : DP </br>
### 1010번 다리놓기 : 조합론 </br>
### 1256번 사전 : 조합론 </br>
</br>
</br>

# 6일차 </br>
### 1717번 집합의 표현 : 집합 </br>
### 3830번 교수님은 기다리지 않는다 : 집합 </br>
### 17398번 통신망 분할 : 집합 </br>
### 2252번 줄 세우기 : 위상정렬 </br>
### 1516번 게임 개발 : 위상정렬 </br>
### 1922번 네트워크 연결 : 스패닝 트리 </br>
</br>
</br>

# 7일차 </br>
### 3117번 Youtube </br>
### 11438번 LCA2 </br>
### 3176번 도로 네트워크 </br>
### 1626번 두번째로 작은 스패닝 트리 </br>
### 1753번 최단경로 : 다익스트라 </br>
edge와 dist 크기 지정
```c++
edges.resize(V + 1); // 정점의 개수만큼 배열 크기 초기화
dist.assign(V + 1, INF); // 거리를 저장할 배열. 처음에는 최대 크기로 초기화
```

s에서 e로 가는 길의 거리가 c
```c++
edges[s].push_back({ e, c });
```

다익스트라 알고리즘
```c++
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
```

</br>

### 5719번 거의 최단경로 : 다익스트라 </br>
Edge 구조체 선언
```c++
struct Edge {
    int to;   // 도착 정점
    int cost; // 간선 가중치
    int idx;  // 간선의 고유 번호
};
```

정방향 그래프와 역방향 그래프 입력받기
```c++
for (int i = 0; i < E; i++) {
  cin >> a >> b >> c;
  // 정방향 그래프: a -> b
  graph[a].push_back({b, c, i});
  // 역방향 그래프: b -> a
  reverseGraph[b].push_back({a, c, i});
}
```

정방향 그래프를 이용한 다익스트라
```c++
// 시작점 S로부터 각 정점까지의 최단 거리 계산
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
```

역방향 그래프를 이용한 bfs
```c++
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
```

다익스트라 계산 이후 최단 경로를 지우고, 다시 다익스트라 계산
```c++
// 첫 번째 다익스트라: S로부터 각 정점까지의 최단 경로 계산
dijkstra(S);
// BFS를 통해 최단 경로에 포함된 간선 제거(mark)
bfs(D);
// 제거된 간선을 제외하고 다시 다익스트라 수행: "거의 최단 경로" 계산
dijkstra(S);
```

</br>
</br>

# 8일차 </br>
### 1854번 K번째 최단경로 찾기 : 다익스트라 </br>
각 정점에 대해 지금까지 발견한 최단 경로 후보들을 저장할 최대 힙 생성
```c++
// top()은 지금까지 저장된 경로 중 가장 큰(= k번째 최단 경로 후보) 값을 반환
vector<priority_queue<ll>> dist(n + 1);
```

다익스트라 알고리즘에 사용할 최소 힙 생성
```c++
// 전역 우선순위 큐: (현재까지 경로의 총 거리, 현재 정점)
priority_queue<pair<ll, int>, vector<pair<ll, int>>, greater<pair<ll, int>>> pq;
```

시작 정점 초기화
``` c++
dist[1].push(0);
pq.push({0, 1});
```

다익스트라 알고리즘
```c++
while (!pq.empty()){
    ll d = pq.top().first;
    int cur = pq.top().second;
    pq.pop();
    
    // cur 정점에서 뻗어나가는 모든 간선에 대해
    for (auto &edge : graph[cur]){
        int nxt = edge.first;
        ll nd = d + edge.second;
        
        // 만약 nxt 정점까지 발견된 경로의 개수가 k개보다 작으면 새 경로를 추가합니다.
        if (dist[nxt].size() < k){
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
```

</br>

### 13308번 주유소 : 다익스트라 </br>
현재 상태를 저장할 State 구조체와 State 구조체 정렬을 위한 CompareState 구조체
```c++
struct State {
    long long total; // 지금까지 누적 비용
    int node;        // 현재 노드
    int minCost;     // 현재까지의 최소 비용 (연료 가격 등)
};

struct CompareState {
    bool operator()(const State &a, const State &b) {
        return a.total > b.total; // 총 비용이 작은 상태 우선
    }
};
```

각 노드의 비용 입력
```c++
vector<int> c(N + 1);
for (int i = 1; i <= N; i++) {
    cin >> c[i];
}
```

방향성이 없는 그래프 생성
```c++
  vector<vector<pair<int,int>>> e(N + 1);
  for (int i = 0; i < M; i++) {
      int a, b, w;
      cin >> a >> b >> w;
      e[a].push_back({b, w});
      e[b].push_back({a, w});
  }
```

d[node][j]: node에 도달했을 때, 현재까지의 최소 비용이 j일 때의 총 비용
```c++
vector<vector<long long>> d(N + 1, vector<long long>(2501, INF));
```

다익스트라 알고리즘
```c++
while (!pq.empty()) {
    State cur = pq.top();
    pq.pop();
    long long total = cur.total;
    int node = cur.node;
    int minCost = cur.minCost;
    
    // 이미 더 좋은 비용으로 이 상태가 업데이트된 경우 건너뜁니다.
    if(total != d[node][minCost]) continue;
    
    if (node == N) {
        ans = total;
        break;
    }
    
    for(auto &edge: e[node]){
        int to = edge.first;
        int cost = edge.second;
        // 이동 후 총 비용 = 현재 비용 + (간선 가중치 * 현재까지의 최소 비용)
        long long newTotal = total + (long long)cost * minCost;
        // 다음 상태에서의 최소 비용은 현재 상태의 minCost와 도착 노드의 비용 c[to] 중 작은 값
        int newMin = min(minCost, c[to]);
        if(newTotal < d[to][newMin]){
            d[to][newMin] = newTotal;
            pq.push({newTotal, to, newMin});
        }
    }
}
```

</br>

### 13907번 세금 : 다익스트라 </br>
### 11657번 타임머신 : 벨만포드 </br>
### 3860번 할로윈 묘지 : 벨만포드 </br>
### 11404번 플로이드 </br>
### 7579번 앱 </br>
</br>
</br>

# 9일차 (bottom-up DP) </br>
### 11053번 가장 긴 증가하는 부분 수열 : DP </br>
### 12015번 가장 긴 증가하는 부분 수열2 : DP </br>
### 14003번 가장 긴 증가하는 부분 수열5 : DP </br>
### 1915번 가장 큰 정사각형 : DP </br>
### 1028번 다이아몬드 광산 : DP </br>
### 2342번 Dance Dance Revolution : DP </br>
### 5626번 제단 : DP </br>
</br>
</br>

# 10일차 (top-down DP) </br>
### 2098번 외판원 순회 : DP </br>
### 1102번 발전소 : DP(bit-mask): </br>
### 2618번 경찰차 : DP </br>
### 11049번 행렬 곱셈 순서 </br>
</br>
</br>
