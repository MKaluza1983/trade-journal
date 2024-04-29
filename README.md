# trade-journal
Simple Trade-Journal application:
- Frontend has been implemented in React
- Backend has been implemented in  Java / Spring Boot

# Start the application
Build and start frontend and backend:
> docker compose up -d

API Definition:
> http://localhost:8080/swagger-ui.html

WEB Application:
> http://localhost:3000/

# Stop the application
> docker compose down

# Design decisions:
I didn't want to have a "simple" trades list, but rather a technical separation
between buying and selling a stock including their relation.
Known solutions from other brokers were not informative enough for me, so I tried a different solution

# Known / open issues:
## General
- No delete trade function
- No tests
- Authorisation / Authentification
- List is not sortable / pageable
- No different currencies

## Frontend
- View is not responsive
- Ugly error handling -> alert
- Validation is still not perfect (ok - green / error - red fields not working with bootstrap)
- Add buy / sell trade -> Enter key starts the buy process
- TypeScript
- Layout
-- Komma vs Point - input / output
-- Euro (X,YZ)
-- Euro (A.BCD,EF)
- Validation
-- Validation failed but call is executed
- Use Bootstrap components in React
- onKeyPress is deprecated (customerId)
- handle functions instead of two commands (e.g. onChange)
- Unify the buy and sell functions

## Backend
- Swagger secure via Basic Auth
- Persistence
-- Postgres
-- Liquibase
- ControllerAdvice to create own error responses object
- N:M relation between BuyTrade and SellTrade would be nicer but too complex for a demo