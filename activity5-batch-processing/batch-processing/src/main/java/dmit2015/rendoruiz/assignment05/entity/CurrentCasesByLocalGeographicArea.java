package dmit2015.rendoruiz.assignment05.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * This class contains the datatype of the csv file from this link:
 * https://data.edmonton.ca/Community-Services/COVID-19-in-Alberta-Current-cases-by-local-geograp/ix8f-s9xp
 *
 * @author Rendo Ruiz
 * @version 2021.04.12
 *
 */
@Entity
@Table(name = "current_covid_19_cases_by_local_geographic_area_in_alberta")
@Getter @Setter
public class CurrentCasesByLocalGeographicArea implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "Location is required")
    @Id
    @Column(nullable = false)
    private String location;

    @NotNull(message = "Total Cases is required")
    @Column(name = "total_cases", nullable = false)
    private Integer totalCases;

    @NotNull(message = "Active Cases is required")
    @Column(name = "active_cases", nullable = false)
    private Integer activeCases;

    @NotNull(message = "Recovered Cases is required")
    @Column(name = "recovered_cases", nullable = false)
    private Integer recoveredCases;

    @NotNull(message = "Deaths is required")
    @Column(nullable = false)
    private Integer deaths;

    @NotNull(message = "Polygon is required")
    @Column(nullable = false)
    @javax.json.bind.annotation.JsonbTransient
    private org.geolatte.geom.Polygon<org.geolatte.geom.G2D> polygon;

    @Version
    private Integer version;
}
