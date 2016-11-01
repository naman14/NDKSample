//
// Created by Naman on 01/11/16.
//

#include <jni.h>
#include "blur/nativeblur.h"
#include "fibonacci/fibonacci.h"

JNIEXPORT void JNICALL Java_com_naman14_ndksample_blur_NativeBlur_startNativeBlur(
        JNIEnv *env, jclass clzz, jobject bitmapOut, jint radius, jint threadCount,
        jint threadIndex, jint round) {
    nativeBlur(env, clzz, bitmapOut, radius, threadCount, threadIndex, round);
}

JNIEXPORT void JNICALL Java_com_naman14_ndksample_fibonacci_FibonacciActivity_startFibNative(
        JNIEnv *env, jclass clazz, jint arg) {

    fibNative((int) arg, env);

}