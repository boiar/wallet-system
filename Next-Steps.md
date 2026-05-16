```
1. create user_settings (currency, def_lang, def_theame)
2. create tbl langs
3. create tbl currencies
5. wallet () 
```


#  FIRST TIME (or after pom.xml changes) 

mvn package -DskipTests                           # build jar locally
docker compose -f docker-compose.dev.yml up -d    # start everything


#  AFTER ANY CODE CHANGE 

mvn package -DskipTests                           # ~5-10 sec local build
docker compose -f docker-compose.dev.yml restart app   # ~2 sec, no image rebuild


#  PROD DEPLOY (unchanged) 

docker compose up --build                         # uses original Dockerfile


--------------------------------------------------

