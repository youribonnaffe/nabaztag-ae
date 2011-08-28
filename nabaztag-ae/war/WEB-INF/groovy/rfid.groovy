import com.appspot.nabaztag.AmbientBlock
import com.appspot.nabaztag.MessageBlock
import com.appspot.nabaztag.Packet
import com.google.appengine.api.datastore.Entity

log.info "rfid"
log.info params.toString()

Entity rabbit = datastore.get('rabbit', params.sn)
rabbit.lastRfid = params.t
rabbit.save()

Entity rfid = new Entity('rfid', params.t+params.sn)
rfid.rabbit = params.sn
rfid.mac = params.t
rfid.save()

response.status = 200

switch(params["t"]){
	case "d0021a0353042e4a" :
		log.info "france inter"
		def packet = new Packet();
		def message = new MessageBlock(139)
		message.addPlayStreamCommand("http://mp3.live.tv-radio.com/franceinter/all/franceinter-32k.mp3");
		packet.addBlock(message)
		def packetGeneratePacket = packet.generatePacket()
		response.contentLength = packetGeneratePacket.length
		sout.write packetGeneratePacket
		break;
	case "d00219a440cf4d77" :
		log.info "radio fip"
		def packet = new Packet();
		def message = new MessageBlock(139)
		message.addPlayStreamCommand("http://mp3.live.tv-radio.com/fip/all/fip-32k.mp3");
		packet.addBlock(message)
		def packetGeneratePacket = packet.generatePacket()
		response.contentLength = packetGeneratePacket.length
		sout.write packetGeneratePacket
		break;
	case "d00219a440cf16ad":
		log.info "ears"
		def packet = new Packet();
		def message = new AmbientBlock()
		def rand = new Random(System.currentTimeMillis())
		def rightEarMove = rand.nextInt(12)+1
		def leftEarMove = rand.nextInt(12)+1
		log.info rightEarMove.toString() + " " + leftEarMove.toString()
		message.setRightEarValue(rightEarMove)
		message.setLeftEarValue(leftEarMove)

		MessageBlock mb = new MessageBlock(Integer.parseInt("7FFFFFFF", 16));
	//packet.addBlock(mb)
		packet.addBlock(message)
	//packet.addBlock(new PingIntervalBlock(10))
		def packetGeneratePacket = packet.generatePacket()
		response.contentLength = packetGeneratePacket.length
		sout.write packetGeneratePacket
		break;
}