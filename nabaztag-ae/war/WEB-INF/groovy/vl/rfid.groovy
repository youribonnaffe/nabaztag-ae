package vl

import com.appspot.nabaztag.Ginko
import com.appspot.nabaztag.Rabbit
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
            new Rabbit().with {
                say = rfid.actionParam
                send response
            }
            break;
        case RfidAction.play_music:
            new Rabbit().with {
                play = rfid.actionParam
                send response
            }
            break;
        case RfidAction.ginko:
            def text = Ginko.readTimeTable(rfid.actionParam)
            log.info text
            new Rabbit().with {
                say = text
                send response
            }
            break;
        case RfidAction.christmas:
            log.info text
            //def packet = RabbitCommand.play(radio pere nowel)
            // TODO play chorecgraphy nowel
            break;
    }
} catch (EntityNotFoundException e) {
    rfid = new Entity('rfid', rfidKey)
    rfid.rabbit = params.sn
    rfid.mac = params.t
    rfid.name = rfid.mac
    rfid.save()
} catch (Exception e) {
    log.warning e.toString()
    // still responds ok to avoid crash
    response.status = 200
}

rabbit.lastRfid = rfid.name
rabbit.save()
