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

물 bfs
```c++
// 돌('X')와 비버의 굴('D')는 침수 X
while(!wq.empty()){
    auto cur = wq.front();
    wq.pop();
    int x = cur.first, y = cur.second;
    for (int d = 0; d < 4; d++){
        int nx = x + dx[d], ny = y + dy[d];
        if(nx < 0 || nx >= R || ny < 0 || ny >= C)
            continue;
        if(forest[nx][ny] == 'X' || forest[nx][ny] == 'D')
            continue;
        if(waterTime[nx][ny] == -1){
            waterTime[nx][ny] = waterTime[x][y] + 1;
            wq.push({nx, ny});
        }
    }
}
```

고슴도치 bfs
```c++
// "다음 분에 물이 차기 전"에 이동 가능한지 hedgehogTime + 1 < waterTime) 확인
while(!hq.empty()){
    auto cur = hq.front();
    hq.pop();
    int x = cur.first, y = cur.second;
    for (int d = 0; d < 4; d++){
        int nx = x + dx[d], ny = y + dy[d];
        if(nx < 0 || nx >= R || ny < 0 || ny >= C)
            continue;
        // 비버의 굴에 도착한 경우
        if(forest[nx][ny] == 'D'){
            ans = hedgehogTime[x][y] + 1;
            reached = true;
            // 더 이상 탐색할 필요 없으므로 큐 비우기
            while(!hq.empty()) hq.pop();
            break;
        }
        // 고슴도치는 빈 칸('.')나 시작 위치('S')로 이동 가능
        if(forest[nx][ny] == '.' || forest[nx][ny] == 'S'){
            if(hedgehogTime[nx][ny] != -1) continue;
            int nextTime = hedgehogTime[x][y] + 1;
            // 만약 해당 칸에 물이 도착하는 시간이 있다면, 고슴도치가 먼저 도착해야 안전
            if(waterTime[nx][ny] != -1 && nextTime >= waterTime[nx][ny])
                continue;
            hedgehogTime[nx][ny] = nextTime;
            hq.push({nx, ny});
        }
    }
}
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

dfs 함수
```c++
int dfs(int x, int y) {
    // 범위를 벗어나거나 구멍('H')이면 게임 종료 => 0 반환
    if(x < 0 || x >= n || y < 0 || y >= m || board[x][y] == 'H')
        return 0;
    
    // 이미 계산된 경우(메모이제이션)
    if(dp[x][y] != 0)
        return dp[x][y];
    
    // 현재 경로에서 재방문 시 무한 루프 발생
    visited[x][y] = true;
    int move = board[x][y] - '0';  // 현재 칸의 숫자(이동할 칸 수)
    
    for(int i = 0; i < 4; i++){
        int nx = x + dx[i] * move;
        int ny = y + dy[i] * move;
        
        // 다음 위치가 범위 밖이거나 구멍이면, 이동할 수 없으므로 1칸 이동한 것으로 처리
        if(nx < 0 || nx >= n || ny < 0 || ny >= m || board[nx][ny] == 'H'){
            dp[x][y] = max(dp[x][y], 1);
        } else {
            // 만약 현재 경로 상에서 이미 방문한 칸이라면 사이클이 발생한 것!
            if(visited[nx][ny]){
                cout << -1 << "\n";
                exit(0);
            }
            dp[x][y] = max(dp[x][y], dfs(nx, ny) + 1);
        }
    }
    visited[x][y] = false;
    return dp[x][y];
}
```

(0, 0)에서 시작
```c++
cout << dfs(0, 0) << "\n";
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
세그먼트 트리(tree)와 지연 업데이트(lazy)를 저장할 전역 배열 포인터
```c++
long* tree;
long* lazy;
```

현재 노드에 저장되어 있는 지연(lazy) 업데이트 값을 자식 노드에 전파하는 함수
```c++
void propagate(long node, long start, long end) {
    if (lazy[node] != 0) {
        // 현재 노드의 구간 합에 지연 업데이트(diff * 구간의 길이)를 적용
        tree[node] += lazy[node] * (end - start + 1);
       
        if (start != end) {  // 리프 노드가 아니라면 자식 노드로 지연 업데이트 값을 전파
            lazy[node * 2] += lazy[node];
            lazy[node * 2 + 1] += lazy[node];
        }
        
        lazy[node] = 0; // 현재 노드의 지연 업데이트 값 초기화
    }
}
```

세그먼트 트리에서 구간 합 쿼리를 수행하는 함수 (지연 업데이트 적용)
```c++
long lazy_query(long node, long start, long end, long left, long right) {
    propagate(node, start, end); // 현재 노드에 대한 지연 업데이트를 먼저 적용
    
    // 현재 구간과 쿼리 구간이 겹치지 않는 경우
    if (right < start || left > end) {
        return 0;
    }
    
    // 현재 구간이 쿼리 구간에 완전히 포함되는 경우
    if (left <= start && right >= end) {
        return tree[node];
    }
    
    // 그렇지 않다면, 구간을 반으로 나누어 자식 노드에서 결과를 구함
    long mid = (start + end) / 2;
    return lazy_query(node * 2, start, mid, left, right) +
           lazy_query(node * 2 + 1, mid + 1, end, left, right);
}
```

세그먼트 트리에서 구간 [left, right]에 diff 값을 더하는 범위 업데이트 함수 (지연 업데이트 사용)
```c++
void lazy_update(long node, long start, long end, long left, long right, long diff) {
    // 현재 노드에 대한 지연 업데이트를 먼저 적용
    propagate(node, start, end);
    
    // 현재 구간과 업데이트 구간이 겹치지 않는 경우
    if (right < start || left > end) {
        return;
    }
    
    // 현재 구간이 업데이트 구간에 완전히 포함되는 경우
    if (left <= start && right >= end) {
        
        tree[node] += diff * (end - start + 1); // 현재 노드의 구간 합에 diff * 구간 길이를 더해줌
        
        if (start != end) { // 리프 노드가 아니라면, 자식 노드에 지연 업데이트 값을 저장
            lazy[node * 2] += diff;
            lazy[node * 2 + 1] += diff;
        }
        return;
    }
    
    // 구간이 일부만 겹치는 경우, 자식 노드에 대해 재귀 호출
    long mid = (start + end) / 2;
    lazy_update(node * 2, start, mid, left, right, diff);
    lazy_update(node * 2 + 1, mid + 1, end, left, right, diff);
    
    tree[node] = tree[node * 2] + tree[node * 2 + 1]; // 자식 노드의 결과를 합산하여 현재 노드의 값을 갱신
}
```

초기 배열 값 입력 및 세그먼트 트리에 단일 업데이트로 반영
```c++
for (int i = 0; i < N; i++) {
    long num;
    cin >> num;
    update(1, 0, MAX - 1, i, num);
}
```

요구조건에 맞춰 연산 수행
```c++
// a == 1: 구간 업데이트 연산
if (a == 1) {
    long b, c, d;
    cin >> b >> c >> d;
    // 입력 받은 b, c를 인덱스에 맞게 (0-indexed) 변환 후 lazy_update 호출
    lazy_update(1, 0, MAX - 1, b - 1, c - 1, d);
}

// a == 2: 구간 합 쿼리 연산
if (a == 2) {
    long b, c;
    cin >> b >> c;
    // 입력 받은 b, c를 인덱스에 맞게 (0-indexed) 변환 후 lazy_query 호출하고 결과 출력
    cout << lazy_query(1, 0, MAX - 1, b - 1, c - 1) << endl;
}
```

</br>

### 1395번 스위치 : 인덱스 트리 </br>
각 구간별 켜긴 스위치의 개수를 저장할 seg 배열과 lazy tree
```c++
vector<int> seg;
vector<bool> lazy; // lazy[idx]가 true면 해당 노드를 나중에 반전 처리해야 함을 의미
```

lazy 플래그가 설정된 구간에 대해 실제 값을 갱신하는 함수
```c++
void propagate(int idx, int s, int e) {
    if(lazy[idx]) {
        // 현재 구간 [s,e]의 스위치 개수를 반전합니다.
        // 즉, 켜진 개수가 (구간 길이 - 기존 켜진 개수)로 바뀝니다.
        seg[idx] = (e - s + 1) - seg[idx];
        if(s != e) { // 리프 노드가 아니라면 자식에게 lazy 플래그 전달
            lazy[idx * 2] = !lazy[idx * 2];
            lazy[idx * 2 + 1] = !lazy[idx * 2 + 1];
        }
        lazy[idx] = false; // 현재 노드는 처리가 끝났으므로 초기화
    }
}
```

구간 [l, r]에 대해 스위치 상태를 토글하는 함수(lazy update)
```c++
void update(int idx, int s, int e, int l, int r) {
    // lazy 처리: 현재 노드에 보류된 작업이 있다면 먼저 처리
    propagate(idx, s, e);
    
    // 현재 구간 [s,e]가 [l, r]과 아예 겹치지 않으면 리턴
    if(e < l || s > r) return;
    
    // 현재 구간이 [l, r]에 완전히 포함된다면
    if(l <= s && e <= r) {
        // lazy 플래그를 설정한 뒤 바로 처리
        lazy[idx] = true;
        propagate(idx, s, e);
        return;
    }
    
    int mid = (s + e) / 2;
    update(idx * 2, s, mid, l, r);
    update(idx * 2 + 1, mid + 1, e, l, r);
    // 자식 노드를 업데이트한 후, 현재 노드 값을 갱신
    seg[idx] = seg[idx * 2] + seg[idx * 2 + 1];
}
```

구간 [l, r]에서 켜진 스위치의 개수를 반환하는 쿼리 함수(lazy query)
```c++
int query(int idx, int s, int e, int l, int r) {
    // lazy 처리
    propagate(idx, s, e);
    
    // 구간 [s,e]가 [l, r]과 전혀 겹치지 않으면
    if(e < l || s > r) return 0;
    
    // 현재 구간이 [l, r]에 완전히 포함되면
    if(l <= s && e <= r) return seg[idx];
    
    int mid = (s + e) / 2;
    return query(idx * 2, s, mid, l, r) + query(idx * 2 + 1, mid + 1, e, l, r);
}
```

문제 요구사항에 맞게 출력
```c++
if(op == 0) { // op == 0 : 구간 [a, b]의 스위치를 토글(업데이트)
    update(1, 1, N, a, b);
} else { // op == 1 : 구간 [a, b]에서 켜진 스위치의 개수를 쿼리
    cout << query(1, 1, N, a, b) << "\n";
}
```

</br>

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
부모 노드와 부모 노드 사이의 무게 차이를 저장할 배열
```c++
// p[i].first : i의 부모 노드
// p[i].second: i와 부모 노드 사이의 (weight[i] - weight[parent[i]])
vector<pair<int,int>> p;
```

a의 루트를 찾으면서 경로 상의 차이를 갱신하는 함수
```c++
int find(int a) {
    if(a == p[a].first) return a;
    int par = p[a].first;
    p[a].first = find(par);
    p[a].second += p[par].second;
    return p[a].first;
}
```

두 노드 a, b가 주어지고, a와 b의 루트가 다르면 두 집합을 합치는 함수
```c++
void uni(int a, int b, int w) {
    int rootA = find(a);
    int rootB = find(b);
    
    if(rootA == rootB) return;
    // a와 b가 다른 집합에 있다면, b의 루트를 a의 루트에 연결합니다.
    // 이때, b의 루트와 a의 루트 사이의 누적 차이를 맞춰줍니다.
    p[rootB].first = rootA;
    p[rootB].second = p[a].second - p[b].second + w;
}
```

1부터 N까지 p 배열 초기화
```c++
for(int i = 1; i <= N; i++){
    p[i].first = i;
    p[i].second = 0; // 초기에는 자기 자신과의 차이는 0입니다.
}
```

(op == !)면 b가 a보다 w만큼 무겁다는 사실을 저장하고, (op == ?)면 누적 차이를 이용해서 무게 계산
```c++
for(int i = 0; i < M; i++){
    char op;
    cin >> op;
    if(op == '!'){
        int a, b, w;
        cin >> a >> b >> w;
        // a와 b 사이에 "weight[b] - weight[a] = w" 관계를 설정합니다.
        uni(a, b, w);
    }
    else if(op == '?'){
        int a, b;
        cin >> a >> b;
        // 두 노드가 같은 집합에 속해 있으면, 누적 차이를 이용하여 두 노드의 무게 차이를 계산합니다.
        if(find(a) == find(b))
            cout << p[b].second - p[a].second << "\n";
        else
            cout << "UNKNOWN" << "\n";
    }
}
```

</br>

### 17398번 통신망 분할 : 집합 </br>
### 2252번 줄 세우기 : 위상정렬 </br>
학생을 정점, 비교 횟수를 간선으로 생각하여 변수 선언
```c++
int V, E; // 정점(V)와 간선(E)의 개수를 저장할 변수
vector<int> ind; // 각 정점의 진입 차수(indegree)를 저장하는 벡터
queue<int> q; // 진입 차수가 0인 정점을 저장할 큐 (탐색 순서를 위한 자료구조)
vector<vector<int>> edges; // 각 정점에서 나가는 간선을 인접 리스트 형식으로 저장하는 벡터
```

입력이 "3 2"면 3은 2보다 앞에 있어야 한다 -> 3에서 2로가는 간선 추가
```c++
for (int i = 0; i < E; i++) {
    cin >> s >> e;
    edges[s].push_back(e); // 시작 정점 s에서 도착 정점 e로 가는 간선을 추가
    ind[e]++; // 도착 정점 e의 진입 차수를 1 증가시킴
}
```

진입 차수가 0인 정점을 큐에 넣음 (탐색 시작점)
```c++
for (int i = 1; i <= V; i++) {
    if (ind[i] == 0)
        q.push(i);
}
```

위상정렬 시작
```c++
while (!q.empty()) {
    int cur = q.front();
    q.pop();
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
```

</br>

### 1516번 게임 개발 : 위상정렬 </br>
### 1922번 네트워크 연결 : 스패닝 트리 </br>
</br>
</br>

# 7일차 </br>
### 3117번 Youtube </br>
### 11438번 LCA2 </br>
### 3176번 도로 네트워크 </br>
### 1626번 두번째로 작은 스패닝 트리 </br>
각 변수와 배열 선언
```c++
vector<vector<array<int, 2>>> adj; // MST 트리: 각 정점마다 (인접 정점, 간선 가중치)를 저장 (1-indexed)
vector<array<int, 3>> edgesList; // 입력받은 전체 간선 리스트 (u, v, w)
vector<array<int, 3>> nonTreeEdges; // MST에 포함되지 않은 간선들

// Binary Lifting 테이블
vector<vector<int>> parentTable; // parentTable[k][v]: v의 2^k번째 조상
vector<vector<int>> maxEdgeTable; // maxEdgeTable[k][v]: v에서 2^k번째 조상까지 경로에서의 최대 간선 가중치
vector<vector<int>> secondEdgeTable; // secondEdgeTable[k][v]: 최대 간선과 같지 않은 후보 중 두번째로 큰 값

vector<int> depth; // 각 정점의 깊이 (1-indexed)
```

Union-Find 함수
```c++
vector<int> uf;
int findUF(int a) {
    if (uf[a] == a) return a;
    return uf[a] = findUF(uf[a]);
}
void unionUF(int a, int b) {
    int pa = findUF(a), pb = findUF(b);
    if (pa != pb)
        uf[pb] = pa;
}
```

최대값과 같지 않은 값 중 큰 값을 반환하는 함수 (둘 다 같다면 -1 반환)
```c++
int largestNeq(int a, int b, int maxVal) {
    if (a == maxVal && b == maxVal) return -1;
    if (a == maxVal) return b;
    if (b == maxVal) return a;
    return max(a, b);
}
```

LCA 쿼리: 정점 x와 y의 경로 상에서 간선의 가중치가 c와 같다면 두번째 후보값을 고려하여 최대 간선 가중치를 반환
```c++
int lcaQuery(int x, int y, int c) {
    int ret = -1;
    if (depth[x] > depth[y]) swap(x, y);
    // y의 깊이를 x와 맞춤
    for (int i = LOGN - 1; i >= 0; i--) {
        if (depth[y] - depth[x] >= (1 << i)) {
            int candidate = (maxEdgeTable[i][y] != c ? maxEdgeTable[i][y] : secondEdgeTable[i][y]);
            ret = max(ret, candidate);
            y = parentTable[i][y];
        }
    }
    if (x == y)
        return ret;
    for (int i = LOGN - 1; i >= 0; i--) {
        if (parentTable[i][x] != parentTable[i][y]) {
            int candidate1 = (maxEdgeTable[i][x] != c ? maxEdgeTable[i][x] : secondEdgeTable[i][x]);
            int candidate2 = (maxEdgeTable[i][y] != c ? maxEdgeTable[i][y] : secondEdgeTable[i][y]);
            ret = max(ret, candidate1);
            ret = max(ret, candidate2);
            x = parentTable[i][x];
            y = parentTable[i][y];
        }
    }
    int candidate1 = (maxEdgeTable[0][x] != c ? maxEdgeTable[0][x] : secondEdgeTable[0][x]);
    int candidate2 = (maxEdgeTable[0][y] != c ? maxEdgeTable[0][y] : secondEdgeTable[0][y]);
    ret = max(ret, candidate1);
    ret = max(ret, candidate2);
    return ret;
}
```

크루스칼 알고리즘으로 MST 구성
```c++
for (auto& edge : edgesList) {
    int u = edge[0], v = edge[1], w = edge[2];
    if (findUF(u) != findUF(v)) {
        cnt++;
        mstCost += w;
        unionUF(u, v);
        // 양방향으로 추가
        adj[u].push_back({ v, w });
        adj[v].push_back({ u, w });
    }
    else {
        nonTreeEdges.push_back({ u, v, w });
    }
}
if (cnt != V - 1) {
    cout << -1 << "\n";
    return 0;
}
```

BFS를 통해 각 정점의 깊이와 바로 위의 부모, 해당 간선 가중치 초기화
```c++
queue<int> q;
depth[1] = 1;
q.push(1);
while (!q.empty()) {
    int cur = q.front();
    q.pop();
    for (auto& p : adj[cur]) {
        int nxt = p[0], w = p[1];
        if (depth[nxt] == 0) {
            depth[nxt] = depth[cur] + 1;
            parentTable[0][nxt] = cur;
            maxEdgeTable[0][nxt] = w;
            secondEdgeTable[0][nxt] = -1;
            q.push(nxt);
        }
    }
}
```

Binary Lifting 테이블 채우기
```c++
for (int i = 1; i < LOGN; i++) {
    for (int v = 1; v <= V; v++) {
        parentTable[i][v] = parentTable[i - 1][parentTable[i - 1][v]];
        maxEdgeTable[i][v] = max(maxEdgeTable[i - 1][v], maxEdgeTable[i - 1][parentTable[i - 1][v]]);
        int l1 = largestNeq(maxEdgeTable[i - 1][v], maxEdgeTable[i - 1][parentTable[i - 1][v]], maxEdgeTable[i][v]);
        int l2 = largestNeq(secondEdgeTable[i - 1][v], secondEdgeTable[i - 1][parentTable[i - 1][v]], maxEdgeTable[i][v]);
        secondEdgeTable[i][v] = max(l1, l2);
    }
}
```

MST에 포함되지 않은 각 간선에 대해 MST 대체 가능성 탐색
```c++
for (auto& edge : nonTreeEdges) {
    int u = edge[0], v = edge[1], w = edge[2];
    int maxCycleEdge = lcaQuery(u, v, w);
    if (maxCycleEdge < 0) continue;
    long long candidate = mstCost - maxCycleEdge + w;
    if (candidate > mstCost && candidate < ans)
        ans = candidate;
}
```

</br>

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
