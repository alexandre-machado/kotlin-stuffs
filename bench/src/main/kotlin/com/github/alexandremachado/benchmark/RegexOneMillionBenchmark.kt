package com.github.alexandremachado.benchmark

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Warmup

/**
 * Benchmark específico para 1 milhão de interações com regex
 */
@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
class RegexOneMillionBenchmark {

    // Texto pequeno para garantir que é a operação de regex que estamos medindo
    private val sampleText = "This is a sample paragraph with some sentences. Another sentence here! And one more sentence?\n\nA new paragraph starts here. With multiple sentences. And more text!"

    @Benchmark
    fun oneMillionRecreatingRegex(): Int {
        var count = 0
        repeat(1_000_000) { 
            val paragraphs = Regex("\n{2,}")
            val sentence = Regex("(?<=[.!?])\\s+")
            
            val paragraphsList = paragraphs.split(sampleText)
            for (paragraph in paragraphsList) {
                val sentences = sentence.split(paragraph)
                count += sentences.size
            }
        }
        return count
    }

    @Benchmark
    fun oneMillionUsingSharedInstance(): Int {
        var count = 0
        val patterns = object {
            val paragraphs = Regex("\n{2,}")
            val sentence = Regex("(?<=[.!?])\\s+")
        }
        
        repeat(1_000_000) { 
            val paragraphsList = patterns.paragraphs.split(sampleText)
            for (paragraph in paragraphsList) {
                val sentences = patterns.sentence.split(paragraph)
                count += sentences.size
            }
        }
        return count
    }

    @Benchmark
    fun oneMillionUsingMessageChunker(): Int {
        var count = 0
        
        repeat(1_000_000) { 
            val paragraphsList = MessageChunker.Patterns.paragraphs.split(sampleText)
            for (paragraph in paragraphsList) {
                val sentences = MessageChunker.Patterns.sentence.split(paragraph)
                count += sentences.size
            }
        }
        return count
    }
}
