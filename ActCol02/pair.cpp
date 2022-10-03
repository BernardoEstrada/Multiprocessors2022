// =================================================================
// Multiprocesadores
// File: sumaPares.c
// Author: Martin Noboa - A01704052
// 		   Bernardo Estrada - A01704320
// =================================================================


#include <iostream>
#include <iomanip>
#include <climits>
#include <algorithm>
#include "utils.h"

const int SIZE = 1000000000; //1e9

using namespace std;

class CountArray {
private:
	int *array, size;
	double result;

public:
	CountArray(int *a, int s) : array(a), size(s) {}

	double getResult() const {
		return result;
	}

	void calculate () {
		result = 0;
		for (int i = 0; i < size; i++) {
			if(array[i]%2 == 0){
				result += array[i];
			}
		}
	}
};

int main(int argc, char* argv[]) {
	int *a;
	double ms;

	a = new int[SIZE];
	fill_array(a, SIZE);
	display_array("a", a);

	cout << "Starting..." << endl;
	ms = 0;
	CountArray obj(a, SIZE);
	for (int i = 0; i < N; i++) {
		start_timer();
		obj.calculate();

		ms += stop_timer();
	}
	cout << "sum = " << (long int) obj.getResult() << endl;
	cout << "avg time = " << setprecision(15) << (ms / N) << " ms" << endl;

	delete [] a;
	return 0;
}
