package PostgreSqlMongo.ServicesObjects;

import PostgreSqlMongo.DataManagementService.UserDMS;
import PostgreSqlMongo.Objects.Users;

import java.util.List;

public class UserAction {
    private final UserDMS userDMS = new UserDMS();
    public Users findUser(int id){ return userDMS.findById(id);}
    public void saveUser(Users user){ userDMS.save(user);}
    public void deleteUser(Users user){ userDMS.delete(user);}
    public void updateUser(Users user){ userDMS.update(user);}
    public List<Users> findAllUsers(){ return UserDMS.findAll();}
}
