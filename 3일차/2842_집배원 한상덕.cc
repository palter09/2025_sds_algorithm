#include <iostream>
#include <algorithm>
#include <vector>
#include <queue>
#include <utility>
#include <string>
using namespace std;

int N;
// 지도 정보: first는 지형 ('P', 'K', 등), second는 높이
vector<vector<pair<char,int>>> mp;
// 모든 칸의 높이를 저장할 벡터 (중복 제거 후 투 포인터에 사용)
vector<int> heights;
pair<int, int> deliver;                  // 배달원(P) 위치
vector<pair<int, int>> destination;      // 집(K)들의 위치

// 8방향 이동 (상, 하, 좌, 우, 대각선)
int d8x[8] = {-1,-1,-1,0,0,1,1,1};
int d8y[8] = {-1,0,1,-1,1,-1,0,1};

// bfs: 현재 [low, high] 높이 구간 내에서 배달원 위치에서 시작하여 모든 집(K)에 도달 가능한지 검사
bool bfs(int low, int high) {
    int startAlt = mp[deliver.first][deliver.second].second;
    if (startAlt < low || startAlt > high)
        return false;
    
    vector<vector<bool>> visited(N, vector<bool>(N, false));
    queue<pair<int, int>> q;
    q.push(deliver);
    visited[deliver.first][deliver.second] = true;
    
    int count = 0; // 도달한 집(K)의 개수
    while (!q.empty()) {
        auto cur = q.front();
        q.pop();
        int x = cur.first, y = cur.second;
        if (mp[x][y].first == 'K')
            count++;
        for (int i = 0; i < 8; i++) {
            int nx = x + d8x[i];
            int ny = y + d8y[i];
            if (nx < 0 || ny < 0 || nx >= N || ny >= N)
                continue;
            if (visited[nx][ny])
                continue;
            int alt = mp[nx][ny].second;
            if (alt < low || alt > high)
                continue;
            visited[nx][ny] = true;
            q.push({nx, ny});
        }
    }
    return (count == destination.size());
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    cin >> N;
    mp.resize(N, vector<pair<char,int>>(N));
    
    // 지도 입력: 한 줄씩 읽고 각 문자별로 처리
    for (int i = 0; i < N; i++) {
        string line;
        cin >> line;  // 한 줄 전체를 읽음
        for (int j = 0; j < N; j++) {
            char c = line[j];
            mp[i][j].first = c;
            mp[i][j].second = 0;  // 높이는 이후 입력에서 채움
            if (c == 'P') {
                deliver = {i, j};
            }
            if (c == 'K') {
                destination.push_back({i, j});
            }
        }
    }
    
    // 각 칸의 높이 입력 및 heights 벡터에 기록
    for (int i = 0; i < N; i++) {
        for (int j = 0; j < N; j++) {
            int h;
            cin >> h;
            mp[i][j].second = h;
            heights.push_back(h);
        }
    }
    
    // heights 정렬 및 중복 제거
    sort(heights.begin(), heights.end());
    heights.erase(unique(heights.begin(), heights.end()), heights.end());
    
    int ans = 1e9;
    int left = 0, right = 0;
    
    // 투 포인터: heights[left] ~ heights[right]를 높이 구간으로 하여 bfs 검사
    while (left < heights.size() && right < heights.size()) {
        int low = heights[left];
        int high = heights[right];
        
        // 배달원의 높이가 구간에 포함되어 있지 않으면 구간 확장
        int startAlt = mp[deliver.first][deliver.second].second;
        if (startAlt < low || startAlt > high) {
            right++;
            continue;
        }
        
        if (bfs(low, high)) {
            ans = min(ans, high - low);
            left++;
        } else {
            right++;
        }
    }
    
    cout << ans << "\n";
    return 0;
}
