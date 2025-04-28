package org.hrachov.com.filmproject.model.youtube;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VideoDirectory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(mappedBy = "videoDirectory", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference // Управляет сериализацией списка videoFrames
    private List<VideoFrame> videoFrames;

    private String description;
}