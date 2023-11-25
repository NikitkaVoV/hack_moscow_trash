package ru.nikitavov.avenir.web.data.handler.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nikitavov.avenir.database.model.base.LinkedSocialNetwork;
import ru.nikitavov.avenir.database.model.base.User;
import ru.nikitavov.avenir.database.repository.realisation.LinkedSocialNetworkRepository;
import ru.nikitavov.avenir.general.model.socuialnetwork.SocialNetworkType;
import ru.nikitavov.avenir.general.model.tuple.Tuple2;
import ru.nikitavov.avenir.general.model.tuple.Tuple3;
import ru.nikitavov.avenir.web.message.realization.user.auth.TokensResponse;
import ru.nikitavov.avenir.web.security.data.UserPrincipal;
import ru.nikitavov.avenir.web.security.service.UserPrincipalService;
import ru.nikitavov.avenir.web.security.service.UserService;
import ru.nikitavov.avenir.web.security.service.auth.AuthenticationService;
import ru.nikitavov.avenir.web.security.service.auth.TokenProvider;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class VkAuthService {

    @Value("${app.vk.client-secret}")
    private String clientSecret;

    private final TokenProvider tokenProvider;
    private final UserService userService;
    private final LinkedSocialNetworkRepository linkedSocialNetworkRepository;

    public TokensResponse loginOrCreateUser(String vkId) {
        Optional<LinkedSocialNetwork> optionalLinkedSocialNetwork
                = linkedSocialNetworkRepository.findByUserSocialNetworkIdAndSocialNetworkType(vkId, SocialNetworkType.VK);

        User user;
        if (optionalLinkedSocialNetwork.isEmpty()) {
            user = userService.registryUser(SocialNetworkType.VK, vkId);
        } else {
            user = optionalLinkedSocialNetwork.get().getUser();
        }

        Tuple3<String, String, Date> tokens = tokenProvider.createTokens(user);
        return new TokensResponse(tokens.param1(), tokens.param2(), tokens.param3().getTime());
    }

    public Tuple2<Boolean, String>  validateRequest(Map<String, String> params) {
        String checkString = params.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith("vk_"))
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> encode(entry.getKey()) + "=" + encode(entry.getValue()))
                .collect(Collectors.joining("&"));

        String sign = getHashCode(checkString, clientSecret);
        String vkId = "";
        if (params.containsKey("vk_user_id")) {
            vkId = params.get("vk_user_id");
        }
        boolean validSign = sign.equals(params.getOrDefault("sign", "none_sign"));
        return new Tuple2<>(validSign, vkId);
    }

    private static String getHashCode(String data, String key) {
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            log.error(e);
            return "";
        }
        byte[] hmacData = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getUrlEncoder().withoutPadding().encode(hmacData));
    }

    private static String decode(String value) {
        return URLDecoder.decode(value, StandardCharsets.UTF_8);
    }

    private static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);

    }

}
