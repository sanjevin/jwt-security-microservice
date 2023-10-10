package com.dms.roleservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("role")
public class RoleController {

    @GetMapping("/getRoles")
    public List<String> getRoles() {
        List<String> users = new ArrayList<>();
        users.add("admin");
        users.add("distributor");
        users.add("salesman");
        return users;
    }

}
