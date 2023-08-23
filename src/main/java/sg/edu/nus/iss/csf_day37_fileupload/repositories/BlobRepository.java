package sg.edu.nus.iss.csf_day37_fileupload.repositories;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class BlobRepository {
    
    @Autowired
    private JdbcTemplate template;

    public static final String SQL_INSERT_INTO_UPLOADS="insert into myuploads (description, media_type, content) values (?, ?, ?)";

    public void upload(String description, String mediaType, InputStream is){
        template.update(SQL_INSERT_INTO_UPLOADS, description, mediaType, is);
    }
}
