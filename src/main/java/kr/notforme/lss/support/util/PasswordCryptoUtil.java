package kr.notforme.lss.support.util;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public final class PasswordCryptoUtil {
    private static final PasswordEncoder ENCODER = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public static String encode(String rawPassword) {
        return ENCODER.encode(rawPassword);
    }

    public static boolean isMatch(String password, String encodedPassword) {
        return ENCODER.matches(password, encodedPassword);
    }

    private PasswordCryptoUtil() {
    }
}
