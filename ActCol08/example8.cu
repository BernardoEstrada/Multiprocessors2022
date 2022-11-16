// =================================================================
//
// File: example8.cu
// Authors: Martin Noboa - A01704052
// 		   Bernardo Estrada - A01704320
// Description: This file contains the code that implements the
//				enumeration sort algorithm using CUDA.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <cuda_runtime.h>
#include "utils.h"


#define SIZE 10000
#define THREADS 256
#define BLOCKS	MMIN(32, ((SIZE / THREADS) + 1))

__global__ void nSort(int *a,int *b){
	int j;
	int tid = threadIdx.x + (blockIdx.x * blockDim.x);

    while(tid<SIZE){
		for(j=0;j<SIZE;j++){
			if(a[tid]>a[j]||a[tid]==a[j]&&j<tid){
				b[tid]+=1;
			}
		}
		tid += blockDim.x * gridDim.x;
	}
}

int main(int argc, char* argv[]) {
	int i, *a,*b,*c,*d_a,*d_b;
	double ms;

	a = (int*) malloc(sizeof(int) * SIZE);
	b = (int*) calloc(SIZE,sizeof(int));
	c = (int*) malloc(sizeof(int) * SIZE);
	cudaMalloc( (void**) &d_a, sizeof(int) * SIZE);
	cudaMalloc( (void**) &d_b, sizeof(int) * SIZE);

	random_array(a, SIZE);
	display_array("before", a);

	cudaMemcpy(d_a, a, sizeof(int) * SIZE, cudaMemcpyHostToDevice);
	printf("Starting...\n");
	ms = 0;
	for (i = 0; i < N; i++) {
		cudaMemcpy(d_b, b, sizeof(int) * SIZE, cudaMemcpyHostToDevice);
		start_timer();

		nSort<<<BLOCKS, THREADS>>>(d_a,d_b);

		ms += stop_timer();
	}
	cudaMemcpy(b,d_b, sizeof(int) * SIZE, cudaMemcpyDeviceToHost);

	for(int i=0;i<SIZE;i++){
		c[b[i]]=a[i];
	}

	for(int i=0;i<SIZE;i++){
		a[i]=c[i];
	}

	display_array("after", a);
	printf("avg time = %.5lf ms\n", (ms / N));

	free(a);
	free(b);
	free(c);
	cudaFree(d_a);
	cudaFree(d_b);
	return 0;
}

