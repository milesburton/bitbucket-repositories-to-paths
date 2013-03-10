package mb.bitbucket

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString


@ToString
@EqualsAndHashCode
class BitbucketRequest {

    String baseUrl
    String resourcePath
}
