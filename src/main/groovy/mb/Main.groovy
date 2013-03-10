package mb

import mb.argumentprocessing.ArgumentsMerge
import mb.bitbucket.BasicBitbucketCredentials
import mb.bitbucket.BitbucketRequest
import mb.usecase.ArgumentsValidatorInteractor
import mb.usecase.WriteBitbucketRepositoriesToFileInteractor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.stereotype.Component

@Component
class Main {

    @Autowired
    ArgumentsValidatorInteractor argumentsValidatorInteractor

    @Autowired
    WriteBitbucketRepositoriesToFileInteractor writeBitbucketRepositoriesToFileInteractor

    @Autowired
    ArgumentsMerge argumentsMerge

    static void main(String[] args) {

        def defaultArguments = [
                'username': [required: true],
                'password': [required: true],
                'outputfile': [required: false, defaultValue: 'bitbucket.urls'],
                'bitbucketurl': [required: false, defaultValue: 'https://api.bitbucket.org'],
                'bitbucketrepouri': [required: false, defaultValue: '/1.0/user/repositories/'],
        ]

        ApplicationContext ctx =
            new AnnotationConfigApplicationContext("mb");

        Main main = ctx.getBean(Main.class);

        main.run(defaultArguments, args.toList())

    }

    void run(Map defaultArguments, List<String> arguments) {

        def errorOutputter = { println it }

        if (argumentsValidatorInteractor.isValid(errorOutputter, defaultArguments, arguments)) {

            Map settingsMap = argumentsMerge.merge(defaultArguments, arguments)

            def credentials = new BasicBitbucketCredentials(username: settingsMap.username, password: settingsMap.password)
            def request = new BitbucketRequest(baseUrl: settingsMap.bitbucketurl, resourcePath: settingsMap.bitbucketrepouri)
            writeBitbucketRepositoriesToFileInteractor.fetchAndWrite(settingsMap.outputfile, request, credentials)

        }

        println "Finished"
    }


}
