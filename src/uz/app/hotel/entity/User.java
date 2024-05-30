package uz.app.hotel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private final String  id= UUID.randomUUID().toString();
    private String name;
    private String username;
    private String password;
    private Boolean confirmed;
    private String code;
    private LocalDateTime confirmationTime;
    private Role role;
}
