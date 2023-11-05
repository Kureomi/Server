package com.line4thon.kureomi;

import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity(name="user_table")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    private String username;
    private String password;
    @Column(name = "unique_url") // 필드와 열 매핑
    private String uniqueUrl; // 필드 이름도 변경 (CamelCase)
    private String role;


    // Getter and Setter methods
    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
