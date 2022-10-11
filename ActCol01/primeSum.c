// =================================================================
// Multiprocesadores
// File: enumerationSort.c
// Author: Martin Noboa - A01704052
// Bernardo Estrada - A01704320
// Description: This program implements sum of all prime numbers
//
// =================================================================

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <locale.h>
#include "utils.h"

#define SIZE 1000000000
#define LIMIT 1000000000

int isPrime(int n);

int main(int argc, char* argv[]) {
  int *a, i, sum;
  double ms;

  a = (int *) malloc(SIZE * sizeof(int));
  fill_array(a, SIZE);

  char *oldLocale = setlocale(LC_NUMERIC, NULL);

	printf("Starting...\n");
	ms = 0;
	for (i = 0; i < N; i++) {
		start_timer();
		
		for(i = 0; i < LIMIT; i++)
      if(isPrime(i))
        sum += i;
		
		ms += stop_timer();
	}
	printf("sum = %d\n", sum);
	printf("avg time = %.5lf ms\n", (ms / N));


  setlocale(LC_NUMERIC, "");
  printf("%'.0d\n", sum);

  setlocale(LC_NUMERIC, oldLocale);
  free(a);
  return 0;
}

int isPrime(int n){
  int i;
  if(n < 2) return 0;
  for(i = 2; i <= sqrt(n); i++)
    if(n%i == 0)
      return 0;
  return 1;
}
