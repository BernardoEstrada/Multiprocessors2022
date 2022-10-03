// =================================================================
// Multiprocesadores
// File: enumerationSort.c
// Author: Martin Noboa - A01704052
// Bernardo Estrada - A01704320
// Description: This program implements Enumeration Sort
//
// =================================================================

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "utils.h"

#define SIZE 10000

void moveToPositions(int* arr, int* pos, int size);
void fillOutPositions(int* arr, int* pos, int size);
void enumSort(int* arr, int size);

int main(int argc, char* argv[]) {
  int *arr = (int *) malloc(sizeof(int) * SIZE);
  random_array(arr, SIZE);

  display_array("unsorted", arr);
  enumSort(arr, SIZE);
  display_array("  sorted", arr);

  free(arr);
  return 0;
}

void enumSort(int* arr, int size) {
  int i,
      j,
      *pos;
  pos = (int *) calloc(size, sizeof(int));
  fillOutPositions(arr, pos, size);
  moveToPositions(arr, pos, size);
  free(pos);
}

void fillOutPositions(int* arr, int* pos, int size) {
  int i, j;
  for(i = 0; i < size; i++){
    for(j = 0; j < size; j++){
      if(arr[i] < arr[j] || (arr[i] == arr[j] && i < j)){
        pos[j] += 1;
      }
    }
  }
}

void moveToPositions(int* arr, int* pos, int size) {
  int i, *temp;
  temp = malloc(sizeof(int) * size);

  for(i = 0; i < size; i++)
    temp[pos[i]] = arr[i];
  for(i = 0; i < size; i++)
    arr[i] = temp[i];

  free(temp);
}
