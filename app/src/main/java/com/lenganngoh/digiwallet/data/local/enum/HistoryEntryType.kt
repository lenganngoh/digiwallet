package com.lenganngoh.digiwallet.data.local.enum

enum class HistoryEntryType(val server_raw: String, val display_string: String) {
    INCOMING("incoming", "received payment from"),
    OUTGOING("outgoing", "cashed out to");

    companion object {
        private val map = HashMap<String, HistoryEntryType>()

        init {
            values().forEach {
                map[it.server_raw] = it
            }
        }

        fun fromString(label: String?): HistoryEntryType? {
            return map[label]
        }
    }
}