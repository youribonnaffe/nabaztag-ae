package com.appspot.nabaztag

class Mem {

	static def lastMsg = -1
	static def surprisePeriodInMin = 30
	static def packet = new Packet()
	static def packetGeneratePacket

	static {
		def packet = new Packet()
		packet.addBlock(new PingIntervalBlock(1))
		packetGeneratePacket = packet.generatePacket()
	}
}
