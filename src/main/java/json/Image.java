package json;

import com.fasterxml.jackson.annotation.JsonAlias;

public record Image(@JsonAlias(value = "b64_json")
                    String b64Json) {
}
