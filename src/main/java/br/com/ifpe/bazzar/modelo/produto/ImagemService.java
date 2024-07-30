package br.com.ifpe.bazzar.modelo.produto;

import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
    
    String originalFilename = file.getOriginalFilename();
    if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".jpg")) {
        throw new IllegalArgumentException("permitimos apenas arquivos .jpg.");
    }
    
    String fileName = System.currentTimeMillis() + "_" + URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8.toString());
    
    RequestBody requestBody = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", fileName, RequestBody.create(file.getBytes(), MediaType.parse(file.getContentType())))
            .build();

    String url = String.format("%s/storage/v1/object/%s/%s", supabaseUrl, bucket, fileName);

    Request request = new Request.Builder()
            .url(url)
            .header("Authorization", "Bearer " + supabaseKey)
            .post(requestBody)
            .build();

    try (Response response = httpClient.newCall(request).execute()) {
        if (!response.isSuccessful()) {
            String errorMessage = response.body().string();
            throw new IOException("Unexpected code " + response + ". Error: " + errorMessage);
        }
        return supabaseUrl + "/storage/v1/object/public/" + bucket + "/" + fileName;
    }
}
    
}