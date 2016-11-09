//
// Created by Naman on 01/11/16.
//

#include <jni.h>
#include "blur/nativeblur.h"
#include "fibonacci/fibonacci.h"
#include "lodepng/displayinfo.h"
#include <android/log.h>

JNIEXPORT void JNICALL Java_com_naman14_ndksample_blur_NativeBlur_startNativeBlur(
        JNIEnv *env, jclass clzz, jobject bitmapOut, jint radius, jint threadCount,
        jint threadIndex, jint round) {
    nativeBlur(env, clzz, bitmapOut, radius, threadCount, threadIndex, round);
}

JNIEXPORT void JNICALL Java_com_naman14_ndksample_fibonacci_FibonacciActivity_startFibNative(
        JNIEnv *env, jclass clazz, jint arg) {

    fibNative((int) arg, env);

}

JNIEXPORT jstring JNICALL Java_com_naman14_ndksample_lodepng_PNGInfoActivity_getPngInfo(
        JNIEnv *env, jclass clazz, jstring file) {

    const char *filename = (*env)->GetStringUTFChars(env, file, 0);
    const char *info = getPngInfo(filename);

    return (*env)->NewStringUTF(env, info);

}