import groovyx.gaelyk.spock.GaelykUnitSpec

import com.google.appengine.api.datastore.Entity

class TestSpec extends GaelykUnitSpec {

    def "user is null"() {
	given: "the most simple gaelyk unit test"
	
	when: "we get the current user"
	def currentUser = user

	then: "the current user is null"
	currentUser == null
    }

}
