//
// Created by Naman on 01/11/16.
//

#ifndef NDKSAMPLE_NATIVEBLUR_H
#define NDKSAMPLE_NATIVEBLUR_H

void nativeBlur(JNIEnv* env, jclass clzz, jobject bitmapOut, jint radius, jint threadCount, jint threadIndex, jint round);

#endif //NDKSAMPLE_NATIVEBLUR_H
