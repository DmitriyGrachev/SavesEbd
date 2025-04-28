package org.hrachov.com.filmproject.model;

import jakarta.persistence.*;
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
public class Movie extends Film {
    private Integer duration;
    @Column(name = "source")
    private String source;
    @Column(name = "poster")
    private String posterPath;
}