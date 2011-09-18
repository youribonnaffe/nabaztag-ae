package com.appspot.nabaztag

import com.appspot.nabaztag.AmbientBlock
import com.appspot.nabaztag.Packet
import com.appspot.nabaztag.PingIntervalBlock

class Mem {

	static def lastMsg = -1
	static def surprisePeriodInMin = 30
	static def packet = new Packet()
	static def packetGeneratePacket
	static def lastRadio = -1

	static {
		def packet = new Packet()
		packet.addBlock(new PingIntervalBlock(1))
		packetGeneratePacket = packet.generatePacket()
	}
}
