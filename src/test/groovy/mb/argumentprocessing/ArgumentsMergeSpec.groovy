package mb.argumentprocessing

import mb.argumentprocessing.argumentprocessors.ArgumentFetcher
import spock.lang.Specification

class ArgumentsMergeSpec extends Specification {

    ArgumentsMerge argumentsMerge

    def setup() {

        argumentsMerge = new ArgumentsMerge()
        argumentsMerge.argumentFetcher = Mock(ArgumentFetcher)
        argumentsMerge.providedArgumentsFetcher = Mock(ProvidedArgumentsFetcher)
    }

    def 'merge'() {

        given:
        def knownArgs = [
                'password': [required: true],
        ]

        def args = ['--password', 'test']

        when:
        def actualArgs = argumentsMerge.merge(knownArgs, args)

        then:
        actualArgs == [password: 'test']

        and:
        1*  argumentsMerge.providedArgumentsFetcher.fetch(knownArgs, args) >> ['password']
        1 * argumentsMerge.argumentFetcher.findValue('password', args) >> 'test'
        0 * _._
    }

    def 'merge with default'() {

        given:
        def knownArgs = [
                'username': [required: false, defaultValue: 'admin']
        ]

        def args = []

        when:
        def actualArgs = argumentsMerge.merge(knownArgs, args)

        then:
        actualArgs == [username: 'admin']

        and:
        1*  argumentsMerge.providedArgumentsFetcher.fetch(knownArgs, args) >> []
        0 * _._
    }
}
