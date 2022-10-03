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

#define SIZE 1000000
#define LIMIT 5000000

int isPrime(int n);

int main(int argc, char* argv[]) {
  double sum = 0;
  int i;
  char *oldLocale = setlocale(LC_NUMERIC, NULL);

  for(i = 0; i < LIMIT; i++)
    if(isPrime(i))
      sum += i;

  setlocale(LC_NUMERIC, "");
  printf("%'.0f\n", sum);

  setlocale(LC_NUMERIC, oldLocale);
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
