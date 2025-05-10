# Le Secret du Chat - A3P Projet Zuul

## Présentation

Ce projet est un jeu d’aventure textuel développé en Java, basé sur le projet pédagogique **Zuul**. Il a été construit étape par étape à partir des [exercices proposés ici](https://perso.esiee.fr/~bureaud/Unites/Zuul/listeExercices.htm), avec une interface graphique et des mécanismes avancés comme l’inventaire, la gestion du poids, les objets magiques, les interactions avec des NPC, etc.

- **Auteur** : LIN Xingtong (Groupe E3T)  
- **Année** : 2024/2025  
- **Thème** : Infiltration dans un laboratoire secret pour retrouver et sauver sa sœur transformée en chat  
- **Langage** : Java  
- **Interface** : Swing (GUI)

---

## Scénario

Vous incarnez **Alex**, un ancien soldat d’élite, parti à la recherche de sa sœur **Alice**, disparue depuis plusieurs années. Après avoir trouvé une bague sur un chat étrange, Alex infiltre un laboratoire secret où des expériences transforment les humains en animaux intelligents. Vous disposez de **100 pas maximum** pour explorer les lieux, collecter des indices, interagir avec les personnages et identifier votre sœur pour la sauver à temps.

---

## Lieux du jeu

- Entrée  
- Salle de rangement  
- Salle de désinfection  
- Salle de réunion  
- Salle des prisonniers  
- Salle des animaux  
- Salle des archives  
- Salle d’expérimentation  
- Jardin  
- Salle de téléportation (aléatoire)

---

## Fonctionnalités

* Déplacement avec `'go'`, `'back'` (pile `Stack<Room>`)
* Observation via `'look'`, `'look item'`, `'items'`
* Gestion d’objets : `'take'`, `'drop'`, `'eat'` (avec **poids** et limite)
* Dialogue avec des NPC : `'talk npc'`, `'talk num'` 
* Téléportation avec le **Beamer** : `'charge'`, `'fire'`
* Salles et objets **verrouillés** (clé ou condition de visibilité)
* Salle de téléportation **aléatoire** : `TransporterRoom` 
* Salles à **sens unique** : `'back'` parfois bloqué
* Commandes de test via fichiers `.txt` : `'test court'`, `'test long'`, `'test ideal'`
* Interface graphique : **Swing** (`JFrame`, `JTextField`, `JPanel`, etc.)
* Commandes aussi accessibles via des **boutons GUI**

---

## Commandes disponibles

| Commande              | Description                                           |
|-----------------------|-------------------------------------------------------|
| `go direction`        | Se déplacer (ex : `go north`)                         |
| `look` / `look item`  | Regarder la salle ou un objet                         |
| `take item` / `drop item` | Ramasser ou déposer un objet                      |
| `items`               | Afficher l’inventaire                                 |
| `eat item`            | Manger un objet                                       |
| `charge` / `fire`     | Utiliser le Beamer                                    |
| `back`                | Revenir à la salle précédente                         |
| `talk npc / talk num` | Parler à un personnage ou choisir une réponse         |
| `quit`                | Quitter le jeu                                        |
| `help`                | Afficher les commandes disponibles                    |
| `test nomFichier`     | Exécuter un fichier de commandes `.txt`               |

---

## Lancer le projet

1. **Téléchargez ou clonez** ce dépôt.
2. **Ouvrez le projet avec BlueJ** :
   - Cliquez sur **Compiler** pour compiler tous les fichiers.
3. **Lancez le jeu** :
   - Clic droit sur la classe `Game`
   - Sélectionnez `new Game()`
   - Cliquez sur "OK"
4. **(Optionnel)** : utilisez les fichiers de test :
   - `test court` (jusqu’à la salle d’expérimentation)
   - `test long` / `test ideal` (terminent automatiquement le jeu)

> Remarque : certains tests n’affichent pas toujours les images (ex : `test long`, `test ideal`) et désactivent les interactions manuelles une fois terminés.

---

## Arborescence du dépôt

```

.
├── Images/                  # Images utilisées dans le jeu
├── doc/, userdoc/, progdoc/ # Documentation générée (Javadoc)
├── \*.java                  # Fichiers sources Java
├── \*.txt                   # Fichiers de test 
├── rapport.pdf              # Rapport complet du projet
└── README.md                # Ce fichier

````

---

## Rapport complet

Voir [rapport.pdf](./rapport.pdf) pour :
- Le plan (map), le scénario complet, les objets et personnages
- Les réponses aux exercices du TP Zuul
- Détails techniques sur les fonctionnalités

---

## Remarques techniques

* **Javadoc** générée (`userdoc`, `progdoc`) avec :
```bash
javadoc -d userdoc -author -version *.java
javadoc -d progdoc -author -version -private -linksource *.java
````

---

## Inspirations

* Projet basé sur **Zuul** (projet pédagogique BlueJ)
* Refonte complète et personnalisation du scénario
* Développé en suivant les [exercices officiels Zuul](https://perso.esiee.fr/~bureaud/Unites/Zuul/listeExercices.htm)

