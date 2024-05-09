package json;

import java.util.List;

public record AddNotesRequest(String action,
                              Long version,
                              Params params) {
    public record Params(List<Notes> notes) {
        public record Notes(String deckName,
                            String modelName,
                            Fields fields,
                            Options options
        ) {
            public record Fields(String Front,
                                 String Back) {
            }

            public record Options(boolean allowDuplicate) {
            }

        }
    }
}
