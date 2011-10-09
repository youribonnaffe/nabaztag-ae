package com.appspot.nabaztag.tts

interface VoiceProvider {
    List<String> convertTextToMP3(String text);
}
