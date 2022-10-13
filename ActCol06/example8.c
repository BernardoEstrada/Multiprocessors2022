// =================================================================
//
// File: example8.c
// Author(s):
// Description: This file contains the code that implements the
//        enumeration sort algorithm using OpenMP.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================
// ======Outputs====================================================
// Single Thread
// avg time = 231.18360 ms
//
// OpenMP
// avg time = 43.54290 ms
//
// Speedup = 5.31x


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "utils.h"

#define SIZE 10000

void enumSort(int *arr, int size){
  int i,j;
  int *auxArr1, *auxArr2;

  auxArr1 = (int*) malloc(sizeof(int) * size);
  auxArr2 = (int*) malloc(sizeof(int) * size);

  #pragma omp parallel for private(j) shared(arr, auxArr1, size)
  for(i = 0; i < size; i++){
    for(j = 0; j < size; j++){
      if(arr[i] > arr[j] || arr[i] == arr[j] && j < i){
        auxArr1[i] += 1;
      }
    }
  }

  #pragma omp parallel for shared(arr, auxArr1, auxArr2, size)
  for(i = 0; i < size; i++){
    auxArr2[auxArr1[i]] = arr[i];
  }

  #pragma omp parallel for shared(arr, auxArr2, size)
  for(i = 0; i < size; i++){
    arr[i] = auxArr2[i];
  }
  free(auxArr1);
  free(auxArr2);
}

int main(int argc, char* argv[]) {
  int i, *a;
  double ms;

  a = (int*) malloc(sizeof(int) * SIZE);

  random_array(a, SIZE);
  display_array("Unsorted", a);

  printf("\nStarting...\n\n");

  ms = 0;
  for (i = 0; i < N; i++) {
    start_timer();

    enumSort(a,SIZE);

    ms += stop_timer();
  }

  display_array("Sorted", a);
  printf("avg time = %.5lf ms\n", (ms / N));

  free(a);
  return 0;
}