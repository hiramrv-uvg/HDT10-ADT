/**
*Main 
*@version: 3.0
*@author: Andrea Maybell Pena 15127 // Steven Rubio, 15044 
*@since 2016-11-08
*/

import java.io.File;
import java.util.Scanner;
import java.util.Vector;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Main {

	public static void main(String[] args) {
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		//Se abre el archivo con la base de datos de Neo4j
		// ***ojo, modificar este valor segun donde se almacene la base de neo4j **** ////
		File file= new File("C:/Users/HRV/Desktop/4to ciclo/Algoritmos y Estructuras de Datos/HDT10/HDT10-ADT-master/BaseHoja10");
		GraphDatabaseService db= dbFactory.newEmbeddedDatabase(file);
		
		//Se comenienza la coneccion con la base de datos 
		//Cualquier operacion realizada en estre brach afectara 
		//Directamente la base
		try (Transaction tx = db.beginTx()) {
			
			Vector<Integer> vectorId= new Vector<Integer>();
			Vector<Long> vectorCant= new Vector<Long>();
			for(int i=0; i<196; i++){
				try{
					Relationship rel= db.getRelationshipById(i);
					long cant= (long) rel.getProperty("cant");
					vectorId.addElement(i);
					vectorCant.addElement(cant);
					//System.out.println(cant);
				}
				catch (Exception e)
				{
					//System.out.println(i+" no es relación");
				}}
			
			//Se crea el scanner para leer la opccion que seleccion el usuario
		Scanner scan= new Scanner(System.in);
		int opcion=0;
		int val=0;
		while (val<=3)
		{
			//Menu con la funcionalidad del programa
			System.out.println(" ");
			System.out.println("Seleccione la accion que desea realizar:");
			System.out.println("1. Ver grafo completo");
			System.out.println("2. Ver relaciones con más de 6 correos");
			System.out.println("3. Eliminar relaciones de auto-envio de correos");
			System.out.println("4. Ver importancia de personas en la red de comunicación con métrica Page Rang");
			System.out.println("5. Ver personas más y menos comunicadas");
			System.out.println("6. Ver cantidad de correos entre personas");
			System.out.println("7. Salir");
			
			try{
				opcion= scan.nextInt();
				switch(opcion){
				
				case 1:
					//Se crea el gráfo completo 
				GraphExplore g1= new GraphExplore();
				System.out.println(" ");
				//Se imprime un mensaje al usuario
				System.out.println("Para ver el grafo vea la ventana que acaba de abirse. \n (No la cierre, esta se cerrará automaticamente al salir.)");
				break;
				
				case 2:
					//Se crea el gráfo con relaciones de solo más de 
					//6 Correos
				GraphExplore2 g2= new GraphExplore2();
					//relaciones con más de 6 correos
					System.out.println(" ");
					String masD6= "Relaciones con más de 6 correos \n";
					//Se seleccionan todas las relaciones que tienen más de 6 correos 
					for(int j=0; j<vectorId.size(); j++){
						if(vectorCant.get(j)>6){
							masD6= masD6 + db.getRelationshipById(vectorId.get(j)).getStartNode().getProperty("nombre");
							masD6= masD6 + " envió "+ vectorCant.get(j) + " correos a: ";
							masD6= masD6 + db.getRelationshipById(vectorId.get(j)).getEndNode().getProperty("nombre");
							masD6= masD6 + "\n";
						}}
					//Se imprimen todas las relaciones 
					System.out.println(masD6);
					System.out.println("Para ver el grafo vea la ventana que acaba de abirse. \n (No la cierre, esta se cerrará automaticamente al salir.)");
				break;
				
				case 3: 
				//eliminar relaciones de autoenvío 
				for(int k=0; k<vectorId.size(); k++)
				{
					//Se buscan todas las relaciones que empiecen y terminen en el mismo nodo
					Relationship r= db.getRelationshipById(vectorId.get(k));
					if (r.getStartNode() == r.getEndNode())
					{
						System.out.println(r.getId());
						System.out.println(r.getStartNode().getProperty("nombre")+" a "+ r.getEndNode().getProperty("nombre"));
						r.delete();
					}
				}
				System.out.println("Se han eliminado las relaciones de autoenvío exitosamente");
				break;
			
				case 4:
				//Implementacion de algoritmo "PageRank"
				Vector<Double> PR1= new Vector<Double>();
				Vector<Double> C= new Vector<Double>();
				for (int l=0; l<14; l++)
				{
					PR1.add(l, (double)db.getNodeById(l).getDegree(Direction.INCOMING));
					//System.out.println("PR1 "+l+": "+PR1.get(l));
					C.add(l, (double)db.getNodeById(l).getDegree(Direction.OUTGOING));
					//System.out.println("C "+l+": "+C.get(l));
				}
			
				Vector<Double> PR2= new Vector<Double>();
				//Se crean doubles para todos los nodos
				double c1=0,c2=0,c3=0,c4=0,c5=0,c6=0,c7=0,c8=0,c9=0,c10=0,c11=0,c12=0,c13=0,c14=0;
				for (int m=0; m<vectorId.size(); m++)
				{
					Relationship r2= db.getRelationshipById(vectorId.get(m));
					//Se obtiene el último nodo
					int n1= (int) r2.getEndNode().getId();
					//Se  obtiene el primer todo 
					int n2= (int) r2.getStartNode().getId();
					//Cada vez que un nodo sea el nodo final, se agregará 
					// Un valor de relevancia para el Page Rank
					if (n1==0)
						c1=c1+((PR1.get(n2))/(C.get(n2)));
					if (n1==1)
						c2=c2+((PR1.get(n2))/(C.get(n2)));
					if (n1==2)
						c3=c3+((PR1.get(n2))/(C.get(n2)));
					if (n1==3)
						c4=c4+((PR1.get(n2))/(C.get(n2)));
					if (n1==4)
						c5=c5+((PR1.get(n2))/(C.get(n2)));
					if (n1==5)
						c6=c6+((PR1.get(n2))/(C.get(n2)));
					if (n1==6)
						c7=c7+((PR1.get(n2))/(C.get(n2)));
					if (n1==7)
						c8=c8+((PR1.get(n2))/(C.get(n2)));
					if (n1==8)
						c9=c9+((PR1.get(n2))/(C.get(n2)));
					if (n1==9)
						c10=c10+((PR1.get(n2))/(C.get(n2)));
					if (n1==10)
						c11=c11+((PR1.get(n2))/(C.get(n2)));
					if (n1==11)
						c12=c12+((PR1.get(n2))/(C.get(n2)));
					if (n1==12)
						c13=c13+((PR1.get(n2))/(C.get(n2)));
					if (n1==13)
						c14=c14+((PR1.get(n2))/(C.get(n2)));
				}
				PR2.addElement(c1);
				PR2.addElement(c2);
				PR2.addElement(c3);
				PR2.addElement(c4);
				PR2.addElement(c5);
				PR2.addElement(c6);
				PR2.addElement(c7);
				PR2.addElement(c8);
				PR2.addElement(c9);
				PR2.addElement(c10);
				PR2.addElement(c11);
				PR2.addElement(c12);
				PR2.addElement(c13);
				PR2.addElement(c14);
				//System.out.println(c1 +"\n"+ c2 +"\n"+ c3 +"\n"+c4 +"\n"+c5 +"\n"+c6 +"\n"+c7 +"\n"+c8 +"\n"+c9 +"\n"+c10 +"\n"+c11 +"\n"+c12 +"\n"+c13 +"\n"+c14 +"\n");
				
				//Conversion y almacenamiento en un nuevo vector
				Vector<Double> PR3= new Vector<Double>();
				for (int p=0; p<14; p++)
				{
					double p2;
					p2= (PR2.get(p))*0.85;
					p2= 0.15+p2;
					PR3.add(p2);
					//System.out.println("PR3 "+p+": "+p2);
				}
			
				Vector<Integer> PR4= new Vector<Integer>();
				for (int i=0; i<14; i++)
				{
					double b= PR3.get(0);
					int k=-1;
					for(int j=0; j<PR3.size(); j++)
					{
						//System.out.println("it"+j+" k= "+k);
						double d= PR3.get(j);
						//System.out.println("b="+b+" d="+d);
						//Se ordenan los nodos en base a quien tiene el valor de relevancia más alto
						if (d>=b)
						{
							b=d;
							k=j;
						}
					}
					PR4.add(k);
					PR3.set(k,0.0);
				}
				System.out.println(" ");
				System.out.println("Personas ordenadas según métrica Page Rank:");
				//Se imprimen en orden los nodos
				for (int i=0; i<14; i++)
				{
					System.out.println(" Per"+(PR4.get(i)+1));
				}
			
				break;
				
				case 5:
				//Funcion para mostrar los Nodos más y menos conectados
				int[] grados= new int[14];
				
				for (int i=0; i<14; i++)
				{
					//Se obtiene al grado de cada nodo
					grados[i]=db.getNodeById(i).getDegree(Direction.BOTH);
				}
				int[] orden= new int[14];
				//Se crea un array donde se colocaran en orden descendente
				int m=-1;
				for (int i=0; i<14; i++)
				{
					int b1=0;
					for(int j=0; j<14; j++)
					{
						int d= grados[j];
						//System.out.println("b1= "+b1+" d= "+d);
						if (d>=b1)
						{
							b1=d;
							m=j;
						}
					}
					//System.out.println("m= "+m);
					orden[i]=m;
					//System.out.println(grados[m]);
					grados[m]=0;
				}
				System.out.println(" ");
				System.out.println("Personas ordenadas de la más comunicada a la menos comunicada:");
				//Se imprime el nombre de las personas segun el array ya ordenado
				for(int z=0; z<14; z++)
				{
					System.out.println(" Per"+ (orden[z]+1));
				}
				break;
				
				
				case 6:
				//Djistra
				//Funcion para ver la cantida de correos entre una persona y otra o todas
				System.out.println(" ");
				int indNodo;
				int fNodo;
				//Se pide el ingreso de la persona origen de la búsqueda 
				System.out.println("Ingrese el número de la persona origen: ");
				/*while (scan.nextInt()>14 || scan.nextInt()==0)
				{
					System.out.println("Debe ingresar un número del 1 al 14");
					System.out.println("Ingrese el número de la persona origen: ");
				}*/
				indNodo= scan.nextInt()-1;
				int iNodo=indNodo;
				//Se pide el ingreso de la persona "Final" en la busqueda
				System.out.println("Ingrese el número de la persona destino (para mostrar todas las relaciones ingrese 0): ");
				/*while (scan.nextInt()>14)
				{
					System.out.println("Debe ingresar un número del 0 al 14");
					System.out.println("Ingrese el número de la persona destino (para mostrar todas las relaciones ingrese 0): ");
				}*/
				fNodo= scan.nextInt() -1;
				
				//Se crean arrays para poder almacenar valores
				long[] nodos= {9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999,9999};
				boolean[] temporales={false,false,false,false,false,false,false,false,false,false,false,false,false,false};
				int[] desde= new int[14];
			
				nodos[indNodo]=0;
				desde[indNodo]=indNodo;
				//temporales[indNodo]=true;
			
				for(int z=0; z<14; z++)
				{
					//establecer nodos temporales
					for(int i=0; i<vectorId.size(); i++)
					{
						//System.out.println("LOOP "+i);
						//System.out.println("indNodo: "+indNodo);
						Relationship r3= db.getRelationshipById(vectorId.get(i));
						int nA= (int)r3.getStartNode().getId();
						int nB= (int)r3.getEndNode().getId();
						long l1= (long) r3.getProperty("cant");
						//Se almacena temporalmente todas las relaciones entre los nodos
						if(nA==indNodo)
						{
							if (nodos[nB]>(nodos[indNodo]+l1))
							{
								nodos[nB]=nodos[indNodo]+l1;
								desde[nB]=indNodo;
							}
						}
					}
					// Se encuentra el menor de los nodos temporales
					long b= 10000;
					int k=-1;
					for(int j=0; j<nodos.length; j++)
					{
						//System.out.println("it"+j+" k= "+k);
						long d= nodos[j];
						//System.out.println("b="+b+" d="+d);
						if(temporales[j]==false)
						{
							if (d<=b)
							{
								b=d;
								k=j;
							}
						}
					}
					//System.out.println("k= "+k);
					
					// establecer minimo como fijo
					temporales[k]=true;
					// cambiar nodo de partida
					indNodo=k;		
					
				}
			
				/*for (int i=0; i<14; i++)
				{
					System.out.println(nodos[i]+" "+desde[i]+" "+ temporales[i]);
				}*/
			
				//Comienzan los dos casos
				
				if (fNodo==-1)
				{
					//para todos los nodos
					for (int i=0; i<14; i++)
					{
						long cco= nodos[i];
						if(cco==9999)
							System.out.println("Per"+(iNodo+1)+" no le ha enviado correos a Per"+(i+1));
						else	
							System.out.println("La cantidad mínima de correos enviados de Per"+(iNodo+1)+" a Per"+(i+1)+" es: "+cco);
					}
					
				}
				else{
				//dado un nodo destino 
				long cor=nodos[fNodo];
				//Si la correlacion nunca fue modificada
				if(cor==9999)
					System.out.println("Per"+(iNodo+1)+" no le ha enviado correos a Per"+(fNodo+1));
				else
					//Caso contrario
					System.out.println("La cantidad mínima de correos enviados de Per"+(iNodo+1)+" a Per"+(fNodo+1)+" es: "+cor);
				}
				break;
				
				
				//Salida dle menu
				case 7:
					System.out.println("");
					System.out.println("¡Gracias por usar el programa!");
					tx.success();
					System.exit(0);
					break;
			}}
			catch(Exception e)
			{
				System.out.println("Seleccione una opcion correcta");
				System.out.println("");
				scan.nextLine();
			}
		}
		//tx.success();
	}

}
}
