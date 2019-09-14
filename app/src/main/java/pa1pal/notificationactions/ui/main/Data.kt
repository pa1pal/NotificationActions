package pa1pal.notificationactions.ui.main


class Data {
    companion object {
        fun getData(): MutableList<Pair<String, String>> {
            val list = mutableListOf<Pair<String, String>>()
            list.apply {
                add(Pair("Text Reply", "Where are you?"))
                add(Pair("Text Reply", "How are you?"))
                add(Pair("Text Reply", "Let's have dinner today at Delhi"))
                add(Pair("Text Reply", "Where are you"))
                add(Pair("Text Reply", "Where are you"))
                add(Pair("Text Reply", "Where are you"))
                add(Pair("Text Reply", "Where are you"))
            }

            return list
        }
    }
}