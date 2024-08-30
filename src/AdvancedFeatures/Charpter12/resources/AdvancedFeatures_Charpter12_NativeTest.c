#include "AdvancedFeatures_Charpter12_NativeTest.h"
#include <stdio.h>

JNIEXPORT void JNICALL Java_AdvancedFeatures_Charpter12_NativeTest_greeting(JNIEnv * env, jclass cl)
{
    printf("Hello, world!\n");
}