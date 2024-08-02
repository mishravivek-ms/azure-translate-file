package org.translate.functions.services;

/**
 * Service class for retrieving Cognitive Services configuration.
 */
public class CognitiveServiecs {

    /**
     * Retrieves the Cognitive Services location from the environment variables.
     *
     * @return The location of the Cognitive Services.
     */
    public static String getCognitiveLocation() {
        return System.getenv("cognetivesearchlocation");
    }

    /**
     * Retrieves the Cognitive Services URL from the environment variables.
     *
     * @return The URL of the Cognitive Services API.
     */
    public static String getCognitiveURL() {
        return System.getenv("cognetiveServiceURL");
    }
}