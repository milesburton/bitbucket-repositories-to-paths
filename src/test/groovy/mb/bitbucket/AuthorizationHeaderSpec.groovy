package mb.bitbucket

import com.popcornteam.restclient.header.HttpHeader
import spock.lang.Specification

class AuthorizationHeaderSpec extends Specification {

   def 'construct'(){

       given:
       String username = 'test'
       String password = 'test'

       when:
       HttpHeader h = new AuthorizationHeader(username, password)

       then:
       h.name == 'Authorization'
       h.value == "Basic dGVzdDp0ZXN0"
   }
}
