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
		if (test(mid)) {
			low = mid;
		}
		else {
			high = mid - 1;
		}
	}

	cout << low;

	return 0;
}

bool test(ll x) {
	ll cost = 0;
	for (int i = 0; i < N; i++) {
		if (a[i] < x) {
			cost += (x - a[i]) * (x - a[i]);
			if (cost > B) return false;
		}
	}
	return cost <= B;
}
