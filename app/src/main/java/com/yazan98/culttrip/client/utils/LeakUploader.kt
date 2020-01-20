package com.yazan98.culttrip.client.utils

import leakcanary.DefaultOnHeapAnalyzedListener
import leakcanary.OnHeapAnalyzedListener
import shark.HeapAnalysis

class LeakUploader : OnHeapAnalyzedListener {
    val defaultListener = DefaultOnHeapAnalyzedListener.create()
    override fun onHeapAnalyzed(heapAnalysis: HeapAnalysis) {
        defaultListener.onHeapAnalyzed(heapAnalysis)
    }
}
