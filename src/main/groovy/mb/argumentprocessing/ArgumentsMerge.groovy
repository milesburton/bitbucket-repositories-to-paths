package mb.argumentprocessing

import mb.argumentprocessing.argumentprocessors.ArgumentFetcher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ArgumentsMerge {

    @Autowired
    ArgumentFetcher argumentFetcher

    @Autowired
    ProvidedArgumentsFetcher providedArgumentsFetcher

    Map merge(Map defaultArguments, List<String> providedArguments) {

        Map arguments = [:]

        List<String> validArguments = providedArgumentsFetcher.fetch(defaultArguments, providedArguments)

        defaultArguments.each { String argument, Map argumentSetting ->

            def argumentValue = validArguments.contains(argument) ? argumentFetcher.findValue(argument, providedArguments) : argumentSetting.defaultValue

            arguments.put(argument, argumentValue)
        }

        arguments
    }
}
