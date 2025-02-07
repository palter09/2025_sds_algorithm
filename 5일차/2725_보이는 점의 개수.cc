#include <iostream>
#include <vector>
using namespace std;

int main() {
    // 입출력 속도 향상을 위한 설정 (C의 stdio와 C++ iostream의 동기화 해제)
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    // 0부터 1110까지 저장할 수 있는 벡터 생성 (문제에서는 1~1000까지만 사용)
    vector<int> euler(1111);
    
    // 1부터 1000까지 각 숫자에 대해 초기값 할당 (초기에는 phi(n)=n으로 설정)
    for (int i = 1; i <= 1000; i++) {
        euler[i] = i;
    }
    
    // 에라토스테네스의 체와 유사한 방법을 사용하여 오일러 피 함수(phi)를 계산
    // phi(n)은 n과 서로소인 1 이상 n 이하의 정수의 개수
    for (int i = 2; i <= 1000; i++) {
        // 만약 euler[i]가 아직 i라면, i는 소수임을 의미
        if (euler[i] == i) {
            // i의 배수들에 대해 phi 값 갱신
            for (int j = i; j <= 1000; j += i) {
                // 오일러 피 함수의 성질: phi(j) = phi(j) - phi(j)/i
                euler[j] = euler[j] - euler[j] / i;
            }
        }
    }
    
    // 테스트 케이스의 수 입력
    int C;
    cin >> C;
    
    // 각 테스트 케이스에 대해 처리
    for (int i = 0; i < C; i++) {
        int N;
        // 결과를 저장할 변수. 문제의 조건에 따라 초기값 3을 부여 (특별한 경우 혹은 기본값)
        int cnt = 3;
        cin >> N;
        
        // 2부터 N까지 각 j에 대해, 미리 계산해둔 오일러 피 함수 값을 이용하여 cnt에 누적
        // 각 j마다 2 * phi(j)를 더함 (문제의 특정 규칙에 따른 계산)
        for (int j = 2; j <= N; j++) {
            cnt += euler[j] * 2;
        }
        
        // 해당 테스트 케이스의 결과 출력
        cout << cnt << endl;
    }
    
    return 0;
}
