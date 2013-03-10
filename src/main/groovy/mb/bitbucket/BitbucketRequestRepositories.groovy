package mb.bitbucket

import com.popcornteam.restclient.RestClient
import com.popcornteam.restclient.response.RestResponse
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class BitbucketRequestRepositories {

    @Autowired
    RestClientFactory restClientFactory

    List<String> fetch(BitbucketRequest request, BasicBitbucketCredentials credentials) {

        RestClient rc = restClientFactory.makeClient(request)

        RestResponse r = rc.get(request.resourcePath, [new AuthorizationHeader(credentials.username, credentials.password)])

        new JsonSlurper().parseText(r.bodyAsString)
                .findAll { it.owner == credentials.username}
        .collect {
            "git@bitbucket.org:${credentials.username}/${it.name}.git"
        }

    }
}
