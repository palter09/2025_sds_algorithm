#include <iostream>
#include <queue>
#include <vector>
#include <string>
using namespace std;

int main(){
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    
    int R, C;
    cin >> R >> C;
    
    vector<string> forest(R);
    for (int i = 0; i < R; i++){
        cin >> forest[i];
    }
    
    // 물이 도달하는 시간을 저장
    // 아직 도달하지 못한 칸은 -1로 초기화
    vector<vector<int>> waterTime(R, vector<int>(C, -1));
    queue<pair<int,int>> wq;
    
    // 물의 초기 위치를 큐에 추가
    for (int i = 0; i < R; i++){
        for (int j = 0; j < C; j++){
            if(forest[i][j] == '*'){
                waterTime[i][j] = 0;
                wq.push({i, j});
            }
        }
    }
    
    int dx[4] = {1, -1, 0, 0};
    int dy[4] = {0, 0, 1, -1};
    
    // 물 BFS: 물은 인접한 빈 칸('.')와 고슴도치의 시작 칸('S')로 퍼진다
    // 돌('X')와 비버의 굴('D')는 침수 X
    while(!wq.empty()){
        auto cur = wq.front();
        wq.pop();
        int x = cur.first, y = cur.second;
        for (int d = 0; d < 4; d++){
            int nx = x + dx[d], ny = y + dy[d];
            if(nx < 0 || nx >= R || ny < 0 || ny >= C)
                continue;
            if(forest[nx][ny] == 'X' || forest[nx][ny] == 'D')
                continue;
            if(waterTime[nx][ny] == -1){
                waterTime[nx][ny] = waterTime[x][y] + 1;
                wq.push({nx, ny});
            }
        }
    }
    
    // 고슴도치가 이동하는 시간을 저장
    vector<vector<int>> hedgehogTime(R, vector<int>(C, -1));
    queue<pair<int,int>> hq;
    
    // 고슴도치 시작 위치 찾기
    int startX, startY;
    for (int i = 0; i < R; i++){
        for (int j = 0; j < C; j++){
            if(forest[i][j] == 'S'){
                startX = i;
                startY = j;
                hedgehogTime[i][j] = 0;
                hq.push({i, j});
                break;
            }
        }
    }
    
    bool reached = false;
    int ans = 0;
    
    // 고슴도치 BFS: 매 분마다 인접한 칸으로 이동하면서
    // "다음 분에 물이 차기 전"에 이동 가능한지 hedgehogTime + 1 < waterTime) 확인
    while(!hq.empty()){
        auto cur = hq.front();
        hq.pop();
        int x = cur.first, y = cur.second;
        for (int d = 0; d < 4; d++){
            int nx = x + dx[d], ny = y + dy[d];
            if(nx < 0 || nx >= R || ny < 0 || ny >= C)
                continue;
            // 비버의 굴에 도착한 경우
            if(forest[nx][ny] == 'D'){
                ans = hedgehogTime[x][y] + 1;
                reached = true;
                // 더 이상 탐색할 필요 없으므로 큐 비우기
                while(!hq.empty()) hq.pop();
                break;
            }
            // 고슴도치는 빈 칸('.')나 시작 위치('S')로 이동 가능
            if(forest[nx][ny] == '.' || forest[nx][ny] == 'S'){
                if(hedgehogTime[nx][ny] != -1) continue;
                int nextTime = hedgehogTime[x][y] + 1;
                // 만약 해당 칸에 물이 도착하는 시간이 있다면, 고슴도치가 먼저 도착해야 안전
                if(waterTime[nx][ny] != -1 && nextTime >= waterTime[nx][ny])
                    continue;
                hedgehogTime[nx][ny] = nextTime;
                hq.push({nx, ny});
            }
        }
    }
    
    if(reached)
        cout << ans << "\n";
    else
        cout << "KAKTUS" << "\n";
    
    return 0;
}
