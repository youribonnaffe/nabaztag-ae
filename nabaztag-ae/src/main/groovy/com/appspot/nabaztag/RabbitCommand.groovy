package com.appspot.nabaztag

import javax.servlet.http.HttpServletResponse

class RabbitCommand {

    public static byte[] say(String text){
	def message = new MessageBlock(139)
	message.addPlayStreamCommand("http://translate.google.com/translate_tts?tl=fr&q="+URLEncoder.encode(text,"UTF-8"))
	return buildPacket(message)
    }

    public static byte[] play(String url){
	def message = new MessageBlock(139)
	message.addPlayStreamCommand(url)
	return buildPacket(message)
    }

    public static void sendPacket(byte[] packet, HttpServletResponse response){
	response.contentLength = packet.length
	response.getOutputStream().write packet
    }

    private static buildPacket(MessageBlock message) {
	def packet = new Packet()
	packet.addBlock(message)
	return packet.generatePacket()
    }
}
