@Entity
@Table(name = "\"user\"")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID;

    private String lastName;
    private String firstName;
    private String phoneNumber;
    private String userName;
    private String password;

    @ManyToOne
    @JoinColumn(name = "RoleNumber")
    private PermRole role;
}