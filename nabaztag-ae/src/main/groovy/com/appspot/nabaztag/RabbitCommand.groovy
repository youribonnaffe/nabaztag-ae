package com.appspot.nabaztag

import javax.servlet.http.HttpServletResponse
import com.appspot.nabaztag.tts.VoiceProvider
import com.appspot.nabaztag.tts.AcapelaVoiceProvider

class RabbitCommand {

    private static final int MESSAGE_ID = 139

    static VoiceProvider voiceProvider = new AcapelaVoiceProvider()

    public static byte[] say(String text) {
        def message = new MessageBlock(MESSAGE_ID)

        voiceProvider.convertTextToMP3(text).each{
            message.addPlayStreamCommand(it)
        }

        return buildPacket(message)
    }

    public static byte[] play(String url) {
        def message = new MessageBlock(MESSAGE_ID)
        message.addPlayStreamCommand(url)
        return buildPacket(message)
    }

    public static void sendPacket(byte[] packet, HttpServletResponse response) {
        response.contentLength = packet.length
        response.getOutputStream().write packet
    }

    private static buildPacket(MessageBlock... messages) {
        def packet = new Packet()
        messages.each { packet.addBlock(it) }
        return packet.generatePacket()
    }
}
