package mb.argumentprocessing

import org.springframework.stereotype.Component

@Component
class ArgumentsUsagePrinter {

    void printUsage(Map args, Closure outputClosure) {

        outputClosure "Usage:"

        args.each { String argName, Map argSettings ->

            def messageArray = [argName.trim(), printRequired(argSettings), printDefault(argSettings)].findAll { it }

            outputClosure "[${messageArray.join(" ")}]"
        }
    }

    private String printRequired(Map argSettings) {

        if (argSettings.required) {
            "required"
        } else {
            ""
        }
    }

    private String printDefault(Map argSettings) {

        if (argSettings.defaultValue) {
            "default: '${argSettings.defaultValue}'"
        } else {
            ""
        }

    }
}