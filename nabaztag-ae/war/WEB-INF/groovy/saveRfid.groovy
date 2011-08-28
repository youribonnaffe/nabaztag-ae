import com.google.appengine.api.datastore.Entity

log.info "save rfid"
log.info params.toString()

Entity rfid = datastore.get('rfid', params.key[1..-1]);
rfid.color = params.color
rfid.name = params.name

rfid.save();

redirect("/web")