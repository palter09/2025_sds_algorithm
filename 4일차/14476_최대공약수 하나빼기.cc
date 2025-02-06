#include <iostream>
#include <vector>
using namespace std;

// 유클리드 호제법을 이용한 최대공약수 함수
long gcd(long a, long b) {
    while(b != 0) {
        long r = a % b;
        a = b;
        b = r;
    }
    return a;
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int N;
    cin >> N;
    vector<int> arr(N);
    for (int i = 0; i < N; i++){
        cin >> arr[i];
    }
    
    // prefix[i] : arr[0] ~ arr[i]의 GCD
    // suffix[i] : arr[i] ~ arr[N-1]의 GCD
    vector<int> prefix(N), suffix(N);
    
    prefix[0] = arr[0];
    for (int i = 1; i < N; i++){
        prefix[i] = gcd(prefix[i-1], arr[i]);
    }
    
    suffix[N-1] = arr[N-1];
    for (int i = N-2; i >= 0; i--){
        suffix[i] = gcd(suffix[i+1], arr[i]);
    }
    
    // 조건을 만족하면서 최대의 GCD와 제거한 수를 저장할 변수
    int bestGCD = 0;
    int removedNumber = 0;
    bool validFound = false;
    
    // 각 원소를 제거하는 경우를 모두 고려
    for (int i = 0; i < N; i++){
        int candidateGCD;
        if (i == 0)
            candidateGCD = suffix[1];
        else if (i == N - 1)
            candidateGCD = prefix[N-2];
        else
            candidateGCD = gcd(prefix[i-1], suffix[i+1]);
        
        // 제거한 수가 candidateGCD의 약수이면(즉, candidateGCD로 나누어 떨어지면) 실패
        if (arr[i] % candidateGCD == 0)
            continue;
        
        // 조건을 만족하는 경우 중 최대의 GCD인 경우 갱신
        if (candidateGCD > bestGCD) {
            bestGCD = candidateGCD;
            removedNumber = arr[i];
            validFound = true;
        }
    }
    
    if(validFound)
        cout << bestGCD << " " << removedNumber;
    else
        cout << -1;
    
    return 0;
}
