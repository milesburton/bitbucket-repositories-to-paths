package mb.argumentprocessing

import spock.lang.Specification

class ProvidedArgumentsFetcherSpec extends Specification {

    ProvidedArgumentsFetcher providedArgumentsFetcher

    def setup() {
        providedArgumentsFetcher = new ProvidedArgumentsFetcher()
    }

    def 'fetch'() {

        given:
        Map knownArgs = [
                'crx': [required: false, defaultValue: 'http://test-publisher01.mb.com:7503/crx/server'],
                'username': [required: false, defaultValue: 'admin'],
                'password': [required: true, defaultValue: null],
                'outputFile': [required: false, defaultValue: 'xpath.result'],
                'xpath': [required: false, defaultValue: null]
        ]

        when:
        List<String> actualArgs = providedArgumentsFetcher.fetch(knownArgs, providedArgs)

        then:
        actualArgs == expectedArgs

        where:
        providedArgs    | expectedArgs
        ['--crx']         | ['crx']
        ['--crx', 'cows'] | ['crx']
        ['--CrX']         | ['crx']
    }
}
