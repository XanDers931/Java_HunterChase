# Suivi de la SAE 3.02

## Semaine du 6/11 au 12/11
- **Paul Milleville :** 
    - Ajout des parametres dans le menu.
    - Modification légères des classes Monster Hunter et Maps pour les relier au menu 
    - Enlever les Imports inutiles
- **Julien :**
    - Modification dans le GameModel ainsi que dans d'autre classes pour bien intégrer le GameModel dans le projet.
- **Alexandre :**
    - Résolution de bug dans le menus
    - initialisation du GameModelpour centralisé les informations de la partie dans une même classe

## Semaine du 13/11 au 19/11
- **Julien Bouin :** 
    - Finalisation du GameModel
    - Ajout des images dans le visuel du jeu.

## Semaine du 20/11 au 26/11
- **Paul Milleville :**
    - Ajout de la classe Stack dans Utils pour le Solveur 
    - Création de la classe Solveur qui vérifie si une map est faisable ou non, en utilisant une Stack. Seulement pour le Nord, Sud, Est et Ouest
    - Modification du Coordinate pour aider le solveur
    - Générateur aléatoire de map avec vérification
    - Solveur prenant en compte les diagonales
    - Générateur avec une taille variable
- **Julien Bouin :**
    - Restructurations des Stages 
    - Corrctif dans la classe Map
    - modification du menu 
    - Ajout des traces du monstre pour le monstre
    - Incorporation du Solveur dans le visuel du jeu
    - Ajout des traces du monstres pour le hunter
    - Ajout de nouvelles images pour le visuel du jeu
    - Refacto des Controllers

## Semaine du 27/11 au 3/12
- **Paul Milleville :** 
    - Ajout de la liaison entre le menu et la taille de la map pour le générateur aléatoire
- **Alexy Fouconnier :** 
    - Refonte complète des tests pour le chasseur et le monstre. 

## Semaine du 4/12 au 10/12
- **Paul Milleville :** 
    - Ajout du Solveur dans le Controllers du monstre
    - Suppression de la classe Stack dans Utils
    - Ajout de la classe Files dans Utils
    - Mise à jour du Solveur avec une méthode utilisant une File au lieu d'une Stack
- **Julien Bouin :**
    - Ajout des Bots pour le monstre et pour le chasseur
    - Liaison avec le menu
- **Alexandre Martel :**
    - Update du menu 
    - Selection dans le menu du jeu avec ou sans bots
- **Alexy Fouconnier :** 
    - Ajout de nouvelle règle depuis le menu
    - Ajout fichier .txt contenant ces même règles

## Semaine du 11/12 au 17/12
- **Paul Milleville :**
    - Suppréssion des println dans les fonctions Solveur
    - Rédaction du suivi.md
- **Julien Bouin :**
    - Ajout de l'affichage de la case touchée par le chasseur dans le visuel du monstre
- **Alexandre Martel :**
    - Résolution des bugs dans le menu
    - Ajout des pseudo pour le monstre et le chasseur (TextField) dans le menu
    - Ajout de la fonctionnalité qui permet de retourner au menu après la fin du jeu