package mb.argumentprocessing

import mb.argumentprocessing.argumentprocessors.ArgumentFetcher
import spock.lang.Specification

class ArgumentsValueValidatorSpec extends Specification {
    ArgumentsValueValidator argumentsValueValidator

    def setup() {
        argumentsValueValidator = new ArgumentsValueValidator()
        argumentsValueValidator.argumentFetcher = Mock(ArgumentFetcher)
    }

    def 'isValid'() {

        given:
        def suppliedKnownArgs = ['crx']
        def providedArgs = ['--crx', 'uri']


        when:
        def isValid = argumentsValueValidator.isValid(suppliedKnownArgs, providedArgs)

        then:
        isValid

        and:
        1 * argumentsValueValidator.argumentFetcher.findValue('crx',providedArgs) >> 'uri'
        0 * _._
    }
}
