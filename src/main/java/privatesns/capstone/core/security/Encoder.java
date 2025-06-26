package privatesns.capstone.core.security;

public interface Encoder {
    String encode(String input);

    boolean matches(String encodedPassword, String rawPassword);
}
