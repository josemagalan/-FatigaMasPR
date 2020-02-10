package com.example.tfg_fatigapr

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.tfg_fatigapr.clasesDatos.Ejercicio
import com.example.tfg_fatigapr.clasesDatos.Serie
import kotlinx.android.synthetic.main.nav_header_main.*


class AnadirEjercicio : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    //lateinit var usuario: Usuario
    lateinit var dia:String
    lateinit var nombreEjercicio:Button
    lateinit var nomEjercicio:String
    lateinit var nomModificacion:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_anadir_ejercicio)
        nombreEjercicio=findViewById(R.id.bt_nuevoEj)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        dia=intent.getStringExtra("dia")!!

    }


    fun seleccionarEjercicio(view:View){
        val ejercicios =
            arrayOf("Banca","Sentadilla","Peso Muerto","Otro")
        //Mejora de ociones
        val modificacionesBanca=arrayOf("Pines","2ct Parada","3ct Parada","Agarre Cerrado","Agarre Abierto","T&G","Sin modificaciones")
        val modificacionesMuerto=arrayOf("Deficit","2ctParada","3ct Parada","Sumo","Convencional","Rack","Semirigido","Bandas","Cadenas")
        val modificacionesSentadilla=arrayOf("2ct Parada","Pines","3ct Parada","Barra Alta","Barra Baja","Cadenas","Tempo")
        val builderEjercicio: AlertDialog.Builder = AlertDialog.Builder(this)
        builderEjercicio.setTitle("Elige un ejercicio")
        builderEjercicio.setIcon(R.drawable.ic_fitness_center_black_10dp)
        val builderModificaciones: AlertDialog.Builder = AlertDialog.Builder(this)
        builderModificaciones.setTitle("Elige una modificacion")
        builderEjercicio.setItems(ejercicios) { dialog, ejercicio ->
            // the user clicked on colors[which]
            nombreEjercicio.text=ejercicios[ejercicio]
            nomEjercicio=ejercicios[ejercicio]
            when(ejercicio){
                0->{builderModificaciones.setItems(modificacionesBanca) { dialogB, opcionBanca ->
                    // the user clicked on colors[which]
                    nombreEjercicio.text=getString(R.string.nombre_ejercicio_modificaciones,nombreEjercicio.text.toString(),modificacionesBanca[opcionBanca])
                    nomModificacion=modificacionesBanca[opcionBanca]}
                    builderModificaciones.show()}
                1->{builderModificaciones.setItems(modificacionesSentadilla) { dialogB, opcionsentadilla ->
                    // the user clicked on colors[which]
                    nombreEjercicio.text=getString(R.string.nombre_ejercicio_modificaciones,nombreEjercicio.text.toString(),modificacionesSentadilla[opcionsentadilla])
                    nomModificacion=modificacionesSentadilla[opcionsentadilla]}
                    builderModificaciones.show()}
                2->{builderModificaciones.setItems(modificacionesMuerto) { dialogB, opcionMuerto ->
                    // the user clicked on colors[which]
                    nombreEjercicio.text=getString(R.string.nombre_ejercicio_modificaciones,nombreEjercicio.text.toString(),modificacionesMuerto[opcionMuerto])
                    nomModificacion=modificacionesMuerto[opcionMuerto]}
                    builderModificaciones.show()}
            }
        }
        builderEjercicio.show()

    }

    fun añadirEj(view: View){
        val db= RoomDataBase.getInstance(this)!!
        val st=getString(R.string.key_editpref_nombre)
        val str=sharedPreferences.getString(st,"")!!
        val usuarioDao=db.usuariosDAO()
        usuario = usuarioDao.seleccionarusuario(str)
        val diaEncontrado=usuario.getDia(dia)
        if(diaEncontrado!=null){
            val dias=usuario.addEjercicio(dia, Ejercicio(0,nombreEjercicio.text.toString(),"",
                mutableListOf(Serie(0,0,0,0)))
            )

        }else{
            usuario.dia.add(
                Dia(dia, mutableListOf(Ejercicio(0,nombreEjercicio.text.toString(),"",
                mutableListOf(Serie(0,0,0,0)))))
            )

        }
        usuarioDao.actualizarDias(usuario.nombre,usuario.dia)
        finish()
    }
}
