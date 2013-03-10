package mb.argumentprocessing

import spock.lang.Specification
import spock.lang.Unroll

class ArgumentsRangeValidatorSpec extends Specification {

    ArgumentsRangeValidator argumentNumberChecker

    def setup() {
        argumentNumberChecker = new ArgumentsRangeValidator()
    }

    @Unroll
    def 'isValid #knownArgs with supplied args #suppliedArgs are expected to be valid #expectedToBeValid'() {

        when:
        boolean isValid = argumentNumberChecker.isValid(knownArgs, suppliedArgs)

        then:
        isValid == expectedToBeValid

        where:
        knownArgs                  | suppliedArgs | expectedToBeValid
        ['crx': [required: true]]  | []           | false
        ['crx': [required: false]] | []           | true

    }
}
