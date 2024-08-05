package ru.kata.spring.boot_security.demo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.lang.NonNull;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
@Data
@NoArgsConstructor // констр без аргум
@RequiredArgsConstructor // констр для полей final, и полей с аннотацией @NonNull
@AllArgsConstructor // значения для всех полей принимает
@ToString(exclude = "users") // Исключить поле users из метода toString
@EqualsAndHashCode(exclude = "users") // Исключить поле users из методов equals и hashCode

public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false, unique = true)
    @NonNull // аннотация дефолтна тут
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Set<User> users = new HashSet<>();


}
