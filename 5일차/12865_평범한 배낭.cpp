#include <iostream>   // 표준 입출력 라이브러리
#include <vector>     // 벡터 자료구조 사용을 위한 라이브러리
using namespace std;

// 물건의 개수(N)와 가방의 최대 무게(K)를 받아 최대 가치를 계산하는 함수 선언
int pack(int N, int K);

// 전역 벡터: 각 물건의 무게와 가치를 저장
vector<int> weights; // 각 물건의 무게를 저장하는 벡터
vector<int> values;  // 각 물건의 가치를 저장하는 벡터

int main() {
    // C++ 표준 입출력의 동기화를 해제하여 입출력 속도를 높임
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int N, K;
    cin >> N >> K; // 물품의 개수 N과 가방의 최대 무게 K를 입력받음
    
    // 각 물건의 무게(W)와 가치(V)를 입력받아 벡터에 저장
    for (int i = 0; i < N; i++) {
        int W, V;
        cin >> W >> V;
        
        weights.push_back(W); // 물건의 무게 저장
        values.push_back(V);  // 물건의 가치 저장
    }
    
    // pack 함수를 호출하여 최대 가치를 계산
    int result = pack(N, K);
    cout << result; // 결과 출력
}

// knapsack 문제를 해결하기 위한 동적 계획법(DP) 함수
int pack(int N, int K) {
    // dp[i][j] : 첫 번째 물건부터 i번째 물건까지 고려했을 때, 최대 무게 j를 만족하는 최대 가치
    // (N+1) x (K+1) 2차원 DP 테이블을 0으로 초기화하여 생성
    vector<vector<int>> dp(N + 1, vector<int>(K + 1, 0));
    
    // 물건들을 하나씩 고려하며 DP 테이블 갱신
    for (int i = 1; i <= N; i++) {
        for (int j = 1; j <= K; j++) {
            // 현재 i번째 물건의 무게(weights[i-1])가 현재 가방 용량(j) 이하인 경우
            if (weights[i - 1] <= j) {
                // 두 경우 중 최댓값 선택:
                // 1. i번째 물건을 포함하지 않는 경우: dp[i-1][j]
                // 2. i번째 물건을 포함하는 경우: dp[i-1][j-weights[i-1]] + values[i-1]
                dp[i][j] = max(dp[i - 1][j], dp[i - 1][j - weights[i - 1]] + values[i - 1]);
            }
            else {
                // 현재 물건의 무게가 j보다 크면 넣을 수 없으므로 이전 결과 그대로 사용
                dp[i][j] = dp[i - 1][j];
            }
        }
    }
    // 모든 물건을 고려한 후, dp[N][K]에는 가방에 넣을 수 있는 최대 가치가 저장됨
    return dp[N][K];
}
