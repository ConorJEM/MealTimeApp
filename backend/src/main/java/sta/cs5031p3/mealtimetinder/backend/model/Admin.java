package sta.cs5031p3.mealtimetinder.backend.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.PostPersist;
import javax.persistence.Table;

/**
 * The Admin model is for administrator, which inherits features from the general user.
 * It refers to the admin table in database.
 * @author 200011181
 */
@Entity
@Getter
@Table(name = "admin")
@NoArgsConstructor
public class Admin extends User {

    public Admin(String username,String password,Status status,String address,String postcode){
        super(null, username,password,status, Role.ADMIN,address,postcode);
    }

}
