package org.translate.functions.utility;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;

import java.nio.charset.StandardCharsets;

/**
 * Utility class for handling blob operations such as translation and deletion.
 */
public class BlobFunctionUtil {

    /**
     * Translates the content of the input blob and moves the translated content to the output blob.
     *
     * @param inputBlob The content of the input blob as a byte array.
     * @param name The name of the input blob.
     * @param outputBlobES The output binding to store the translated content.
     * @param context The execution context for logging and other context information.
     */
    public static void Translate_Move(byte[] inputBlob, String name, OutputBinding<byte[]> outputBlobES, ExecutionContext context) {
        try {
            // Convert the inputBlob byte array to a string
            String sourceString = new String(inputBlob, StandardCharsets.UTF_8);
            String destinationStringES = Translate.translateText(sourceString, System.getenv("sourceLanguage"), System.getenv("targetLanguage"));
            byte[] data = destinationStringES.getBytes(StandardCharsets.UTF_8);
            outputBlobES.setValue(data);
            deleteBlob(name, context);
        } catch (Exception e) {
            System.out.println("Exception in reading file-->" + e.getMessage());
        }
    }

    /**
     * Deletes the specified blob from the 'incoming' container.
     *
     * @param blobName The name of the blob to be deleted.
     * @param context The execution context for logging and other context information.
     */
    private static void deleteBlob(String blobName, ExecutionContext context) {
        String connectionString = System.getenv("AzureWebJobsStorage");
        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
        BlobClient blobClient = blobServiceClient.getBlobContainerClient("incoming").getBlobClient(blobName);
        blobClient.delete();
        context.getLogger().info("Original blob deleted successfully.");
    }
}