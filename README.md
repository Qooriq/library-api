# library-api
##    For launching you should have: 
**Docker:**
Launch each compose.yaml
##    Then use:
```bash
   ./gradlew clean 
```
## At the end:
**Launch each application in any order**

If incorrect secret of keycloak you should put the right one.
If some requests instead of bad status returns null you should change name
of mapper for example:
From MapperConfiguration -> MapperConfigurationn and press rename usages.