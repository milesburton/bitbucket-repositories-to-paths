package mb.argumentprocessing.argumentprocessors

import spock.lang.Specification

class ArgumentFetcherSpec extends Specification {

    ArgumentFetcher argumentFetcher

    def setup() {
        argumentFetcher = new ArgumentFetcher()
    }

    def 'fetch argument value'() {
        given:
        def argumentName = 'username'
        def arguments = ['--username', 'miles']

        when:
        def value = argumentFetcher.findValue(argumentName, arguments)

        then:
        value == 'miles'
    }

    def 'fetch missing argument'() {

        given:
        def argumentName = 'username'
        def arguments = ['--username', '--password']

        when:
        argumentFetcher.findValue(argumentName, arguments)

        then:
        MissingArgumentException ex = thrown()
        ex.argumentName == 'username'
    }

    def 'fetch argument values'() {
        given:
        def argumentName = 'username'
        def arguments = ['--username', 'miles', 'em', 'chris']

        when:
        def value = argumentFetcher.findValue(argumentName, arguments)

        then:
        value == ['miles', 'em', 'chris']
    }

    def 'fetch argument values with bounds'() {
        given:
        def argumentName = 'username'
        def arguments = ['--username', 'miles', 'em']

        when:
        def value = argumentFetcher.findValue(argumentName, arguments)

        then:
        value == ['miles', 'em']
    }

    def 'fetch argument values with more arguments'() {
        given:
        def argumentName = 'username'
        def arguments = ['--username', 'miles', 'em', 'chris', '--test', 'testvalue']

        when:
        def value = argumentFetcher.findValue(argumentName, arguments)

        then:
        value == ['miles', 'em', 'chris']
    }
}
