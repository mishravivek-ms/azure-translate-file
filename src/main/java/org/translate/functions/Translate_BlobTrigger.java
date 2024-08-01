package org.translate.functions;

import com.microsoft.azure.functions.annotation.*;
import com.microsoft.azure.functions.*;
import org.translate.functions.utility.BlobFunctionUtil;

/**
 * Azure Functions with Azure Blob trigger.
 */
public class Translate_BlobTrigger {
    /**
     * This function will be invoked when a new or updated blob is detected at the specified path.
     * The blob contents are provided as input to this function.
     *
     * @param inputBlob The content of the blob that triggered the function.
     * @param name The name of the blob that triggered the function.
     * @param outputBlobES The output binding to store the translated content.
     * @param context The execution context for logging and other context information.
     */
    @FunctionName("TranslateFile")
    public void run(
        @BlobTrigger(name = "inputBlob", dataType = "binary", path = "incoming/{name}", connection = "AzureWebJobsStorage")
        byte[] inputBlob,
        @BindingName("name") String name,
        @BlobOutput(name = "outputBlobES", dataType = "binary", path = "outgoingtranslate/{name}", connection = "AzureWebJobsStorage")
        OutputBinding<byte[]> outputBlobES,
        final ExecutionContext context
    ) {
        context.getLogger().info("Java Blob trigger function processed a blob." + name + " size->" + inputBlob.length);

        // Translate and move the blob content using BlobFunctionUtil
        BlobFunctionUtil.Translate_Move(inputBlob, name, outputBlobES, context);
    }
}