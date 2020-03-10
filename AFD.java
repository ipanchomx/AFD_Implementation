
import java.util.ArrayList;
import java.util.Scanner;

public class AFD {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Creación de la matriz de transiciones y un arreglo de strings para obtener el
		// alfabeto
		String[] alfabeto;

		// Lectura del lenguaje
		Scanner sC = new Scanner(System.in);
		String esc = "";
		do {
			System.out.println("Ingresa el alfabeto de tu lenguaje -separado por comas-:");
			String myString = sC.nextLine();
			alfabeto = myString.split(",");

			// Creación de un ArrayList con el fin de tener el lenguaje filtrado (en caso de
			// que una persona haya puesto caracteres repetidos
			// Esto también facilitará la búsqueda en la tabla de transiciones.
			ArrayList<String> alphabetComplete = new ArrayList<String>();
			for (int i = 0; i < alfabeto.length; i++) {
				if (!(alphabetComplete.contains(alfabeto[i]))) {
					alphabetComplete.add(alfabeto[i]);
				}
			}
			String simbols = "";
			for (int i = 0; i < alphabetComplete.size(); i++) {
				simbols += alphabetComplete.get(i);
			}
			
			alphabetComplete.clear();													//Limpiar la estructura.

			ArrayList<Character> alphabet = new ArrayList<Character>();
			for (int i = 0; i < simbols.length(); i++) {
				alphabet.add(simbols.charAt(i));
			}
			// Solicitud de número de estados para validación de entrada siguiente, donde se
			// solicita el llenado de tabla de transiciones.
			System.out.println("Ingresa el número de estados:");
			short numEstados = Short.decode(sC.nextLine());

			// Bloque para el llenado de la matriz de transiciones.
			ArrayList<int[]> matrizTransiciones = new ArrayList<int[]>();
			System.out.println("Matriz de transiciones");
			System.out.println(
					"Ingresa renglón por renglón las transiciones de los estados según el simbolo: (sólo números)");
			System.out.println(
					"Recuerda que deben haber la misma cantidad de estados separados por coma que la longitud del alfabeto.");
			System.out.println("Comenzando con el estado 0");
			int i = 0;
			String tempEst; 															// Variable para obtener la cadena donde vienen los datos de los renglones de
																						// tabla de transiciones.
			String[] tempToArrayInt = new String[alfabeto.length]; 						// Arreglo temporal para obtener cada dato en un
																						// casillero diferente.
			boolean flag = false; 														// Bandera para validación de datos.
			int temp = 0; 																// Temporal para obtener el dato de estado.
			while (i < numEstados) { 													// Loop para obtener los renglones según los estados que estableció el usuario.
				int[] tempInt = new int[alfabeto.length];								//Arreglo temporal de enteros para ingresar los estados por fila
				do {
					flag = false;											
					tempEst = sC.nextLine();
					tempToArrayInt = tempEst.split(",");
					for (int j = 0; j < tempToArrayInt.length; j++) {					//Loop para revisar el contenido de la cadena que contiene los estados. Con este puedo saber
						temp = Integer.decode(tempToArrayInt[j]);						// si los datos vienen correctos (saber que esten en el rango especificado según el número de estados).
						if (((temp > (numEstados - 1)) || (temp < 0))) {
							flag = true;
						}
					}
					if (flag) {
						System.out.println("No puedes introducir un estado con una denominación mayor al número de estados o menor a él.");
					}
				} while ((tempToArrayInt.length != alfabeto.length) || (flag == true));	

				for (int j = 0; j < tempToArrayInt.length; j++) {						//Loop para agregar al arreglo temporal todos los estados de la fila.
					tempInt[j] = Integer.decode(tempToArrayInt[j]);
				}
				matrizTransiciones.add(tempInt);										//Agregar el arreglo a la matriz
				i++;
			}

			short estFinal;																//Numero de estados finales
			do {
				System.out.println("Ingresa el número de estados finales: ");
				estFinal = Short.decode(sC.nextLine());
			} while (estFinal > numEstados || estFinal < 1);							//Condición para revisar que el estado final este dentro del rango.
			System.out.println("Ingresa los estados finales: (Separados por coma)");
			ArrayList<Integer> estadosFinales = new ArrayList<>();

			do {																		//do while para revisar que los estados finales estén en el formato 
				flag = false;															//requerido y que ninguno este fuera del rango
				estadosFinales.clear();
				myString = sC.nextLine();
				tempToArrayInt = myString.split(",");
				for (int j = 0; j < tempToArrayInt.length; j++) {
					temp = Integer.decode(tempToArrayInt[j]);
					if ( ((temp <= (numEstados - 1)) && (temp >= 0)) && ( !estadosFinales.contains(temp) )) {
						estadosFinales.add(temp);
					}
				}
			} while (estadosFinales.size() != estFinal);
//			Aquí se lee la cadena a revisar si es aceptado por el AFD.
			System.out.println("Ingresa la cadena a analizar en el AFD: ");
			myString = sC.nextLine();
			int estado = 0, columna = 0;
//			Movimiento por la tabla de transiciones siguiendo la lectura de la cadena a revisar.
			for (int j = 0; j < myString.length(); j++) {
				if(alphabet.contains(myString.charAt(j))) {								//Condición para revisar que el simbolo leído pertenezca al alfabeto.
					columna = alphabet.indexOf(myString.charAt(j));
					estado = matrizTransiciones.get(estado)[columna];
					
				}else {																	
					System.out.println("Simbolo no permitido: "+ myString.charAt(j) );
					break;
				}
				
			}
//			Bloque para revisar si la cadena fue admitida.			
			if (estadosFinales.contains(estado)) {										
				System.out.println("Cadena permitida");
			}else {
				System.out.println("Cadena no permitida");
			}
		System.out.println("Si deseas salir ingresa -1:");
		esc = sC.nextLine();
		} while (esc.compareTo("-1") != 0);												//Condición para saber si debo terminar el programa.
		sC.close();
		return;
	}

}
