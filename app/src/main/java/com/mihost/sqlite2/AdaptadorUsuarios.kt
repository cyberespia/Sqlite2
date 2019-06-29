package com.mihost.sqlite2

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_agregar_usuarios.view.*
import kotlinx.android.synthetic.main.fila_usuario_edicion.view.*
import kotlinx.android.synthetic.main.fila_usuarios.view.*

class AdaptadorUsuarios(context: Context, val listaUsuarios: ArrayList<Usuarios>) : RecyclerView.Adapter<MiHolder>() {

val context = context

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MiHolder {

        val layoutInflater = LayoutInflater.from(p0.context)
        val cellForRow = layoutInflater.inflate(R.layout.fila_usuarios, p0, false)
        return MiHolder(cellForRow)


    }

    override fun getItemCount(): Int {

        return listaUsuarios.count()

    }

    override fun onBindViewHolder(p0: MiHolder, p1: Int) {

        //p1 es la posicion

        val datofila = listaUsuarios.get(p1)

        p0.vista.usuarioTxt.text = datofila.nombreUsuario

        //boton borrar

        p0.vista.borrarBtn.setOnClickListener {

            //Toast.makeText(context, "presiono borrar", Toast.LENGTH_LONG).show()

            val nombreU = datofila.nombreUsuario
            var alertDialog = AlertDialog.Builder(context)
                .setTitle("Aviso")
                .setMessage("Seguro de que desea borrar: $nombreU")
                .setPositiveButton("Si", DialogInterface.OnClickListener { dialog, which ->

                    if (MainActivity.handler.borrarUsuario(datofila.idUsuario)) {

                        listaUsuarios.removeAt(p1)
                        notifyItemRemoved(p1)
                        notifyItemRangeChanged(p1, listaUsuarios.size)

                        Toast.makeText(context, "Usuario: $nombreU borrado", Toast.LENGTH_LONG).show()


                    } else {
                        Toast.makeText(context, "Error al borrar", Toast.LENGTH_LONG).show()

                    }
                })

                .setNegativeButton("No", DialogInterface.OnClickListener{dialog, which ->})
                .setIcon(R.drawable.ic_warning_black_24dp)
                .show()


                }


        //boton actualizar

        p0.vista.editarBtn.setOnClickListener {

            val layoutInflater = LayoutInflater.from(context)
            val vistaEdit = layoutInflater.inflate(R.layout.fila_usuario_edicion, null)


            println("=======update =====")
            println(datofila.nombreUsuario)
            println(datofila.passwordUsuario)
            println(datofila.nivelUsuario)
            println(datofila.idUsuario)


            vistaEdit.usuarioAct.text = Editable.Factory.getInstance().newEditable(datofila.nombreUsuario)
            vistaEdit.passwordAct.text = Editable.Factory.getInstance().newEditable(datofila.passwordUsuario)
            //vistaEdit.nivelAct.text = Editable.Factory.getInstance().newEditable(datofila.nivelUsuario).toString()





            vistaEdit?.nivelAct?.setText(datofila.nivelUsuario.toString())


            //cellForRow.usuarioAct.setText(datofila.nombreUsuario)
            //cellForRow.passwordAct.setText(datofila.passwordUsuario)
            //cellForRow.nivelAct.setText(datofila.nivelUsuario)


            val builder = AlertDialog.Builder(context)
                .setTitle("Actualizar usuario")
                .setView(vistaEdit)
                .setPositiveButton("Actualizar") { dialog, which ->

                    val isUpdate: Boolean = MainActivity.handler.actualizarUsuario(
                        datofila.idUsuario.toString(),
                        vistaEdit.usuarioAct.toString(),
                        vistaEdit.passwordAct.toString(),
                        vistaEdit.nivelAct.toString()
                    )

                    if (isUpdate == true) {


                        // FALLA LA EDICION AL GUARDAR FALATA REVISAR

                        //cellForRow.usuarioAct.text.toString()

            /*                    cellForRow.usuarioAct.setText(datofila.nombreUsuario)
                                cellForRow.passwordAct.setText(datofila.passwordUsuario)
                                cellForRow.nivelAct.setText(datofila.nivelUsuario)*/

                        //cellForRow.usuarioAct.setText("xxxx")
                        //listaUsuarios[p1].passwordUsuario = cellForRow.passwordAct.text.toString()
                        //cellForRow.nivelAct.setText(datofila.nivelUsuario)


                        listaUsuarios[p1].nombreUsuario = vistaEdit.usuarioAct.text.toString()
                        listaUsuarios[p1].passwordUsuario= vistaEdit.passwordAct.text.toString()
                        listaUsuarios[p1].nivelUsuario= vistaEdit.nivelAct.text.toString().toInt()

                        notifyDataSetChanged()

                        Toast.makeText(context, "Actualizacion correcta", Toast.LENGTH_LONG).show()


                    } else {
                        Toast.makeText(context, "Error al actualizar", Toast.LENGTH_LONG).show()

                    }
                }

                .setNegativeButton("Cancel", DialogInterface.OnClickListener{dialog, which ->})
                val alerta: AlertDialog = builder.create()
                alerta.show()






        }



        }




}





class MiHolder(val vista: View):  RecyclerView.ViewHolder(vista) {






}