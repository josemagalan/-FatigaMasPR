package com.example.tfg_fatigapr

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tfg_fatigapr.clasesDatos.Serie

class AdaptadorSeries(private val myDataset: MutableList<Serie>) :
    RecyclerView.Adapter<AdaptadorSeries.MyViewHolder>() {
    var db:RoomDataBase?=null
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        var peso:EditText = view.findViewById(R.id.edittext_peso)
        var repeticiones:EditText = view.findViewById(R.id.edittext_repeticiones)
        var rpe:EditText = view.findViewById(R.id.edittext_RPE)
        var id:TextView = view.findViewById(R.id.id_serie)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.id.text=(position+1).toString()
        holder.peso.setText(myDataset[position].peso.toString())
        db = RoomDataBase.getInstance(holder.peso.context)


        holder.peso.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString()!="")
                    db!!.serieDAO().actualizarPeso(p0.toString().toInt(),myDataset[position].dia,myDataset[position].idEjercicio,myDataset[position].id)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
        holder.repeticiones.setText(myDataset[position].reps.toString())
        holder.repeticiones.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString()!="")
                    db!!.serieDAO().actualizarReps(p0.toString().toInt(),myDataset[position].dia,myDataset[position].idEjercicio,myDataset[position].id)
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
        holder.rpe.setText(myDataset[position].RPE.toString())
        holder.rpe.addTextChangedListener(object:TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString()!="")
                    db!!.serieDAO().actualizarRPE(p0.toString().toInt(),myDataset[position].dia,myDataset[position].idEjercicio,myDataset[position].id)

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        })
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.series, parent, false) as View
        return MyViewHolder(view)
    }
    override fun getItemCount(): Int {
        return myDataset.size
    }

    fun removeAt(position: Int) {
        db!!.serieDAO().eliminarSerie( myDataset[position])
        myDataset.removeAt(position)
        notifyItemRemoved(position)
    }

}