package kr.notforme.lss.business.model.user;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "id"))
public class User {
    public static final int MIN_ID_LEN = 3;
    public static final int MAX_ID_LEN = 24;
    public static final int MIN_PASSWORD_LEN = 8;
    public static final int MAX_PASSWORD_LEN = 16;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long seq;
    @Column(length = MAX_ID_LEN)
    @Size(min = MIN_ID_LEN, max = MAX_ID_LEN)
    private String id;
    @Column(length = MAX_PASSWORD_LEN)
    @Size(min = MIN_PASSWORD_LEN, max = MAX_PASSWORD_LEN)
    private String password;

    private static Set<GrantedAuthority> AUTHORITIES = new HashSet<>();

    static {
        AUTHORITIES.add(new SimpleGrantedAuthority("USER"));
    }

    public long getSeq() {
        return seq;
    }

    public void setSeq(long seq) {
        this.seq = seq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDetails toUserDetails() {
        // for security context, implement UserDetails direclty in the following logic.
        // we only use username and password
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return AUTHORITIES;
            }

            @Override
            public String getPassword() {
                return User.this.getPassword();
            }

            @Override
            public String getUsername() {
                return User.this.getId();
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}

