package com.appspot.nabaztag.tts

class GoogleVoiceProvider implements VoiceProvider {

    List<String> convertTextToMP3(String text) {

        def encodedText = text
        def listOfStrings = new LinkedList<String>()
        def size = 0
        encodedText.split().each {

            if (size + it.length() < 80) {
                size += it.length()
                def current = listOfStrings.poll()
                listOfStrings.addFirst((current ? current : "") + " " + it)
            } else {
                listOfStrings.addFirst(it)
                size = it.length()
            }
        }

        return listOfStrings.reverse().collect { "http://translate.google.com/translate_tts?ie=UTF-8&tl=fr&q=" + URLEncoder.encode(it, "UTF-8") }
    }
}
