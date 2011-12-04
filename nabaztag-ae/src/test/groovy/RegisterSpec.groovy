import groovyx.gaelyk.spock.GaelykUnitSpec

import com.google.appengine.api.datastore.Entity

class RegisterSpec extends GaelykUnitSpec {

    def setup() {
        helper.envIsLoggedIn = true
        helper.envEmail = 'bob@gmail.com'
        helper.envAuthDomain = 'domain'
        helper.envAttributes."com.google.appengine.api.users.UserService.user_id_key" = 'bob'
        user = users.currentUser

        groovlet 'register.groovy'
    }

    def "we can register a rabbit"() {
        given: "a mac address denoting a rabbit"
        this.register.params.mac = 'test'

        when: "we register the rabbit"
        this.register.get()

        then: "the rabbit exists"
        def rabbit = datastore.get('rabbit', 'test')
        rabbit.key.name == 'test'
    }

    def "we cannot register an already registered rabbit"() {
        given: "a already registered rabbit"
        def rabbit = new Entity('rabbit', 'test')
        rabbit.save()
        this.register.params.mac = 'test'

        when: "we register the rabbit"
        this.register.get()

        then: "the registration fails"
        this.register.forward == '/WEB-INF/pages/error.gtpl'
    }
}
