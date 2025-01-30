package ua.mykola.thoughtflow.document;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ua.mykola.thoughtflow.model.Topic;

import java.util.Collection;
import java.util.List;

@Data
@Builder
@Document(collection = "users")
public class User implements UserDetails {
    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    private String password;

    private List<Topic> favoriteTopics;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
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
}
