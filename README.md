# TicTacToe

Un jeu de Tic Tac Toe (morpion) personnalisable avec plusieurs modes de jeu et une IA intégrée.

## Fonctionnalités

- Grille de taille variable (3x3 et plus)
- Mode solo avec IA (niveau facile et difficile)
- Mode deux joueurs (local)
- Détection automatique du gagnant avec mise en évidence des cases gagnantes
- Gestion des directions de victoire (horizontal, vertical, diagonales)
- Interface simple et réactive basée sur Jetpack Compose


## Utilisation

- Lancez l’application sur un émulateur ou un appareil Android.
- Choisissez le mode de jeu (solo ou deux joueurs).
- Sélectionnez la taille de la grille.
- Commencez à jouer !

## Structure du projet

- `logic/GameLogic.kt` : Contient la logique du jeu (vérification des gagnants, IA, gestion du plateau).
- `viewmodel/GameViewModel.kt` : Gère l’état de l’application et les interactions utilisateur.
- `model/` : Contient les classes de modèle comme `Player`, `PlayerSymbol`, `WinDirection`, etc.
- `ui/` : Contient les composants Jetpack Compose pour l’interface utilisateur.


## Installation

Clonez ce dépôt sur votre machine :

```bash
git clone https://github.com/ssisirix361/tictactoe.git
