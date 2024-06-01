package uz.app.hotel.entity;


import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private final String  id= UUID.randomUUID().toString();
    private String name;
    private String email;
    private String password;
    private Boolean confirmed;
    private String code;
    private LocalDateTime confirmationTime;
    private Role role;

    public User (String name, String email, Role role){
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
