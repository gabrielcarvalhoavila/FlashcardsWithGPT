package json;

import java.util.List;



public record ChatRequest(String model, List<Message> messages, double temperature) {
    public record Message(String role, String content) {}
}
