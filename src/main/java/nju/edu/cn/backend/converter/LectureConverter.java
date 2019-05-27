package nju.edu.cn.backend.converter;

import com.github.jmnarloch.spring.boot.modelmapper.ConverterConfigurerSupport;
import nju.edu.cn.backend.entity.Lecture;
import nju.edu.cn.backend.vo.LectureVO;
import org.modelmapper.AbstractConverter;
import org.modelmapper.Converter;
import org.springframework.stereotype.Component;

/**
 * Lecture -> LectureVO
 * <p>
 *
 * @author Shenmiu
 */
@Component
public class LectureConverter extends ConverterConfigurerSupport<Lecture, LectureVO> {
    @Override
    protected Converter<Lecture, LectureVO> converter() {
        return new AbstractConverter<Lecture, LectureVO>() {
            @Override
            protected LectureVO convert(Lecture source) {
                return LectureVO.builder()
                        .id(source.getId())
                        .title(source.getTitle())
                        .speaker(source.getSpeaker())
                        .start(source.getStart())
                        .validityDays(source.getValidityDays())
                        .expire(source.getStart().plusDays(source.getValidityDays()))
                        .build();
            }
        };
    }
}
