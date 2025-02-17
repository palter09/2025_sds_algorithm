# 1회
### 안전운전을 도와줄 차세대 지능형 교통시스템
굉장히 복잡한 입출력 문제. 솔직히 안나올 것 같음. </br>

모든 신호를 [번호][들어오는 방향][나가는 방향]으로 정리 </br>

차의 현재 위치와 다음 위치를 저장할 junction, junction2 </br>

junction = [row][col][나가는 방향] </br>
junction2 = [row][col][들어오는 방향] </br>

5중 for문을 돌며 visit에 가능한지 기록 </br>
time / row / col / inDir / outDir </br>

visit이 1인 개수를 출력</br>

# 2회


# 3회


# 4회
### 슈퍼컴퓨터 클러스터
이진탐색 문제 </br>

low, high, mid 설정 </br>

high = low + 1인 경우 무한루프에 들어갈 수 있어서 mid = low + high + 1 </br>

성능 x에 도달하기 위한 비용을 계산해서 요구사항과 비교 </br>
low = mid </br>
high = mid - 1 </br>

주어진 요구사항보다 비용이 크면 false, 아니면 true 반환 </br>

low와 high가 같아지면 low 출력 </br>

### 통근버스 출발순서 검증하기
DP 문제 </br>

more[i][k] -> i와 k사이에 A_i보다 큰 값이 몇개 있는지 </br>

arr[i] < arr[k] -> k가 아니라 j개수를 세고 +1해서 저장 </br>

arr[i] >= arr[k] -> k 개수를 세서 total 계산 </br>

# 5회
### 성적 평가
인덱스 트리같이 보이는 평범한 문제 </br>

{점수, 순서} 형태로 저장 </br>

점수가 높은 순으로 정렬하여 순위를 메기고, 순서에 맞게 순위를 부여 </br>

# 6회
### 출퇴근길
참신한 그래프 문제 </br>

기본적으로 dfs 이용 </br>
void dfs(int now, vector<vector<int>>& adj, vector<int>& visit) </br>

S와 T에서 나가는 간선을 확인하는 fromS, fromT </br>
출근길에 T를 만나면 멈춤 : fromS[T] = 1 </br>
퇴근길에 S를 만나면 멈출 : fromT[S] = 1 </br>
dfs(S, adj, fromS) </br>
dfs(T, adj, fromT) </br>

S와 T로 들어오는 간선을 확인하는 toS, toT </br>
dfs(S, adjR, toS) </br>
dfs(T, adjR, toT) </br>

fromS[i], fromT[i], toS[i], toT[i]가 모두 1이면 찾은것 </br>

S와 T를 포함해서 세기 때문에 -2 해서 출력 </br>
cout << count-2 </br>

# 7회
### 자동차 테스트
lower_bound 문제 </br>

lower_bound 함수만 사용할 줄 알면 풀수 있는 문제 </br>
vector의 크기만 조심하자 </br>

mileage.resize(n) </br>
idx = lower_bound(mileage.begin(), mileage.end(), m) - mileage.begin() </br>

벡터 크기를 n+1로 하면 빈 공간이 생겨 잘못된 결과가 나온다 </br>
마지막에 mileage.begin()을 빼주지 않으면 mileage의 iterator를 반환한다 </br>



