#include <iostream>
#include <deque>
using namespace std;

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int N, L;
    cin >> N >> L;
    
    // pair: {값, 인덱스}
    deque<pair<int, int>> dq;
    
    for (int i = 0; i < N; i++){
        int num;
        cin >> num;
        
        // 현재 값보다 큰 값들은 앞으로 최소값 후보가 될 수 없으므로 제거
        while (!dq.empty() && dq.back().first > num)
            dq.pop_back();
        
        dq.push_back({num, i});
        
        // 윈도우 범위 밖의 인덱스 제거
        if (dq.front().second <= i - L)
            dq.pop_front();
        
        // 현재 윈도우의 최소값은 덱의 front에 있음
        cout << dq.front().first << " ";
    }
    
    return 0;
}
