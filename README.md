---
page_type: sample
languages:
- java
products:
- Azure Function
description: "Create the Azure function that translate the file from one language to another language."
---

# Translate file through Azure function

## Overview

This project demonstrates how to create an Azure Function that translates the content of a file from one language to another using Azure Cognitive Services. The translated content is then stored in a different blob storage container. The project includes utility classes for handling blob operations, performing text translation, and retrieving secrets from Azure Key Vault. It also provides a detailed configuration setup and instructions for running the sample, ensuring that all necessary Azure resources and environment variables are correctly configured.
## Prerequisites

- Java Development Kit (JDK) 8 or later
- Maven
- Azure Subscription
- Azure Storage Account
- Azure Cognitive Services Translator resource
- Azure Key Vault

## MAVEN Dependencies

The following are the major dependencies required for this project, as specified in the `pom.xml` file:

### Azure Functions Java Library
This dependency is required to create and run Azure Functions using Java.
```xml
<dependency>
    <groupId>com.microsoft.azure.functions</groupId>
    <artifactId>azure-functions-java-library</artifactId>
    <version>${azure.functions.java.library.version}</version>
</dependency>
<dependency>
    <groupId>com.azure</groupId>
    <artifactId>azure-core</artifactId>
    <version>1.37.0</version>
</dependency>
<dependency>
    <groupId>com.azure</groupId>
    <artifactId>azure-ai-translation-text</artifactId>
    <version>1.0.0-beta.1</version>
</dependency>
<dependency>
    <groupId>com.azure</groupId>
    <artifactId>azure-storage-blob</artifactId>
    <version>12.14.2</version>
</dependency>
<dependency>
    <groupId>com.azure</groupId>
    <artifactId>azure-security-keyvault-secrets</artifactId>
    <version>4.3.0</version>
</dependency>
<dependency>
    <groupId>com.azure</groupId>
    <artifactId>azure-identity</artifactId>
    <version>1.4.0</version>
</dependency>
```

## Project Structure

- `local.settings.json`: Contains local settings for the Azure Function, including Key Vault references for secrets.
- `src/main/java/org/translate/functions/utility/BlobFunctionUtil.java`: Utility class for handling blob operations and invoking the translation function.
- `src/main/java/org/translate/functions/utility/Translate.java`: Utility class for performing text translation using Azure Cognitive Services.
- `src/main/java/org/translate/functions/services/KeyVaultServices.java`: Service class for retrieving secrets from Azure Key Vault.
- `src/main/java/org/translate/functions/services/CognativeServiecs.java`: Service class for retrieving Cognitive Services configuration.

## Configuration

### local.settings.json

This file contains the configuration settings for the Azure Function, including references to secrets stored in Azure Key Vault.

```json
{
  "IsEncrypted": false,
  "Values": {
    "AzureWebJobsStorage": "@Microsoft.KeyVault(SecretUri=https://<your-keyvault-name>.vault.azure.net/secrets/AzureWebJobsStorage/<secret-id>)",
    "FUNCTIONS_WORKER_RUNTIME": "java",
    "KEYVAULTCONGNETIVEKEY": "@Microsoft.KeyVault(SecretUri=https://<your-keyvault-name>.vault.azure.net/secrets/COGNETIVEKEY/<secret-id>)",
    "KeyVaultUri": "https://<your-keyvault-name>.vault.azure.net/",
    "cognetiveServiceURL": "https://api.cognitive.microsofttranslator.com",
    "cognetivesearchlocation": "eastus",
    "sourceLanguage": "en",
    "targetLanguage": "es"
  }
}

```
### BlobFunctionUtil.java
This class handles the blob operations, including reading the input blob, invoking the translation function, and storing the translated content in the output blob.

### Translate.java
This class contains the logic for translating the text content using Azure Cognitive Services.

### KeyVaultServices.java
This class retrieves the secrets from Azure Key Vault using the Key Vault SDK.

### CognativeServiecs.java
This class retrieves the configuration settings for Azure Cognitive Services.

## Running the Sample
- Clone the repository.
- Configure the local.settings.json file with your Azure Key Vault and Cognitive Services details.
- Build the project using Maven:
- mvn clean package
- Deploy the Azure Function to your Azure subscription.
