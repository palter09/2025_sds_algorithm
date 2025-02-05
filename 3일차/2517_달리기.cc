#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

int* tree;  // 구간 트리 (세그먼트 트리)
vector<pair<int, int>> runners; // 각 선수를 {실력, 시작 순서}로 저장

// 구간 트리에서 [left, right] 구간의 합을 구하는 함수
int query(int node, int start, int end, int left, int right) {
    // 구간이 전혀 겹치지 않는 경우
    if (right < start || end < left) {
        return 0;
    }
    // 현재 구간이 [left, right]에 완전히 포함되는 경우
    if (left <= start && end <= right) {
        return tree[node];
    }
    int mid = (start + end) / 2;
    return query(node * 2, start, mid, left, right) +
           query(node * 2 + 1, mid + 1, end, left, right);
}

// idx 위치의 값을 diff 만큼 변화시키는 함수 (여기서는 1을 더해 해당 위치에 선수가 등록됨을 표시)
void update(int node, int start, int end, int idx, int diff) {
    // 범위에 해당하지 않으면 return
    if (idx < start || idx > end)
        return;
    tree[node] += diff;
    if (start != end) {
        int mid = (start + end) / 2;
        update(node * 2, start, mid, idx, diff);
        update(node * 2 + 1, mid + 1, end, idx, diff);
    }
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int N;
    cin >> N;
    
    // 세그먼트 트리 배열 할당 (최대 크기 4*N)
    tree = new int[N * 4];
    fill(tree, tree + N * 4, 0); // 초기값 0
    
    // 입력: 각 선수의 평소 실력. (출발 순서는 입력 순서: 0부터 시작)
    for (int i = 0; i < N; i++){
        int skill;
        cin >> skill;
        runners.push_back({skill, i});
    }
    
    // 평소 실력이 높은 선수일수록 반드시 앞에 있어야 하므로,
    // 평소 실력이 높은 순서(내림차순)로 처리합니다.
    sort(runners.begin(), runners.end(), [](const pair<int,int>& a, const pair<int,int>& b){
        return a.first > b.first;
    });
    
    // 각 선수의 최선 등수를 저장할 배열
    // 최선 등수 = (자신보다 평소 실력이 좋은 선수들 중, 출발 순서상 앞에 이미 등록된 선수의 수) + 1
    vector<int> ans(N);
    
    // 평소 실력이 높은 선수부터 순서대로 처리합니다.
    for (int i = 0; i < N; i++){
        int pos = runners[i].second; // 원래 시작 순서
        int countAhead = query(1, 0, N - 1, 0, pos); // pos보다 앞(혹은 pos 포함)에서 이미 등록된 선수 수
        ans[pos] = countAhead + 1; // 최선의 등수는 (앞에 있는 선수 수 + 1)
        update(1, 0, N - 1, pos, 1); // 해당 위치에 현재 선수 등록 (값 1 추가)
    }
    
    // 원래 출발 순서대로 결과를 출력합니다.
    for (int i = 0; i < N; i++){
        cout << ans[i] << "\n";
    }
    
    delete[] tree;
    return 0;
}
