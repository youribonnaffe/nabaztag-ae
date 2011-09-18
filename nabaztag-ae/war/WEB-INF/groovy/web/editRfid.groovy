package web
import com.google.appengine.api.datastore.Entity

Entity rfid = datastore.get('rfid', params.rfidKey);

request.rfid = rfid

forward("/WEB-INF/pages/rfid-dialog.gtpl")