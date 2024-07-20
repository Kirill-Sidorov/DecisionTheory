# DecisionTheory
Programs for solving problems in decision theory.

The input data for the program is in file **"data.xlsx"**.

Available languages:
* russian.

### Features:
* pure strategies;
* mixed strategies;
* matrix Reduction;
* solving 2x2 matrix game;
* solving a 2xN/Nx2 matrix game;
* statistical games;
* Bellman-Zadeh approach to decision making;
* direct output;
* reverse output.

### Required software:
* JDK 8 (jdk1.8.0_201);
* apache maven 3.9.6.

### LIMITATION

The program is adapted only for screens with a display resolution of 1920x1080!

If you need to run the program on a computer with a display larger than FullHd, then you need to adjust the sizes of interface elements in the "UI" class.

### Images
![start page](https://github.com/Kirill-Sidorov/DecisionTheory/blob/readmedata/images/reduction_example.jpg)
|:--:| 
| *Image 1 - Matrix reduction example* |
![compare](https://github.com/Kirill-Sidorov/DecisionTheory/blob/readmedata/images/statistical_games_example.jpg)
| *Image 2 - Statistical games example* |
![storage manager](https://github.com/Kirill-Sidorov/DecisionTheory/blob/readmedata/images/matrix_game_solution_example.jpg)
| *Image 3 - Matrix game solution example* |

### Installation
1. Clone the repository.
2. Go to **"core"** directory and run the command (this command install "core" dependency in your local .m2 directory):
```console
mvn clean install
```
3. Go to **"ui"** directory and run the command (this command create "target" directory):
```console
mvn clean package
```
5. Copy **"data.xlsx"** (located at the root project) in to **"target"** directory.
6. Go to **"target"** directory and run **"ui-1.0-SNAPSHOT.jar"**.

All project libs will locate in **"target/libs"** directory .
