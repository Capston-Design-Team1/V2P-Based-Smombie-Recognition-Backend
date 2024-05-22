CREATE TABLE ap (
	ap_id INT AUTO_INCREMENT PRIMARY KEY,
    ap_name VARCHAR(100) NOT NULL UNIQUE, -- IP+MAC Base 64 Encoding
    latitude DECIMAL(10,7), -- AP location
    longitude DECIMAL(11,7)
);

CREATE TABLE user (
	user_id INT AUTO_INCREMENT PRIMARY KEY,
    device_id VARCHAR(36) NOT NULL UNIQUE, -- Device Identifier UUID
    latitude DECIMAL(10,7), -- User Location
    longitude DECIMAL(11,7),
    speed DECIMAL(6,3), -- User Speed
    direction DECIMAL(7,4), -- User Direction
    mode BOOLEAN NOT NULL, -- User mode Pedestrian(0) Vehicle(1)
    smombie BOOLEAN NOT NULL DEFAULT 0, -- Not Smomobie(0) Smombie(1)
    fcm_token VARCHAR(255), -- FCM Token
    ap_id INT, -- AP ID
    CONSTRAINT user_contact_id_fk
    FOREIGN KEY (ap_id)
    REFERENCES ap(ap_id)
);