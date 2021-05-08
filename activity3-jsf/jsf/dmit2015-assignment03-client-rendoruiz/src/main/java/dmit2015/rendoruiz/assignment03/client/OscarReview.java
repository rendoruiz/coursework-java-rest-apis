package dmit2015.rendoruiz.assignment03.client;

import lombok.Data;
import java.time.LocalDateTime;

/**
 * This class models an Oscar Review that contains 7 fields:
 * id, category, nominee, review, username, createdDateTime, lastModifiedDateTime
 *
 * @author Rendo Ruiz
 * @version 2021.03.12
 *
 */
@Data
public class OscarReview {

    private Long id;

    private String category;

    private String nominee;

    private String review;

    private String username;

    private LocalDateTime createdDateTime;

    private LocalDateTime lastModifiedDateTime;

    private Integer version;
}
