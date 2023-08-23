package sg.models;

public record UploadContent (Integer id, String description, String contentType, byte[] content){
}
