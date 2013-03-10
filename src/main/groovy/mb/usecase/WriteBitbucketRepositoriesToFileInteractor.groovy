package mb.usecase

import mb.bitbucket.BasicBitbucketCredentials
import mb.bitbucket.BitbucketRequest
import mb.bitbucket.BitbucketRequestRepositories
import mb.io.FileFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class WriteBitbucketRepositoriesToFileInteractor {

    @Autowired
    BitbucketRequestRepositories bitbucketRequestRepositories

    @Autowired
    FileFactory fileFactory

    void fetchAndWrite(String outputFileUri, BitbucketRequest request, BasicBitbucketCredentials credentials) {

        def outputFile = fileFactory.fetchHandle(outputFileUri)

        if (outputFile.exists()) {
            outputFile.delete()
        }

        if (!outputFile.createNewFile()) {
            throw new RuntimeException()
        }

        Set<String> repos = bitbucketRequestRepositories.fetch(request, credentials)

        repos.each {
            outputFile.append(it + "\n")
        }
    }
}
