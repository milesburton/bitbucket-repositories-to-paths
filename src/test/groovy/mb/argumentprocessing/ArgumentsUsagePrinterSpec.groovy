package mb.argumentprocessing

import spock.lang.Specification

class ArgumentsUsagePrinterSpec extends Specification {

    ArgumentsUsagePrinter argumentUsagePrinter
    def printer
    String outputMessage = ""

    def setup() {

        argumentUsagePrinter = new ArgumentsUsagePrinter()
        printer = { outputMessage += it + "\n" }
        outputMessage = ""
    }

    def 'fetch with required default'() {

        given:
        Map knownArgs = [
                'crx': [required: false, defaultValue: 'http://test-publisher01.mb.com:7503/crx/server']
        ]

        when:
        argumentUsagePrinter.printUsage(knownArgs, printer)

        then:
        outputMessage == """Usage:
[crx default: 'http://test-publisher01.mb.com:7503/crx/server']
"""
    }

    def 'fetch with default'() {

        given:
        Map knownArgs = [
                'crx': [required: true, defaultValue: 'http://test-publisher01.mb.com:7503/crx/server']
        ]

        when:
        argumentUsagePrinter.printUsage(knownArgs, printer)

        then:
        outputMessage == """Usage:
[crx required default: 'http://test-publisher01.mb.com:7503/crx/server']
"""
    }

    def 'fetch'() {

        given:
        Map knownArgs = [
                'password': [required: false]
        ]

        when:
        argumentUsagePrinter.printUsage(knownArgs, printer)

        then:
        outputMessage == """Usage:
[password]
"""
    }

    def 'fetch with required'() {

        given:
        Map knownArgs = [
                'password': [required: true, defaultValue: null]
        ]

        when:
        argumentUsagePrinter.printUsage(knownArgs, printer)

        then:
        outputMessage == """Usage:
[password required]
"""
    }

    def 'fetch with multiple'() {

        given:
        Map knownArgs = [
                'crx': [required: false, defaultValue: 'http://test-publisher01.mb.com:7503/crx/server'],
                'username': [required: false, defaultValue: 'admin'],
                'password': [required: true, defaultValue: null],
                'outputFile': [required: false, defaultValue: 'xpath.result'],
                'xpath': [required: false, defaultValue: null]
        ]

        when:
        argumentUsagePrinter.printUsage(knownArgs, printer)

        then:
        outputMessage == """Usage:
[crx default: 'http://test-publisher01.mb.com:7503/crx/server']
[username default: 'admin']
[password required]
[outputFile default: 'xpath.result']
[xpath]
"""
    }
}