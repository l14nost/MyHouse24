package lab.space.my_house_24.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "message")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100)
    private String title;

    @Column(length = 1000)
    private String description;

    @Column(length = 1000)
    private String descriptionStyle;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "is_check")
    private Boolean isCheck;

    @Column(name = "send_date")
    private Instant sendDate;

    @ManyToOne
    private Staff staff;

    @ManyToOne
    private House house;

    @ManyToOne
    private Section section;

    @ManyToOne
    private Floor floor;

    @ManyToOne
    private Apartment apartment;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "message_user",
            joinColumns = @JoinColumn(name = "message_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    Set<User> users = new HashSet<>();

    public void addUser(User user){
        users.add(user);
        user.getMessageList().add(this);
    }
}
