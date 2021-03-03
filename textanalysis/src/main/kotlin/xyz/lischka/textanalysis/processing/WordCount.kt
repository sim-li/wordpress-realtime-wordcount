package xyz.lischka.textanalysis.processing

object WordCount {
    fun count(words: List<String>): Map<String, Int> {
        val occurrences: MutableMap<String, Int> = HashMap()
        words.forEach { w ->
            occurrences[w] = if (occurrences.containsKey(w)) occurrences[w]!! + 1 else 1
        }
        return occurrences
    }
}
