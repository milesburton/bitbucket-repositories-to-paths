package mb.usecase

import mb.argumentprocessing.ArgumentsRangeValidator
import mb.argumentprocessing.ArgumentsUsagePrinter
import mb.argumentprocessing.ArgumentsValueValidator
import mb.argumentprocessing.ProvidedArgumentsFetcher
import spock.lang.Specification

class ArgumentsValidatorInteractorSpec extends Specification {

    ArgumentsValidatorInteractor argumentValidatorInteractor

    Map defaultArguments
    List<String> providedArguments
    String msg = ""
    Closure printer

    def setup() {

        argumentValidatorInteractor = new ArgumentsValidatorInteractor()
        argumentValidatorInteractor.argumentsRangeValidator = Mock(ArgumentsRangeValidator)
        argumentValidatorInteractor.providedArgumentsFetcher = Mock(ProvidedArgumentsFetcher)
        argumentValidatorInteractor.argumentsUsagePrinter = Mock(ArgumentsUsagePrinter)
        argumentValidatorInteractor.argumentsValueValidator = Mock(ArgumentsValueValidator)

        defaultArguments = [:]
        providedArguments = []
        msg = ""
        printer = { msg += it + "\n" }
    }

    def 'isValid with invalid number of params'() {


        when:
        def isValid = argumentValidatorInteractor.isValid(printer, defaultArguments, providedArguments)

        then:
        !isValid
        msg  == "Too few required arguments\n"

        and:
        1 * argumentValidatorInteractor.providedArgumentsFetcher.fetch(defaultArguments, providedArguments) >> []
        1 * argumentValidatorInteractor.argumentsRangeValidator.isValid(defaultArguments, providedArguments) >> false
        1 * argumentValidatorInteractor.argumentsUsagePrinter.printUsage(defaultArguments, printer)
        0 * _._
    }

    def 'isValid with invalid arguments'() {


        when:
        def isValid = argumentValidatorInteractor.isValid(printer, defaultArguments, providedArguments)

        then:
        !isValid

        and:

        1 * argumentValidatorInteractor.providedArgumentsFetcher.fetch(defaultArguments, providedArguments) >> []
        1 * argumentValidatorInteractor.argumentsRangeValidator.isValid(defaultArguments, providedArguments) >> true
        1 * argumentValidatorInteractor.argumentsValueValidator.isValid([], providedArguments) >> false
        1 * argumentValidatorInteractor.argumentsUsagePrinter.printUsage(defaultArguments, printer)
        0 * _._
    }

    def 'isValid'() {


        when:
        def isValid = argumentValidatorInteractor.isValid(printer, defaultArguments, providedArguments)

        then:
        isValid

        and:
        1 * argumentValidatorInteractor.providedArgumentsFetcher.fetch(defaultArguments, providedArguments) >> []
        1 * argumentValidatorInteractor.argumentsRangeValidator.isValid(defaultArguments, providedArguments) >> true
        1 * argumentValidatorInteractor.argumentsValueValidator.isValid([], providedArguments) >> true
        0 * _._
    }

}
