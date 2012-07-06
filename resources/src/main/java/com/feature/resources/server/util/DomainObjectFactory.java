package com.feature.resources.server.util;

import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.domain.Properties;
import com.feature.resources.server.domain.User;
import com.feature.resources.server.dto.FileMeta;
import com.feature.resources.server.dto.UserDTO;
import com.feature.resources.server.dto.UserStatus;
import com.feature.resources.server.util.security.Digests;
import com.google.common.base.Preconditions;

import java.util.UUID;

/**
 * User: ZouYanjian
 * Date: 12-6-5
 * Time: 上午11:09
 * FileName:DomainObjectFactory
 */
public class DomainObjectFactory {
    private static final int INTERATIONS = 1024;
    private static final int SALT_SIZE = 8;
    private static final String ALGORITHM = "SHA-1";

    public Graphic createGraphic(String fileName, String contentType) {
        Graphic graphic = new Graphic();
        graphic.setType(contentType);
        graphic.setName(fileName);
        return graphic;
    }

    public Properties createProperties(String fileName, long size, String contentType) {
        Properties properties = new Properties();
        properties.setName(fileName);
        properties.setSize(size);
        properties.setUuid(UUID.randomUUID().toString());
        properties.setMimeType(contentType);
        properties.setStatus("unchecked");
        return properties;
    }

    public FileMeta createFileMeta(Graphic graphic) {
        String url = "../" + graphic.getOriginalFilePath();
        String thumbnailUrl = "../" + graphic.getThumbnailPath();
        String deleteUrl = "../rs/file/" + graphic.getId();
        long fileSize = graphic.getProperties().getSize();
        String fileName = graphic.getProperties().getName();
        return new FileMeta(fileName, fileSize, url, thumbnailUrl, deleteUrl);
    }

    public User translateToUser(UserDTO userDTO) {
        Preconditions.checkNotNull(userDTO,"userDTO is null");
        User user = new User();
        user.setLoginName(userDTO.getLoginName());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPlainPassword(userDTO.getPassword());

        HashPassword hashPassword = encrypt(user.getPlainPassword());
        user.setSalt(hashPassword.salt);
        user.setPassword(hashPassword.password);
        user.setStatus(UserStatus.ACTIVE.getValue());
        return user;
    }

    public static class HashPassword{
        public String salt;
        public String password;
    }

    public HashPassword encrypt(String plainText){
        HashPassword result = new HashPassword();
        byte[] salt = Digests.generateSalt(SALT_SIZE);
        result.salt = Encodes.encodeHex(salt);
        byte[] hashPassword = Digests.sha1(plainText.getBytes(),salt,INTERATIONS);
        result.password = Encodes.encodeHex(hashPassword);
        return result;
    }
}
