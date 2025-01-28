    package com.webalk.webapp.config;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.core.userdetails.User;
    import org.springframework.security.core.userdetails.UserDetails;
    import org.springframework.security.core.userdetails.UserDetailsService;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.security.crypto.password.PasswordEncoder;
    import org.springframework.security.provisioning.InMemoryUserDetailsManager;
    import org.springframework.security.web.SecurityFilterChain;

    @Configuration
    public class SecurityConfig {
        private final RoleBasedAuthenticationSuccessHandler successHandler;

        // Injektáljuk a RoleBasedAuthenticationSuccessHandler-t
        public SecurityConfig(RoleBasedAuthenticationSuccessHandler successHandler) {
            this.successHandler = successHandler;
        }

            @SuppressWarnings("removal")
            @Bean
            public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                        .csrf().disable()
                        .authorizeHttpRequests()
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/books").hasRole("USER")
                        .anyRequest().authenticated()
                        .and()
                        .formLogin()
                        .successHandler(successHandler)
                        .permitAll()
                        .and()
                        .logout()
                        .logoutSuccessUrl("/login?logout")
                        .permitAll();

                return http.build();
            }


        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Bean
        public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
            // InMemory felhasználók beállítása
            UserDetails admin = User.withUsername("admin")
                    .password(passwordEncoder.encode("admin"))
                    .roles("ADMIN") // Admin jogosultság
                    .build();

            UserDetails user = User.withUsername("user")
                    .password(passwordEncoder.encode("user"))
                    .roles("USER") // Felhasználói jogosultság
                    .build();

            return new InMemoryUserDetailsManager(admin, user);
        }
    }
