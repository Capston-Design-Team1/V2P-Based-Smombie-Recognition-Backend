package com.capston.v2psmombie.service;

import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FcmService {

    private final FirebaseMessaging firebaseMessaging;


    //TODO: 토큰을 저장하는 메서드
//    @Transactional
//    public void saveToken(FcmTokenDto request){
//
//    }


    //TODO: 위험도가 1인 스몸비들에게 푸시 알림을 전송하는 메서드
//    public void sendMessageToSmombies(List<LocationDto> dangerSmombies) {
//        String title = "";
//        String body = "";
//        dangerSmombies.stream()
//                .forEach(smombie -> sendMessageByToken(smombie, title, body));
//    }


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
