package nju.edu.cn.backend.converter;

import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import nju.edu.cn.backend.entity.Comment;
import nju.edu.cn.backend.vo.CommentVO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

/**
 * Comment -> CommentVO
 * <p>
 *
 * @author Shenmiu
 */
@Component
public class CommentConverter extends ConverterConfigurerSupport<Comment, CommentVO> {
    @Override
    protected Converter<Comment, CommentVO> converter() {
        return new AbstractConverter<Comment, CommentVO>() {
            @Override
            protected CommentVO convert(Comment source) {
                return CommentVO.builder()
                        .id(source.getId())
                        .nickName(source.getNickName())
                        .text(source.getText())
                        .likes(source.getLikes())
                        .createdAt(source.getCreatedAt())
                        .build();
            }
        };
    }
}
