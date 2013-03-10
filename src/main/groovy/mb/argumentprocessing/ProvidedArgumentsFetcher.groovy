package mb.argumentprocessing

import org.springframework.stereotype.Component

@Component
class ProvidedArgumentsFetcher {

    List<String> fetch(Map knownArguments, List<String> arguments) {

        arguments.findAll {

            if (it.length() > 2) {
                def key = it.toLowerCase().substring(2)

                knownArguments.containsKey(key)
            }

        }*.substring(2)*.toLowerCase()
    }
}
