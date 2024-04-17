package json;

import com.fasterxml.jackson.annotation.JsonAlias;

public record ImageRequest(String model,
                           String prompt,
                           int n,
                           String size,
                           String response_format) {
}
