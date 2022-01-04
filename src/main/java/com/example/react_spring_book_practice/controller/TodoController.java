package com.example.react_spring_book_practice.controller;

import com.example.react_spring_book_practice.dto.ResponseDTO;
import com.example.react_spring_book_practice.dto.TodoDTO;
import com.example.react_spring_book_practice.model.TodoEntity;
import com.example.react_spring_book_practice.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class TodoController {
    @Autowired
    private TodoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testTodo() {
        String str = service.testService(); // 테스트 서비스 사용
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createTodo(@AuthenticationPrincipal String userId,
            @RequestBody TodoDTO dto) {
        try {
            // (1) TodoEntity로 변환한다.
            TodoEntity entity = TodoDTO.toEntity(dto);

            // (2) id를 null로 초기화한다. 생성 당시에는 id가 없어야 하기 때문이다.
            entity.setId(null);

            // (3) 임시 사용자 아이디를 설정해준다.
            // 기존 temporary-user 대신 @AuthenticationPrincipal에서 넘어온 userId로 설정해준다.
            entity.setUserId(userId);

            // (4) 서비스를 이용해 Todo Entity를 생성한다.
            List<TodoEntity> entities = service.create(entity);

            // (5) Java Stream을 이용해 return된 Entity List를 TodoDTO 리스트로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // (6) 변환된 TodoDTO List를 이용해 ResponseDTO를 초기화한다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // (7) ResponseDTO를 return한다
            return ResponseEntity.ok().body(response);

        } catch (Exception e) {
            // (8) 혹시 예외가 있는 경우 dto 대신 error에 메세지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveTodoList(@AuthenticationPrincipal String userId) {
        // (1) 서비스 메서드의 retrieve() 메소드를 사용해 Todo 리스트를 가져온다.
        List<TodoEntity> entities = service.retrieve(userId);

        // (2) Java Stream을 이용해 리턴된 엔티티 리스트를 TodoDTO List로 변환한다.
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // (3) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // (4) ResponseDTO를 리턴한다.
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto) {
        String temporaryUserId = "temporary-user"; // temporary user id.

        // (1) dto를 entity로 변환한다.
        TodoEntity entity = TodoDTO.toEntity(dto);

        // (2) id를 userId로 초기화한다.
        entity.setUserId(temporaryUserId);

        // (3) 서비스를 이용해 entity를 업데이트한다.
        List<TodoEntity> entities = service.update(entity);

        // (4) Java Stream을 이용해 리턴된 엔티티 리스트를 TodoDTO List로 변환한다.
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        // (5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        // (6) ResponseDTO를 리턴한다.
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteTodo(@AuthenticationPrincipal String userId, @RequestBody TodoDTO dto) {
        try {
            // (1) TodoEntity로 변환한다.
            TodoEntity entity = TodoDTO.toEntity(dto);

            // (2) 임시 사용자 아이디를 설정해준다.
            entity.setUserId(userId);

            // (3) 서비스를 이용해 entity를 삭제한다.
            List<TodoEntity> entities = service.delete(entity);

            // (4) Java Stream을 이용해 리턴된 엔티티 리스트를 TodoDTO List로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            // (5) 변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화한다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            // (6) ResponseDTO를 리턴한다.
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            // (7) 혹시 예외가 있는 경우 dto 대신 error에 메세지를 넣어 리턴한다.
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);
        }
    }


}
