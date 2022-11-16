// =================================================================
//
// File: example7.cu
// Authors: Martin Noboa - A01704052
// 		   Bernardo Estrada - A01704320
// Description: This file contains the code to brute-force all
//				prime numbers less than MAXIMUM using CUDA.
//
// Copyright (c) 2020 by Tecnologico de Monterrey.
// All Rights Reserved. May be reproduced for any non-commercial
// purpose.
//
// =================================================================

#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <cuda_runtime.h>
#include "utils.h"

#define MAXIMUM 1000000 //1e6
#define THREADS 256
#define BLOCKS	MMIN(32, ((MAXIMUM / THREADS) + 1))


__global__ void prime(int *array){
    int tid = threadIdx.x + (blockIdx.x * blockDim.x);
    int j=2;
    int flag=0;
	while (tid < MAXIMUM) {
            j=2;
            while((j*j)<=tid){
                if(tid%j==0){
                    flag=1;
                    break;
                }
                j++;
            }
            if(flag==0){
                array[tid]=1;
            }
        tid += blockDim.x * gridDim.x;
	}
}

int main(int argc, char* argv[]) {
	int i, *a;
	double ms;
    int * d_a;

	a = (int *) malloc(sizeof(int) * (MAXIMUM + 1));
	printf("At first, neither is a prime. We will display to TOP_VALUE:\n");
	for (i = 2; i < TOP_VALUE; i++) {
		if (a[i] == 0) {
			printf("%i ", i);
		}
	}
	printf("\n");

    cudaMalloc( (void**) &d_a, sizeof(int) * MAXIMUM);
    //cudaMemcpy(d_a, a, sizeof(int) * SIZE, cudaMemcpyHostToDevice);

	printf("Starting...\n");
	ms = 0;
	for (i = 0; i < N; i++) {
		start_timer();

		//primeN(a, MAXIMUM);
        prime<<<BLOCKS, THREADS>>>(d_a);

		ms += stop_timer();
	}

    cudaMemcpy(a,d_a, sizeof(int) * MAXIMUM, cudaMemcpyDeviceToHost);
	printf("Expanding the numbers that are prime to TOP_VALUE:\n");
	for (i = 2; i < TOP_VALUE; i++) {
		if (a[i] == 1) {
			printf("%i ", i);
		}
	}
	printf("\n");
	printf("avg time = %.5lf ms\n", (ms / N));

	free(a);
	return 0;
}
