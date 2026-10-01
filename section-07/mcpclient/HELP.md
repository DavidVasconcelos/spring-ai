# Local Development Resources Guide

This document provides the necessary instructions and credentials to access the local databases and tools for this application.

### Silencing Java 25 Memory Warnings in IntelliJ IDEA

If you are running the application using Java 25+, you may see console warnings regarding `sun.misc.Unsafe` or restricted native access. This is caused by high-performance libraries (like Netty and gRPC) transitioning to the new Java memory APIs.

When starting the application via the terminal using `./gradlew bootRun`, these warnings are handled automatically by the `build.gradle` configuration. However, **IntelliJ IDEA bypasses Gradle** when you click the "Play" button, meaning you must add the JVM arguments directly to the IDE.

**To fix this in IntelliJ:**

1. In the top toolbar next to the green Play/Run button, click your application name and select **Edit Configurations...**
2. In the configuration window, look for the **VM options** field.
   *(Note: If you do not see it, click the blue **Modify options** text or the ⚙️ gear icon, and check **Add VM options**).*
3. Paste the following arguments into the **VM options** box:

   ```text
   --sun-misc-unsafe-memory-access=allow --enable-native-access=ALL-UNNAMED