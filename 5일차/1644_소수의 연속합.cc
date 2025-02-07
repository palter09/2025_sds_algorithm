#include <iostream>
#include <vector>
using namespace std;

int main() {
    // 입출력 속도 향상을 위해 C++ 표준 입출력의 동기화를 해제함
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N;
    cin >> N; // 목표 숫자 N을 입력받음

    int pCount = 0; // N 이하의 소수 개수를 저장할 변수
    vector<int> prime(N+1); // 0부터 N까지의 숫자에 대해 소수 여부를 체크할 배열 (0이면 소수, 1이면 소수가 아님)

    // 에라토스테네스의 체를 이용하여 2부터 N까지의 소수를 판별
    for (int i = 2; i <= N; i++) {
        // prime[i]가 0이면 아직 체크되지 않은 소수임
        if (prime[i] == 0) {
            pCount++; // 소수를 발견하면 소수 개수를 1 증가
            // i의 배수들을 모두 소수가 아님을 표시
            for (int j = i * 2; j <= N; j += i) {
                prime[j] = 1;
            }
        }
    }

    // 소수들의 누적 합(prefix sum)을 저장할 벡터 생성
    // 누적 합 배열의 크기는 pCount+1이며, psum[0]은 0으로 초기화됨
    vector<int> psum(pCount+1);
    int p = 1; // 누적 합 배열의 인덱스 (1부터 시작)

    // 2부터 N까지 순회하며 소수인 경우 누적 합 계산
    for (int i = 2; i <= N; i++) {
        if (prime[i] == 0) { // i가 소수인 경우
            psum[p] = psum[p-1] + i; // 이전 누적 합에 현재 소수를 더함
            p++; // 다음 인덱스로 이동
        }
    }

    // 두 포인터 기법을 이용하여 소수들의 연속합이 N이 되는 경우의 수를 찾음
    int left = 0;  // 구간의 시작 인덱스를 나타내는 포인터
    int right = 1; // 구간의 끝 인덱스를 나타내는 포인터
    int cnt = 0;   // 조건에 맞는 연속합의 개수를 저장할 변수

    // left와 right 모두 누적 합 배열의 인덱스 범위 내에 있고,
    // left < right인 동안 반복 (구간이 비어있지 않은 경우)
    while (left <= pCount && right <= pCount && left < right) {
        // psum[right] - psum[left]는 left+1번째 소수부터 right번째 소수까지의 합
        int val = psum[right] - psum[left];

        // 합이 N과 일치하면 조건에 맞는 경우를 발견
        if (val == N) {
            cnt++; // 경우의 수 증가
        }

        // 현재 합이 N보다 작으면, 합을 늘리기 위해 오른쪽 포인터를 이동
        if (val < N) {
            right++;
        }
        // 현재 합이 N보다 크거나 같으면, 합을 줄이기 위해 왼쪽 포인터를 이동
        else {
            left++;
        }
    }

    // N과 일치하는 소수의 연속합 개수를 출력
    cout << cnt;
    return 0;
}
