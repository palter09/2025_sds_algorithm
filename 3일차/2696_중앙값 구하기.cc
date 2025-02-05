#include <iostream>
#include <queue>
#include <vector>
using namespace std;

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int T;
    cin >> T;
    
    while(T--){
        int M;
        cin >> M;
        
        // 중앙값의 개수는 (M+1)/2 (문제에서 M은 홀수임)
        vector<int> medians;
        medians.reserve((M+1)/2);
        
        // max_heap: 왼쪽 절반 (내림차순, 중앙값 관리용)
        priority_queue<int> max_heap;
        // min_heap: 오른쪽 절반 (오름차순)
        priority_queue<int, vector<int>, greater<int>> min_heap;
        
        for (int i = 0; i < M; i++){
            int num;
            cin >> num;
            
            // 첫 원소부터 max_heap에 넣습니다.
            if(max_heap.empty() || num <= max_heap.top()){
                max_heap.push(num);
            } else {
                min_heap.push(num);
            }
            
            // 두 힙의 크기 균형을 맞춥니다.
            if(max_heap.size() > min_heap.size() + 1){
                min_heap.push(max_heap.top());
                max_heap.pop();
            }
            else if(min_heap.size() > max_heap.size()){
                max_heap.push(min_heap.top());
                min_heap.pop();
            }
            
            // 0번 인덱스부터 시작하여, 지금까지의 원소 개수가 홀수이면 중앙값(max_heap.top())을 기록
            if(i % 2 == 0){  // (i가 짝수이면 입력받은 개수는 i+1로 홀수입니다.)
                medians.push_back(max_heap.top());
            }
        }
        
        // 출력: 첫 줄에 중앙값의 개수를 출력
        cout << medians.size() << "\n";
        
        // 이후 중앙값들을 한 줄에 10개씩 출력합니다.
        for (int i = 0; i < medians.size(); i++){
            cout << medians[i] << " ";
            if((i + 1) % 10 == 0)
                cout << "\n";
        }
        cout << "\n";
    }
    
    return 0;
}
