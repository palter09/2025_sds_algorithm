#include <iostream>
#include <algorithm>
#include <stack>
using namespace std;

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int N;
    cin >> N;
    int max_area = 0;
    
    // (높이, 시작 인덱스)를 저장하는 스택
    stack<pair<int, int>> s;
    
    for (int i = 1; i <= N; i++){
        int h;
        cin >> h;
        // 현재 막대가 확장 가능한 시작 인덱스
        int start = i;
        
        // 스택의 top의 높이가 현재 높이보다 크거나 같으면 pop
        while (!s.empty() && s.top().first >= h) {
            int height = s.top().first;
            int idx = s.top().second;
            s.pop();
            // 해당 높이가 [idx, i-1]까지 확장될 수 있음
            max_area = max(max_area, height * (i - idx));
            // 새로운 막대는 더 왼쪽에서 시작할 수 있음
            start = idx;
        }
        
        // 현재 높이 h가 시작되는 인덱스는 start
        s.push({h, start});
    }
    
    // 모든 막대를 처리한 후, 남은 막대들에 대해 cleanup
    int i = N + 1;
    while (!s.empty()){
        int height = s.top().first;
        int idx = s.top().second;
        s.pop();
        max_area = max(max_area, height * (i - idx));
    }
    
    cout << max_area;
    return 0;
}
