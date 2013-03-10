package mb.bitbucket

import com.popcornteam.restclient.header.HttpHeader
import com.sun.org.apache.xml.internal.security.utils.Base64
import groovy.transform.EqualsAndHashCode


@EqualsAndHashCode
class AuthorizationHeader extends HttpHeader {

    AuthorizationHeader(String username, String password) {

        super('Authorization', "Basic ${Base64.encode("${username}:${password}".bytes)}")
    }
}
