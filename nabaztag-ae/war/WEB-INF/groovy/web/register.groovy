package web
import com.google.appengine.api.datastore.Entity
import com.google.appengine.api.datastore.EntityNotFoundException

log.info params.toString()

try {
    Entity rabbit = datastore.get('rabbit', params.mac)
    log.warning "the rabbit"+params.mac +" already belongs to somebody"
    forward "/WEB-INF/pages/error.gtpl"
} catch(EntityNotFoundException notFound){
    rabbit = new Entity('rabbit',params.mac)
    rabbit.userId = user.userId
    rabbit.name = params.mac
    rabbit.save()
    log.info rabbit.toString()
    redirect "/web"
}
