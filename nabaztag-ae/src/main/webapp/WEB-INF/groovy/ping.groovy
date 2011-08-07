import com.appspot.nabaztag.AmbientBlock
import com.appspot.nabaztag.MessageBlock
import com.appspot.nabaztag.Packet
import com.appspot.nabaztag.PingIntervalBlock

log.info "ping"
log.info params.toString()

class Mem {
	static def lastMsg = -1
}

def now = Calendar.instance
if(now.get(Calendar.MINUTE) != Mem.lastMsg){
	Mem.lastMsg = -1
}
if( now.get(Calendar.MINUTE) % 5 == 0 && now.get(Calendar.MINUTE) != Mem.lastMsg){
	Mem.lastMsg = now.get(Calendar.MINUTE)
	log.info "play sound"
	def packet = new Packet();
	def message = new MessageBlock(139)
	def fileToPlay = "http://nabaztag-cdn.appspot.com/surprise/"+(new Random().nextInt(298)+1)+".mp3"
	//	def fileToPlay = "http://translate.google.com/translate_tts?tl=fr&q=bonjour"
	log.info fileToPlay
	message.addPlaySoundCommand(fileToPlay)
	packet.addBlock(message)
	def packetGeneratePacket = packet.generatePacket()
	response.contentLength = packetGeneratePacket.length
	sout.write packetGeneratePacket
	response.status = 200
} else {
	log.info "default ambient block"
	def packet = new Packet();
	def message = new AmbientBlock()
	packet.addBlock(new PingIntervalBlock(10))
	packet.addBlock(message)
	def packetGeneratePacket = packet.generatePacket()
	response.contentLength = packetGeneratePacket.length
	sout.write packetGeneratePacket
	response.status = 200
}

