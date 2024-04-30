# trade-journal
Simple Trade-Journal application:
- The frontend has been implemented in React
- The backend has been implemented in Java / Spring Boot

# Start the application
Build and start frontend and backend:
> docker compose up -d

Open the API definition:
> http://localhost:8080/swagger-ui.html

Open the web application:
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
- No tests (just on backend side to show all kinds of tests)
- Authorisation / Authentication
- List is not sortable / pageable
- No different currencies

## Frontend
- View is not responsive
    - Use grid instead of table
- Ugly error handling -> alert
- Browser console:
    - Invalid DOM property `novalidate`. Did you mean `noValidate`?
    - Warning: A future version of React will block javascript: URLs as a security precaution. Use event handlers inste
ad if you can. If you need to generate unsafe HTML try using dangerouslySetInnerHTML instead. React was passed "javascript:void(0);".
- Validation is still not perfect (ok - green / error - red fields not working with bootstrap)
- Add buy / sell trade
    - Enter key starts the buy process
    - Enter key shows validation error
- TypeScript
- Layout
    - Euro (X,YZ) - Comma vs Point - input / output
    - Euro (A.BCD,EF) - Thousands 
    - Euro (A,BCDEF) - Too many digits after comma -> https://cchanxzy.github.io/react-currency-input-field/
- Validation
    - Validation failed but call is executed
- Use Bootstrap components in React
- onKeyPress is deprecated (customerId)
- handle functions instead of two commands (e.g. onChange)

## Backend
- Swagger secure via Basic Auth
- ProblemDetails
- Persistence
    - Postgres
    - Liquibase
    - TestContainers
- ControllerAdvice to create own error responses object
- N:M relation between BuyTrade and SellTrade would be nicer but too complex for a demo