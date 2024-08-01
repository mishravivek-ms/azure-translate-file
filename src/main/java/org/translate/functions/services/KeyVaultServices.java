package org.translate.functions.services;

/**
 * Service class for retrieving secrets from Azure Key Vault.
 */
public class KeyVaultServices {

    /**
     * Retrieves the Key Vault URL from the environment variables.
     *
     * @return The URL of the Azure Key Vault.
     */
    public static String getKeyVaultURL() {
        return System.getenv("KeyVaultUri");
    }

    /**
     * Retrieves the secret for the Cognitive Services key from the environment variables.
     *
     * @return The secret key for Cognitive Services.
     */
    public static String getSecretforCognativeKey() {
        return System.getenv("KEYVAULTCONGNETIVEKEY");
    }

    /**
     * Retrieves the secret for the Storage key from the environment variables.
     *
     * @return The secret key for Storage.
     */
    public static String getSecretforStorageKey() {
        return System.getenv("KEYVAULTSTORAGEKEY");
    }

}