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
### 1753번 최단경로 </br>
### 5719번 거의 최단경로 </br>
</br>
</br>

# 8일차 </br>
### 1854번 K번째 최단경로 찾기 : 다익스트라 </br>
### 13308번 주유소 : 다익스트라 </br>
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
