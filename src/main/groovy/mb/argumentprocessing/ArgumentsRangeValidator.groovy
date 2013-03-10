package mb.argumentprocessing

import org.springframework.stereotype.Component

@Component
class ArgumentsRangeValidator {

    Closure outputClosure

    boolean isValid(Map knownArguments, List<String> arguments) {

        def requiredArgs = knownArguments.keySet().findAll { knownArguments."${it}".required }

        arguments.size() >= requiredArgs.size()
    }
}
