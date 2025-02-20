#include <iostream>
#include <vector>
using namespace std;

// 두 벡터를 교차 병합(interleaving)하는 함수
vector<int> merge(const vector<int>& lst1, const vector<int>& lst2) {
    int n = lst1.size();
    vector<int> merged;
    merged.reserve(2 * n);
    for (int i = 0; i < n; i++) {
        merged.push_back(lst1[i]);
        merged.push_back(lst2[i]);
    }
    return merged;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int h, k, r;
    cin >> h >> k >> r;
    int numTasks = 1 << h; // 2^h

    // 2^h개의 task를 입력받음 (각 task는 k개의 정수로 구성)
    vector<vector<int>> tasks(numTasks, vector<int>(k));
    for (int i = 0; i < numTasks; i++) {
        for (int j = 0; j < k; j++) {
            cin >> tasks[i][j];
        }
    }

    // h번의 라운드에 걸쳐 merge 진행
    for (int i = 1; i <= h; i++) {
        int newSize = 1 << (h - i);  // 남은 그룹의 개수: 2^(h-i)
        vector<vector<int>> tasks2(newSize);
        for (int j = 0; j < newSize; j++) {
            if (i % 2 == 1) {
                // 홀수 라운드: 두 번째 그룹을 첫 번째 그룹 앞에 merge
                tasks2[j] = merge(tasks[2 * j + 1], tasks[2 * j]);
            } else {
                // 짝수 라운드: 첫 번째 그룹을 두 번째 그룹 앞에 merge
                tasks2[j] = merge(tasks[2 * j], tasks[2 * j + 1]);
            }
        }
        tasks = move(tasks2);
    }

    // 최종 task(tasks[0])의 앞 (r - h)개의 원소 합산
    int limit = r - h;
    long long ans = 0;
    for (int i = 0; i < limit; i++) {
        ans += tasks[0][i];
    }
    
    cout << ans << "\n";
    return 0;
}
