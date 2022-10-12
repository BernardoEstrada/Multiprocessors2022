// =================================================================
//
// File: example7.c
// Author(s):
// Description: This file contains the code to brute-force all
//				prime numbers less than MAXIMUM using OpenMP.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================
// ======Outputs====================================================
// Single Thread
// sum = 37550402023
// avg time = 120.07190 ms
//
// OpenMP
// sum = 37550402023
// avg time = 20.87950 ms
//
// Speedup = 5.75x

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "utils.h"

#define SIZE 1e6

int isPrime(int n){
  int i;
  if(n < 2) return 0;
  for(i = 2; i <= sqrt(n); i++)
    if(n%i == 0)
      return 0;
  return 1;
}

double sumPrimes(int size) {
  int i;
  double sum;

  sum = 0;
  #pragma omp parallel for shared(size) reduction(+:sum)
  for (i = 0; i < size; i++) {
    if(isPrime(i)) {
      sum += i;
    }
  }
  return sum;
}

int main(int argc, char* argv[]) {
  int i;
  double ms, sum;

  printf("\nOpenMP \n");

	printf("Starting...\n");
	ms = 0;
	for (i = 0; i < N; i++) {
		start_timer();
		sum = sumPrimes(SIZE);
		ms += stop_timer();
	}
	printf("\nsum = %.0f\n", sum);
	printf("exp = 37550402023\n");
	printf("avg time = %.5lf ms\n", (ms / N));

  return 0;
}