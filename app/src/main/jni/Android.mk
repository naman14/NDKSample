LOCAL_PATH := $(call my-dir)

    include $(CLEAR_VARS)

    LOCAL_MODULE        := ndksample
    LOCAL_SRC_FILES     :=  \
                               ./blur/nativeblur.c \

    LOCAL_LDLIBS := -llog
    LOCAL_CFLAGS = -DSTDC_HEADERS

    include $(BUILD_SHARED_LIBRARY)