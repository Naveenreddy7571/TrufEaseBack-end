package org.truf.naveentruf.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.truf.naveentruf.Models.TrufUser;
import org.truf.naveentruf.Repositories.UserRepository;

import java.util.Arrays;
import java.util.Optional;

@Service
public class TrufUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<TrufUser> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            TrufUser trufUser = user.get();
            String[] roles = getRoles(trufUser);
            return User.builder()
                    .username(trufUser.getEmail())
                    .password(trufUser.getPassword())
                    .roles(roles)
                    .build();
        } else {
            throw new UsernameNotFoundException(email);
        }
    }

    public String[] getRoles(TrufUser user) {
        if (user.getRole() == null) {
            return new String[]{"USER"};
        }
        return user.getRole().split(",");
    }
}
