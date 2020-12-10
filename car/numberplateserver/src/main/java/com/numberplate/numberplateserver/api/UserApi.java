package com.numberplate.numberplateserver.api;

import com.alibaba.fastjson.JSONObject;
import com.numberplate.numberplateserver.entity.User;
import com.numberplate.numberplateserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserApi {
    private UserService userService;

    @Autowired
    public UserApi(UserService userService){
        this.userService = userService;
    }

    @PostMapping("")
    public Object add(@RequestBody User user){
        if (userService.findByName(user.getName()) != null) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message","The user name is already in use");
            return jsonObject;
        }

        if (userService.add(user) instanceof User){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message","success");
            return jsonObject;
        } else {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("message","failed");
            return jsonObject;
        }
    }
}
