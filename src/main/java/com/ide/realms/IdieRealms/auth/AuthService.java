package com.ide.realms.IdieRealms.auth;

import com.ide.realms.IdieRealms.auth.dto.LoginRequest;
import com.ide.realms.IdieRealms.auth.dto.RegisterRequest;
import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.exception.AccountAlreadyExists;
import com.ide.realms.IdieRealms.exception.InvalidPassword;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.shared.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Transactional
    public void registerPlayer (RegisterRequest registerRequest) {

//        check if account exsits by nickname (or , and) with nickname
        accountRepository.findByEmail(registerRequest.getEmail())
                .ifPresent(account -> {
                    throw new AccountAlreadyExists("Account with provided email already exists");
                });

        var existingNIckname = accountRepository.existsByNickname(registerRequest.getNickname());

        if (existingNIckname) {
            throw new AccountAlreadyExists("Account with provided nickname already exists");
        }

//      hashing password
        String encodedPassword = passwordEncoder.encode(registerRequest.getPassword());

//        building object and saving to database
        Hero hero = Hero.builder()
                .heroClass(registerRequest.getHeroClass())
                .nickname(registerRequest.getHeroNickname())
                .build();

        Account account = Account.builder()
                .role(Role.USER)
                .hero(hero)
                .email(registerRequest.getEmail())
                .nickname(registerRequest.getNickname())
                .password(encodedPassword)
                .build();

        accountRepository.save(account);
    }


    public String loginPlayer (LoginRequest loginRequest) {

//        check if user exists
        Account user = accountRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new AccNotExist("Account with provided email does not exist"));

//        verify password
        boolean isValid = passwordEncoder.matches(
                loginRequest.getPassword(),
                user.getPassword()
        );

        if (isValid == false) {
            throw new InvalidPassword("Invalid Password");
        }

        String token = jwtService.generateToken(user);

        return token;
    }
}