package vl
import com.google.appengine.api.utils.SystemProperty

log.info "locate"
log.info params.toString()

def server = "192.168.1.95:8080";
if (SystemProperty.environment.value() == SystemProperty.environment.value().Production) {
	server = "nabaztag-ae.appspot.com"
}
sout.println "ping "+server
sout.println "broad "+server
sout.println "xmpp_domain "+server