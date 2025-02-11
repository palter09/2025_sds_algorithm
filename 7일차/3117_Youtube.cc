#include <iostream>
#include <vector>
using namespace std;
 
int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
 
    int N, K;
    long long M;
    cin >> N >> K >> M;
    
    // 시작 위치들 입력
    vector<int> start(N);
    for(int i = 0; i < N; i++){
        cin >> start[i];
    }
    
    // M번 이동을 위해 필요한 이진 점프 테이블의 높이 결정
    int LOG = 0;
    while((1LL << LOG) <= M) LOG++;
    
    // dp[i][j] : j번 칸에서 2^i번 이동한 후의 칸 번호
    vector<vector<int>> dp(LOG, vector<int>(K + 1, 0));
    
    // dp[0]는 한 번 이동한 결과 (입력 순열)
    for (int j = 1; j <= K; j++){
        cin >> dp[0][j];
    }
    
    // 더블링 테이블 구축: dp[i][j] = dp[i-1][ dp[i-1][j] ]
    for (int i = 1; i < LOG; i++){
        for (int j = 1; j <= K; j++){
            dp[i][j] = dp[i-1][ dp[i-1][j] ];
        }
    }
    
    // 각 시작 위치에서 M번 이동한 최종 위치 계산
    for (int i = 0; i < N; i++){
        int pos = start[i];
        for (int bit = 0; bit < LOG; bit++){
            if(M & (1LL << bit)){
                pos = dp[bit][pos];
            }
        }
        cout << pos << " ";
    }
    
    return 0;
}