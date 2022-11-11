// =================================================================
//
// File: example8.cpp
// Author: Pedro Perez
// Description: This file contains the code that implements the
//				enumeration sort algorithm using Intel's TBB.
//				To compile: g++ example8.cpp -ltbb
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

const int SIZE = 100000;

using namespace std;
using namespace tbb;

class EnumSort
{
private:
	int *array,*b,*c, size;

public:
	EnumSort(int *a,int *b,int *c, int s) : array(a),b(b),c(c), size(s) { }


    void operator() (const blocked_range<int> &r) const {
		for (int i = r.begin(); i != r.end(); i++) {
			for (int j = 0; j < size; j++) {
				if (array[i] > array[j] || array[i] == array[j] && j < i) {
					b[i] += 1;
				}
			}
		}
	}

    void last(){
        for(int i = 0; i < size; i++){
			c[b[i]] = array[i];
		}

		for(int i = 0; i < size; i++){
			array[i] = c[i];
		}
    }
};

int main(int argc, char *argv[])
{
	int *a,*b,*c;
	double ms;

	a = new int[SIZE];
    b = new int[SIZE];
    c = new int[SIZE];
	random_array(a, SIZE);
	display_array("before", a);
	int *aux = new int[SIZE];
    int *aux1 = new int[SIZE];
    int *aux2 = new int[SIZE];

	cout << "Starting..." << endl;
	ms = 0;
	for (int i = 0; i < N; i++) {
		memcpy(aux, a, sizeof(int) * SIZE);
        memcpy(aux1, b, sizeof(int) * SIZE);
        memcpy(aux2, c, sizeof(int) * SIZE);
        start_timer();
		//EnumSort en(aux, SIZE);
		
		// call your method here.
		//en.sort();
        EnumSort obj(aux,aux1,aux2,SIZE);
        parallel_for(blocked_range<int>(0, SIZE),  obj);
        obj.last();
		ms += stop_timer();
	}

	memcpy(a, aux, sizeof(int) * SIZE);
	display_array("after", a);
	cout << "avg time = " << setprecision(15) << (ms / N) << " ms" << endl;
	delete[] a;
	delete [] aux;
	return 0;
}

