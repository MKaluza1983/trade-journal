# trade-journal
Simple Trade-Journal application:
- Frontend has been implemented in React
- Backend has been implemented in  Java / Spring Boot

# Start
Build and start frontend and backend:
> docker compose up -d

API Definition:
> http://localhost:8080/swagger-ui.html

WEB Application:
> http://localhost:3000/

# Design decisions:
I didn't want to have a "simple" trades list, but rather a technical separation
between buying and selling a stock including their relation.
Known solutions from other brokers were not informative enough for me, so I tried a different solution

# TODO:
- Search filter for open / closed trades
- Buy / Sell Order Toggle
- Docker Compose -> backend (MacOS error?!)
- Adjust Formular Styling on top 

# Open issues:
- Not responsive
- No tests
- TypeScript
- Authorisation / Authentification
-- Swagger secure via Basic Auth
- Layout
-- Komma vs Point
-- Euro (X.YZ)
- Validation 
- Einheitliche Formatierung (Punkt und Komma)
- Persistierung -> Postgres
- Persistierung -> Liquibase
