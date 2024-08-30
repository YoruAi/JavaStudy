#include "AdvancedFeatures_Chapter12_NativeTest.h"
#include <stdio.h>

JNIEXPORT void JNICALL Java_AdvancedFeatures_Chapter12_NativeTest_greeting(JNIEnv * env, jclass cl)
{
    printf("Hello, world!\n");
}