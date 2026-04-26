@Entity
@Table(name = "permrole")
public class PermRole {

    @Id
    @Column(name = "RoleNumber")
    private Integer roleNumber;

    @Column(name = "PermRole")
    private String permRole;
}