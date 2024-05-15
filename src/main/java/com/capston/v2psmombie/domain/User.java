package com.capston.v2psmombie.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(name = "device_id")
    private String deviceId;

    private double latitude;
    private double longitude;
    private double speed;
    private double direction;
    private boolean mode;
    private boolean smombie;
    private String fcmToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ap_id")
    private Ap connectedAp;


    @Builder
    public User(
            String deviceId,
            double latitude, double longitude,
            double speed, double direction,
            boolean mode, boolean smombie,
            Ap connectedAp
    ) {
        this.deviceId = deviceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.direction = direction;
        this.mode = mode;
        this.smombie = smombie;
        this.connectedAp = connectedAp;
    }

    public static UserBuilder builder(String deviceId, boolean mode) {
        UserBuilder userBuilder = new UserBuilder();
        userBuilder.deviceId(deviceId);
        userBuilder.mode(mode);
        return userBuilder;
    }


    public void update(
            double latitude, double longitude,
            double speed, double direction,
            boolean mode, boolean smombie,
            Ap connectedAp
    ) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.speed = speed;
        this.direction = direction;
        this.mode = mode;
        this.smombie = smombie;
        this.connectedAp = connectedAp;
    }


    public void updateFcmToken(String fcmToken){
        this.fcmToken = fcmToken;
    }

}

