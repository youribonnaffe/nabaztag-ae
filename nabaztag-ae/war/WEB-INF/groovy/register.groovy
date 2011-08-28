import com.google.appengine.api.datastore.Entity

log.info params.toString()

Entity rabbit = new Entity('rabbit',params.mac)
rabbit.userId = user.userId

log.info rabbit.toString()

rabbit.save()

redirect "/web"