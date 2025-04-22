package com.cg.hms.adm.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.cg.hms.adm.entity.UserEntity;
import com.cg.hms.adm.model.UserModel;

@Component
public class UserUtil {
	public UserModel convertUserEntityToModel(UserEntity userEntity) {
        UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setName(userEntity.getUsername());
        userModel.setPassword(userEntity.getPassword());
        userModel.setRole(userEntity.getRole());
        userModel.setPhone(userEntity.getPhone());
        userModel.setEmail(userEntity.getEmail());

        return userModel;
    }

    public UserEntity convertUserModelToEntity(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(userModel.getId());
        userEntity.setUsername(userModel.getName());
        userEntity.setPassword(userModel.getPassword());
        userEntity.setRole(userModel.getRole());
        userEntity.setPhone(userModel.getPhone());
        userEntity.setEmail(userModel.getEmail());

        return userEntity;
    }

    public List<UserModel> convertUserEntityListToModelList(List<UserEntity> userEntities) {
        List<UserModel> userModels = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            UserModel userModel = convertUserEntityToModel(userEntity);
            userModels.add(userModel);
        }

        return userModels;
    }

    

    public void updateUserEntityFromModel(UserModel userModel, UserEntity userEntity) {
        if (userModel.getName() != null) {
            userEntity.setUsername(userModel.getName());
        }
        if (userModel.getPassword() != null) {
            userEntity.setPassword(userModel.getPassword());
        }
        if (userModel.getRole() != null ) {
            userEntity.setRole(userModel.getRole());  // Convert String role to Enum
        }
        if (userModel.getPhone() != null) {
            userEntity.setPhone(userModel.getPhone());
        }
        if (userModel.getEmail() != null) {
            userEntity.setEmail(userModel.getEmail());
        }
    }

}
