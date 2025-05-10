package org.hrachov.com.filmproject.model.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;
import org.hrachov.com.filmproject.service.CommentService;
import org.hrachov.com.filmproject.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    @NotNull
    private Long filmId; // Изменено
    @NotNull
    private String text;
    private LocalDateTime time;
    private long likes; // Новое поле
    private long dislikes; // Новое поле
}