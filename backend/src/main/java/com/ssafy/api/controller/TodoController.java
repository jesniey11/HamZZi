package com.ssafy.api.controller;

import com.ssafy.api.request.TodoRequest;
import com.ssafy.api.service.TodoService;
import com.ssafy.db.entity.Todo.Todo;
import com.ssafy.db.repository.TodoRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("api/todo")
@RequiredArgsConstructor
public class TodoController {

    @Autowired
    TodoService todoService;

    @Autowired
    TodoRepository todoRepository;

    // 작성자와 날짜가 맞는 Todo리스트 조회(작성자는 완료 / 날짜만 맞추면 됨)
    @GetMapping("/{nickname}")
    @ApiOperation(value = "Todo 조회", notes = "Todo 정보를 출력한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> getTodos(@PathVariable String nickname){
        List<Todo> todo = todoService.getTodos(nickname);

        return ResponseEntity.status(200).body(todo);
    }

    // Todo 생성 API(완료)
    @PostMapping
    @ApiOperation(value = "Todo 생성", notes = "필요한 정보를 전부 입력한다")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public String createTodo(
            @RequestBody @ApiParam(value = "todo 생성", required = true) TodoRequest todoInfo) {
        Todo todo = todoService.createTodo(todoInfo);

        return "ID: " + todo.getTodo_id() + " OWNER: " + todo.getNickname() + " CONTENT: " + todo.getContent() ;
    }

    // Todo ischeck 바꾸기(완료)
    @PutMapping("/check/{id}")
    @ApiOperation(value = "Todo 완료", notes = "해당 Todo를 완료한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "Todo 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public String checkTodo(@PathVariable Long id){
        Todo todo = todoService.checkUpdateTodo(id);
        return "ischeck: " + todo.getIscheck() ;
    }

    // todo content 수정(이거도 안됨)
    @PutMapping("/{id}")
    @ApiOperation(value = "Todo 수정", notes = "해당 Todo를 수정한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "Todo 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public String updateTodo(@PathVariable Long id, @RequestBody Todo content) {
        Todo todo = todoService.updateTodo(id);

        return "Content : " + todo.getContent() ;
    }

    // todo 삭제
    @DeleteMapping("/{id}")
    @ApiOperation(value = "Todo 삭제", notes = "해당 Todo를 삭제한다.")
    public String deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return "ID: " + id;
    }

}
