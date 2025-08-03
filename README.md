# TicTacToe

Un jeu de Tic Tac Toe (morpion) avec plusieurs modes de jeu et une IA intégrée.

## Fonctionnalités

- Grille de taille variable (3x3 et 4x4)
- Mode solo avec IA (niveau facile et difficile)
- Mode deux joueurs (local)
- Détection automatique du gagnant avec mise en évidence des cases gagnantes
- Gestion des directions de victoire (horizontal, vertical, diagonales)
- Interface simple et réactive basée sur Jetpack Compose


## Utilisation
- Application Android développée en Kotlin avec Jetpack Compose.
- Lancez l’application sur un émulateur ou un appareil Android via Android Studio.
- Choisissez le mode de jeu (solo ou deux joueurs).
- Sélectionnez la taille de la grille.
- Commencez à jouer !

## Structure du projet

- `logic/GameLogic.kt` : Contient la logique du jeu (vérification des gagnants, IA, gestion du plateau).
- `viewmodel/GameViewModel.kt` : Gère l’état de l’application et les interactions utilisateur.
- `model/` : Contient les classes de modèle comme `Player`, `PlayerSymbol`, `WinDirection`, etc.
- `ui/` : Contient les composants Jetpack Compose pour l’interface utilisateur.

## Génération de l'APK via GitHub Actions

Chaque fois que vous créez un **tag** qui commence par `v` (par exemple `v1.0.0`), une build automatique se lance via GitHub Actions pour générer l'APK de la version correspondante.
Le fichier APK est ensuite automatiquement attaché à la release créée sur GitHub, prêt à être téléchargé.


## Installation

Clonez ce dépôt sur votre machine :

```bash
git clone https://github.com/ssisirix361/tictactoe.git

## 📱 Télécharger l'application

Vous pouvez télécharger la dernière version de l'application ici :

[⬇️ Télécharger l’APK](https://github.com/ssisirix361/tictactoe/releases/download/v1.0.0/app-release.apk)


