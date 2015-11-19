# google-api-discovery-tools
Automatically exported from code.google.com/p/google-api-discovery-tools

## Overview
This project helps you parse and understand Google APIs Discovery documents. Natively understands the JSON schema type system, schema references, method conventions, etc. Simple example:

```java
Discovery doc = Helper.getDiscovery("plus", "v1");
for (Method method : doc.getMethods().values()) {
  System.out.println("Method named: " + method.getId());
}
```
