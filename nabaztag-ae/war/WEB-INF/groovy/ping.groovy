import com.appspot.nabaztag.MessageBlock
import com.appspot.nabaztag.Packet

def now = Calendar.instance
def currentMinute = now.get(Calendar.MINUTE)
if(currentMinute != Mem.lastMsg && currentMinute % Mem.surprisePeriodInMin == 0 ){
	Mem.lastMsg = now.get(Calendar.MINUTE)
	log.info "play sound"
	def packet = new Packet();
	def message = new MessageBlock(139)
	def fileToPlay = "http://nabaztag-cdn.appspot.com/short/ding.mp3"
	if(currentMinute == 0){
		"http://nabaztag-cdn.appspot.com/surprise/"+(new Random(System.currentTimeMillis()).nextInt(298)+1)+".mp3"
	}
	log.info fileToPlay
	message.addPlaySoundCommand(fileToPlay)
	packet.addBlock(message)
	def packetGeneratePacket = packet.generatePacket()
	response.contentLength = packetGeneratePacket.length
	sout.write packetGeneratePacket
	response.status = 200
}else {
	response.contentLength = Mem.packetGeneratePacket.length
	sout.write Mem.packetGeneratePacket
	response.status = 200
}

