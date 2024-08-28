package com.dev.innverview.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private String email;
    private String kakaoId;

    public static class Builder {
        private Long id;
        private String nickname;
        private String email;
        private String kakaoId;


        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder nickname(String nickname) {
            this.nickname = nickname;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder kakaoId(String kakaoId) {
            this.kakaoId = kakaoId;
            return this;
        }

        public User build() {
            User user = new User();
            user.id = this.id;
            user.nickname = this.nickname;
            user.email = this.email;
            user.kakaoId = this.kakaoId;
            return user;
        }
    }
}
