#include <iostream>
#include <vector>
using namespace std;

// 최대 조합 값을 계산할 때 사용할 최대 숫자 (여기서는 100까지 계산)
int MAX = 100;

int main() {
    // C++ 표준 입출력과 C의 입출력을 동기화하지 않음으로써 입출력 속도를 향상시킴
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    // 2차원 벡터 d를 생성 (크기: (MAX+1) x (MAX+1)), 모든 원소는 0으로 초기화됨.
    // d[i][j]는 "i choose j" (즉, i C j)의 값을 저장하기 위한 용도임.
    vector<vector<int>> d(MAX+1, vector<int>(MAX+1, 0));
    
    // 기본 조합 값 설정: 0C0 = 1
    d[0][0] = 1;
    
    // 파스칼의 삼각형 원리를 이용해 조합 값을 미리 계산함.
    // i는 행(즉, n의 값), j는 열(즉, r의 값)을 의미함.
    for (int i = 1; i < MAX; i++) {
        // nC0 (즉, iC0)는 항상 1임.
        d[i][0] = 1;
        // 1 <= j <= i 인 경우에 대해 조합 값을 계산
        for (int j = 1; j <= i; j++) {
            // 파스칼의 점화식: nCr = (n-1)C(r-1) + (n-1)Cr
            d[i][j] = d[i-1][j-1] + d[i-1][j];
        }
    }
    
    int T;
    // 테스트 케이스의 개수를 입력받음
    cin >> T;
    
    // 각 테스트 케이스에 대해 계산한 조합 값을 출력함.
    for (int i = 0; i < T; i++) {
        int N, M;
        // 두 정수 N과 M을 입력받음.
        cin >> N >> M;
        
        // 조합은 보통 nCk 형태 (n >= k)로 계산되므로,
        // 입력된 두 수 중 더 큰 수를 n, 더 작은 수를 k로 사용하여 d 배열에서 값을 가져옴.
        if (N > M)
            cout << d[N][M] << endl; // N이 더 큰 경우: N C M
        else
            cout << d[M][N] << endl; // M이 더 큰 경우: M C N
    }
    
    return 0;
}
