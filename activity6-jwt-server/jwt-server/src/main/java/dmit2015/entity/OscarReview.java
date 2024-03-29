package dmit2015.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * This class models an Oscar Review that contains 7 fields:
 * id, category, nominee, review, username, createdDateTime, lastModifiedDateTime
 * The category is limited to 5 options: film, actor, actress, editing, effects
 *
 * @author Rendo Ruiz
 * @version 2021.03.02
 *
 */
@Entity                     // This class is map to database table with the same name as the class name
@Setter @Getter
@NoArgsConstructor
@Table(name = "oscarreviews")
public class OscarReview implements Serializable {
    @Id                 // This is the primary key field
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // This primary key field is generated by the database
    private Long id;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "The Category field is required.")
    @Pattern(regexp = "^(film|actor|actress|editing|effects)$",  // Must only use letters.
            message = "The field Category must match the regular expression '^(film|actor|actress|editing|effects)$'.")
    private String category;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "The Nominee field is required.")
    private String nominee;

    @Column(length = 500, nullable = false)
    @NotBlank(message = "The Review field is required.")
    private String review;

    @Column(length = 50, nullable = false)
    @NotBlank(message = "The Username field is required.")
    private String username;

    @Column(nullable = false)
    private LocalDateTime createdDateTime;

    @Column(nullable = false)
    private LocalDateTime lastModifiedDateTime;

    @PrePersist
    private void beforePersist() {
        createdDateTime = LocalDateTime.now();
        lastModifiedDateTime = createdDateTime;
    }

    @PreUpdate
    private void beforeUpdate() {
        lastModifiedDateTime = LocalDateTime.now();
    }

    @Version
    private Integer version;
}
