package ru.nikitavov.avenir.web.controller.rest.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nikitavov.avenir.database.model.base.User;
import ru.nikitavov.avenir.database.repository.realisation.UserRepository;
import ru.nikitavov.avenir.web.message.model.wrapper.MessageWrapper;
import ru.nikitavov.avenir.web.message.realization.enitiy.FindsRequest;
import ru.nikitavov.avenir.web.message.realization.enitiy.misc.PageableResponse;
import ru.nikitavov.avenir.web.message.realization.enitiy.user.UserInfoResponse;
import ru.nikitavov.avenir.web.util.SortUtil;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("userss")
public class UserController2 {

    ExampleMatcher cameraMatcher = ExampleMatcher.matchingAny()
            .withIgnoreCase()
            .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
    private final UserRepository userRepository;

    @GetMapping()
    public ResponseEntity<MessageWrapper<PageableResponse<List<UserInfoResponse>>>> getViolations(FindsRequest request) {
        User routeExample = User.builder().name(request.filter()).surname(request.filter()).patronymic(request.filter()).email(request.filter()).build();
        Example<User> example = Example.of(routeExample, cameraMatcher);
        Page<User> cameras = userRepository.findAll(example,
                PageRequest.of(request.page(), request.size(), SortUtil.parseSort(request.sort())));

        List<UserInfoResponse> list = cameras.get()
                .map(user -> new UserInfoResponse(user.getId(), user.getName(), user.getSurname(), user.getPatronymic(), user.getEmail())
                )
                .toList();

        return ResponseEntity.ok(MessageWrapper.create(new PageableResponse<>(list, cameras.getTotalPages(), cameras.getTotalElements())));


    }

    @GetMapping("/{id}")
    public ResponseEntity<MessageWrapper<UserInfoResponse>> getCamera(@PathVariable Integer id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();

        UserInfoResponse userInfoResponse = new UserInfoResponse(user.getId(), user.getName(), user.getSurname(), user.getPatronymic(), user.getEmail());

        return ResponseEntity.ok(new MessageWrapper<>(userInfoResponse));
    }
}
