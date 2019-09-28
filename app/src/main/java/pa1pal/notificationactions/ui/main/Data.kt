package pa1pal.notificationactions.ui.main

class Data {
    companion object {
        fun getData(): MutableList<Pair<String, String>> {
            val list = mutableListOf<Pair<String, String>>()
            list.apply {
                add(Pair("Maps/ URL/ Reply", "Hi XYZ,Your visit has been scheduled on 2019-07-03 at 08:00 PM for property in Koramangala.The address for the property is 5th Block, KHB Colony, Koramangala. Map location: http://bit.ly/2PG4H1r."))
                add(Pair("Flight", "Your flight JBU523 JFK to LAX will go from Gate 52"))
                add(Pair("Phone", "Please reach out to - Smith +91 9000000000, our Supervisor for any inquiries"))
                add(Pair("URL", "Here you will find lots of android tutorials https://developer.android.com"))
                add(Pair("Address", "Let's have dinner today at 11101 Ventura Blvd #3134, Studio City, CA 91604, United States"))
                add(Pair("Flight", "Dear Mr Sharma -Your flight AI 514 from DEL - HYD, 0755-1045 hrs. Web check-in now by clicking here - http://www.airindia.in/"))
                add(Pair("Text Reply", "Where are you?"))
                add(Pair("Text Reply", "How are you?"))
                add(Pair("Date Time", "As per new schedule your maths exam is tomorrow "))
                add(Pair("Email", "For any query mail me at pawanpal004@gmail.com"))
                add(Pair("Flight Number", "Your Flight JBU523 JFK to LAX is delayed by 30 minutes"))
                add(Pair("Track Flight", "Remind me for my flight AI 514 from DEL - HYD at 6 am"))
                add(Pair("Text Reply", "Are you coming in the event?"))
                add(Pair("Text Reply", "I am bored!"))
                add(Pair("Text Reply", "My exam went well"))
                add(Pair("Text Reply", "I got my dream job"))
                }

            return list
        }
    }
}