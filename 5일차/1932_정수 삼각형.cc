#include <iostream>
#include <string>
#include <vector>
using namespace std;

// (i, j) 위치에서 삼각형의 최하단까지 도달하는 경로 중 합이 최대가 되는 값을 반환하는 재귀 함수의 선언
int solve(int i, int j, int n);

// 삼각형의 각 원소들을 저장하는 2차원 벡터
vector<vector<int>> a;

// 동적 프로그래밍(DP)을 위한 메모이제이션 배열 (이미 계산한 경로 합을 저장)
vector<vector<int>> d;

int main() {
    // C++ 입출력 속도 최적화: C와 C++ 입출력의 동기화를 해제하고, cin.tie(nullptr)를 사용하여 불필요한 입출력 버퍼 동기화를 없앰
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int n;  // 삼각형의 행 수
    cin >> n;
    
    // 삼각형 배열과 DP 배열을 (n+1) x (n+1) 크기로 생성하며 모든 원소를 0으로 초기화
    // 여기서 n+1을 사용하는 이유는 인덱스를 1부터 n까지 사용하고, 0번째 행은 더미(dummy)로 남겨두기 위함임
    a.assign(n+1, vector<int>(n+1, 0));
    d.assign(n+1, vector<int>(n+1, 0));

    // 삼각형 형태의 입력을 받음 (행 번호 1부터 n까지)
    // 각 행 i는 i개의 숫자를 가짐 (인덱스 0부터 i-1까지 사용)
    for (int i = 1; i <= n; i++) {
        for (int j = 0; j < i; j++) {
            int num;
            cin >> num;
            a[i][j] = num;  // 삼각형의 (i, j) 위치에 숫자 저장
        }
    }
    
    // 0번째 행은 더미 행(값이 0)이며, 실제 삼각형은 1번 행부터 시작함
    // 최상단에서부터 삼각형의 최대 경로 합을 구하여 출력함
    cout << solve(0, 0, n);
}

// (i, j) 위치에서 삼각형의 최하단까지 도달하는 경로 중 합이 최대가 되는 값을 반환하는 함수
int solve(int i, int j, int n) {
    // 기저 조건: 마지막 행(n행)에 도달하면, 현재 위치의 값을 반환
    if (i == n) {
        return a[i][j];
    }
    
    // 이미 (i, j)에서의 최대 경로 합을 계산한 경우, 메모이제이션 배열에 저장된 값을 반환하여 중복 계산을 피함
    if (d[i][j] != 0) {
        return d[i][j];
    }
    
    // 재귀적으로 아래 두 경로(왼쪽 아래와 오른쪽 아래)의 최대 경로 합을 구함
    int left = solve(i + 1, j, n);
    int right = solve(i + 1, j + 1, n);
    
    // 현재 위치의 값(a[i][j])과 두 경로 중 큰 값의 합을 계산하여 DP 배열에 저장
    d[i][j] = max(left, right) + a[i][j];
    return d[i][j];
}
