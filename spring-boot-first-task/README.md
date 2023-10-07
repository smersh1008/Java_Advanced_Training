# Module 1: Spring Foundation

### There are 3 DataSource configurations:
1. DevDataSourceConfiguration - initiated only if profile="dev" and "main.data.source.enabled"="true".
2. QaDataSourceConfiguration - initiated only if profile="qa" and "main.data.source.enabled"="true".
3. SubDataSourceConfiguration - initiated only if DataSource bean is missing.

### Notes:
* Each DataSource is populated by 2 test Employee entities as soon as it is initialized.
* For each DataSource there are tests by profiles and "main.data.source.enabled" property 
under 'src/test/java/com/epam/trainning/springbootfirsttask/repository'.

### Actuator
* RandomNumberActuatorEndpointTest contains tests of Custom Actuator endpoint.
* *HealthActuatorEndpointTest classes contains tests of Health details info by profiles: if profile="local",
health details are displayed always, otherwise - when authorized.