package PostgreSqlMongo.Objects;

import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "\"Users\"")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToMany(mappedBy = "user")
    private Set<Contacts> contacts = new LinkedHashSet<>();

    @Column(name = "name", nullable = false, length = 20)
    private String name;


    public Integer getIds(){ return id;}

    public void setIds(Integer id){ this.id = id;}

    public String getName(){ return name;}

    public void setName(String name){ this.name = name;}

    public Set<Contacts> getContacts(){ return contacts;}

    public void setContacts(Set<Contacts> contacts){ this.contacts = contacts;}

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contacts=" + contacts +
                '}';
    }
}
