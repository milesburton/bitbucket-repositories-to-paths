package mb.bitbucket

import com.popcornteam.restclient.RestClient
import com.popcornteam.restclient.factory.HttpClientFactory
import org.springframework.stereotype.Component

@Component
class RestClientFactory {

    RestClient makeClient(BitbucketRequest r) {
        new RestClient(r.baseUrl, [], new HttpClientFactory().makeThreadSafeHttpClient(1))
    }
}
