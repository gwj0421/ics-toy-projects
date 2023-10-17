package com.example.ics.dto.clova;

public enum ClovaImageType {
    JPG("image/jpg","jpg"),
    JPEG("image/jpeg","jpeg"),
    PNG("image/png", "png"),
    PDF("application/pdf", "pdf"),
    TIFF("image/tiff", "tiff");

    private final String mimeType;
    private final String extension;

    ClovaImageType(String mimeType, String extension) {
        this.mimeType = mimeType;
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static String getExtensionFromMimeType(String mimeType) {
        for (ClovaImageType value : values()) {
            if (value.mimeType.equalsIgnoreCase(mimeType)) {
                return value.getExtension();
            }
        }
        throw new IllegalArgumentException("gwj : " + mimeType + " type is not format");
    }
}
