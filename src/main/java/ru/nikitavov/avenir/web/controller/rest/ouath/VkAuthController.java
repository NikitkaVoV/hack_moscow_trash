package ru.nikitavov.avenir.web.controller.rest.ouath;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nikitavov.avenir.general.model.tuple.Tuple2;
import ru.nikitavov.avenir.web.data.handler.service.VkAuthService;
import ru.nikitavov.avenir.web.message.model.wrapper.MessageWrapper;
import ru.nikitavov.avenir.web.message.realization.user.auth.TokensResponse;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/auth/vk")
@RequiredArgsConstructor
public class VkAuthController {

    private final VkAuthService vkAuthService;

    @PostMapping("/login")
    public ResponseEntity<MessageWrapper<TokensResponse>> login(@RequestBody Map<String, String> params) {
        Tuple2<Boolean, String> validated = vkAuthService.validateRequest(params);
        if (!validated.param1()) {
            return ResponseEntity.badRequest().body(new MessageWrapper<>(null, 1));
        }
        TokensResponse tokensResponse = vkAuthService.loginOrCreateUser(validated.param2());
        return ResponseEntity.ok(new MessageWrapper<>(tokensResponse));
    }

}
