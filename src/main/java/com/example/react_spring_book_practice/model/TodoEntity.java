package com.example.react_spring_book_practice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder // Builder 패턴을 사용하여 오브젝트를 만들 수 있도록 해준다
@NoArgsConstructor // 매개변수 없는 생성자 구현
@AllArgsConstructor // 클레스의 모든 멤버변수를 매개변수로 받는 생성자 구현
@Data // 클래스 멤버 변수의 Getter/Setter 메서드를 구현
@Entity
@Table(name = "todo")
public class TodoEntity {
    @Id
    @GeneratedValue(generator = "system-uuid") // object를 DB에 저장할 때 id를 자동으로 생성하겠다
    @GenericGenerator(name="system-uuid", strategy = "uuid2")
    private String id; // 이 오브젝트의 아이디
    private String userId; // 이 오브젝트를 생성한 사용자의 아이디
    private String title; // Todo 타이틀(예: 운동하기)
    private boolean done; // true => Todo를 완료한 경우 (checked)
}
