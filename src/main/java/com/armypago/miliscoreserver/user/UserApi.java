package com.armypago.miliscoreserver.user;

import com.armypago.miliscoreserver.domain.branch.Branch;
import com.armypago.miliscoreserver.domain.user.Education;
import com.armypago.miliscoreserver.domain.user.User;
import com.armypago.miliscoreserver.user.dto.UserCreate;
import com.armypago.miliscoreserver.user.dto.UserDetail;
import com.armypago.miliscoreserver.user.dto.UserLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.armypago.miliscoreserver.user.UserApi.USER_URL;

@RequiredArgsConstructor
@RequestMapping(USER_URL)
@RestController
public class UserApi {

    static final String USER_URL = "/api/v1/user";

    private final CustomOAuth2UserService customOAuth2UserService;
    private final UserService userService;
    private final UserRepository userRepository;
    private final EducationRepository educationRepository;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody String token){
        User user = userService.loadUser(token);

        if(!user.hasInitialized()){
            UserCreate.Form form = userService.loadForm(user.getId());
            return ResponseEntity.status(HttpStatus.OK).body(form);
        }
        UserLogin.Session session = new UserLogin.Session(user.getId(), user.getName());
        return ResponseEntity.status(HttpStatus.OK).body(session);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> register(@PathVariable Long id,
                                      @RequestBody UserCreate.Request request){
        User user = userService.initiateUser(id, request);
        UserLogin.Session session = new UserLogin.Session(id, user.getName());
        return ResponseEntity.status(HttpStatus.OK).body(session);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id){
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("사용자를 찾을 수 없습니다.");
        }
        UserDetail.Response response = new UserDetail.Response(user.get());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

//    @PostMapping("/{id}")
//    public ResponseEntity<?> create(@PathVariable Long id,
//                                    @Valid @RequestBody UserCreate.Request request, Errors errors){
//        // TODO 수정자와 유저가 같은지 validate
//        UserDetail.Response response = null;
//        String errorMsg = errors.hasErrors() ?
//                errors.getAllErrors().get(0).getDefaultMessage() : null;
//        if(!errors.hasErrors()){
//            boolean initialized = userService.initializeUser(id, request);
//            System.out.println("");
//        }
//        return !errors.hasErrors() ?
//                ResponseEntity.status(HttpStatus.OK).body(response) :
//                ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(errors.getAllErrors().get(0).getDefaultMessage());
//    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateInfo(@PathVariable Long id){
        return null;
    }

    @PutMapping("/authenticate/{id}")
    public ResponseEntity<?> authenticate(@PathVariable Long id){
        return null;
    }

    //    @PostMapping
//    public ResponseEntity<?> create(@Valid @RequestBody BranchDetailDto.Request request, Errors errors) {
//
//        BranchDetailDto.Response response = null;
//        if(!errors.hasErrors()){
//            Branch branch = branchRepository.save(request.toEntity());
//            response = new BranchDetailDto.Response(branch, null);
//        }
//        return !errors.hasErrors() ?
//                ResponseEntity.status(HttpStatus.OK).body(response) :
//                ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(errors.getAllErrors().get(0).getDefaultMessage());
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<?> update(@PathVariable Long id,
//                       @Valid @RequestBody BranchDetailDto.Request branchRequestDto, Errors errors){
//
//        BranchDetailDto.Response response = null;
//        if(!errors.hasErrors()){
//            branchRepository.findById(id).ifPresent(branch -> {
//                branch.changeInfo(branchRequestDto.getName());
//            });
//            response = branchQueryRepository.findById(id);
//        }
//        return !errors.hasErrors() ?
//                ResponseEntity.status(HttpStatus.OK).body(response) :
//                ResponseEntity.status(HttpStatus.BAD_REQUEST).body("이미 존재하는 병과명입니다.");
//    }
}
