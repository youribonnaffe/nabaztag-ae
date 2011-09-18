package web
import com.google.appengine.api.datastore.Entity


Entity rabbit = datastore.get('rabbit', params.key);
rabbit.name = params.name

rabbit.save();

redirect("/web")