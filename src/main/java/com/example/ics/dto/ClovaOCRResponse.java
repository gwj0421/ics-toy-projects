package com.example.ics.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class ClovaOCRResponse {
    private String version;
    private String requestId;
    private String timestamp;
    private List<ImageRecognitionResults> images;

    @Getter
    public static class ImageRecognitionResults {
        private String uid;
        private String name;
        private String inferResult;
        private String message;
        private ValidationResult validationResult;
        private ConvertedImageInfo convertedImageInfo;
        private List<ImageField> fields;
    }

    @Getter
    public static class ValidationResult {
        private String result;
    }

    @Getter
    public static class ConvertedImageInfo {
        private Integer width;
        private Integer height;
        private Integer pageIndex;
        private boolean longImage;
    }

    @Getter
    public static class ImageField {
        private String valueType;
        private BoundingPoly boundingPoly;
        private String inferText;
        private float inferConfidence;
        private String type;
        private boolean lineBreak;
    }

    @Getter
    public static class BoundingPoly {
        private List<Vertices> vertices;
    }

    @Getter
    public static class Vertices {
        private float x;
        private float y;
    }
}

