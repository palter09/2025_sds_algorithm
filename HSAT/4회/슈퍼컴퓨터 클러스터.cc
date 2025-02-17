#include <iostream>
#include <vector>
#include <cmath>
using namespace std;

typedef long long ll;
int N;
vector<ll> a;
ll B;

bool test(ll x);

int main() {
	ios::sync_with_stdio(false);
	cin.tie(nullptr);

	cin >> N >> B;

	a.resize(N);
	for (int i = 0; i < N; i++) {
		cin >> a[i];
	}

	ll low = 1, high = 2000000000LL;
	while (low < high) {
		int mid = (low + high + 1) / 2; // high = low+1 인 경우에 무한루프가 돌아서 +1 해줌
		if (test(mid)) { // mid까지 성능을 올릴 수 있으면 low를 mid로 올림
			low = mid;
		}
		else { // mid가 안되면 high를 mid로 내림
			high = mid - 1;
		}
	}

	cout << low; // low와 high가 같아져서 출력되니 low 출력

	return 0;
}

// 성능 x에 도달하기 위한 비용을 계산해서 B와 비교하고, 작거나 같은지 테스트하는 함수
bool test(ll x) {
	ll cost = 0;
	for (int i = 0; i < N; i++) {
		if (a[i] < x) { // 현재 성능이 원하는 성능보다 작은지 확인
			cost += (x - a[i]) * (x - a[i]); // 작으면 성능을 올리고 비용 계산
			if (cost > B) return false;
		}
	}
	return cost <= B;
}
