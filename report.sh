# Runtime Manager Report
curl --location --request GET "http://clpr-anypoint-platform-management.uk-e1.cloudhub.io/anypoint/reports/runtime" \
--header "X-AUTH-STRATEGY: connectedApp" \
--header "client_id: ${CLIPPER_CLIENT_ID}" \
--header "client_secret: ${CLIPPER_CLIENT_SECRET}" \
--header "X-TRANSACTION-ID: "$(date +%Y-%m-%d_%H-%M-%S)  \
--header "X-CONNECTED-APP-CLIENT_ID: ${X_CONNECTED_APP_CLIENT_ID}" \
--header "X-CONNECTED-APP-CLIENT_SECRET: ${X_CONNECTED_APP_CLIENT_SECRET}" \
-o "/Users/mhaque/Desktop/ClipperRuntimeReports_$(date +%Y-%m-%d_%H-%M-%S).xlsx"


# API Manager report(API Usage)
curl --location --request GET "http://clpr-anypoint-platform-management.uk-e1.cloudhub.io/anypoint/reports/apim" \
--header "X-AUTH-STRATEGY: connectedApp" \
--header "client_id: ${CLIPPER_CLIENT_ID}" \
--header "client_secret: ${CLIPPER_CLIENT_SECRET}" \
--header "X-TRANSACTION-ID: "$(date +%Y-%m-%d_%H-%M-%S)  \
--header "X-CONNECTED-APP-CLIENT_ID: ${X_CONNECTED_APP_CLIENT_ID}" \
--header "X-CONNECTED-APP-CLIENT_SECRET: ${X_CONNECTED_APP_CLIENT_SECRET}" \
-o "/Users/mhaque/Desktop/ClipperAPIM_$(date +%Y-%m-%d_%H-%M-%S).xlsx"