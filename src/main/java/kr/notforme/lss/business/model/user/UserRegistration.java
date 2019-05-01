package kr.notforme.lss.business.model.user;

import kr.notforme.lss.support.util.PasswordCryptoUtil;

public class UserRegistration {
    private String id;
    private String password;

    public UserRegistration(String id, String password) {
        this.id = id;
        this.password = PasswordCryptoUtil.encode(password);
    }

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        return user;
    }
}

