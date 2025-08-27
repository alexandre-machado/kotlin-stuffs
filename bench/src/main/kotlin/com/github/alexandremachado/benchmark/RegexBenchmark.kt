package com.github.alexandremachado.benchmark

import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import org.openjdk.jmh.annotations.Fork
import org.openjdk.jmh.annotations.Measurement
import org.openjdk.jmh.annotations.Warmup

/**
 * Benchmark que compara a performance de criação de uma nova instância de Regex
 * versus o uso de uma instância compartilhada em um object.
 */
@State(Scope.Benchmark)
@Fork(1)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
class RegexBenchmark {

    // Texto de exemplo para realizar os testes
    private val sampleText = buildString {
        repeat(100) {
            append("This is a sample paragraph with some sentences. Another sentence here! And one more sentence?\n\n")
            append("A new paragraph starts here. With multiple sentences. And more text!\n\n")
        }
    }

    // Object que contém os padrões de regex pré-compilados
    object Patterns {
        val paragraphs = Regex("\n{2,}")
        val sentence = Regex("(?<=[.!?])\\s+")
    }

    @Benchmark
    fun recreatingRegexEachTime(): List<String> {
        val paragraphs = Regex("\n{2,}")
        val paragraphsList = paragraphs.split(sampleText).filter { it.isNotEmpty() }
        
        val result = mutableListOf<String>()
        for (paragraph in paragraphsList) {
            val sentence = Regex("(?<=[.!?])\\s+")
            val sentences = sentence.split(paragraph).filter { it.isNotEmpty() }
            result.addAll(sentences)
        }
        
        return result
    }

    @Benchmark
    fun usingSharedRegexInstance(): List<String> {
        val paragraphsList = Patterns.paragraphs.split(sampleText).filter { it.isNotEmpty() }
        
        val result = mutableListOf<String>()
        for (paragraph in paragraphsList) {
            val sentences = Patterns.sentence.split(paragraph).filter { it.isNotEmpty() }
            result.addAll(sentences)
        }
        
        return result
    }

    @Benchmark
    fun usingMessageChunkerObject(): List<String> {
        val paragraphsList = MessageChunker.Patterns.paragraphs.split(sampleText).filter { it.isNotEmpty() }
        
        val result = mutableListOf<String>()
        for (paragraph in paragraphsList) {
            val sentences = MessageChunker.Patterns.sentence.split(paragraph).filter { it.isNotEmpty() }
            result.addAll(sentences)
        }
        
        return result
    }
}

/**
 * Object que contém padrões de Regex pré-compilados
 * conforme fornecido no exemplo
 */
object MessageChunker {
    object Patterns {
        val paragraphs = Regex(
            "\n{2,}"
        )

        val sentence  = Regex(
            "(?<=[.!?])\\s+"
        )
    }
}
