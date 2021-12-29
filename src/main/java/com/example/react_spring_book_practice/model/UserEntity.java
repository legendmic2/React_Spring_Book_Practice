package com.example.react_spring_book_practice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "email")})
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid") // object를 DB에 저장할 때 id를 자동으로 생성하겠다
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    private String id; // 사용자에게 고유하게 부여되는 id

    @Column(nullable = false)
    private String username; // 사용자의 이름

    @Column(nullable = false)
    private String email; // 사용자의 email, 아이디와 같은 기능을 한다.

    @Column(nullable = false)
    private String password; // 패스워드
}
