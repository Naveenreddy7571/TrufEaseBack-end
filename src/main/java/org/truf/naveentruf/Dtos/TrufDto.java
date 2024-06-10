package org.truf.naveentruf.Dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Blob;

@Getter
@Setter
public class TrufDto {
    private String groundName;
    private String groundDescription;
    private int groundWidth;
    private int groundHeight;
    private Double price;
    private MultipartFile groundImg;
}
