package com.capston.v2psmombie.service;

import com.capston.v2psmombie.domain.Ap;
import com.capston.v2psmombie.domain.User;
import com.capston.v2psmombie.dto.UserCreateDto;
import com.capston.v2psmombie.dto.UserUpdateDto;
import com.capston.v2psmombie.repository.ApRepository;
import com.capston.v2psmombie.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final ApRepository apRepository;


    @Transactional
    public String create(UserCreateDto dto) {

        User user = dto.toUserEntity();
        if (userRepository.findByDeviceId(user.getDeviceId()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 디바이스입니다");
        }
        return userRepository.save(user).getDeviceId();
    }


    @Transactional
    public String update(String deviceId, UserUpdateDto dto) {

        User user = getUserByDeviceId(deviceId);

        Ap targetAp = apRepository.findByName(dto.getApName())
                .orElseThrow(() -> new IllegalArgumentException("해당 이름의 AP가 없습니다"));

        user.update(
                dto.getLatitude(), dto.getLongitude(),
                dto.getSpeed(), dto.getDirection(),
                dto.getMode(), dto.getSmombie(),
                targetAp
        );

        return userRepository.save(user).getDeviceId();
    }


    @Transactional
    public String delete(String deviceId) {

        User target = getUserByDeviceId(deviceId);
        userRepository.delete(target);
        return target.getDeviceId();
    }


    public List<User> getSmombieUsers(String deviceId) {

        User target = getUserByDeviceId(deviceId);
        List<User> users = userRepository.findByConnectedApId(target.getConnectedAp().getId());
        return users.stream()
                .filter(user -> user.isMode() == false && user.isSmombie() == true)
                .toList();
    }


    public User getUserByDeviceId(String deviceId) {
        return userRepository.findByDeviceId(deviceId)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다"));
    }

}

