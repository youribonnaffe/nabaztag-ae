import com.google.appengine.api.datastore.Entity
import groovyx.gaelyk.spock.GaelykUnitSpec

class CronSpec extends GaelykUnitSpec {

    def setup() {
        groovlet 'cron.groovy'
    }

    def "a scheduled action is played"() {
        given: "a rabbit is configured to say hello at 5pm"

        when: "the cron job is launched"

        then: "the rabbit says hello on the next ping"
    }

    def "no action scheduled results in a lazy rabbit"() {
        given: "a rabbit is configured to do nothing (lazy rabbit!)"

        when: "the cron job is launched"

        then: "the rabbit does nothing"
    }
}
