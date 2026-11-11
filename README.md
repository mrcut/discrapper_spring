# Discrapper — API Java / Spring Boot

API d’un projet de stage permettant de consulter des messages Discord classés par catégorie et d’administrer des utilisateurs et des références de serveurs Discord.

**Stack :** Java 8 (cible du `pom.xml`), Spring Boot 2.6.6, Spring Web, Spring Data JPA / Hibernate, MySQL, Spring Security, Maven Wrapper.

**Interface associée :** [Discrapper React](https://github.com/mrcut/discrapper_api).

## Fonctionnalités présentes dans le code

- Connexion par email / mot de passe et génération de jetons UUID stockés en base, avec expiration de 90 minutes.
- Contrôles de rôles `admin`, `employe` et `user` dans les contrôleurs.
- Consultation des messages, détail et filtrage par catégorie ; suppression réservée à l’administrateur.
- Création, consultation, modification et suppression des références Discord.
- Administration des utilisateurs et modification du profil connecté.
- Validations métier dans les services, notamment des champs requis et des doublons Discord.

Ces jetons ne sont pas des JWT. La présence de Spring Security ne constitue pas une validation de sécurité de l’application.

## Organisation et points de lecture

| Dossier | Responsabilité |
| --- | --- |
| `src/main/java/root/controleurs` | Routes REST et contrôles d’accès |
| `src/main/java/root/service` | Logique métier et gestion des jetons |
| `src/main/java/root/repository` | Accès aux données via Spring Data JPA |
| `src/main/java/root/entites` | Modèle de données |
| `src/main/java/root/http` | Objets d’entrée et de réponse |

Pour examiner l’architecture : [DiscordCTRL](src/main/java/root/controleurs/DiscordCTRL.java), [DiscordServiceImpl](src/main/java/root/service/DiscordServiceImpl.java) et [AccessSecurityServiceImpl](src/main/java/root/service/AccessSecurityServiceImpl.java).

## Démarrage local

### Prérequis et limites

- Un JDK compatible avec ce projet historique ciblant Java 8 et un serveur MySQL local.
- Une base `discrapper_api` avec son schéma et des données de démonstration, dont un utilisateur initial.
- **Le dépôt ne fournit pas de script SQL ni de migration de base.** La configuration utilise `spring.jpa.hibernate.ddl-auto=none` : lancer le serveur ne crée pas les tables. La démonstration complète nécessite donc de préparer la base et un compte avec un mot de passe compatible avec l’encodeur du projet.

### Commandes

```sh
git clone https://github.com/mrcut/discrapper_spring.git
cd discrapper_spring
```

Configurer la connexion dans `src/main/resources/application.properties` pour une base locale dédiée, ou la surcharger avec les variables Spring `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME` et `SPRING_DATASOURCE_PASSWORD`. Ne pas publier de véritables identifiants.

Sous Windows :

```powershell
.\mvnw.cmd spring-boot:run
```

Sous macOS / Linux :

```sh
sh mvnw spring-boot:run
```

L’API est configurée sur `http://localhost:3030`. L’interface React attend `http://localhost:3030/api/`.

Exemples de routes : `POST /api/login`, `GET /api/messages`, `GET /api/category/{id}`, `GET /api/discords`. Les routes protégées attendent `Authorization: Bearer <jeton>` ; les droits varient selon le rôle.

## Limites de la démonstration

La route `GET /api/user/script` appelle un script Python via un chemin Windows absolu dans `UtilisateurServiceImpl`. Ce script n’est pas fourni dans ce dépôt : **la collecte Discord ne peut pas être reproduite avec ce seul dépôt**. La présentation porte sur l’API et les données déjà disponibles.

## Aperçu à compléter

> Capture réelle à ajouter après préparation de la base : liste des messages et filtrage par catégorie dans l’interface React. Aucune capture ni donnée de démonstration n’est inventée ici.

## Vérifications

Le dépôt contient un test de chargement du contexte Spring. Commande : `.\mvnw.cmd test` sous Windows, ou `sh mvnw test` sous macOS / Linux. La connexion à la base peut être nécessaire. Ces commandes sont documentées à partir des fichiers du dépôt ; leur réussite n’est pas attestée par cette mise à jour documentaire.
