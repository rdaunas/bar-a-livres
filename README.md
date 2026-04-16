# Install et lancement du front en local
## Pré-requis: node v24+ 
```
cd ./front/bar-a-livre-front
npm i
ng serve

```



# Install et lancement du back
## Pré-requis: jdk 21
```
cd ./back
gradle bootRun

```

#Setup base de données
## Pré-requis SQL SERVER
Executer dans la base les scripts Init_DB.sql et 1-migration.sql présent dans ./back/src/main/ressources/Script_bdd
