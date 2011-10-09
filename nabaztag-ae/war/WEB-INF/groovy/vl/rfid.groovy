package vl

import com.appspot.nabaztag.Ginko
import com.appspot.nabaztag.RabbitCommand
import com.appspot.nabaztag.RfidAction
import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.EntityNotFoundException

log.info params.toString()

Entity rabbit = datastore.get('rabbit', params.sn)
if (rabbit == null) {
    rabbit = new Entity('rabbit', params.sn)
}


def rfidKey = params.t + params.sn
Entity rfid = null
try {
    rfid = datastore.get('rfid', rfidKey)
    switch (RfidAction.valueOf(rfid.action)) {
        case RfidAction.tell_me_something:
            def packet = RabbitCommand.say(rfid.actionParam)
            RabbitCommand.sendPacket(packet, response)
            break;
        case RfidAction.play_music:
            def packet = RabbitCommand.play(rfid.actionParam)
            RabbitCommand.sendPacket(packet, response)
            break;
        case RfidAction.ginko:
            def text = Ginko.readTimeTable(rfid.actionParam)
            log.info text
            def packet = RabbitCommand.say(text)
            RabbitCommand.sendPacket(packet, response)
            break;
    }
} catch (EntityNotFoundException e) {
    rfid = new Entity('rfid', rfidKey)
    rfid.rabbit = params.sn
    rfid.mac = params.t
    rfid.name = rfid.mac
    rfid.save()
} catch(Exception e){
    log.warning e.toString()
    // still responds ok to avoid crash
    response.status = 200
}

rabbit.lastRfid = rfid.name
rabbit.save()
