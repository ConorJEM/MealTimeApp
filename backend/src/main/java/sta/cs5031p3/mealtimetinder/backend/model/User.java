package sta.cs5031p3.mealtimetinder.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * The User model is for the general user, includes admin, hunter and restaurant user.
 * It refers to the user table in database.
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String username;

    @JsonIgnore
    private String password;

    public enum Status {
        REGISTERED,
        PENDING,
        DELETED
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum Role {
        ADMIN,
        HUNTER,
        RESTAURANT
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    private String address;

    private String postcode;

}
