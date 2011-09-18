package web
import com.google.appengine.api.datastore.Entity


Entity rfid = datastore.get('rfid', params.key);
rfid.color = params.color
rfid.name = params.name
rfid.action = params.rfidAction
rfid.actionParam = params.rfidActionParam

rfid.save();

redirect("/web")