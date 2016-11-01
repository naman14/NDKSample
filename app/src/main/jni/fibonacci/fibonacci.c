//
// Created by Naman on 01/11/16.
//

#include "fibonacci.h"
#include <jni.h>

void fibNative(int n, JNIEnv *env) {
    int i = 0, c;

    for (c = 1; c <= n; c++) {
        int numberPrinted = i;
        i++;
        sendToJava(fib(i), numberPrinted, env);
    }
}

int fib(int n) {
    if (n == 0)
        return 0;
    else if (n == 1)
        return 1;
    else
        return (fib(n - 1) + fib(n - 2));
}

void sendToJava(int n, int numberPrinted, JNIEnv *globalEnv) {

    jclass clazz = (*globalEnv)->FindClass(globalEnv,
                                           "com/naman14/ndksample/fibonacci/FibonacciActivity");
    jmethodID send = (*globalEnv)->GetStaticMethodID(globalEnv, clazz, "numberRecieved",
                                                     "(II)V");
    (*globalEnv)->CallStaticVoidMethod(globalEnv, clazz, send, n, numberPrinted);
    (*globalEnv)->DeleteLocalRef(globalEnv, clazz);

}

