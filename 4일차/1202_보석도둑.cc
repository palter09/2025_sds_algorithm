#include <iostream>
#include <vector>
#include <algorithm>
#include <queue>
using namespace std;

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int N, K;
    cin >> N >> K;
    
    // 보석: {무게, 가치}
    vector<pair<int,int>> jewels(N);
    for (int i = 0; i < N; i++) {
        int weight, value;
        cin >> weight >> value;
        jewels[i] = {weight, value};
    }
    // 무게 오름차순 정렬
    sort(jewels.begin(), jewels.end());
    
    // 각 가방의 최대 수용 무게를 입력받아 정렬
    vector<int> bags(K);
    for (int i = 0; i < K; i++) {
        cin >> bags[i];
    }
    sort(bags.begin(), bags.end());
    
    long long totalValue = 0;
    // 보석의 가치가 큰 순으로 뽑기 위한 최대 힙
    priority_queue<int> available;
    int j = 0;  // 보석 배열의 인덱스
    
    // 가방의 용량이 작은 순서대로 처리
    for (int i = 0; i < K; i++) {
        // 현재 가방에 담을 수 있는 보석들을 모두 available에 추가
        while (j < N && jewels[j].first <= bags[i]) {
            available.push(jewels[j].second);
            j++;
        }
        // 현재 가방에 담을 수 있는 보석이 있다면 가장 가치가 높은 보석 선택
        if (!available.empty()) {
            totalValue += available.top();
            available.pop();
        }
    }
    
    cout << totalValue << "\n";
    return 0;
}
