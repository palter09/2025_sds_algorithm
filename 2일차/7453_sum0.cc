#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;
typedef long long ll;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int n;
    cin >> n;
    
    vector<int> A(n), B(n), C(n), D(n);
    for (int i = 0; i < n; i++) {
        cin >> A[i] >> B[i] >> C[i] >> D[i];
    }
    
    // A+B와 C+D의 모든 합을 저장할 배열
    vector<ll> AB;
    vector<ll> CD;
    AB.reserve(n * n);
    CD.reserve(n * n);
    
    // A와 B의 모든 조합의 합
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            AB.push_back((ll)A[i] + B[j]);
        }
    }
    
    // C와 D의 모든 조합의 합
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            CD.push_back((ll)C[i] + D[j]);
        }
    }
    
    // 두 배열 모두 오름차순 정렬
    sort(AB.begin(), AB.end());
    sort(CD.begin(), CD.end());
    
    ll cnt = 0;
    int i = 0;
    int j = CD.size() - 1;
    
    // 두 포인터를 이용하여 AB[i] + CD[j] == 0 인 쌍을 찾음
    while (i < AB.size() && j >= 0) {
        ll sum = AB[i] + CD[j];
        
        if (sum == 0) {
            // 현재 AB[i]와 CD[j] 값과 같은 값의 개수를 각각 센다.
            ll currentAB = AB[i];
            ll currentCD = CD[j];
            ll countAB = 0, countCD = 0;
            
            while (i < AB.size() && AB[i] == currentAB) {
                countAB++;
                i++;
            }
            
            while (j >= 0 && CD[j] == currentCD) {
                countCD++;
                j--;
            }
            
            cnt += countAB * countCD;
        }
        else if (sum < 0) {
            i++;
        }
        else { // sum > 0
            j--;
        }
    }
    
    cout << cnt << "\n";
    return 0;
}
