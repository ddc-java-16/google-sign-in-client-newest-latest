# General Google Sign In Client

---
## Summary

This is a simple OpenIDConnect/OAuth 2.0 client, intended for use with Google Sign In for the purpose of verifying access, and&mdash;optionally&mdash;obtaining a bearer token that can be used in server-side testing.

---

## How to use

### In this project

1. Refactor the base package name (`edu.cnm.deepdive.signin`) in this project, to match the intended package for your project.

2. Modify the `basePackageName` property value in `gradle.properties`, to match the base package name used in the previous step.

3. Execute the Gradle `signingReport` task to get the SHA1 hash from your debug keystore; this will be used in the steps below.

### In Google Cloud console

1. If you haven't done so already, create a project for your application.

2. Complete all necessary prerequisites (e.g., consent screen information, setting scope, adding test users) for creating OAuth2.0 credentials.

3. Create OAuth2.0 credentials for Android, specifying the base package name and SHA1 hash obtained in the previous steps. 

4. If the project will include a web service implemented as an OAuth2.0 resource server, create OAuth2.0 credentials for a web application. The client ID for these credentials will be used in the steps below. 

### In this project (again)

1. If web application credentials were created (step 4 in the group of steps above), copy the client ID from those credentials and paste them into the `serviceClientId` property value in the `app/local.properties` file.

2. Build and run the Android app.

---

## Credits, copyrights, and license information

Written by Nicholas Bennett.

&copy; 2023 CNM Ingenuity, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
