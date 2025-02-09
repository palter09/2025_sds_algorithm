#include <iostream>
#include <vector>
#include <algorithm>
#include <string>
#include <cstdlib>
using namespace std;

int n, m;
vector<string> board;
vector<vector<int>> dp;         // (x,y)에서 출발할 때 최대 이동 횟수
vector<vector<bool>> visited;   // 현재 DFS 경로상에서 방문한 여부

// 이동 방향: 위, 아래, 왼쪽, 오른쪽
int dx[4] = {-1, 1, 0, 0};
int dy[4] = {0, 0, -1, 1};

int dfs(int x, int y) {
    // 범위를 벗어나거나 구멍('H')이면 게임 종료 => 0 반환
    if(x < 0 || x >= n || y < 0 || y >= m || board[x][y] == 'H')
        return 0;
    
    // 이미 계산된 경우(메모이제이션)
    if(dp[x][y] != 0)
        return dp[x][y];
    
    // 현재 경로에서 재방문 시 무한 루프 발생
    visited[x][y] = true;
    int move = board[x][y] - '0';  // 현재 칸의 숫자(이동할 칸 수)
    
    for(int i = 0; i < 4; i++){
        int nx = x + dx[i] * move;
        int ny = y + dy[i] * move;
        
        // 다음 위치가 범위 밖이거나 구멍이면, 이동할 수 없으므로 1칸 이동한 것으로 처리
        if(nx < 0 || nx >= n || ny < 0 || ny >= m || board[nx][ny] == 'H'){
            dp[x][y] = max(dp[x][y], 1);
        } else {
            // 만약 현재 경로 상에서 이미 방문한 칸이라면 사이클이 발생한 것!
            if(visited[nx][ny]){
                cout << -1 << "\n";
                exit(0);
            }
            dp[x][y] = max(dp[x][y], dfs(nx, ny) + 1);
        }
    }
    visited[x][y] = false;
    return dp[x][y];
}

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    cin >> n >> m;
    board.resize(n);
    for(int i = 0; i < n; i++){
        cin >> board[i];
    }
    
    dp.assign(n, vector<int>(m, 0));
    visited.assign(n, vector<bool>(m, false));
    
    cout << dfs(0, 0) << "\n";
    
    return 0;
}
