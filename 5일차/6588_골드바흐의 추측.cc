<span style="font-size: 12px;>
#include <iostream>
#include <vector>
using namespace std;

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    const int MAX = 1000000;
    // vector<bool>를 사용하면 메모리 사용도 줄어듭니다.
    vector<bool> isPrime(MAX + 1, true);
    isPrime[0] = isPrime[1] = false;
    
    // 에라토스테네스의 체 – i*i부터 시작하여 최적화
    for (int i = 2; i * i <= MAX; i++){
        if (isPrime[i]){
            for (int j = i * i; j <= MAX; j += i){
                isPrime[j] = false;
            }
        }
    }
    
    while (true) {
        int n;
        cin >> n;
        if (n == 0) break;
        
        bool found = false;
        // n = p + q에서 p가 더 작으므로, p <= n/2만 확인하면 충분
        // 그리고 2를 제외한 소수는 홀수이므로, 3부터 홀수만 확인
        for (int i = 3; i <= n / 2; i += 2) {
            if (isPrime[i] && isPrime[n - i]) {
                cout << n << " = " << i << " + " << n - i << "\n";
                found = true;
                break;
            }
        }
        
        if (!found) {
            cout << "Goldbach's conjecture is wrong." << "\n";
        }
    }
    return 0;
}

</span>
