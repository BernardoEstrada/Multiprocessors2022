// =================================================================
//
// File: example12.c
// Author: Pedro Perez
// Description: This file implements the merge sort algorithm. The
//				time this implementation takes will be used as the
//				basis to calculate the improvement obtained with
//				parallel technologies.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "utils.h"

#define SIZE 100000000 //1e7
#define GRAIN 10000 // 1e3

void swap(int *a, int i, int j) {
	int aux = a[i];
	a[i] = a[j];
	a[j] = aux;
}

void copy_array(int *A, int *B, int low, int high) {
	int size = high - low + 1;
	memcpy(A + low, B + low, sizeof(int) * size);
}

void merge(int *A, int *B, int low, int mid, int high) {
    int i, j, k;
    i = low;
    j = mid + 1;
    k = low;
    while(i <= mid && j <= high){
        if(A[i] < A[j]){
            B[k] = A[i];
            i++;
        }else{
            B[k] = A[j];
            j++;
        }
        k++;
    }
    for(; j <= high; j++){
        B[k++] = A[j];
    }

	for(; i <= mid; i++){
        B[k++] = A[i];
    }
}

void split(int *A, int *B, int low, int high) {
    int  mid, size, i, j;

	size = high - low + 1;
	if(size < GRAIN) {
        for(i = low + 1; i < size; i++){
			for(j = i; j > low && A[j] < A[j - 1]; j--){
				swap(A, j, j - 1);
			}
		}
		return;
    }

    mid = low + ((high - low) / 2);
    split(A, B, low, mid);
    split(A, B, mid +1, high);
    merge(A, B,low, mid, high);
    copy_array(A,B, low, high);
}

void merge_sort(int *A, int size) {
	int *B = (int*) malloc(sizeof(int) * size);
	split(A, B, 0, size - 1);
	free(B);
}

int main(int argc, char* argv[]) {
	int i, j, *a, *aux;
	double ms;

	a = (int*) malloc(sizeof(int) * SIZE);
	aux = (int*) malloc(sizeof(int) * SIZE);
	random_array(a, SIZE);
	display_array("before", a);

	printf("Starting...\n");
	ms = 0;
	for (i = 0; i < N; i++) {
		start_timer();

		memcpy(aux, a, sizeof(int) * SIZE);
		merge_sort(aux, SIZE);

		ms += stop_timer();

		if (i == (N - 1)) {
			memcpy(a, aux, sizeof(int) * SIZE);
		}
	}
	display_array("after", a);
	printf("avg time = %.5lf ms\n", (ms / N));

	free(a); free(aux);
	return 0;
}
