package io.watssuggang.voda.common.jwt.service;

import io.watssuggang.voda.common.jwt.dto.*;
import io.watssuggang.voda.common.jwt.repository.*;
import jakarta.transaction.*;
import java.util.*;
import lombok.*;
import org.springframework.stereotype.*;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository tokenRepository;
    private final TokenProvider tokenProvider;

    @Transactional
    public void removeRefreshToken(String accessToken) {
        System.out.println("removeRefreshToken 메서드");

        RefreshToken token = tokenRepository.findByAccessToken(accessToken)
            .orElseThrow(IllegalArgumentException::new);

        tokenRepository.delete(token);
    }

    @Transactional
    public String republishAccessToken(String accessToken) {
        // 액세스 토큰으로 Refresh 토큰 객체를 조회
        Optional<RefreshToken> refreshToken = tokenRepository.findByAccessToken(accessToken);

        // RefreshToken이 존재하고 유효하다면 실행
        if (refreshToken.isPresent() && tokenProvider.verifyToken(
            refreshToken.get().getRefreshToken())) {
            // RefreshToken 객체를 꺼내온다.
            RefreshToken resultToken = refreshToken.get();
            // 권한과 아이디를 추출해 새로운 액세스토큰을 만든다.
            String newAccessToken = tokenProvider.generateAccessToken(resultToken.getId(),
                tokenProvider.getRole(resultToken.getRefreshToken()));
            // 액세스 토큰의 값을 수정해준다.
            resultToken.updateAccessToken(newAccessToken);
            tokenRepository.save(resultToken);
            // 새로운 액세스 토큰을 반환해준다.
            return newAccessToken;
        }

        return null;
    }

}
