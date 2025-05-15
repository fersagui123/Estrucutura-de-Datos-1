class Node {
    int valor;
    Node izquierda;
    Node derecha;
    int altura;

    // Constructor
    public Node(int valor) {
        this.valor = valor;
        this.altura = 1; // Altura inicial de un nuevo nodo es 1
        this.izquierda = null;
        this.derecha = null;
    }
}

// Clase AVLTree
class AVLTree {
    Node raiz;

    public AVLTree() {
        this.raiz = null;
    }

    int getAltura(Node nodo) { // Obtener la altura de un nodo, si es null retorna 0.
        if (nodo == null) {
            return 0;
        }
        return nodo.altura;
    }

    int max(int a, int b) { // Obtener el máximo de dos enteros
        return (a > b) ? a : b;
    }

    int getFactorBalance(Node nodo) { // Obtener el factor de balance de un nodo
        if (nodo == null) {
            return 0;
        }
        return getAltura(nodo.izquierda) - getAltura(nodo.derecha);
    }

    Node rotarDerecha(Node y) { // Rotación a la derecha
        Node x = y.izquierda;
        Node T2 = x.derecha;

        x.derecha = y; // Realizar rotación
        y.izquierda = T2;

        y.altura = max(getAltura(y.izquierda), getAltura(y.derecha)) + 1; // Actualizar alturas
        x.altura = max(getAltura(x.izquierda), getAltura(x.derecha)) + 1;

        return x; // Retornar nueva raiz
    }

    Node rotarIzquierda(Node x) { // Rotación a la izquierda
        Node y = x.derecha;
        Node T2 = y.izquierda;

        y.izquierda = x;  // Realizar rotación
        x.derecha = T2;

        x.altura = max(getAltura(x.izquierda), getAltura(x.derecha)) + 1; // Actualiza las alturas
        y.altura = max(getAltura(y.izquierda), getAltura(y.derecha)) + 1;

        return y; // Retornar nueva raiz
    }

    public void insertar(int valor) { // Insertar un nodo en el árbol AVL
        raiz = insertar(raiz, valor);
        System.out.println("\nArbol después de insertar " + valor + ":");
        printTree();
    }

    private Node insertar(Node nodo, int valor) { // Método para insertar un valor en el árbol
        if (nodo == null) { //Realiza inserción BST normal
            return new Node(valor);
        }

        if (valor < nodo.valor) {
            nodo.izquierda = insertar(nodo.izquierda, valor);
        } else if (valor > nodo.valor) {
            nodo.derecha = insertar(nodo.derecha, valor);
        } else {
            // Los valores duplicados no son permitidos
            return nodo;
        }

        nodo.altura = 1 + max(getAltura(nodo.izquierda), getAltura(nodo.derecha)); //Actualizar altura del nodo actual

        int balance = getFactorBalance(nodo); //Obtiene el factor de balance para verificar si el nodo se desbalanceó

        //Si el nodo está desbalanceado, hay 4 casos posibles

        // Caso Izquierda-Izquierda (Left-Left)
        if (balance > 1 && valor < nodo.izquierda.valor) {
            return rotarDerecha(nodo);
        }

        // Caso Derecha-Derecha (Right-Right)
        if (balance < -1 && valor > nodo.derecha.valor) {
            return rotarIzquierda(nodo);
        }

        // Caso Izquierda-Derecha (LR)
        if (balance > 1 && valor > nodo.izquierda.valor) {
            nodo.izquierda = rotarIzquierda(nodo.izquierda);
            return rotarDerecha(nodo);
        }

        // Caso Derecha-Izquierda (RL)
        if (balance < -1 && valor < nodo.derecha.valor) {
            nodo.derecha = rotarDerecha(nodo.derecha);
            return rotarIzquierda(nodo);
        }

        // Si no hay desbalance, retornar el nodo sin cambios
        return nodo;
    }

    public void printTree() { // Método principal para imprimir el árbol
        if (raiz == null) {
            System.out.println("Árbol vacío");
            return;
        }
        printTree(raiz, 0);
    }

    private void printTree(Node nodo, int nivel) { // Método recursivo para imprimir el árbol jerárquicamente
        if (nodo == null) {
            return;
        }

        // Primero imprimimos el subárbol derecho para que el árbol crezca hacia la izquierda en la consola
        printTree(nodo.derecha, nivel + 1);

        // Imprimimos el nodo actual con la indentación adecuada
        for (int i = 0; i < nivel * 4; i++) {
            System.out.print(" ");
        }
        System.out.println(nodo.valor);

        // Finalmente imprimimos el subárbol izquierdo
        printTree(nodo.izquierda, nivel + 1);
    }
}

public class Main {
    public static void main(String[] args) {
        AVLTree arbol = new AVLTree();
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        
        System.out.println("=== IMPLEMENTACION DE ARBOL AVL ===");
        System.out.println("Ingrese números enteros de uno en uno.");
        System.out.println("Para finalizar, ingrese 'exit' o -1.");
        
        while (true) {
            System.out.print("\nIngrese un número: ");
            String entrada = scanner.nextLine();
            
            if (entrada.equalsIgnoreCase("exit")) {
                break;
            }
            
            try {
                int valor = Integer.parseInt(entrada);
                if (valor == -1) {
                    break;
                }
                arbol.insertar(valor);
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido, 'exit' o -1 para salir.");
            }
        }
        
        System.out.println("\nPrograma finalizado. Arbol AVL final:");
        arbol.printTree();
        
        scanner.close();
    }
}