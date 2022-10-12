// =================================================================
//
// File: example4.c
// Author(s):
// Description: This file contains the code to count the number of
//				even numbers within an array using OpenMP.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================
// ======Outputs====================================================
// Single Thread
// count = 50003777
// avg time = 895.55570 ms
//
// OpenMP
// count = 50000478
// avg time = 143.00200 ms
//
// Speedup = 6.26x

#include <stdio.h>
#include <stdlib.h>
#include "utils.h"

#define SIZE 100000000

// count number of even numbers in an array using OpenMP
double countEven(int *a, int size) {
  int i;
  double sum = 0;

  #pragma omp parallel for reduction(+:sum)
  for (i = 0; i < size; i++) {
    if (a[i] % 2 == 0) {
      sum++;
    }
  }
  return sum;
}

int main(int argc, char* argv[]) {
  int *a, i;
  double ms, sum;

  printf("\nOpenMP \n");
  a = (int *) malloc(SIZE * sizeof(int));
  random_array(a, SIZE);

	printf("Starting...\n");
	ms = 0;
	for (i = 0; i < N; i++) {
		start_timer();
		sum = countEven(a, SIZE);
		ms += stop_timer();
	}
	printf("count = %.0f\n", sum);
	printf("avg time = %.5lf ms\n", (ms / N));
  
  return 0;
}
