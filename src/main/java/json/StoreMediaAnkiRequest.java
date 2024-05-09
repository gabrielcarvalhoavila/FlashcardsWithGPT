package json;

public record StoreMediaAnkiRequest(String action,
                                    Long version,
                                    Params params) {
    public record Params(String filename,
                         String url) {
    }
}
