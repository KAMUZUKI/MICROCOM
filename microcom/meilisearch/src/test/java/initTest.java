import cn.hutool.core.io.file.FileReader;
import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import com.meilisearch.sdk.Index;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

/**
 * @author MUZUKI
 * @Classname initTest
 * @Description TODO
 * @Date 2023/5/20 21:39
 */

class initTest {
    public static void main(String[] args) throws Exception {
        Client client = new Client(new Config("http://175.178.212.207:7700", "AYp0zkq8s7xgn1oO6XZnU4eY9uVj7EKH68Xsz7sj9d0"));
        FileReader fileReader = new FileReader(new File("D:\\Gitresp\\MICROCOM\\microcom\\framework\\src\\main\\resources\\static\\movies.json"));
        String moviesJson = fileReader.readString();
//        client.createIndex("movies");
        // An index is where the documents are stored.
        Index index = client.index("movies");

        // If the index 'movies' does not exist, Meilisearch creates it when you first add the documents.
        index.addDocuments(moviesJson); // => { "taskUid": 0 }

    }
}
