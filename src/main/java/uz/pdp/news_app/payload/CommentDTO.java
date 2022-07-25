package uz.pdp.news_app.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    @NotNull(message = "text Required")
    private String text;

    @NotNull(message = "id Required")
        private Long postId;
}
