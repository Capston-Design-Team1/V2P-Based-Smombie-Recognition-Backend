package com.capston.v2psmombie.controller;

import com.capston.v2psmombie.dto.FcmTokenRequest;
import com.capston.v2psmombie.service.FcmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class FcmController {

    private final FcmService fcmService;

    @PostMapping("/fcm/token")
    public ResponseEntity<String> saveUserToken(@RequestBody FcmTokenRequest request) {
        try {
            fcmService.saveToken(request);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("[SUCCESS] 사용자 토큰 변경: " + request.getDeviceId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("[FAIL] 사용자 토큰 변경 실패");
        }
    }


    /**
     * FCM Notification 알림 테스트 API
     * 토큰을 받아 해당 기기에 알림을 전송
     *
     * @param token
     */
    @GetMapping("/fcm/test/{token}")
    public void fcmNotificationTest(@PathVariable String token) {
        String title = "FCM Notification Test API";
        String body = String.format("푸시 알림이 성공적으로 전송되었습니다. 당신의 token은 %s입니다.", token);
        fcmService.sendMessageByToken(token, title, body);
    }

}

