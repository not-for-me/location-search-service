package kr.notforme.lss;

import kr.notforme.lss.business.model.user.User;

public class Fixtures {
    public static User generate(String id) {
        User user = new User();
        user.setId(id);
        user.setPassword("abcd");

       return  user;
    }


}
