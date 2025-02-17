#include <iostream>
#include <vector>
using namespace std;

int N, T;
vector<vector<vector<int>>> signal;

// 들어오는 방향 4개, 나가는 방향 4개
// 나갈수 있으면 1, 없으면 0
// 좌:0, 하:1, 우:2, 상:3
// [신호번호][들어오는 방향][나가는 방향]
vector<vector<vector<int>>> signalInfo(13, vector<vector<int>>(4, vector<int>(4, 0)));

// 교차로에 차가 현재 어디어디 있는지 기록
// 들어오는 방향에 따라 나가는 방향이 결정되기 때문에 N*N*4 크기
vector<vector<vector<int>>> junction;

// 차의 다음 위치 기록
// [row][col][들어오는 방향]
vector<vector<vector<int>>> junction2;

// 과거 기록
vector<vector<int>> visit;

void update(int time, int i, int j, int inDir, int outDir);

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	//signalInfo[1][0][1] = signalInfo[1][0][2] = signalInfo[1][0][3] = 1;
	//signalInfo[2][1][2] = signalInfo[2][1][3] = signalInfo[2][1][0] = 1;
	//signalInfo[3][2][3] = signalInfo[3][2][0] = signalInfo[3][2][1] = 1;
	//signalInfo[4][3][0] = signalInfo[4][3][1] = signalInfo[4][3][2] = 1;
	//    (i+1) % 4             (i+2) % 4              (i+3) % 4  
	for (int i = 0; i < 4; i++) {
		// 신호 1~4
		signalInfo[i + 1][i][(i + 1) % 4] = 1;
		signalInfo[i + 1][i][(i + 2) % 4] = 1;
		signalInfo[i + 1][i][(i + 3) % 4] = 1;
		// 신호 5~8
		signalInfo[i + 5][i][(i + 2) % 4] = 1;
		signalInfo[i + 5][i][(i + 3) % 4] = 1;
		// 신호 9~12
		signalInfo[i + 9][i][(i + 1) % 4] = 1;
		signalInfo[i + 9][i][(i + 2) % 4] = 1;
	}

	cin >> N >> T;

	signal.resize(N+1, vector<vector<int>>(N+1, vector<int>(5)));
	for (int i = 0; i < N; i++) {
		for (int j = 0; j < N; j++) {
			for (int k = 0; k < 4; k++) {
				cin >> signal[i][j][k];
			}
		}
	}

	junction.resize(N + 1, vector<vector<int>>(N + 1, vector<int>(4)));
	junction[0][0][1] = 1; // 초기 자동차 위치

	junction2.resize(N + 1, vector<vector<int>>(N + 1, vector<int>(4)));
	visit.resize(N, vector<int>(N));
	visit[0][0] = 1;

	for (int time = 0; time < T; time++) {
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				for (int inDir = 0; inDir < 4; inDir++) {

					if (junction[i][j][inDir]) { // i,j 교차로에서 inDir이 가능한 경우만 고려
						for (int outDir = 0; outDir < 4; outDir++) {
							update(time, i, j, inDir, outDir);
						}
						junction[i][j][inDir] = 0;
					}
					
				}
			}
		}

		junction.swap(junction2);
	}

	int count = 0;
	for (int i = 0; i < N;i++) {
		for (int j = 0; j < N; j++) {
			if (visit[i][j] == 1) {
				count += 1;
			}
		}
	}
	cout << count;

	return 0;
}

void update(int time, int i, int j, int inDir, int outDir) {
	int signalNow = signal[i][j][time % 4];
	if (signalInfo[signalNow][inDir][outDir]) { // 현재 신호가 존재하는 신호면
		if (outDir == 0 && j != 0) { // 나가는 방향이 0이면 받는 입장에선 2로 받음
			junction2[i][j - 1][2] = 1;
			visit[i][j - 1] = 1;
		}

		if (outDir == 1 && i != N-1) { // 나가는 방향이 1이면 받는 입장에선 3으로 받음
			junction2[i + 1][j][3] = 1;
			visit[i + 1][j] = 1;
		}

		if (outDir == 2 && j != N-1) { // 나가는 방향이 2이면 받는 입장에선 0으로 받음
			junction2[i][j + 1][0] = 1;
			visit[i][j + 1] = 1;
		}

		if (outDir == 3 && i != 0) { // 나가는 방향이 3이면 받는 입장에선 1로 받음
			junction2[i - 1][j][1] = 1;
			visit[i - 1][j] = 1;
		}
	}
	return;
}
