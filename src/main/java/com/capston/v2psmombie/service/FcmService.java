package com.capston.v2psmombie.service;

import com.capston.v2psmombie.domain.User;
import com.capston.v2psmombie.dto.FcmTokenRequest;
import com.capston.v2psmombie.dto.LocationDto;
import com.capston.v2psmombie.repository.UserRepository;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    @Transactional
    public void saveToken(FcmTokenRequest request) {
        User user = userRepository.findByDeviceId(request.getDeviceId())
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        user.updateFcmToken(request.getFcmToken());
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public String getToken(String deviceId) {
        User user = userRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다."));

        return user.getFcmToken();
    }


    public void sendMessageToSmombies(List<LocationDto> dangerSmombies) {
        String title = "[ALERT] 보행자 알림";
        String body = "주의! 근처에 충돌 가능성이 높은 차량이 있습니다";
        dangerSmombies.stream()
                .forEach(smombie -> sendMessageByToken(getToken(smombie.getDeviceId()), title, body));
    }


    public void sendMessageByToken(String targetToken, String title, String body) {
        Message message = makeMessage(targetToken, title, body);
        try {
            firebaseMessaging.send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
    }


    private Message makeMessage(String targetToken, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setNotification(notification)
                .setToken(targetToken)
                .build();

        return message;
    }

}
