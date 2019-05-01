package kr.notforme.lss.business.model.user;

public class UserRegistration {
    private String id;
    private String password;

    public UserRegistration(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setPassword(password);
        return user;
    }
}

