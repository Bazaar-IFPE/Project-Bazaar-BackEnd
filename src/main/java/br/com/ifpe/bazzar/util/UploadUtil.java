package br.com.ifpe.bazzar.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtil {
    public static boolean fazerUploadImagem(MultipartFile imagem){

        boolean sucessoUpload = false;

        if(!imagem.isEmpty()){
            String nomeArquivo = imagem.getOriginalFilename();

            try {
                // criando diretório para armazenar o arquivo;
                String pastaUploadImagem = "C:\\Users\\danil\\Desktop\\IFPE\\IFPE - BAZZAR\\Project-Bazaar-BackEnd\\src\\main\\resources\\static\\uploaded-imgs";
                File dir = new File(pastaUploadImagem);
                if(!dir.exists()){
                    dir.mkdirs();
                }

                // criando o arquivo no diretório;
                File serverFile = new File(dir.getAbsolutePath() + File.separator + nomeArquivo);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

                stream.write(imagem.getBytes());
                stream.close();

                System.out.println("Armazenado em: " + serverFile.getAbsolutePath());
                System.out.println("Fez o upload do arquivo: " + nomeArquivo + " com sucesso");

                sucessoUpload = true;

            } catch (Exception e) {
                System.out.println("Erro ao carregar o arquivo: " + nomeArquivo + " => " + e.getMessage());
            }
        } else {
            System.out.println("Falha ao carregar o arquivo, porque ele está vazio");
        }

        return sucessoUpload;
    }
}
