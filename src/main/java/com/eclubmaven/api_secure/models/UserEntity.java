package com.eclubmaven.api_secure.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.UUID;

@Entity
@Table(name = "users")
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE id = ?")
@Where(clause = "deleted_at IS NULL")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Le prénom est obligatoire ...")
    private String firstName;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "Le nom est obligatoire ...")
    private String lastName;

    @Column(nullable = false, unique = true, length = 50)
    @Email(message = "Format d'email invalide ...")
    @NotBlank(message = "L'email est obligatoire ...")
    private String email;

    @Column(length = 20)
    @Pattern(regexp = "^\\+?[0-9]{7,15}$", message = "Numéro de téléphone invalide")
    private String phone;

    @Column(nullable = false, unique = true, length = 50)
    @NotBlank(message = "Le pseudo est obligatoire ...")
    private String pseudo;

    @Column(nullable = false, length = 255)
    @NotBlank(message = "Le mot de passe est obligatoire ...")
    private String password;

    public UUID getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPhone() {
        return this.phone;
    }

    public String getPseudo() {
        return this.pseudo;
    }

    public String getPassword() {
        return this.password;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}