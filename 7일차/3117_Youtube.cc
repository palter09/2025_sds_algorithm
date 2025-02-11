#include <iostream>
#include <vector>
using namespace std;
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int N, K;
    long long M;
    cin >> N >> K >> M;
    
    // 시작 위치 입력
    vector<int> start(N);
    for (int i = 0; i < N; i++){
        cin >> start[i];
    }
    
    // 필요한 dp 테이블의 높이 (즉, M의 최대 비트 수)
    int LOG = 0;
    while ((1LL << LOG) <= M) LOG++;
    
    // dp[i][j] : j번 칸에서 2^(i)번 점프한 후의 칸 번호
    // 여기서 dp[0]는 한 번 점프한 결과 f(j)를 의미
    vector<vector<int>> dp(LOG, vector<int>(K + 1, 0));
    
    // dp[0]를 입력받은 순열 f로 초기화 (칸은 1번부터 K번까지)
    for (int i = 1; i <= K; i++){
        cin >> dp[0][i];
    }
    
    // 이진 점프 테이블 구성: dp[i][j] = dp[i-1][ dp[i-1][j] ]
    for (int i = 1; i < LOG; i++){
        for (int j = 1; j <= K; j++){
            dp[i][j] = dp[i-1][ dp[i-1][j] ];
        }
    }
    
    // 각 시작 위치에 대해 M번 점프한 결과 계산  
    // 단, dp 테이블은 dp[0]부터 시작하므로,
    // M의 이진 표현의 bit번째 (1-indexed) 비트를 확인하여
    // bit번째 비트가 켜져 있으면, dp[bit-1] (즉, 2^(bit-1)번 점프)를 수행합니다.
    for (int i = 0; i < N; i++){
        int pos = start[i];
        for (int bit = 1; bit <= LOG; bit++){
            if (M & (1LL << (bit - 1))) {
                pos = dp[bit - 1][pos];
            }
        }
        cout << pos << " ";
    }
    
    return 0;
}