package org.translate.functions.utility;

import com.azure.ai.translation.text.TextTranslationClient;
import com.azure.ai.translation.text.TextTranslationClientBuilder;
import com.azure.ai.translation.text.models.*;
import com.azure.core.credential.AzureKeyCredential;
import org.translate.functions.services.CognativeServiecs;
import org.translate.functions.services.KeyVaultServices;

import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for translating text using Azure Cognitive Services.
 */
public class Translate {

    /**
     * Translates the given text from the source language to the target language using Azure Cognitive Services.
     *
     * @param sourceString The text to be translated.
     * @param sourcelanguage The language code of the source text.
     * @param targetLanguage The language code of the target text.
     * @return The translated text.
     * @throws RuntimeException if required environment variables are not set.
     */
    public static String translateText(String sourceString, String sourcelanguage, String targetLanguage) {

        // Retrieve the API key and region from the environment variables
        String apikey = KeyVaultServices.getSecretforCognativeKey();
        String region = CognativeServiecs.getCognativeLocation();

        // Check if any required environment variables are missing
        if (apikey == null || region == null || CognativeServiecs.getCognativeURL() == null || sourcelanguage == null || targetLanguage == null) {
            System.out.println("Please set the environment variables cognetivesearchkey and cognetivesearchlocation and sourceLanguage and targetLanguage");
            throw new RuntimeException("Please set the environment variables cognetivesearchkey and cognetivesearchlocation and sourceLanguage and targetLanguage");
        }

        // Create an AzureKeyCredential using the API key
        AzureKeyCredential credential = new AzureKeyCredential(apikey);

        // Build the TextTranslationClient using the credential, region, and endpoint
        TextTranslationClient client = new TextTranslationClientBuilder()
                .credential(credential)
                .region(region)
                .endpoint(CognativeServiecs.getCognativeURL())
                .buildClient();

        // Prepare the list of target languages and the content to be translated
        List<String> targetLanguages = new ArrayList<>();
        targetLanguages.add(targetLanguage);
        List<InputTextItem> content = new ArrayList<>();
        content.add(new InputTextItem(sourceString));

        // Perform the translation
        List<TranslatedTextItem> translations = client.translate(targetLanguages, content, null, sourcelanguage, TextType.PLAIN, null, ProfanityAction.NO_ACTION, ProfanityMarker.ASTERISK, false, false, null, null, null, false);

        // Build the translated content string
        StringBuilder newContent = new StringBuilder();
        for (TranslatedTextItem translation : translations) {
            for (Translation textTranslation : translation.getTranslations()) {
                newContent.append(textTranslation.getText()).append("\n");
            }
        }

        // Return the translated text
        return newContent.toString();
    }
}