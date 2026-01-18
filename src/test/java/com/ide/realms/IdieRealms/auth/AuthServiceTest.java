package com.ide.realms.IdieRealms.auth;

import com.ide.realms.IdieRealms.auth.dto.LoginRequest;
import com.ide.realms.IdieRealms.auth.dto.RegisterRequest;
import com.ide.realms.IdieRealms.exception.AccNotExist;
import com.ide.realms.IdieRealms.exception.AccountAlreadyExists;
import com.ide.realms.IdieRealms.exception.InvalidPassword;
import com.ide.realms.IdieRealms.hero.Hero;
import com.ide.realms.IdieRealms.shared.HeroClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Auth unit tests")
class AuthServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private Account account;

    @Mock
    private Hero hero;

    @InjectMocks
    private AuthService authService;

    private RegisterRequest registerRequest;

    private LoginRequest loginRequest;


    @BeforeEach
    void setUp () {
        this.registerRequest = RegisterRequest.builder()
                .email("johndoe@example.com")
                .nickname("Geralt")
                .password("StRoNgPaSsWoRd123!@")
                .visualConfig("0;1;3;5;6")
                .heroClass(HeroClass.WARRIOR)
                .heroNickname("Geralt from Rivia")
                .build();

        this.loginRequest = LoginRequest.builder()
                .email("johndoe@example.com")
                .password("examplePassword123")
                .build();

        this.hero = Hero.builder()
                .id(1L)
                .nickname("Geralt")
                .heroClass(HeroClass.WARRIOR)
                .level(1)
                .strength(10)
                .constitution(10)
                .visualConfig("1;0;2;0;0")
                .build();

        this.account = Account.builder()
                .id(1L)
                .email("johndoe@example.com")
                .password("encoded_password")
                .hero(hero)
                .build();


    }

    @DisplayName("Registration tests")
    @Nested
    class RegisterPlayerTest {

        @Test
        @DisplayName("Should register user successfuly")
        void shouldRegisterUserSuccessfuly() {
            // GIVEN
            when(accountRepository.findByEmail(registerRequest.getEmail()))
                    .thenReturn(Optional.empty());

            when(accountRepository.existsByNickname(registerRequest.getNickname()))
                    .thenReturn(false);

            when(passwordEncoder.encode(registerRequest.getPassword()))
                    .thenReturn("hashed_password_123");

            // WHEN
            authService.registerPlayer(registerRequest);

            // THEN
            verify(accountRepository, times(1)).save(any(Account.class));
            verify(passwordEncoder).encode(registerRequest.getPassword());
        }

        @DisplayName("shouldThrowExceptionWhenEmailAlreadyExists")
        @Test
        void shouldThrowExceptionWhenEmailAlreadyExists () {
//            given
            when(accountRepository.findByEmail(registerRequest.getEmail()))
                    .thenReturn(Optional.of(new Account()));

//            when
            final AccountAlreadyExists exception = assertThrows(
                    AccountAlreadyExists.class,
                    () -> authService.registerPlayer(registerRequest)
            );

//            then

            assertEquals("Account with provided email already exists", exception.getMessage());

            verify(accountRepository, never()).existsByNickname(registerRequest.getNickname());
            verify(accountRepository, never()).save(any());
        }

        @DisplayName("shouldThrowExceptionWhenNicknameAlreadyExists")
        @Test
        void shouldThrowExceptionWhenNicknameAlreadyExists () {
//            given
            when(accountRepository.findByEmail(registerRequest.getEmail()))
                    .thenReturn(Optional.empty());

            when(accountRepository.existsByNickname(registerRequest.getNickname()))
                    .thenReturn(true);
//            when
            final AccountAlreadyExists exception = assertThrows(
                    AccountAlreadyExists.class,
                    () -> authService.registerPlayer(registerRequest)
            );
//            then
            assertEquals("Account with provided nickname already exists", exception.getMessage());
            verify(accountRepository,times(1)).existsByNickname(registerRequest.getNickname());
            verify(accountRepository, never()).save(any());
        }

        @DisplayName("Login tests")
        @Nested
        class LoginTests {

            @DisplayName("Login successfuly")
            @Test
            void shouldLoginSuccessfuly () {
//                given
                when(accountRepository.findByEmail(loginRequest.getEmail())).thenReturn(
                        Optional.of(account)
                );

                when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

                String jwtToken = "fake-jwt-token";
                when(jwtService.generateToken(account)).thenReturn(jwtToken);

//                when and then
                String resultToken = authService.loginPlayer(loginRequest);

                assertEquals(jwtToken,resultToken);
                verify(jwtService).generateToken(account);
            }


            @DisplayName("Should throw AccNotExist when email is not found")
            @Test
            void shouldThrowExceptionWhenAccountDoesNotExist () {

                when(accountRepository.findByEmail(loginRequest.getEmail()))
                        .thenReturn(Optional.empty());

                // WHEN & THEN
                assertThrows(AccNotExist.class, () -> authService.loginPlayer(loginRequest));

                verify(passwordEncoder, never()).matches(anyString(), anyString());
                verify(jwtService, never()).generateToken(any());

            }

            @Test
            @DisplayName("should throw invalid password exception")
            void shouldThrowExceptionWhenPasswordIsInvalid() {
                // given
                when(accountRepository.findByEmail(loginRequest.getEmail()))
                        .thenReturn(Optional.of(account));

                when(passwordEncoder.matches(eq(loginRequest.getPassword()), eq(account.getPassword())))
                        .thenReturn(false);

                // when and then
                assertThrows(InvalidPassword.class, () -> authService.loginPlayer(loginRequest));

                verify(jwtService, never()).generateToken(any());
            }
        }
    }
}