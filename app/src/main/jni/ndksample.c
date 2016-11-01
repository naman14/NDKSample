//
// Created by Naman on 01/11/16.
//

#include <jni.h>
#include "blur/nativeblur.h"

JNIEXPORT void JNICALL Java_com_naman14_ndksample_blur_NativeBlur_nativeBlur(
        JNIEnv *env, jclass clzz, jobject bitmapOut, jint radius, jint threadCount,
        jint threadIndex, jint round) {
    nativeBlur(env, clzz, bitmapOut, radius, threadCount, threadIndex, round);
}
