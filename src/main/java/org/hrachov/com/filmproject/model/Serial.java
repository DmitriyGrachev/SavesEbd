package org.hrachov.com.filmproject.model;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@PrimaryKeyJoinColumn(name = "film_id")
public class Serial extends Film {
    private Integer seasons;
    private Integer episodes;
    @Column(name = "poster")
    private String posterPath;
}
