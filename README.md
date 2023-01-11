# PROJ731 / GUERIOT Benjamin, NICOLAS Thomas

### Sommaire 

- [Description](#description)
- [Utilisation](#utilisation)

---
## Description

Ce projet a été réalisé dans le cadre de notre formation à Polytech Annecy-Chambéry 

Le sujet est porté sur les map-reduce. Dans celui-ci, nous allons analyser de long fichiers texte afin de connaître le nombre d'apparition de chaque mot.

Pour ce faire, nous allons diviser le texte le nombre de mappers demandé par l'utilisateur.


--

Ensuie, chaque mapper réparti ses résultats dans un nombre de dictionnaire correspondant au nombre de reducers. 

Cette séparation est réalisé par un shuffle qui consiste à répartir les données équitablement entre chaque reducer.

En effet, notre shuffle se base sur la fonction hashCode() de java. Cette fonction est optimisé donc nous ne perdrons pas beaucoup de performance et donc de temps.

On réalise ensuite un calcul mathématique, nous faisons un modulo correspondant au nombre de reducers sur la valeur obtenue ci-dessus pour connaitre le reducer assigné.

--


Les reducers vont récupérer les dictionnaires de chaque mappers qui leurs corresponds pour les analyser. Cela consiste à fusionner tous les différents dictionnaires pour former un seul et unique dictionnaire contenant les même mots par reducer. 

Enfin, nous regroupons tous les dictionnaires de chaque reducer pour former le dictionnaire final qui nous permettra d'afficher, à l'aide d'une fonction, les mots avec leur valeur dans un nouveau fichier texte.

En ce qui concerne les difficultés rencontrés, nous avons dû prendre en considération la concomitation des threads. En effet, il ne fallait pas que les threads modifient les même liste en même temps.

En outre, nous avons dû réfléchir un moment pour réaliser la séparation du grand texte en plusieurs parties.

--

Pour conclure, ce projet nous aura permis de découvrir un nouveau moyen de calcul sur un gros nombre de données. Nous pouvons facilement remarquer la différence entre un parcours brut et un parcours type map-reduce.
Exemple : 
  1 fichier de 500mo - > 27 secondes en brut
  1 fichier de 500mo - > 19 secondes avec le map-reduce 


---
## Utilisation

Lancer le fichier Manager.java.

Rentrer les informations dans la fenêtre apparaîssant (nombre mappers et reducers).

Glisser le fichier à analyser.

#### Installation

Cloner le projet dans un repertoire : 

`git  clone https://github.com/BenjaminGueriot/PROJ731'
