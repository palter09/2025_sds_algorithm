#include <iostream>
#include <vector>
using namespace std;

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int N, M, K;
    cin >> N >> M >> K;
    
    // 1번 인덱스부터 사용하기 위해 크기를 N+1로 설정합니다.
    vector<int> f(N + 1);
    for (int i = 1; i <= N; i++) {
        cin >> f[i];
    }
    
    // parent[0] 배열 (1번 인덱스부터 M번까지)
    const int MAX_POW = 31;
    vector<vector<int>> parent(MAX_POW, vector<int>(M + 1));
    for (int i = 1; i <= M; i++) {
        cin >> parent[0][i];
    }
    
    // 이중 for문을 이용해 parent 테이블을 채웁니다.
    for (int i = 1; i < MAX_POW; i++) {
        for (int j = 1; j <= M; j++) {
            parent[i][j] = parent[i - 1][parent[i - 1][j]];
        }
    }
    
    // 각 f[i]에 대해 K-1번 점프 후의 최종 위치를 계산합니다.
    for (int i = 1; i <= N; i++) {
        int num = f[i];
        int k = K - 1;
        for (int j = MAX_POW - 1; j >= 0; j--) {
            if (k >= (1 << j)) {
                num = parent[j][num];
                k -= (1 << j);
            }
        }
        cout << num << " ";
    }
    
    return 0;
}