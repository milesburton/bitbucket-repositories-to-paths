package mb.bitbucket

import com.popcornteam.restclient.RestClient
import com.popcornteam.restclient.response.RestResponse
import mb.bitbucket.fixtures.BitbucketRepositoriesFixture
import spock.lang.Specification


class BitbucketRequestRepositoriesSpec extends Specification {

    BitbucketRequestRepositories bitbucketRequestRepositories
    RestClient mockRestClient
    RestResponse mockRestResponse

    def setup() {
        bitbucketRequestRepositories = new BitbucketRequestRepositories()
        bitbucketRequestRepositories.restClientFactory = Mock(RestClientFactory)
        mockRestClient = Mock(RestClient)
        mockRestResponse = Mock(RestResponse)
    }

    def 'fetch'() {

        given:
        String username = 'wgsn'
        String password = 'test'
        String baseUrl = 'http//test'
        String resourcePath = '/someapi'

        BasicBitbucketCredentials basicBitbucketCredentials = new BasicBitbucketCredentials(username: username, password: password)
        BitbucketRequest bitbucketRequest = new BitbucketRequest(baseUrl: baseUrl, resourcePath: resourcePath)

        when:
        List<String> repos = bitbucketRequestRepositories.fetch(bitbucketRequest, basicBitbucketCredentials)

        then:
        repos == [
                "git@bitbucket.org:wgsn/angular-search.git",
                "git@bitbucket.org:wgsn/asset-uploader.git",
                "git@bitbucket.org:wgsn/authentication.git",
                "git@bitbucket.org:wgsn/autonomy-ingestion.git",
                "git@bitbucket.org:wgsn/autonomy-search-service.git",
                "git@bitbucket.org:wgsn/bitbucket-sync-miles.git",
                "git@bitbucket.org:wgsn/cq-tests.git",
                "git@bitbucket.org:wgsn/gitolite-admin.git",
                "git@bitbucket.org:wgsn/mywgsn.git",
                "git@bitbucket.org:wgsn/mywgsn-genesis.git",
                "git@bitbucket.org:wgsn/mywgsn-nemesis.git",
                "git@bitbucket.org:wgsn/salesforce.git",
                "git@bitbucket.org:wgsn/snowball.git",
                "git@bitbucket.org:wgsn/testing.git",
                "git@bitbucket.org:wgsn/tools.git",
                "git@bitbucket.org:wgsn/twigkit-search.git",
                "git@bitbucket.org:wgsn/vogue.git",
                "git@bitbucket.org:wgsn/wgsn.git",
                "git@bitbucket.org:wgsn/wgsn-dev.git",
                "git@bitbucket.org:wgsn/wgsn-mobile-services.git",
                "git@bitbucket.org:wgsn/wgsn-test.git"]

        and:
        1 * bitbucketRequestRepositories.restClientFactory.makeClient(bitbucketRequest) >> mockRestClient
        1 * mockRestClient.get(resourcePath, [new AuthorizationHeader(username, password)]) >> mockRestResponse
        1 * mockRestResponse.bodyAsString >> new BitbucketRepositoriesFixture().repositoriesResponse
        0 * _._


    }
}

