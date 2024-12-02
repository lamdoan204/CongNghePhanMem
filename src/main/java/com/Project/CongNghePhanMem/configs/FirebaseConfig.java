package com.Project.CongNghePhanMem.configs;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;



import java.io.ByteArrayInputStream;


@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp initializeFirebase() throws IOException {
        String serviceAccountJson = "{\n" +
                "  \"type\": \"service_account\",\n" +
                "  \"project_id\": \"win-project-dcfd0\",\n" +
                "  \"private_key_id\": \"7d849f15d397900f10658899ca5bf49c9b31d6ce\",\n" +
                "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCgsvy+65dyujKP\\nwcf5P2c65Ha9r7F22oum9ulMBRR2yzKX/BA/491XkkQ7Uj8j6/Nj068mQ0koZMvO\\nnZwheAwYgRWZwh4oS30g0o3TivMQPVEwJe7JntR9/JrUrTvlocgvkn8G0DMk5JIi\\n9JBkYlyVPpTMgWxSAHUxandwJhJitgyl/VrIpb/+uNymOLtaDz5cA0lwcXUOTaDt\\nQEp7Vm/4CkYc4jcoDref5R/7HP5K1yFsNZciwObVFgawzcy9v+flzv4JFlPZRwWd\\nFuA9tL+h5Cl9Ihsohn455qzeHfxdxWqFDe8bzZBWRihIlyvhAwsRVqBVBsz2z3ND\\niOhYbfeNAgMBAAECggEAKrGS45hM2r6sOzQ1EDuvjd5vy5J3eAe1DY1QyxEYcD0o\\n+mcN3COKRT9HYGlH/B+uQKnXQTekIpaw7PpDpkKyF/qHhhukDnmLy2IVKwwh6oEX\\n5k5Vo/xOIRCxA+roXGhAep8Vn3ZF+Dm/t4i5KepWBZKkTrbd0+VqPVA3XjTHMrEW\\nU28j8DfjSCZv0tT/zduDAqBzDFMppx6L9GgIZUefjgdN/pORKL2ri/9QMt7EUog4\\nAlPrY/x5U9x+tRIO+Phpb5dSK7q+CjI9sM/htbrEOeAQtVVkjI8TzP8N9kqybXxP\\nVxFyzK40T3lUxpjHoFwSz+PJAVehgWJOtx0lmh47IwKBgQDEUpXQN+pLw+h6cQSA\\nA603G/d/4UfhdeA7oHf7R+aEWUDeu4Ne8SSU/b+vBGzQH3QhdfBg7oh4HP48pY0z\\na0nCpRelWyuEszbAM+CL5xOGWnqfTxC7txUVdifP+l9vby0RobKN77SgnPRWzhQj\\nxCUOwHVInx71EkGjOQmVr7oTnwKBgQDRjELb/gOo+7h0esfn7gn5dPC+/51ZA8ri\\nF3oorLbImH0Uok/5+aoqfVjNlP6//RvQpbTr3gxvzV0CdUdRSOJeJzAp8gPdyU5X\\nWQfUxE59tExAFCUmZYCbVZJBOYLy8C/XZNWC6eAYVK0NuZCEsipBJ9QH2YlC+zik\\n/fNPpBiFUwKBgGgpuTskr7dLVonAdTbGvJzExeifytehBzwbxA0a9P+7q2OB8g3p\\nvBMhHxbpLiKuzZsDTGrhpJ62I15iKCzj+9q+ZXFikhByVdZcsAMKs51RXEYyWRp/\\n5Tvh2PXMTd5+CleUh3wvdpLRNALqjFz9sPvGuXOymWZGLXVAygkVl5DZAoGBAJI+\\nG2NBUfibRM416lIZNOjeDi6eYof/N5a239CHSUGR+qlt5fcFoRbqSBGiDHCdCs6A\\n91UtsOxOHL1ccBtwjFs9akW726rrlTssrWR2ZHkiXZzS8mJrsGe4Psw6gQNfUWQ3\\nwTMOWhFg9y1+9jdFyGuUiku4LC2M+qlbenV4hox1AoGAMULIOe3A8/kGQW48VNd7\\nZAUPtetGBb+HuzRPkTWQoJHQEWTtz0epOnJvnz5Jt7xIDbm7AAJNs+tykjzrq8YN\\nyor4U7D3KzIDvOs/qP71kh6FNbGbNjdb6cPLehUdn4RmlD8k1AX77zg4CEqQ0hRF\\nTiXGUNiu5OWu5f8Up88S4ys=\\n-----END PRIVATE KEY-----\\n\",\n" +
                "  \"client_email\": \"firebase-adminsdk-33on6@win-project-dcfd0.iam.gserviceaccount.com\",\n" +
                "  \"client_id\": \"108887611362123119292\",\n" +
                "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                "  \"token_uri\": \"https://oauth2.googleapis.com/token\",\n" +
                "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/firebase-adminsdk-33on6%40win-project-dcfd0.iam.gserviceaccount.com\",\n" +
                "  \"universe_domain\": \"googleapis.com\"\n" +
                "}";

        ByteArrayInputStream serviceAccount = new ByteArrayInputStream(serviceAccountJson.getBytes());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://win-project-dcfd0-default-rtdb.asia-southeast1.firebasedatabase.app")
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
