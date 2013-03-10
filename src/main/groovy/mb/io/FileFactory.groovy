package mb.io

import org.springframework.stereotype.Component

@Component
class FileFactory {

    File fetchHandle(String uri) {
        new File(uri)
    }
}
