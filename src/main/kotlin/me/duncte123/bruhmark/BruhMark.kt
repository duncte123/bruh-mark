/*
 *     A shitty benchmark that I hope does not become a standard.
 *     Copyright (C) 2023 Duncan Sterken
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.duncte123.bruhmark

import java.util.concurrent.CountDownLatch

class BruhMark {
    fun run(input: String): String {
        val stages = input.split("\n\n").map { part ->
            part.split(":".toRegex(), 2)[1]
                .split("\n")
                .map { it.trim() }
                .filter { it.isNotBlank() }
        }

        val seedsRaw = stages.removeFirst()

        val seedPairs = seedsRaw.first()
            .split(" ")
            .map { it.toLong() }
            // Comment out the two lines below to switch to part one
            .chunked(2)
            .map { (source, length) ->
                source .. (source + length)
            }

        val latch = CountDownLatch(seedPairs.size)
        val results = mutableListOf<Long>()

        seedPairs.map { stage1 ->
            runOnVThread("RangeThread[$stage1]") {
                val stageData = stage1.map {
                    println("[${Thread.currentThread().name}] Mapped $it in all stages")
                    var res = it
                    stages.map { stage ->
                        runOnVThread("StageThread[$it]") {
                            val stageMapping = makeMappingRanges(stage)

                            res = getMappedInRange(res, stageMapping)
                            println("[${Thread.currentThread().name}] Mapped $it to $res")
                        }
                    }.forEach { t -> t.join() }
                    res
                }

                results.add(stageData.minOf { it })
                latch.countDown()
            }
        }

        latch.await()

        return "${results.minOf { it }}"
    }

    private fun makeMappingRanges(input: List<String>): List<Pair<LongRange, LongRange>> {
        val ranges = mutableListOf<Pair<LongRange, LongRange>>()

        input.forEach {
            val (destStart, sourceStart, length) = it.split(" ").map { item -> item.toLong() }

            ranges.add(
                (sourceStart .. (sourceStart + length)) to (destStart .. (destStart + length))
            )
        }

        return ranges
    }

    private fun getMappedInRange(input: Long, ranges: List<Pair<LongRange, LongRange>>): Long {
        val foundRanges = ranges.filter { it.first.contains(input) }

        if (foundRanges.isEmpty()) {
            return input
        }

        foundRanges.forEach { (source, dest) ->
            val sourceIndex = source.indexOf(input)

            if (sourceIndex > -1) {
                val found = dest.elementAtOrNull(sourceIndex)

                if (found != null) {
                    return found
                }
            }

        }

        return input
    }

    private fun runOnVThread(name: String, task: () -> Unit) = Thread.ofVirtual().name(name).start(task)

    fun getInput(): String {
        return String(
            javaClass.getResourceAsStream("/dayFiveInput.txt")?.readAllBytes() ?: byteArrayOf()
        ).trim()
    }
}

fun main() {
    val startTime = System.currentTimeMillis()

    val benchmark = BruhMark()
    val input = benchmark.getInput()

    benchmark.run(input)

    val endTime = System.currentTimeMillis()

    println("Took ${endTime - startTime}ms")
}
