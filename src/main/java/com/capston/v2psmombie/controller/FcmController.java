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
     * 사용자의 디바이스 ID를 param으로 받는다
     * 사용자 정보(토큰)를 가져와 알림으로 전송한다
     *
     * @param deviceId
     */
    @GetMapping("/fcm/test/{deviceId}")
    public void fcmNotificationTest(@PathVariable String deviceId) {
        String token = fcmService.getToken(deviceId);
        String title = "FCM Notification Test with User Data";
        String body = String.format("당신의 토큰은 %s 입니다.", token);
        fcmService.sendMessageByToken(token, title, body);
    }

}

