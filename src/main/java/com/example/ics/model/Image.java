package com.example.ics.model;

import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Image {
    private MultipartFile imageFile;

    @Builder
    public Image(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }
}
