package com.example.proyectobuscaminas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Declaración de elementos
    int tablero[][];
    TableLayout tl;
    int contadorMinas = 0;
    int minas = 10;
    int filas = 8;
    int n2,n;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tl = findViewById(R.id.tl);
        crearTablero(minas,filas);

    }

    //Sobreescribo el método para la creación de menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubuscaminas, menu);
        return true;
    }

    //Sobreescribo el método para la selección de item menú
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //Recojo en la variable num, el id del item seleccionado
        int num = item.getItemId();
        //Utilizo un switch elegir que hacer según el el item pulsado
        switch (num) {
            //Si el botón pulsado es el id1, es decir es el icono de las instrucciones
            case R.id.i1:
                //Utilizo AlertDialog.builder para mostrar una ventana de alerta, mostrando las instrucciones
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //Guardo el mensaje que deseo mostrar
                builder.setMessage("El juego consiste en despejar todas las casillas de una pantalla que no oculten una mina" +
                        "\n -Los números indican las minas en las casillas de alrededor." +
                        "\n -Si se descubre alguna casilla sin número indica que no hay minas alrededor." +
                        "\n -Se puede poner una bandera en la casilla que el jugador crea que hay una mina." +
                        "\n -Si descubres una casilla con una mina, ¡Has perdido!");
                //Para guardar el botón de confirmar
                builder.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    //Cuando el botón se hace click asigno lo que debe de hacer
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Utilizo el Toast para mostrar ¡Reglas controladas! hacia alguna interacción realizada por el usuario, como es darle al botón de confirmar.
                        Toast.makeText(MainActivity.this, "¡Reglas controladas!", Toast.LENGTH_LONG).show();
                    }
                });
                builder.create().show();
                return true;
            case R.id.i2:
                crearTablero(minas,filas);
                //Si el botón pulsado es el id3, es decir el menuitem con titulo principiante
            case R.id.i3:
                //Creo un tablero con 8 filas y 10 minas
                crearTablero(10, 8);
                return true;
            //Si el botón pulsado es el id4, es decir el menuitem con titulo intermedio
            case R.id.i4:
                //Creo un tablero con 8 filas y 10 minas
                crearTablero(30, 12);
                return true;
                //Si el botón pulsado es el id5, es decir el menuitem con titulo avanzado
            case R.id.i5:
                //Creo un tablero con 8 filas y 10 minas
                crearTablero(60, 16);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    /**
     * Método para crear el tableto del buscaminas
     * @param minas número de minas que hay en el tablero
     * @param filas número de filas que tendrá el tablero
     */
    @SuppressLint("ResourceAsColor")
    public void crearTablero(int minas, int filas) {
        tablero=new int[filas][filas];
        //Creo las minas en el tablero
        crearMinas(minas,filas);
        //Borro tablero anterior
        tl.removeAllViews();
        for(int i=0;i<filas;i++){
            //Creamos fila
            TableRow fila=new TableRow(getApplicationContext());
            fila.setBackgroundColor(R.color.black);
            // Creamos parámetros
            TableLayout.LayoutParams tlFila=new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,TableLayout.LayoutParams.MATCH_PARENT);
            tlFila.weight = 1;

            //Asignamos parámetros
            fila.setLayoutParams(tlFila);

            for(int j=0;j<filas;j++) {
                //Si el tablero es -1, es decir es una mina
                if (tablero[i][j] == -1) {
                    // Creamos botón
                    ImageButton boton = new ImageButton(getApplicationContext());
                    boton.setId(Integer.parseInt("10"+i+j));
                    //Ajustes de tamaño para que la imagen no coga toda la pantalla
                    boton.setAdjustViewBounds(true);
                    boton.setMaxHeight(1);
                    boton.setMaxWidth(1);
                    //Si hacemos click en el botón llamo al método econtrar mina
                    boton.setOnClickListener(view -> {
                        if (boton.isEnabled()) {
                            encontrarMina(boton);

                        }

                    });
                    //Si hacemos clic largo en el botón ponemos una bandera
                    int finalI2 = i;
                    int finalJ2 = j;
                    boton.setOnLongClickListener(view -> {
                        if(boton.isEnabled()){
                           boton.setImageResource(R.drawable.banderanegra);
                            boton.setEnabled(false);
                            if (tablero[finalI2][finalJ2] == -1){
                                contadorMinas++;
                                Toast.makeText(getApplicationContext(), "HAS ACERTADO!!Hay una mina, llevas "+contadorMinas+" minas acertadas, sigue así \uD83D\uDE0A", Toast.LENGTH_LONG).show();
                            if (contadorMinas==minas){
                                //Utilizo AlertDialog.builder para mostrar una ventana de alerta, mostrando las instrucciones
                                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                //Guardo el mensaje que deseo mostrar
                                builder.setMessage("¡¡¡HAS GANADO !!! ENHORABUENA, ERES UNA MÁQUINA\uD83D\uDE0A");
                                //Para guardar el botón de confirmar
                                builder.setPositiveButton("Jugar de nuevo", new DialogInterface.OnClickListener() {
                                    //Cuando el botón se hace click en jugar de nuevo asigno lo que debe de hacer, crear un tablero
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        crearTablero(minas,filas);
                                    }
                                });
                                builder.create().show();
                            }
                            }

                        }
                        return true;
                    });
                            //Creamos parámetros
                    TableRow.LayoutParams tlBoton = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
                    tlBoton.weight = 1;

                    //Asignamos parámetros
                    boton.setLayoutParams(tlBoton);

                    //Añadir botón a fila
                    fila.addView(boton);

                }else{
                    //Creo un botón
                    Button boton=new Button(getApplicationContext());
                    boton.setId(Integer.parseInt("10"+i+j));
                    int finalI = i;
                    int finalJ = j;
                    //Si hago click en el botón llamo al método mostrar números
                    boton.setOnClickListener(view -> {
                        if (boton.isEnabled()) {
                           ponerNumeros(finalI, finalJ,filas);
                        }
                    });
                    //Si hago click largo en el botón marco una bandera en ese botón
                    int finalI1 = i;
                    int finalJ1 = j;
                    boton.setOnLongClickListener(view -> {
                        if(boton.isEnabled()) {
                            boton.setText("\uD83C\uDFF4");
                            boton.setEnabled(false);
                                    if (tablero[finalI1][finalJ1] != -1) {
                                        //Utilizo AlertDialog.builder para mostrar una ventana de alerta, mostrando las instrucciones
                                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                                        //Guardo el mensaje que deseo mostrar
                                        builder.setMessage("¡¡¡HAS PERDIDO \uD83D\uDE1E\uD83D\uDE1E!!! Has puesto una bandera  y no hay mina\uD83D\uDE1E");
                                        //Para guardar el botón de confirmar
                                        builder.setPositiveButton("Jugar de nuevo", new DialogInterface.OnClickListener() {
                                            //Cuando el botón se hace click en jugar de nuevo asigno lo que debe de hacer, crear un tablero
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                crearTablero(minas,filas);
                                            }
                                        });
                                        builder.create().show();
                                    }
                                }

                        return true;
                    });


                    // Creamos parámetros
                    TableRow.LayoutParams lpBoton=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
                    lpBoton.weight= 1;

                    //Asignamos parámetros
                    boton.setLayoutParams(lpBoton);
                    fila.addView(boton);
                }
            }

           tl.addView(fila);

        }

        }


    /**
     * Método que recojo la imagen de una mina en el boton y muestro con
     * un AlertDialog que ha perdido al encontrar la mina
     * @param ib
     */
    public void encontrarMina(ImageButton ib){
                ib.setImageResource(R.drawable.bomba2);
                //desactivo el botón
                ib.setEnabled(false);
                //Utilizo AlertDialog.builder para mostrar una ventana de alerta, mostrando las instrucciones
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //Guardo el mensaje que deseo mostrar
                builder.setMessage("¡¡¡HAS PERDIDO \uD83D\uDE1E\uD83D\uDE1E!!! Has descubierto una mina \uD83D\uDE1E");
                //Para guardar el botón de confirmar
                builder.setPositiveButton("Jugar de nuevo", new DialogInterface.OnClickListener() {
                    //Cuando el botón se hace click en jugar de nuevo asigno lo que debe de hacer, crear un tablero
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        crearTablero(minas,filas);
                    }
                });
                builder.create().show();

        }

    /**
     * Método para crear las minas en el tableto
     * @param minas número de minas
     * @param filas número de filas
     */
        public void crearMinas(int minas,int filas){
            //Realizo un bucle con el numero de minas que hay
            for (int i=0;i<minas;i++){
                //Recojo en dos variables dos número random donde irán las minas
                n=(int)(Math.random()*filas-1);
                n2=(int)(Math.random()*filas-1);
                if (tablero[n][n2]!=-1){
                    tablero[n][n2]=-1;
                }else{
                    i--;
                }
            }
            //Hago un bucle para las casillas del tableto que sean distinta de -1, cuenten las minas
            //de su alrededor
            for (int i=0;i<filas;i++) {
                for (int j = 0; j < filas; j++) {
                    if (tablero[i][j]!=-1){
                        tablero[i][j]=contarMinas(i,j, filas);
                    }
                }
            }
        }

    /**
     * Método para contar  las minas de alrededor
     */
    public int contarMinas(int i, int j, int filas) {
        //primero el contador esta a 0
        int contador = 0;
//Voy recorriendo por las posiciones del tablero y le añado al contador
        //si es igual a -1, eso quiere decir que hay mina
        if (i==0 && j!=0 && j!=filas-1) {
            if(tablero[i][j-1]==-1)contador++;
            if(tablero[i][j+1]==-1)contador++;
            if(tablero[i+1][j-1]==-1)contador++;
            if (tablero[i+1][j]==-1)contador++;
            if(tablero[i+1][j+1]==-1)contador++;
        }
        if(i==0 && j==filas-1){
            if (tablero[i][j - 1] == -1) contador++;
            if (tablero[i + 1][j] == -1) contador++;
            if (tablero[i + 1][j - 1] == -1) contador++;
        }
        if(j==0 && i!=0 && i!=filas-1){
            if(tablero[i+1][j]==-1)contador++;
            if(tablero[i][j+1]==-1)contador++;
            if(tablero[i-1][j+1]==-1)contador++;
            if (tablero[i-1][j]==-1)contador++;
            if(tablero[i+1][j+1]==-1)contador++;
        }

        if(i!=0 && i!=filas-1 && j!=0&&j!=filas-1){
            if(tablero[i-1][j-1]==-1)contador++;
            if(tablero[i-1][j]==-1)contador++;
            if(tablero[i-1][j+1]==-1)contador++;
            if (tablero[i][j-1]==-1)contador++;
            if(tablero[i][j+1]==-1)contador++;
            if(tablero[i+1][j-1]==-1)contador++;
            if(tablero[i+1][j]==-1)contador++;
            if(tablero[i+1][j+1]==-1)contador++;
        }

        if(i==filas-1&& j==filas-1){
            if (tablero[i-1][j-1]==-1)contador++;
            if (tablero[i-1][j]==-1)contador++;
            if (tablero[i][j-1]==-1)contador++;

        }
        if(j==filas-1 && i!=0 && i!=filas-1){
            if(tablero[i-1][j-1]==-1)contador++;
            if(tablero[i-1][j]==-1)contador++;
            if(tablero[i][j-1]==-1)contador++;
            if (tablero[i+1][j-1]==-1)contador++;
            if(tablero[i+1][j]==-1)contador++;

        }
        if (i==filas-1 && j==0){
            if(tablero[i-1][j]==-1)contador++;
            if(tablero[i-1][j+1]==-1)contador++;
            if(tablero[i][j+1]==-1)contador++;
        }
        if (i==filas-1 && j!=0 && j!=filas-1){
            if(tablero[i-1][j-1]==-1)contador++;
            if(tablero[i-1][j]==-1)contador++;
            if(tablero[i-1][j+1]==-1)contador++;
            if (tablero[i][j-1]==-1)contador++;
            if(tablero[i][j+1]==-1)contador++;


        }
        if (i==0 && j==0) {

            if (tablero[i][j + 1] == -1) contador++;
            if (tablero[i + 1][j] == -1) contador++;
            if (tablero[i + 1][j + 1] == -1) contador++;
        }
//Devuelvo el contador
        return contador;
    }

    /**
     *Método para mostrar número que dentro de él llamo al método recorrerCasillas
     * @param i
     * @param j
     */
    @SuppressLint("ResourceAsColor")
    public void ponerNumeros(int i, int j, int filas){
        //Recojo el id del boton
            Button b=findViewById(Integer.parseInt("10"+i+j));
            //Si el tablero el igual a 0 y esta pulsado
            if (tablero[i][j]==0 && b.isEnabled()){
                b.setEnabled(false);
                recorrerCasillas(i,j,filas);
            }else if(tablero[i][j]!=0){
                b.setText(""+tablero[i][j]);
                b.setTextSize(8);
                b.setEnabled(false);
                b.setTextColor(R.color.black);


            }else{
                b.setEnabled(false);
             }

    }




    /**
     * Método para recorrer las casillas
     * @param i
     * @param j
     */
    public void recorrerCasillas(int i,int j, int filas){
        //Recorro las posiciones del tablero y pongo los números
            if (i==0 &&j==0){
               ponerNumeros(i+1,j+1,filas);
                ponerNumeros(i,j+1,filas);
                ponerNumeros(i+1,j,filas);
            }
            if(i==0&& j!=0 &&j!=filas-1){
                ponerNumeros(i,j-1,filas);
                ponerNumeros(i,j+1,filas);
                ponerNumeros(i+1,j-1,filas);
                ponerNumeros(i+1,j,filas);
                ponerNumeros(i+1,j,filas);
            }
            if(i==0 &&j==filas-1){
                ponerNumeros(i,j-1,filas);
                ponerNumeros(i+1,j-1,filas);
                ponerNumeros(i+1,j,filas);
            }
            if (j==0&&i!=0&&i!=filas-1){
                ponerNumeros(i-1,j,filas);
                ponerNumeros(i-1,j+1,filas);
                ponerNumeros(i,j+1,filas);
                ponerNumeros(i+1,j,filas);
                ponerNumeros(i+1,j+1,filas);

            }
            if (j==filas-1&& i!=0&&i!=filas-1){
                ponerNumeros(i-1,j-1,filas);
                ponerNumeros(i-1,j,filas);
                ponerNumeros(i,j-1,filas);
                ponerNumeros(i+1,j-1,filas);
                ponerNumeros(i+1,j,filas);
            }
            if(i==filas-1&&j==filas-1){
                ponerNumeros(i-1,j-1,filas);
                ponerNumeros(i-1,j,filas);
                ponerNumeros(i,j-1,filas);
            }
            if (i!=0&&i!=filas-1&&j!=0&&j!=filas-1){
                ponerNumeros(i-1,j-1,filas);
                ponerNumeros(i-1,j,filas);
                ponerNumeros(i-1,j+1,filas);
                ponerNumeros(i,j+1,filas);
                ponerNumeros(i,j-1,filas);
                ponerNumeros(i+1,j-1,filas);
                ponerNumeros(i+1,j,filas);
                ponerNumeros(i+1,j+1,filas);
            }
        }


}

