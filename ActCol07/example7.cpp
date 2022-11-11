// =================================================================
//
// File: example7.cpp
// Author: Pedro Perez
// Description: This file contains the code to brute-force all
//				prime numbers less than MAXIMUM using Intel's TBB.
//				To compile: g++ example7.cpp -ltbb
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

#include <iostream>
#include <iomanip>
#include <cstring>
#include <cmath>
#include <tbb/parallel_for.h>
#include <tbb/blocked_range.h>
#include "utils.h"

const int MAXIMUM = 100000; //1e6

using namespace std;
using namespace tbb;

class FindPrimes {
private:
	int *array;
	long int result;

public:
	FindPrimes(int *a) : array(a), result(0) {}

	FindPrimes(FindPrimes &x, split): array(x.array), result(0) {}

	long int getResult() const {
		return result;
	}

	void operator() (const blocked_range<int> &r) {
		for (int i = r.begin(); i != r.end(); i++) {
            if(array[i] < 2){
				isPrime = false;
			} 
			for (int j = 2; j <= array[i]/2; ++j) {
				if (array[i] % j == 0) {
					isPrime = false;
				}
			}
			if(isPrime){
				result+= 1;
			}
		}
	}

};


int main(int argc, char* argv[]) {
	int *a;
	long int result = 0;
	double ms;

	a = new int[SIZE];
	fill_array(a, SIZE);
	cout << "At first, neither is a prime. We will display to TOP_VALUE:\n";
	for (i = 2; i < TOP_VALUE; i++) {
		cout << i << " ";
	}
	cout << "\n";

	cout << "Starting..." << endl;
	ms = 0;
    FindPrimes obj(a,MAXIMUM);
	for (int i = 1; i <= N; i++) {
		start_timer();

		AddArray obj(a);
        parallel_for(blocked_range<int>(0, MAXIMUM),  FindPrimes(a,MAXIMUM));

		ms += stop_timer();
	}
	cout << "Expanding the numbers that are prime to TOP_VALUE:\n";
    double total = 0
	for (i = 2; i < TOP_VALUE; i++) {
		if (a[i] == 1) {
			total += i;
		}
	}
	cout << "avg time = " << setprecision(15) << (ms / N) << " ms" << endl;

	delete [] a;
	return 0;
}

