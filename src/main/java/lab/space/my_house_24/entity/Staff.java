package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lab.space.my_house_24.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Table(name = "staff")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Staff implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 25, nullable = false)
    private String firstname;

    @Column(length = 25, nullable = false)
    private String lastname;

    @Column(length = 25, nullable = false)
    private String phone;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 250)
    private String token;

    @Column(length = 250)
    private String forgotToken;

    @Column
    private Boolean theme;

    @Column
    private Boolean tokenUsage;

    @Column
    private Boolean forgotTokenUsage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 50, nullable = false)
    private UserStatus staffStatus;

    @ManyToOne
    private Role role;

    @OneToMany(mappedBy = "staff")
    private List<CashBox> cashBoxList = new ArrayList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "staff_house",
            joinColumns = @JoinColumn(name = "staff_id"),
            inverseJoinColumns = @JoinColumn(name = "house_id")
    )
    private Set<House> houseList = new HashSet<>();

    @OneToMany(mappedBy = "staff")
    private List<MastersApplication> applicationList = new ArrayList<>();

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                ", forgotToken='" + forgotToken + '\'' +
                ", theme=" + theme +
                ", tokenUsage=" + tokenUsage +
                ", forgotTokenUsage=" + forgotTokenUsage +
                ", staffStatus=" + staffStatus +
                ", role=" + role +
                ", cashBoxList=" + cashBoxList +
                ", houseListSize=" + houseList.size() +
                ", applicationList=" + applicationList +
                '}';
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
            List<GrantedAuthority> authorities
                    = new ArrayList<>();
            List<SecurityLevel> securityLevels = getRole().getSecurityLevelList();
            for (SecurityLevel securityLevel : securityLevels) {
                authorities.add(new SimpleGrantedAuthority(securityLevel.getPage().name()));
            }
            return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
