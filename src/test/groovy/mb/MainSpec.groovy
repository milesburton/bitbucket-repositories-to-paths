package mb

import mb.argumentprocessing.ArgumentsMerge
import mb.bitbucket.BasicBitbucketCredentials
import mb.bitbucket.BitbucketRequest
import mb.usecase.ArgumentsValidatorInteractor
import mb.usecase.WriteBitbucketRepositoriesToFileInteractor
import spock.lang.Specification

class MainSpec extends Specification {

    Main main

    def setup() {
        main = new Main()
        main.argumentsMerge = Mock(ArgumentsMerge)
        main.argumentsValidatorInteractor = Mock(ArgumentsValidatorInteractor)
        main.writeBitbucketRepositoriesToFileInteractor = Mock(WriteBitbucketRepositoriesToFileInteractor)
    }

    def 'run'() {

        given:
        List<String> arguments = ['--username', 'test', '--password', 'test']
        Map defaults = [
                outputfile: 'test',
                bitbucketurl: 'uri',
                bitbucketrepouri: 'resourrce'
        ]

        Map expectedArgs = [
                outputfile: 'test',
                bitbucketurl: 'uri',
                bitbucketrepouri: 'resourrce',
                username: 'test',
                password: 'test'
        ]

        when:
        main.run(defaults, arguments)

        then:
        1 * main.argumentsValidatorInteractor.isValid({ true }, defaults, arguments) >> true
        1 * main.argumentsMerge.merge(defaults, arguments) >> expectedArgs
        1 * main.writeBitbucketRepositoriesToFileInteractor.fetchAndWrite(expectedArgs.outputfile, new BitbucketRequest(baseUrl: expectedArgs.bitbucketurl, resourcePath: expectedArgs.bitbucketrepouri), new BasicBitbucketCredentials(username: expectedArgs.username, password: expectedArgs.password))
        0 * _._

    }

    def 'run invalid args'() {

        given:
        List<String> arguments = ['--username', 'test', '--password', 'test']
        Map defaults = [
                outputfile: 'test',
                bitbucketurl: 'uri',
                bitbucketrepouri: 'resourrce'
        ]

        when:
        main.run(defaults, arguments)

        then:
        1 * main.argumentsValidatorInteractor.isValid({ true }, defaults, arguments) >> false
        0 * _._

    }
}
