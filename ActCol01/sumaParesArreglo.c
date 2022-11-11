// =================================================================
// Multiprocesadores
// File: sumaPares.c
// Author: Martin Noboa - A01704052
// Bernardo Estrada - A01704320
// Description: This program implements a sum of even numbers
//              in an array
//
// =================================================================

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "utils.h"

#define SIZE 1000000000

int main(int argc, char* argv[]) {
	int i, *a, result=0;
	double ms;
	a = (int *) malloc(sizeof(int) * SIZE);
  char *txt = "array";

	fill_array(a, SIZE);

	printf("Starting...\n");
	ms = 0;
	for (int i = 1; i <= N; i++) {
		start_timer();
		for (i = 0; i < SIZE; i++) {
			if(a[i]%2 == 0){
				result += a[i];
			}
		}
		ms += stop_timer();
	}

	free(a);
  printf("Resultado: %d\n", result);
	printf("avg time = %f ms\n", (ms / N));

	return 0;
}
