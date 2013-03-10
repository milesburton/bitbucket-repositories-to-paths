package mb.usecase

import mb.bitbucket.BasicBitbucketCredentials
import mb.bitbucket.BitbucketRequest
import mb.bitbucket.BitbucketRequestRepositories
import mb.io.FileFactory
import spock.lang.Specification


class WriteBitbucketRepositoriesToFileSpec extends Specification {

    WriteBitbucketRepositoriesToFileInteractor writeBitbucketRepositoriesToFile
    File mockFile

    def setup() {

        writeBitbucketRepositoriesToFile = new WriteBitbucketRepositoriesToFileInteractor()
        writeBitbucketRepositoriesToFile.bitbucketRequestRepositories = Mock(BitbucketRequestRepositories)
        writeBitbucketRepositoriesToFile.fileFactory = Mock(FileFactory)
        mockFile = Mock(File)
    }

    def 'fetchAndWrite'() {

        given:
        String fileUri = ''
        BitbucketRequest fakeRequest = new BitbucketRequest()
        BasicBitbucketCredentials fakeCreds = new BasicBitbucketCredentials()


        when:
        writeBitbucketRepositoriesToFile.fetchAndWrite(fileUri, fakeRequest, fakeCreds)

        then:
        1 * writeBitbucketRepositoriesToFile.fileFactory.fetchHandle(fileUri) >> mockFile
        1 * mockFile.exists() >> false
        1 * mockFile.createNewFile() >> true
        1 * writeBitbucketRepositoriesToFile.bitbucketRequestRepositories.fetch(fakeRequest, fakeCreds) >> ['test']
        1 * mockFile.getPath() >> '/tmp/test'
        0 * _._
    }

    def 'fetchAndWrite with create new file error'() {

        given:
        String fileUri = ''
        BitbucketRequest fakeRequest = new BitbucketRequest()
        BasicBitbucketCredentials fakeCreds = new BasicBitbucketCredentials()


        when:
        writeBitbucketRepositoriesToFile.fetchAndWrite(fileUri, fakeRequest, fakeCreds)

        then:
        thrown(RuntimeException)

        and:
        1 * writeBitbucketRepositoriesToFile.fileFactory.fetchHandle(fileUri) >> mockFile
        1 * mockFile.exists() >> false
        1 * mockFile.createNewFile() >> false
        0 * _._
    }
}
