package com.struninproject.onlinestore.config.security;

import com.struninproject.onlinestore.repository.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * The {@code CustomUserDetailService} class
 *
 * @author Strunin Dmytro
 * @version 1.0
 */
//https://russianblogs.com/article/4159851543/
@Component
public class CustomUserDetailsService implements UserDetailsService {
//    private final PasswordEncoder passwordEncoder;
//    private final Logger log = LoggerFactory.getLogger(getClass());
//
//    @Autowired
//    public CustomUserDetailsService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    public CustomUserDetailsService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email);
    }

//    /**
//     * Сравните пароль, полученный из базы данных, с паролем, предоставленным клиентской частью
//     */
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        log.info("имя пользователя:" + username);
//        // Указываем данные авторизации пользователя для входа, здесь указывается пароль, собственно, нужно проверить пароль в базе
//        String password = passwordEncoder.encode("123456");
//        log.info("Пароль в базе данных:" + password);
//        return new User(username, password,
//                true, true, true, true,        // Пользователь заблокирован
//                AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
//    }
}