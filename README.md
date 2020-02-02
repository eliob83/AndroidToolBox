# TD : Développement application Android

## Partie I : Création et configuration du projet

a) Création du projet. Package name: fr.isen.bilisari.androidtoolbox. Langage Kotlin.

b) API minimum 95 % d'utilisateurs: Android KitKat.

d) Fichiers XML de descriptions, Classes activités .kt.


## Partie II : Manipulation de l'interface utilisateur

d) Toast.makeText(...).show()

e) Condition sur username.text (idem password).

f) Utilisation d'Intent.


## Partie III : Cycle de vie activity / fragment

a) Les fragments permettent la réutilisation de "morceaux de vue" sur plusieurs activités, et leur remplacement aisément. Le cycle de vie d'une activité est la hiérarchie des différentes étapes de vie de celle-ci, de la création à la destruction, en passant par la mise en pause, le réveil, etc. ; afin de suivre la vie de l'application.

b) LifecycleActivity

c) Utilisation de Log.d et override de onCreate, onResume, onPause, onDestroy...

d) Lorsque l'orientation change, l'activité est recréée. Les dimensions de l'application changent (inversement des axes), affichage différent possible.

f) Facultatif : Le premier fragment est détruit lorsque le second le remplace.

g) Facultatif : Les deux fragments coexistent.


## Partie IV : Sauvegarder les informations au sein d'une application

a) Sauvegarde en fichier texte, dans un dossier dédié à l'application, ou en BDD.

h) Génération de clef privée/publique, stockage dans SharedPreferences dans la clef privée afin de déchiffrer le message codé par la clef publique et stocké en JSON.


## Partie V : Accès fonctionnalités du smartphone et permissions

d) Avec les API >= 23, il est nécessaire demander la permission, alors qu'en dessous une simple déclaration à l'installation de l'application nous permet d'utiliser les fonctionnalités déclarées.

e) Affichage du nom et du numéro.


## Partie VI : Utilisation des WebServices et de librarie

e) Création d'une transformation RoundTransformation afin d'arrondir les images à afficher.