package br.com.ifpe.bazzar.modelo.produto;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImagemService {

    private final OkHttpClient httpClient = new OkHttpClient();
    private final String supabaseUrl;
    private final String supabaseKey;
    private final String bucket;

    public ImagemService(
        @Value("${supabase.url}") String supabaseUrl,
        @Value("${supabase.key}") String supabaseKey,
        @Value("${supabase.storage.bucket}") String bucket) {
        this.supabaseUrl = supabaseUrl;
        this.supabaseKey = supabaseKey;
        this.bucket = bucket;
    }

    public String uploadImage(MultipartFile file) throws IOException {
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, RequestBody.create(file.getBytes(), MediaType.parse(file.getContentType())))
                .build();

        Request request = new Request.Builder()
                .url(supabaseUrl + "/storage/v1/object/" + bucket + "/" + fileName)
                .header("Authorization", "Bearer " + supabaseKey)
                .post(requestBody)
                .build();

        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            // Parse the response and return the public URL of the uploaded file
            return supabaseUrl + "/storage/v1/object/public/" + bucket + "/" + fileName;
        }
    }
}