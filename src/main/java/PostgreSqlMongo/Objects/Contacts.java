package PostgreSqlMongo.Objects;

import jakarta.persistence.*;

@Entity
@Table (name = "\"Contacts\"")
public class Contacts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"userId\"")
    private Users user;

    @Column(name = "\"contactName\"", nullable = false)
    private String contactName;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "email", length = 100)
    private String email;
    public Integer getIds(){ return id;}
    public void setIds(Integer id){ this.id = id;}

    public Users getUser() { return user;}

    public void setUser(Users user){ this.user = user;}

    public String getContactName(){ return contactName;}

    public void setContactName(String contactName){ this.contactName = contactName;}
    public String getPhone(){ return phone;}

    public void setPhone(String phone){ this.phone = phone;}
    public String getEmail(){ return email;}

    public void setEmail(String email){ this.email = email;}

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", customerId=" + id +
                ", contactName='" + contactName + '\'' +
                ", phone=" + phone +
                ", email='" + email + '\'' +
                '}';
    }
}
