package sg.edu.nus.iss.csf_day37_fileupload.repositories;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import sg.models.UploadContent;

@Repository
public class BlobRepository {
    
    @Autowired
    private JdbcTemplate template;

    public static final String SQL_INSERT_INTO_UPLOADS="insert into myuploads (description, media_type, content) values (?, ?, ?)";

    public static final String SQL_SELECT_UPLOADS_BY_ID = "select * from myuploads where id = ?";


    public void upload(String description, String mediaType, InputStream is){
        template.update(SQL_INSERT_INTO_UPLOADS, description, mediaType, is);
    }

    public Optional<UploadContent> selectUploadsById (Integer id){
        List<UploadContent> result = template.query(SQL_SELECT_UPLOADS_BY_ID, (ResultSet rs) -> {
                List<UploadContent> results = new LinkedList<>();
                while (rs.next()){
                    UploadContent uc = new UploadContent(
                                            id, rs.getString("description"),
                                            rs.getString("media_type"),
                                            rs.getBytes("content"));
                results.add(uc);
                }
                return results;
            }, id);
        if (result.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(result.get(0));
    }

}
