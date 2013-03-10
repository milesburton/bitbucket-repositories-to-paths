package mb.argumentprocessing.argumentprocessors

import org.springframework.stereotype.Component

@Component
class ArgumentFetcher {

    def findValue(String argumentName, List<String> arguments) {

        List<String> remainingArguments = getArgumentsAfterArgument(arguments, argumentName)

        def idxOfLastValue = getIndexOfLastValue(remainingArguments)

        if (idxOfLastValue == -1) throw new MissingArgumentException(argumentName: argumentName)

        def argumentValues = remainingArguments[0..idxOfLastValue]

        argumentValues.size() > 1 ? argumentValues : argumentValues[0]
    }

    private List<String> getArgumentsAfterArgument(List<String> arguments, String argumentName) {

        def idx = arguments.findIndexOf { it == "--${argumentName}" }

        def remainingArguments = arguments.subList(idx + 1, arguments.size())
        remainingArguments
    }

    private int getIndexOfLastValue(List<String> remainingArguments) {
        hasMoreArguments(remainingArguments) ? getIndexOfNextArgument(remainingArguments) - 1 : remainingArguments.size() - 1
    }

    private boolean hasMoreArguments(List<String> remainingArguments) {
        getIndexOfNextArgument(remainingArguments) > -1
    }

    private int getIndexOfNextArgument(List<String> remainingArguments) {
        remainingArguments.findIndexOf { it.startsWith('--') }
    }
}
